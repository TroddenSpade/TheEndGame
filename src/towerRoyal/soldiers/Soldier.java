package towerRoyal.soldiers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import towerRoyal.map.Map;
import towerRoyal.map.Tile;
import towerRoyal.towers.Tower;

import java.util.ArrayList;
import java.util.Random;

enum Type {
    MELEE,RANGE;
}

public class Soldier implements Runnable{
    private static final int ONE_MINUTE = 1000;

    private String name;
    private double energy;
    private double health;
    private double initialHealth;
    private int velocity;
    private double damage;
    private int range;
    private Type type;
    private Tile tile;
    private Tile last;
    private Map map;
    private boolean alive = true;
    private Circle circle ;
    private ProgressBar healthBar = new ProgressBar();
    private BorderPane soldierPane = new BorderPane();
    public static int num =0;
    private int id ;

    public Soldier(String name,double energy,double health,int velocity,double damage,int range,Type type){
        this.name = name;
        this.energy = energy;
        this.health = health;
        initialHealth = health;
        this.velocity = velocity;
        this.damage = damage;
        this.range = range;
        this.type = type;
        id = num ++;
    }

    public void takeDamage(double damage){
        double newHealth = this.health - damage;
        if(newHealth <= 0){
            dead();
            health = 0;
        }else{
            health = newHealth;
        }
        healthBar.setProgress(health/initialHealth);
    }

    public void setXY(double x,double y){
        this.circle = new Circle(10);
    }

    public String getName() {
        return name;
    }

    public double getEnergy() {
        return energy;
    }

    public double getDamage() {
        return damage;
    }

    public double getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public int getVelocity() {
        return velocity;
    }

    public BorderPane getSoldierPane() {
        healthBar.setProgress(1);
        healthBar.setPrefWidth(tile.getHeight());
        soldierPane.setCenter(circle);
        soldierPane.setTop(healthBar);
        soldierPane.setLayoutX(tile.getX());
        soldierPane.setLayoutY(tile.getY());
        return soldierPane;
    }

    public void setTile(Tile tile,Map map) {
        this.setXY(tile.getX()+tile.getHeight()/2,tile.getY()+tile.getHeight()/2);
        this.tile = tile;
        this.map = map;
    }

    public void changeTile(Tile tile){
        this.tile = tile;
    }
    public void changeLast() {
        this.last = tile;
    }

    public int getId() {
        return id;
    }

    public void moveToNewTile(Tile tile){
        this.tile.removeSoldier(this);
        tile.addSoldier(this);
        changeLast();
        changeTile(tile);
        this.soldierPane.setLayoutX(tile.getX());
        this.soldierPane.setLayoutY(tile.getY());
    }

    public void dead(){
        Thread.currentThread().interrupt();
        alive = false;
        this.tile.removeSoldier(this);
        Platform.runLater(()->{
            map.removeFromPane(this.soldierPane);
        });
    }

    public void shoot(){
        Tower tower = tile.findAnTower(range);
        if(tower == null){

        }else{
            tower.takeDamage(damage);
        }
    }

    public void move(){
        try {
            Thread.sleep(ONE_MINUTE/velocity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<Tile> neighbours = tile.findNeighbours();
        neighbours.remove(last);
        if(neighbours.size() == 0){
            dead();

        }else {
            moveToNewTile(neighbours.get(0));
        }
        neighbours.clear();
    }

    @Override
    public void run() {
        while(alive){
            Tower tower = tile.findAnTower(range);
            if(tower == null){
                move();
            }else{
                try {
                    Thread.sleep(ONE_MINUTE);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(alive) {
                    tower.takeDamage(damage);
                }
            }
        }
    }
}
