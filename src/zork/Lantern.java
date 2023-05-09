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
    
}
