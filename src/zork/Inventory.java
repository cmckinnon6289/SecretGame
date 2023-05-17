package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  private final int MAXWEIGHT = 100;
  private int currentWeight;

  public Inventory() {
    this.items = new ArrayList<Item>();
    this.currentWeight = getCurrentWeight();
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getCurrentWeight() {
    int weight = 0; 
    for (int i = 0; i < items.size(); i++) {
      Item current = items.get(i); 
      weight += Items.getWeight; 
    }
    return weight;
  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

}
