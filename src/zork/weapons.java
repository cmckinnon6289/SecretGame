package zork;

public class weapons extends Item {

    private int damage; 

    public weapons(int damage, int weight, String name, boolean isOpenable, Room location){
        super(weight, name, isOpenable, location);
        this.damage = damage; 
        this.weight = weight;  
    }

    public int getDamage(){
        return damage;  
    } 

    public String getWeapon(){
        return name;   
    } 

    public void attack() {
        System.out.println("Attacking with " + name + ", dealing " + damage + " damage!");
    }
    
}
