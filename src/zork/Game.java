package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {
  public static int moves = 1;
  public static int HP = 10;
  
  public static HashMap<CoordKey, Room> roomMap = new HashMap<CoordKey, Room>();

  private Parser parser;
  private static Room currentRoom;

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      milestoneCheck();
      initRooms("src\\zork\\data\\rooms.json");
      CoordKey initCoords = new CoordKey(0,0);
      currentRoom = roomMap.get(initCoords);
    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

  private void milestoneCheck() {
    int increment = 13;
    if (moves == increment) {
      System.out.println("-----");
      System.out.println("Your stomach pangs. When was your last meal?");
      System.out.println("-----");
    } else if (moves == 3 * increment) {
      System.out.println("-----");
      System.out.println("Awareness gives way to lightheadedness. You can barely stand straight.\nYour sense of direction is hazy.");
      System.out.println("-----");
      confusion();
    }
  }

  private void confusion() {
    // make later
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
      JSONArray passageJSON =((JSONArray)((JSONObject) roomObj).get("passages"));
      String[] passages = new String[passageJSON.size()];
      for (int i = 0; i < passageJSON.size(); i++) {
        passages[i] = (String) passageJSON.get(i);
      }

      room.setRoomDetails(roomName, roomDescription, coords, passages, isLocked);
      roomMap.put(coords, room);
    }
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();

    boolean finished = false;
    while (!finished) {
      Command command;
      try {
        command = parser.getCommand();
        finished = processCommand(command);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    System.out.println("Thank you for playing. Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("You wake up on your side in a concrete fortress. Your eyes are dry and your vision is blurry.");
    System.out.println("You don't know where you are, nor how you got here. You stagger onto your feet as best you can.");
    System.out.println("Your stomach whimpers with pain. You hear nothing except it. Then a deafening thud.");
    System.out.println("Your compulsion to move overrides the excruciating pain. You don't know what made that thud,");
    System.out.println("but you don't want it to get any closer to you.");
    System.out.println();
    System.out.println("----------");
    System.out.println();
    System.out.println("Welcome to the maze!");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.description());
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
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    }
    return false;
  }

  // implementations of user commands:

  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are lost. You are alone. The rooms");
    System.out.println("are forebearing and intimidating.");
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
      System.out.println(currentRoom.description());
    }
  }

  public static Room getCurrentRoom() {
    return currentRoom;
  }
} 