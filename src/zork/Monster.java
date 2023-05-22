package zork;

public class Monster {
    private String name;
    private int health;
    private int attackDamage;

    public Monster(String name, int health, int attackDamage) {
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

    public boolean isAlive() {
        return health > 0;
    }
}
