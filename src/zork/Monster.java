package zork;

public class Monster extends Item {
    private String name;
    private int health;
    private int attackDamage;
    private boolean isMonster;

    public Monster(String name, int health, int attackDamage, Room location) {
        super(1000, name, false, location);
        this.isMonster = true; 
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
    }

    public void attacked(int damage){
        health -= damage; 
    }

    public void strikePlayer() {
        int damage = (int)(Math.random()* attackDamage) + 1;
        Game.HP -= damage; 
        System.out.println(name + " striked you for " + damage + " damages."); 
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void killed(){
        System.out.println("You have been killed by the monster");
    }

    public void monsterDead(){
        System.out.println("you killed the monster");
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isMonster(){
        return isMonster; 
    }
}
