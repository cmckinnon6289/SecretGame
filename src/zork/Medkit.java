package zork;

public class Medkit extends Item {

    private int healValue;

    public Medkit(int healValue, int weight, String name, boolean isOpenable, Room location) {
        super(weight, name, isOpenable, location);
        this.healValue = healValue;
    }

    public int gethealValue(){
        return healValue; 
    }

    public void useMedkit(){
        if(10 - Game.HP < healValue)
         System.out.println("your health needs to be blow or equals to " + (10 - healValue) + "  use the medkit");
        else 
         Game.HP += healValue; 
    }
    
}
