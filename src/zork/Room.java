package zork;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

  private String roomName;
  private String description;
  private Boolean isLocked;
  private CoordKey coords;
  private ArrayList<String> passages;

  public ArrayList<String> getPassages() {
    return passages;
  }

  public void setExits(ArrayList<String> newPassages) {
    this.passages = newPassages;
  }

  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {
    this.description = description;
    passages = new ArrayList<String>();
  }

  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    passages = new ArrayList<String>();
  }

  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String description() {
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
      ex.printStackTrace();
      return null;
    }
  }

  /*
   * private int getDirectionIndex(String direction) { int dirIndex = 0; for
   * (String dir : directions) { if (dir.equals(direction)) return dirIndex; else
   * dirIndex++; }
   * 
   * throw new IllegalArgumentException("Invalid Direction"); }
   */
  public String getRoomName() {
    return roomName;
  }

  public CoordKey getRoomCoords() {
    return coords;
  }

  /**
   * Defines details of the room, including name, description, coordinates, and passageways.
   * @param roomName
   * @param description
   * @param coordsArr
   * @param passageArr
  */

  public void setRoomDetails(String roomName, String description, CoordKey coords, String[] passageArr, Boolean locked){
    this.description = description;
    this.roomName = roomName;
    this.isLocked = locked;
    this.coords = coords;
    for (String p : passageArr) {
      this.passages.add(p);
    }
  }

  public String getDescription() {
    return description;
  }
}
