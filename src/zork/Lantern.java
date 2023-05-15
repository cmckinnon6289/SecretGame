package zork;

public class Lantern extends Item {
   boolean isOn;
   Room lanternRoom;
    public Lantern(double weight, String name, boolean isOpenable, boolean isOn, Room currentRoom){
        super(weight, name, isOpenable, currentRoom);
        this.isOn = isOn;
        this.lanternRoom = currentRoom;
    }
    public void use() {
        System.out.print("The lantern gives off weak light. ");
        if (Item.getRoomItems(lanternRoom) != null) {
            System.out.println("Something glimmers in the corner in the room.");
        } else {
            System.out.println("Nothing catches your eye.");
        }
    }
}
