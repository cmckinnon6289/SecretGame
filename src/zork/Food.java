package zork;

public class Food extends Item{
    int healValue;
    Food(double weight, String name, boolean isOpenable, Room location, int healValue){
        super(weight, name, isOpenable, location);
        this.healValue = healValue;
    }

    public static void eat(int healValue){
        Game.HP += healValue;
    }
}
