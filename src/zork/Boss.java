package zork;

/**
 * Boss
 */
public class Boss {

    public int hitPoints;
    public int attackPower; 
    public Boss(int hp, int damage){
        this.hitPoints = hp;
        this.attackPower = damage;
    }    
}