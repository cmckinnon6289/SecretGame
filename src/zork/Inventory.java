package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  public static final int MAXWEIGHT = 50;
  private int currentWeight;

  public Inventory() {
    this.items = new ArrayList<Item>();
    this.currentWeight = getCurrentWeight();
  }

  public int getCurrentWeight() {
    int weight = 0; 
    for (int i = 0; i < items.size(); i++) {
      Item current = items.get(i); 
      weight += current.getWeight();    
    }
    return weight;
  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= MAXWEIGHT)
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }
  public boolean removeItem(Item item){
    if(items.contains(item)){
    return items.remove(item);
    }
    else{
    System.out.println("You do not have that item, so you cannot remove it");
    return false;
    }
  }

  public Object getItem(String name) {
    for (int i = 0; i < items.size(); i++) {
      Object target = items.get(i);
      Item targetItem = (Item) target;
      if (targetItem.name.toLowerCase().equals(name.toLowerCase())) {
        return target;
      }
    }
    return null;
  }

}
