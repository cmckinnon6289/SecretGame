package zork;
import java.util.ArrayList;

public class Lantern extends Item {

   public boolean isOn;

   public Lantern(int weight, boolean isOn, String name, boolean isOpenable, Room location){
        super(weight, name, isOpenable, location); 
        this.isOn = isOn;
        this.weight = weight; 
        this.name = "lantern";
    }

    public boolean isLanternON(){
        return isOn; 
    }

    public void lightUp(){
        isOn = true; 
    }

    public void useLantern() {
        if(isOn == true){
          System.out.print("The lantern gives off weak light. ");
          ArrayList<Item> items = Item.getRoomItems(Game.getCurrentRoom());
          if (items != null) {
             System.out.print("You see: ");
             items.forEach((item) -> {System.out.println(item+", ");});
             System.out.println("and nothing else.");
          } else 
             System.out.println("You see nothing of value in the room.");
        }
        else if(isOn == false)
          System.out.println("The lantern is not on");
        }
    }

