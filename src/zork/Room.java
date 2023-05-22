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
  // public void setInventory(HashMap itemsList){
  //   for (int i = 0; i < itemsList.size(); i++) {
  //     Inventory.addItem(itemsList[i])
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
  public String description() {
    return "Room: " + roomName + "\n\n" + description + "\n" + itemsInRoom;
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
      HashMap<String,Integer> dirs = directionParse(direction);
      CoordKey currCoords = new CoordKey(Game.getCurrentRoom());
      currCoords.addTo("x",dirs.get("x"));
      currCoords.addTo("y",dirs.get("y"));
      Room test = Game.roomMap.get(currCoords);
      if (test != null) {
        if (test.isLocked) {
          System.out.println("The door is locked.");
          currCoords.addTo("x",dirs.get("x")*-1);
          currCoords.addTo("y",dirs.get("y")*-1);
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
}
