package zork;

public class weapons {
    private int damage;
    private String weapon; 
    public weapons(String weapon, int damage){
        this.weapon = weapon;
        this.damage = damage;
    }
    public static void hit(int damage){
        Boss.hp =- damage; 
    } 
    
}
