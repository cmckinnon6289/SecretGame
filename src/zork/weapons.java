package zork;

public class weapons extends Item {

    private int damage; 
    private String weapon; 

    public weapons(String weapon, int damage, int weight){
        this.weapon = weapon;
        this.damage = damage; 
        this.weight = weight; 
    }

    public int getDamage(){
        return damage;  
    } 

    public String getWeapon(){
        return weapon;   
    } 

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setName(String weapon) {
        this.weapon = weapon;
    }

    // Other methods
    public void attack() {
        System.out.println("Attacking with " + weapon + ", dealing " + damage + " damage!");
    }
    
}
