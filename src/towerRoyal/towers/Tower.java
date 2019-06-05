package towerRoyal.towers;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class Tower implements Runnable {
    private String name;
    private double health;
    private double damage;
    private int energy;
    private int range;
    private boolean working;
    private Label nameLabel;
    private Label damageLabel;
    private Label rangeLabel;
    private Label energyLabel;
    private HBox box = new HBox();

    public Tower(String name,double health,int energy,int range,double damage){
        this.name = name;
        this.damage = damage;
        this.energy = energy;
        this.health = health;
        this.range = range;
        nameLabel = new Label(name);
        damageLabel = new Label("Damage: "+damage);
        rangeLabel = new Label("Range: "+range);
        energyLabel = new Label("Energy: " +energy);
        box.getChildren().addAll(nameLabel,energyLabel,damageLabel,rangeLabel);
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

    public HBox getBox() {
        return box;
    }

    @Override
    public void run() {

    }
}
