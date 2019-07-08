package towerRoyal.soldiers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import towerRoyal.Main;
import towerRoyal.Player;
import towerRoyal.map.Map;
import towerRoyal.map.Tile;
import towerRoyal.towers.Tower;

import java.util.ArrayList;

enum Type {
    MELEE,RANGE;
}

public class Soldier implements Runnable{
    private static final int ONE_SECOND = 1000;

    private Player owner;
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
    private Color color;
    private ProgressBar healthBarTower = new ProgressBar();
    private BorderPane soldierPane = new BorderPane();
    public static int num =0;
    private int id ;

    public Soldier(Player owner,String name,
                   double energy, double health,
                   int velocity, double damage,
                   int range, Type type,Color color){
        this.owner = owner;
        this.name = name;
        this.energy = energy;
        this.health = health;
        initialHealth = health;
        this.velocity = velocity;
        this.damage = damage;
        this.range = range;
        this.type = type;
        this.color = color;
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
        healthBarTower.setProgress(health/initialHealth);
    }

    public void getHeal(double extraHealth){
        double newHealth = this.health + extraHealth;
        if(newHealth > initialHealth){
            health = initialHealth;
        }else{
            health = newHealth;
        }
        healthBarTower.setProgress(health/initialHealth);
    }

    public void setXY(double x,double y){
        this.circle = new Circle(10);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isWounded() {
        return initialHealth == health ? false : true;
    }


    public Tile getTile() {
        return tile;
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
        healthBarTower.setProgress(1);
        healthBarTower.setPrefWidth(tile.getHeight());
        healthBarTower.getStylesheets().add("/styles/Main.css");
        if(owner.getPid() == 0){
            healthBarTower.getStylesheets().add("/styles/red.css");
        }
        soldierPane.setCenter(circle);
        circle.setFill(this.color);
        soldierPane.setTop(healthBarTower);
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

    public Player getOwner() {
        return owner;
    }

    public void moveToNewTile(Tile tile){
        this.tile.removeSoldier(this);
        tile.addSoldier(this);
        if(tile.getType().equals(towerRoyal.map.Type.RED)){
            Main.getPlayer(1-owner.getPid()).gotHit();
        }
        changeLast();
        changeTile(tile);
        this.soldierPane.setLayoutX(tile.getX());
        this.soldierPane.setLayoutY(tile.getY());
    }

    public void dead(){
        alive = false;
        this.tile.removeSoldier(this);
        Platform.runLater(()->{
            map.removeFromPane(this.soldierPane);
        });
    }


    public Tile getLast() {
        return last;
    }

    public void move(){
        try {
            Thread.sleep(ONE_SECOND/velocity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Tower tower = tile.findATower(range,1-owner.getPid());
        Soldier soldier = tile.findASoldier(range,1-owner.getPid());
        if(tower != null){
            try {
                Thread.sleep(ONE_SECOND);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(alive) {
                tower.takeDamage(damage);
            }
        }else if(soldier != null){
            try {
                Thread.sleep(ONE_SECOND);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(alive) {
                soldier.takeDamage(damage);
            }
        }else{
            ArrayList<Tile> neighbours = tile.findNeighbours(owner.getPid());
            neighbours.remove(last);
            if(neighbours.size() == 0){
                dead();
            }else {
                moveToNewTile(neighbours.get(0));
            }
            neighbours.clear();
        }
    }

    @Override
    public void run(){
        while(alive){
            Tower tower = tile.findATower(range,1-owner.getPid());
            Soldier soldier = tile.findASoldier(range,1-owner.getPid());
            if(tower != null){
                try {
                    Thread.sleep(ONE_SECOND);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(alive) {
                    tower.takeDamage(damage);
                }
            }else if(soldier != null){
                try {
                    Thread.sleep(ONE_SECOND);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(alive) {
                    soldier.takeDamage(damage);
                }
            }else{
                move();
            }
        }
    }
}
