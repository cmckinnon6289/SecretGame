package zork;

public class Food {
    int healValue;
    Food(int healValue){
        this.healValue = healValue;
    }

    public static void eat(int healValue){
        Game.HP += healValue;
    }
}
