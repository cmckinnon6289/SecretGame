package zork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {
  public static Monster fighting = null;
  public static boolean inFight = false;
  public static boolean isConfused = false;
  public static boolean finished = false;
  public static int moves = 1;
  public static int HP = 15;
  public static final int MAXHP = 25;
  public static HashMap<CoordKey, Room> roomMap = new HashMap<CoordKey, Room>();
  public static Inventory playerInventory = new Inventory();

  private Parser parser;
  private static Room currentRoom;
  
  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initGame();
      CoordKey initCoords = new CoordKey(0,0);
      currentRoom = roomMap.get(initCoords);
    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

  private void initGame() throws Exception {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      initItems("src\\zork\\data\\items.json");
      roomMap.forEach((key,val) -> {
        val.initRoomItems();
      });
      File bg = new File("src\\zork\\data\\sfx\\background.wav");
      try {
        AudioHandler.loopClip(bg);
      } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void checkHP(){
    if(HP > MAXHP)
      HP = MAXHP;
  }
  private void ambience() {
    int ambNum = (int) (Math.round(Math.random()*25));
    String path = null;
    if (ambNum == 1) {
      path = "src\\zork\\data\\sfx\\thud.wav";
    } else if (ambNum == 9) {
      path = "src\\zork\\data\\sfx\\gunfire.wav";
    } else if (ambNum == 18) {
      path = "src\\zork\\data\\sfx\\ghost.wav";
    } if (path != null) {
      File sfx = new File(path);
      try {
        AudioHandler.playClip(sfx);
      } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void milestoneCheck() {
    final int INC = 14;
    if (moves == INC) {
      System.out.println("-----");
      System.out.println("Your stomach pangs. When was your last meal?");
      System.out.println("-----");
    } else if (moves == INC*3) {
      System.out.println("-----");
      System.out.println("Awareness gives way to lightheadedness. You can barely stand straight.\nYour sense of direction is hazy.");
      System.out.println("-----");
      confusion();
    }
  }

  private void confusion() {
    isConfused = true;
  }

  private void initRooms(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("rooms");

    for (Object roomObj : jsonRooms) {
      Room room = new Room();
      String roomName = (String) ((JSONObject) roomObj).get("name");
      String roomDescription = (String) ((JSONObject) roomObj).get("description");
      CoordKey coords = new CoordKey(((Long) ((JSONObject) roomObj).get("x")).intValue(),((Long) ((JSONObject) roomObj).get("y")).intValue());
      Boolean isLocked = (Boolean) ((JSONObject) roomObj).get("isLocked");
      String keyID = null;
      if (isLocked) {
        keyID = (String) ((JSONObject) roomObj).get("id");
      }
      JSONArray passageJSON =((JSONArray)((JSONObject) roomObj).get("passages"));
      String[] passages = new String[passageJSON.size()];
      for (int i = 0; i < passageJSON.size(); i++) {
        passages[i] = (String) passageJSON.get(i);
      }
      room.setRoomDetails(roomName, roomDescription, coords, passages, isLocked, keyID);
      if (roomMap.containsKey(coords))
        throw new RoomStateException(coords);
      roomMap.put(coords, room);
      room.setRoomItems(Item.getRoomItems(room));
    }
  }

  private void initItems(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);
    JSONArray jsonItems = (JSONArray) json.get("items");

    for (Object itemObj : jsonItems) {
      String itemName = (String) ((JSONObject) itemObj).get("name");
      int weight = ((Long) ((JSONObject) itemObj).get("weight")).intValue(); 
      Boolean openable = (Boolean) ((JSONObject) itemObj).get("openable");
      Boolean isKey = (Boolean) ((JSONObject) itemObj).get("isKey");
      Boolean isMonster = (Boolean) ((JSONObject) itemObj).get("isMonster");
      CoordKey locationKey = new CoordKey(((Long) ((JSONObject) itemObj).get("x")).intValue(),((Long) ((JSONObject) itemObj).get("y")).intValue());
      Room location = roomMap.get(locationKey);
      if (isKey) {
        String keyID = (String) ((JSONObject) itemObj).get("keyID");
        new Key(keyID, itemName, weight, location);
      } else if (isMonster) {
        int hp = ((Long) ((JSONObject) itemObj).get("hp")).intValue(); 
        int attack = ((Long) ((JSONObject) itemObj).get("attack")).intValue();
        new Monster(itemName, hp, attack, location);
      } else if (itemName.toLowerCase().equals("axe") || itemName.toLowerCase().equals("sword")) {
        new Weapon(15,weight,itemName,false,location);
      } else if (itemName.toLowerCase().equals("lantern")) {
        new Lantern(10,itemName,false,location);
      } else {
        new Item(weight,itemName,openable,location);
      }
    }
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printHeadphoneWarning();
    printWelcome();
    final CoordKey exitCoords = new CoordKey(1,-3);
    while (!finished) {
      Command command;
      moves++;
      try {
        getCurrentRoom().checkForMonsters();
        command = parser.getCommand();
        finished = processCommand(command);
        ambience();
        milestoneCheck();
        if (getCurrentRoom().getRoomCoords().equals(exitCoords)) {
          ending();
          finished = true;
        }
        if (inFight) {
          fighting.strikePlayer();
          if (HP <= 0) fighting.killed();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Thank you for playing. Good bye.");
  }

  private void ending() {
    System.out.println("-----------------------------------------------------");
    System.out.println("INCIDENT REPORT");
    System.out.println("-----------------------------------------------------");
    System.out.println("On ██████████, at █████, subject D-9341 was seen");
    System.out.println("breaching the perimeter of Site-19. Subject was");
    System.out.println("apprehended and terminated by ███████ agents.");
    System.out.println("An investigation has begun to search for any");
    System.out.println("explanation as to how subject D-9341 escaped.");
    System.out.println("-----------------------------------------------------");
  }

  private void printHeadphoneWarning() {
      System.out.println("----------");
      System.out.println("For the most immersive experience, we recommend wearing headphones.");
      System.out.println("Please type \"continue\" below when you are ready to proceed.");
      System.out.println("----------");
      Boolean ready = false;
      while (!ready) {
        String confirm = Parser.in.nextLine();
        if (confirm.toLowerCase().equals("continue")) {
          System.out.println("----------");
          ready = true;
        }
        else
          System.out.println("Invalid entry.");
      }
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("You wake up on your side in a concrete fortress. Your eyes are dry and your vision is blurry.");
    System.out.println("You don't know where you are, nor how you got here. You stagger onto your feet and shake.");
    System.out.println("Your stomach whimpers with pain. You hear nothing except it. Then a deafening thud.");
    System.out.println("Your compulsion to move overrides the excruciating pain. You don't know what made that thud,");
    System.out.println("but you don't want it to get any closer to you.");
    System.out.println();
    System.out.println("---------------------------------------------------------------------------------------");
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Welcome to the facility.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.description(currentRoom));
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go") || commandWord.equals("move") || commandWord.equals("run")) { // move between rooms
      if (inFight) {
        System.out.println("You don't have the strength or speed to outrun it.");
      } else {
        goRoom(command);
      }
    } else if (commandWord.equals("quit") || commandWord.equals("exit")) { // quit game
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("eat") && !command.hasSecondWord()) { // eat food to restore hp
      String secondWord = command.getSecondWord();
      if (secondWord.equals("apple") || secondWord.equals("sandwich") || secondWord.equals("bread")) {
        Item food = (Item) playerInventory.getItem(command.getSecondWord());
        if (food != null) {
          Game.HP += secondWord.length();
          Game.checkHP();
          System.out.println("You eat your food, bringing your health up to " + Game.getHP());
          playerInventory.removeItem(food);
        } else System.out.println("You wish you could.");
      } else System.out.println("Not a valid food item.");
    }
    else if ((commandWord.equals("take") || commandWord.equals("grab") || commandWord.equals("get")) && command.hasSecondWord()) { // take an item from a room
      Item item = Room.takeItemFromRoom(command.getSecondWord());
      if (item == null) {
        System.out.println("No such item exists in this room.");
      } else {
        if (playerInventory.getCurrentWeight()+item.getWeight() > Inventory.MAXWEIGHT) {
          System.err.println("Adding this itme to your inventory could incapacitate you. You decide to put it down.");
        } else {
          ArrayList<Item> roomItems = Room.itemsList.get(getCurrentRoom());
          roomItems.remove(item);
          playerInventory.addItem(item);
          System.out.println("You pick up the "+item.getName()+".");
          Room.itemsList.put(getCurrentRoom(),roomItems);
        }
      }
    } else if (commandWord.equals("drop") || commandWord.equals("release") && command.hasSecondWord()) {
      Item item = (Item) playerInventory.getItem(command.getSecondWord());
      if (item == null) System.out.println("Item does not exist.");
      else {
        getCurrentRoom().putItemInRoom(item);
        playerInventory.removeItem(item);
        System.out.println("You let go of the "+command.getSecondWord()+" and watch as it falls to the ground with strange focus.");
      }
    }
    else if(commandWord.equals("jump")) {
      System.out.println("The energy you expended felt like a knife to the chest as it left your body.");
    }
    else if(commandWord.equals("search")) {
       System.out.println("*****");
    }
    else if(commandWord.equals("unlock")) {
      if (command.hasSecondWord()) {
        Room roomToUnlock = currentRoom.searchForRoom(command.getSecondWord());
        if (roomToUnlock == null) System.out.println("Room does not exist.");
        else if (command.hasThirdWord()) {
          Key key = (Key) playerInventory.getItem(command.getThirdWord().toLowerCase());
          if (key == null) System.out.println("Key not found.");
          else roomToUnlock.unlockRoom(key);
          
        } else System.out.println("No key specified.");
      } else System.out.println("No room specified.");
    }
    else if (commandWord.equals("use") && command.hasSecondWord()) {
      String secondWord = command.getSecondWord();
      if (secondWord.equals("lantern")) {
        Lantern lantern = (Lantern) playerInventory.getItem(secondWord);
        if (lantern != null) lantern.useLantern();
        else System.out.println("You don't have a lantern.");
      } else if (secondWord.equals("medkit")) {
        Item medkit = (Item) playerInventory.getItem(secondWord);
        if (medkit != null) {
          HP += 10;
          Game.checkHP();
          System.out.println("You use the medkit. You feel better.\nYour health is now "+Game.getHP()+".");
        }
      } else System.out.println("You cannot use this item.");
    } else if (commandWord.equals("attack")) {
      if (command.hasSecondWord()) {
        Monster target = currentRoom.getEntity(command.getSecondWord().toLowerCase());
        if (command.hasThirdWord() && target != null) {
          Weapon weapon = (Weapon) playerInventory.getItem(command.getThirdWord().toLowerCase());
          if (weapon != null) target.strikeMonster(weapon);
        }
      }
    }
    return false;
  }

  // implementations of user commands:

  private static int getHP() {
    return HP; 
  }

  public static void startFight() {
    inFight = true;
  }

  public static void endFight() {
    inFight = false;
    currentRoom.destroyEntity(fighting);
  }

  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are lost. You are alone. The rooms");
    System.out.println("are forebearing and intimidating.");
    System.out.println(currentRoom.description(currentRoom));
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("In the confusion, you forgot to specify which direction you want to go in.");
      return;
    }
    String direction = command.getSecondWord();
    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);
    if (nextRoom == null)
      System.out.println("You cannot go that way.");
    else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.description(currentRoom));
      System.out.println(getCurrentRoom().getRoomCoords()); // debugging purposes
    }
  }

  public static Room getCurrentRoom() {
    return currentRoom;
  }
} 
