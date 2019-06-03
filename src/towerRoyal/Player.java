package towerRoyal;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class Player implements Runnable{
    private static final double MAX_ENERGY = 100.0;
    private static final double EXTRA_ENERGY_PER_SECOND = 10.0;
    private static final int SLEEP_TIME = 100;

    private boolean running = true;
    private int lives = 3;
    private String name;
    private double energy;
    private Label livesLabel = new Label("Lives : " + lives);
    private ProgressBar energyBar = new ProgressBar();
    private Pane playerPane = new Pane(energyBar,livesLabel);

    public Player(String name){
        this.name = name;
        this.energy = 0;
    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e){

            }
            addEnergy();
            energyBar.setProgress(getEnergy()/100);
            livesLabel.setText("Lives : " + lives);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getEnergy() {
        return energy;
    }

    public Pane getPlayerPane() {
        return playerPane;
    }

    public void addEnergy() {
        double newEnergy = this.energy + EXTRA_ENERGY_PER_SECOND/10;
        if(newEnergy > MAX_ENERGY){
            this.energy = MAX_ENERGY;
        }else {
            this.energy = newEnergy;
        }
    }

    public void decreaseEnergy(double val){
        double newEnergy = this.energy - val;
        if(newEnergy >= 0){
            this.energy = newEnergy ;
        }
    }
}
