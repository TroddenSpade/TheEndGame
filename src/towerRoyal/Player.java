package towerRoyal;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.layout.TilePane;
import towerRoyal.map.Tile;
import towerRoyal.soldiers.AutoNoob;
import towerRoyal.soldiers.GayGunner;
import towerRoyal.soldiers.Soldier;
import towerRoyal.soldiers.SoldierList;
import towerRoyal.towers.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Runnable , Serializable {
    private static final double ONE_MINUTE = 1000;
    private static final double MAX_ENERGY = 100.0;
    private static final int SLEEP_TIME = 100;
    private static final int NUMBER_OF_TOWERS_FOR_EACH_PLAYER = 3;
    private static int num = 0;

    private ArrayList<Thread> threads = new ArrayList<>();
    private Soldier selectedSoldier;
    private int pid;
    private double extraEnergyPerSec = 10.0;
    private boolean running = true;
    private int lives = 3;
    private String name;
    private double energy;
    private Label livesLabel = new Label("Lives : " + lives);
    private Label nameLabel = new Label();
    private ProgressBar energyBar = new ProgressBar();
    private TilePane playerPane = new TilePane(energyBar,livesLabel,nameLabel);
    private ArrayList<SoldierList> soldiers = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Tile> reds = new ArrayList<>();
    private VBox myTowerList = new VBox();
    private VBox mySoldierList = new VBox();


    public Player(){
        this.pid = num++;
        this.energy = 0;
        energyBar.setPrefSize(200,10);
        for(int i=0; i<NUMBER_OF_TOWERS_FOR_EACH_PLAYER; i++){
            towers.add(new Black(pid));
            towers.add(new Hospital(pid));
            towers.add(new Electric(pid));
        }
        towers.add(new Builder(pid));
        for(int i=0; i<towers.size(); i++){
            myTowerList.getChildren().add(towers.get(i).getImageView());
        }
        this.myTowerList.setOnMouseClicked(event -> {
            Main.setSelectedTower(
                towers.get((int)event.getY()/Tower.IMAGE_HEIGHT));
            Main.setSelectedPlayer(this);
        });
        this.mySoldierList.setOnMouseClicked(event -> {
            Main.setSelectedSoldier(soldiers.get((int)(event.getY()/64.)).addSoldier(this));
        });
        if(pid == 0){
            playerPane.getStylesheets().add("/styles/red.css");
        }
    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            addEnergy();
            Platform.runLater(()->{
                energyBar.setProgress(getEnergy()/100);
                livesLabel.setText("Lives : " + lives);
            });
            for (Thread thread :threads){
                try {
                    if(thread.isInterrupted()){
                        System.out.println("intrup");
                        threads.remove(thread);
                     }
                }catch (Exception e){
                    System.out.println("cant remove");
                }
            }
        }
        Main.stopGame();
    }

    public int getLives() {
        return lives;
    }

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public void setName(HBox box) {
        Label name = new Label("Enter Your Name :");
        TextField nameField = new TextField();
        nameField.setPrefWidth(200);
        Button accept = new Button("Set");
        accept.setOnAction(e -> {
            if(nameField.getText().trim().equals("")){
                name.setText("Enter Your Name : (Field Can not be Empty!)");
            }else {
                this.name = nameField.getText().trim();
                box.getChildren().remove(accept);
                name.setText("Enter Your Name : (SET)");
            }
        });
        box.getChildren().addAll(name,nameField,accept);
    }

    public double getEnergy() {
        return energy;
    }

    public void setSelectedSoldier(Soldier selectedSoldier) {
        this.selectedSoldier = selectedSoldier;
    }

    public Soldier getSelectedSoldier() {
        return selectedSoldier;
    }

    public TilePane getPlayerPane() {
        return playerPane;
    }

    public ArrayList<SoldierList> getSoldiers() {
        return soldiers;
    }

    public VBox getMyTowerList() {
        return myTowerList;
    }

    public void addRedTile(Tile tile){
        reds.add(tile);
    }

    public ArrayList<Tile> getReds() {
        return reds;
    }

    public void addEnergy() {
        double newEnergy = this.energy + extraEnergyPerSec*SLEEP_TIME/ONE_MINUTE;
        if(newEnergy > MAX_ENERGY){
            this.energy = MAX_ENERGY;
        }else {
            this.energy = newEnergy;
        }
    }

    public Boolean decreaseEnergy(double val){
        double newEnergy = this.energy - val;
        if(newEnergy >= 0){
            this.energy = newEnergy ;
            return true;
        }
        return false;
    }

    public void setSoldiers(ArrayList<SoldierList> soldiers) {
        boolean isGay = false;
        boolean isNoob = false;
        this.soldiers = soldiers;
        for(SoldierList soldierList : soldiers){
            mySoldierList.getChildren().add(soldierList.getImageView());
            if(soldierList.getSoldier() instanceof GayGunner){
                isGay = true;
            }else if(soldierList.getSoldier() instanceof AutoNoob){
                isNoob = true;
            }
        }
        if(isGay && isNoob){
            nameLabel.setText("ooh God, "+name+" is A Noob Gay ! -_-");
        }else if(isGay){
            nameLabel.setText(name+" is A Gay ! (Why Ar U Gae ?)");
        }else if(isNoob){
            nameLabel.setText(name+" is A Noob ! (Noob as a bot)");
        }else{
            nameLabel.setText(name);
        }
    }

    public VBox getMySoldierList() {
        return mySoldierList;
    }

    public void pickedBuilder(){
        this.extraEnergyPerSec /= 4;
    }

    public void removeTowerFromList(Tower tower){
        towers.remove(tower);
    }

    public void gotHit(){
        this.lives--;
        if(lives == 0){
            running = false;
        }
    }

    public ArrayList<Thread> getThreads(){
        return threads;
    }

    public void addToThreads(Thread thread){
        threads.add(thread);
    }
}
