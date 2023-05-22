package zork;

public class Food extends Item{

    Food(int weight, String name, boolean isOpenable, Room location, int healValue){
        super(weight, name, isOpenable, location);
    }

    public static void eat(){
        if(Game.HP == 10)
         System.out.println("you are at a full health of 10, your stomach is at full capacity");
        else
         Game.HP++; 
    }
}
