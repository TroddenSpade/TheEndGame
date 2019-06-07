package towerRoyal;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.layout.TilePane;
import towerRoyal.soldiers.Soldier;
import towerRoyal.soldiers.SoldierKinds;
import towerRoyal.towers.*;

import java.util.ArrayList;

public class Player implements Runnable{
    private static final double ONE_MINUTE = 1000;
    private static final double MAX_ENERGY = 100.0;
    private static final int SLEEP_TIME = 100;
    private static final int NUMBER_OF_TOWERS_FOR_EACH_PLAYER = 3;

    private double extraEnergyPerSec = 10.0;
    private boolean running = true;
    private int lives = 3;
    private String name;
    private double energy;
    private Label livesLabel = new Label("Lives : " + lives);
    private ProgressBar energyBar = new ProgressBar();
    private TilePane playerPane = new TilePane(energyBar,livesLabel);
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private VBox myTowerList = new VBox();

    public Player(){
        this.energy = 0;
        for(int i=0; i<NUMBER_OF_TOWERS_FOR_EACH_PLAYER; i++){
            towers.add(new Black());
            towers.add(new Hospital());
            towers.add(new Electric());
        }
        towers.add(new Builder());
        for(int i=0; i<towers.size(); i++){
            myTowerList.getChildren().add(towers.get(i).getImageView());
        }
        this.myTowerList.setOnMouseClicked(event -> {
            Main.setSelectedTower(
                towers.get((int)event.getY()/Tower.IMAGE_HEIGHT));
            Main.setSelectedPlayer(this);
        });

    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e){

            }
            addEnergy();
            Platform.runLater(()->{
                energyBar.setProgress(getEnergy()*SLEEP_TIME/ONE_MINUTE);
                livesLabel.setText("Lives : " + lives);
            });
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

    public TilePane getPlayerPane() {
        return playerPane;
    }

    public VBox getMyTowerList() {
        return myTowerList;
    }

    public void addEnergy() {
        double newEnergy = this.energy + extraEnergyPerSec;
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

    public void addCards(ArrayList<SoldierKinds> list){
        for(SoldierKinds s : list){
            this.soldiers.add(s.getSoldier());
        }
    }

    public void pickedBuilder(){
        this.extraEnergyPerSec /= 4;
    }

    public void removeTowerFromList(Tower tower){
        towers.remove(tower);
    }

}
