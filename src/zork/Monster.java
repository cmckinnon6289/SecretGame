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
        if (health <= 0) this.monsterDead();
    }

    public void strikePlayer() {
        int damage = (int) (Math.random()* attackDamage);
        Game.HP -= damage; 
        System.out.println(name + " striked you for " + damage + " damages."); 
    }

    public void strikeMonster(Weapon weapon) {
        int damage = (int) (Math.random() * weapon.getDamage()) + 2;
        this.attacked(damage);
        System.out.println("You strike the "+this.getName()+" for " + damage +" HP.\nThe monster currently sits at "+this.getHealth()+" HP.");
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
        Game.finished = true;
    }

    public void monsterDead(){
        System.out.println("You killed the monster.");
        Game.endFight();
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isMonster(){
        return isMonster; 
    }
}
