package zork;

public class Monster {
    private String name;
    int health;
    private int attackDamage;

    // Constructor with name, health, and attackDamage
    public Monster(String name, int health, int attackDamage) {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
    }

    // Getter methods
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
