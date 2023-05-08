package zork;

import java.util.HashMap;
import java.util.ArrayList;

public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  public static HashMap<Room,ArrayList<Item>> supplyRoster = new HashMap<Room,ArrayList<Item>>();

  public Item(int weight, String name, boolean isOpenable, Room location) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
    this.place(location);
  }

  public Item() {
    this.weight = 0;
    this.name = "DEFAULT ITEM";
    this.isOpenable = false;
    CoordKey t = new CoordKey(0,0);
    this.place(Game.roomMap.get(t));
  }

/**
   * Adds an item to a HashMap of items. Each room with items has an {@code ArrayList<Item>} with all the items in the room. Checks whether the Room already has an entry in the {@code HashMap<Room, ArrayList<Item>> supplyRoster} and reacts accordingly.
   * @param key (Room)
  */

  public void place(Room key) {
    if (supplyRoster.containsKey(key)) {
      ArrayList<Item> send = supplyRoster.get(key);
      send.add(this);
      supplyRoster.put(key,send);
    } else {
      ArrayList<Item> send = new ArrayList<Item>();
      send.add(this);
      supplyRoster.put(key,send);
    }
  }

  public ArrayList<Item> getRoomItems(Room t) {
    try {
      return supplyRoster.get(t);
    } catch (Exception ex) {
      return null;
    }
  }

  public void open() {
    if (!isOpenable) {
      if (!super.isLocked())
        System.out.println(this.name + " isn't something you can open.");
      else
        System.out.println("The key hole catches your eye as you fail to open it.");
    } else {

    }
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isOpenable() {
    return isOpenable;
  }

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public void setLocation(Room location) {
    supplyRoster.forEach((room,items) -> {
      if (items.contains(this)) {
        items.remove(this);
      }
    });
    this.place(location);
  }

}
