package towerRoyal.towers;

public class Tower implements Runnable {
    private double health;
    private double damage;
    private int energy;
    private int range;
    private boolean working = true;

    public Tower(double health,int energy,int range,double damage){
        this.damage = damage;
        this.energy = energy;
        this.health = health;
        this.range = range;
    }

    public Tower(double health,int energy,int range){
        this.energy = energy;
        this.health = health;
        this.range = range;
    }

    public Tower(double health){
        this.health = health;
    }

    public void takeDamage(double damage){
        double newHealth = this.health - damage;
        if(newHealth <= 0){
            this.working = false;
        }
    }

    @Override
    public void run() {

    }
}
