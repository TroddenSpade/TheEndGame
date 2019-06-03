package towerRoyal.soldiers;

enum Type {
    MELEE,RANGE;
}

public class Soldier {
    private String name;
    private double energy;
    private double health;
    private int velocity;
    private double damage;
    private int range;
    private Type type;
    private boolean alive = true;

    public Soldier(String name,double energy,double health,int velocity,double damage,int range,Type type){
        this.name = name;
        this.energy = energy;
        this.health = health;
        this.velocity = velocity;
        this.damage = damage;
        this.range = range;
        this.type = type;
    }

    public void takeDamage(double damage){
        double newHealth = this.health - damage;
        if(newHealth <= 0){
            this.alive = false;
        }
    }

    public void move(){

    }

}
