package zork;

public class Lantern extends Item {
    private double lanternWeight; 
    private String lanternName; 
    private boolean lanternIsOpenable;
    private Room lanternCurrentRoom;
    Lantern(double weight, String name, boolean isOpenable, Room currentRoom){
        super(weight, name, isOpenable, currentRoom);
        this.lanternWeight = weight;
        this.lanternName = name; 
        this.lanternIsOpenable = isOpenable;
        this.lanternCurrentRoom = currentRoom;
    }
    public void use() {
        System.out.print("The lantern gives off weak light. ");
        if (Item.getRoomItems(lanternCurrentRoom) != null) {
            System.out.println("SOmething glimmers in the corner in the room.");
        } else {
            System.out.println("Nothing catches your eye.");
        }
    }
}
