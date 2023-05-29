package zork;

public class Weapon extends Item {

    private int damage; 

    public Weapon(int damage, int weight, String name, boolean isOpenable, Room location){
        super(weight, name, isOpenable, location);
        this.damage = damage; 
        this.weight = weight;  
    }

    public int getDamage(){
        return damage;  
    } 

    public String getWeaponName(){
        return name;   
    } 

    public void attack() {
        System.out.println("Attacking with " + name + ", dealing " + damage + " damage!");
    }
    
}
