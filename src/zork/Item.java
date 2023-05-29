package zork;

import java.util.ArrayList;

public class Item extends OpenableObject { 

  public int weight;
  public String name;
  private boolean isOpenable;

  public Item(int weight, String name, boolean isOpenable, Room location) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable; 
    this.place(location);
  } 

  public static ArrayList<Item> getRoomItems(Room t) {
    try {
      return Room.itemsList.get(t);
    } catch (Exception ex) {
      return null;
    }
  }

  public void place(Room key) {
    if (Room.itemsList.containsKey(key)) {
      ArrayList<Item> send = Room.itemsList.get(key);
      send.add(this);
      Room.itemsList.put(key,send);
    } else {
      ArrayList<Item> send = new ArrayList<Item>();
      send.add(this);
      Room.itemsList.put(key,send);
    }
  }

  public void open() {
    if (!isOpenable) {
      if (!isLocked())   
        System.out.println(this.name + " isn't something you can open.");
      else
        System.out.println("The item's key hole catches your eye as you fail to open it.");
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

  public boolean isOpenable() {
    return isOpenable;
  }

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public void setLocation(Room location) {
    Room.itemsList.forEach((room,items) -> {
      if (items.contains(this)) {
        items.remove(this);
      }
    });
    this.place(location);
  }

  @Override
  public String toString() {
    return this.getName();
  }
}

