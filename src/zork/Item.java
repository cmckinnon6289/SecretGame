package zork;

import java.util.HashMap;
import java.util.ArrayList;

public class Item extends OpenableObject {
  private int weight;
  private String name;
  private boolean isOpenable;
  public static HashMap<Room,ArrayList<Item>> supplyRoster = new HashMap<Room,ArrayList<Item>>();

  public Item(int weight, String name, boolean isOpenable, Room place) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
    Room location = place;
  }

  public Item() {
    this.weight = 0;
    this.name = "DEFAULT ITEM";
    this.isOpenable = false;
    CoordKey t = new CoordKey(0,0);
    Room location = Game.roomMap.get(t);
  }

  public void account(Room key,Item toAdd) {
    if (supplyRoster.containsKey(key)) {
      ArrayList<Item> send = supplyRoster.get(key);
      send.add(toAdd);
      supplyRoster.put(key,send);
    } else {
      ArrayList<Item> send = new ArrayList<Item>();
      send.add(toAdd);
      supplyRoster.put(key,send);
    }
  }

  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be opened.");

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

}
