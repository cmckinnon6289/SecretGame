package zork;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

  private String roomName;
  private String description;
  private Boolean isLocked;
  private String keyID;
  private CoordKey coords; 
  private ArrayList<String> passages;
  private ArrayList<Item> itemsInRoom = new ArrayList<Item>();
  public static HashMap<Room,ArrayList<Item>> itemsList = new HashMap<Room,ArrayList<Item>>();

  public ArrayList<String> getPassages() {
    return passages;
  }

  public void setExits(ArrayList<String> newPassages) {
    this.passages = newPassages;
  }
  
  public void addItem(Item item){ 
    itemsInRoom.add(item);
  }
  // public void setInventory(ArrayList<Item> itemsList){
  //   for (int i = 0; i < itemsList.size(); i++) {
  //     Inventory.addItem(itemsList.get(i));
  //   }
  // }

  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {  
    this.description = description;
    passages = new ArrayList<String>();
    keyID = null;
  }

  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    passages = new ArrayList<String>();
    keyID = null;
  }

  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String description(Room room) {
    if (Game.isConfused) {
      return "Room: " + roomName + "\n\n" + "Nothing makes sense to you.\nThere are "+ passages.size() +" exits in this room.";
    }
    return "Room: " + roomName + "\n\n" + description;
  }

  /**
   * Returns a HashMap with x and y values based on a given direction.
   * @exception IllegalStateException Invalid direction.
   * @return String, Integer HashMap.
   */
  public HashMap<String, Integer> directionParse(String direction) {
    HashMap<String, Integer> vals = new HashMap<>();
    if (direction.equals("north")){
      vals.put("y", 1);
      vals.put("x",0);
    }
    else if (direction.equals("south")){
      vals.put("y", -1);
      vals.put("x",0);
    }
    else if (direction.equals("east")){
      vals.put("x",1);
      vals.put("y",0);
    }
    else if (direction.equals("west")){
      vals.put("x",-1);
      vals.put("y",0);
    }
    else {
      System.out.println("Invalid direction, could not parse direction.");
    }
    return vals;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    try {
      Room test = searchForRoom(direction);
      if (test != null) {
        if (test.isLocked) {
          System.out.println("The door is locked.");
          return null;
        } else
          return test;
      } else {
          return null;
      }
    } catch (Exception ex) {
      System.out.println("Unknown error.");
      return null;
    }
  }

  public Room searchForRoom(String direction) {
    HashMap<String,Integer> dirs = directionParse(direction);
    Boolean valid = false;
    for (String passage : Game.getCurrentRoom().getPassages()) {
      if (direction.equals(passage)) {
        valid = true;
        break;
      }
    } if (valid) {
      CoordKey currCoords = new CoordKey(Game.getCurrentRoom());
      currCoords.addTo("x",dirs.get("x"));
      currCoords.addTo("y",dirs.get("y"));
      Room test = Game.roomMap.get(currCoords);
      return test;
    } else {
      return null;
    }
  }

  public void initRoomItems() {
    Game.roomMap.forEach((key,room) -> {
      try {
        room.itemsInRoom = itemsList.get(room);
      } catch(Exception e) {
        room.itemsInRoom = null;
      }
    });
  }

  public String getRoomName() {
    return roomName;
  }

  public CoordKey getRoomCoords() {
    return coords;
  }

  /**
   * Defines details of the room, including name, description, coordinates, and passageways.
   * @param roomName (String)
   * @param description (String)
   * @param coordsArr (CoordKey)
   * @param passageArr (String[])
   * @param locked (Boolean)
   * @param keyID (String, only if locked)
  */

  public void setRoomDetails(String roomName, String description, CoordKey coords, String[] passageArr, Boolean locked, String key){
    this.description = description;
    this.roomName = roomName;
    this.isLocked = locked;
    this.coords = coords; 
    this.keyID = key;
    for (String p : passageArr) {
      this.passages.add(p);
    }
  }

  public void setRoomItems(ArrayList<Item> items) {
    this.itemsInRoom = items;
  }

  public String getDescription() {
    return description;
  }

  public void unlockRoom(Key key){
    if (!this.isLocked) {
      System.out.println("This room is not locked.");
    } else {
      if (this.getKeyId().equals(key.getKeyId())) {
        this.isLocked = false;
        System.out.println("You hear a click as the lock turns. The door is now unlocked.");
      } else {
        System.out.println("Wrong key.");
      }
    }
  }

  private String getKeyId() {
    return keyID; 
  }

  public static Item takeItemFromRoom(String itemID) {
    ArrayList<Item> roomItems = Game.getCurrentRoom().itemsInRoom;
    Item target = null;
    if (roomItems != null) {
      for (Item item : roomItems) {
        if (item.getName().toLowerCase().equals(itemID)) target = item;
      }
    }
    return target;
  }

  /**
   * Checks to see if there are any instances of monsters in the room. If true, starts a fight and sets {@code Game.fighting} to the monster in the room.
   */
  public void checkForMonsters() {
    if (this.itemsInRoom != null) {
      for (Object item : this.itemsInRoom) {
        if (item instanceof Monster) {
          Game.startFight();
          Game.fighting = (Monster) item;
        }
      }
    }
  }

  public void destroyEntity(Monster target) {
    for (int i = 0; i < this.itemsInRoom.size(); i++) {
      Item currentItem = this.itemsInRoom.get(i);
      if (currentItem instanceof Monster && currentItem.getName().toLowerCase().equals(target.getName().toLowerCase())) {
        this.itemsInRoom.remove(i);
      }
    }
  }
  
  /**
  * Checks to see if there is a lantern in the current room. If true, alerts the player (lanterns are the only way a player can check rooms for items).
  */
  public void lanternCheck() {
    for (Item item : this.itemsInRoom) {
      if (item instanceof Lantern) {
        System.out.println("There is a lantern next to you.");
        break;
      }
    }
  }

  /**
   * Gets the entity in the room matching the specified name.
   * @return Monster
   */
  public Monster getEntity(String name) {
    for (Item item : this.itemsInRoom) {
      if (item instanceof Monster && item.getName().equals(name.toUpperCase())) {
        return (Monster) item;
      }
    }
    return null;
  }

  public void putItemInRoom(Item item) {
    if (this.itemsInRoom != null) {
      this.itemsInRoom.add(item);
    } else {
      ArrayList<Item> items = new ArrayList<Item>();
      items.add(item);
      this.itemsInRoom = items;
      itemsList.put(Game.getCurrentRoom(),items);
    }
  }
}
