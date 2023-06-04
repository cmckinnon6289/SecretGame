package zork;
import java.util.ArrayList;

public class Lantern extends Item {
    public Lantern(int weight, String name, boolean isOpenable, Room location){
        super(weight, name, isOpenable, location);
    }

    public void useLantern() {
        System.out.print("The lantern gives off weak light. ");
        ArrayList<Item> items = Item.getRoomItems(Game.getCurrentRoom());
        if (items != null) {
            System.out.print("You see: ");
            items.forEach((item) -> {System.out.print(item+", ");});
            System.out.println("and nothing else.");
        } else 
            System.out.println("You see nothing of value in the room.");
        }
    }