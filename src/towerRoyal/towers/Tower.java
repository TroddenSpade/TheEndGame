package towerRoyal.towers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import towerRoyal.map.Map;
import towerRoyal.map.Tile;
import towerRoyal.soldiers.Soldier;

import java.io.FileInputStream;

public class Tower implements Runnable {
    public static final int IMAGE_HEIGHT = 64;

    private String name;
    private int owner;
    private double initialHealth;
    private double health;
    private double damage;
    private int energy;
    private int range;
    private Map map;
    private Type type;
    private Tile tile;
    private Image image;
    private ImageView imageView;
    private BorderPane towerPane = new BorderPane();
    private ProgressBar healthBar = new ProgressBar();
    private boolean alive = true;
//    private Label nameLabel;
//    private Label damageLabel;
//    private Label rangeLabel;
//    private Label energyLabel;


    public Tower(int owner,
                 Type type,
                 String name,
                 double health,
                 int energy,
                 int range, double damage, String image){
        this.owner = owner;
        initialHealth = health;
        this.type = type;
        this.name = name;
        this.damage = damage;
        this.energy = energy;
        this.health = health;
        this.range = range;
        try {
            FileInputStream fis = new FileInputStream(image);
            this.image = new Image(fis);
            this.imageView = new ImageView(this.image);
            imageView.setFitHeight(IMAGE_HEIGHT);
            imageView.setFitWidth(IMAGE_HEIGHT);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        nameLabel = new Label(name);
//        damageLabel = new Label("Damage: "+damage);
//        rangeLabel = new Label("Range: "+range);
//        energyLabel = new Label("Energy: " +energy);
//        box.getChildren().addAll(nameLabel,energyLabel,damageLabel,rangeLabel);
    }

    public Tower(int owner,Type type,double health,String image){
        this.owner = owner;
        this.type = type;
        try {
            FileInputStream fis = new FileInputStream(image);
            this.image = new Image(fis);
            this.imageView = new ImageView(this.image);
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.health = health;
        initialHealth = health;
    }

    public void takeDamage(double damage){
        double newHealth = this.health - damage;
        if(newHealth <= 0){
            dead();
            health = 0;
        }else {
            health = newHealth;
        }
        healthBar.setProgress(health/initialHealth);
    }

    public void getHeal(double extraHealth){
        double newHealth = this.health + extraHealth;
        if(newHealth > initialHealth){
            health = initialHealth;
        }else {
            health = newHealth;
        }
        healthBar.setProgress(health/initialHealth);
    }

    public BorderPane getTowerPane(Tile tile){
        healthBar.setProgress(1);
        imageView.setFitHeight(tile.getHeight()-16);
        imageView.setFitWidth(tile.getHeight());
        healthBar.setPrefWidth(tile.getHeight());
        healthBar.getStylesheets().add("/styles/Main.css");
        if(owner == 0)
            healthBar.getStylesheets().add("/styles/red.css");
        towerPane.setCenter(imageView);
        towerPane.setTop(healthBar);
        towerPane.setLayoutX(tile.getX());
        towerPane.setLayoutY(tile.getY());
        return towerPane;
    }

    public Type getType() {
        return type;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDamaged() {return initialHealth == health ? false : true;}

    public Tile getTile() {
        return tile;
    }

    public int getRange() {
        return range;
    }

    public int getOwner() {
        return owner;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setTile(Tile tile, Map map) {
        this.tile = tile;
        this.map = map;
    }

    public void dead(){
        this.alive = false;
        Platform.runLater(()->{
            map.removeFromPane(this.towerPane);
        });
        this.tile.removeTower();
    }

    public void shoot(Soldier soldier){
        if(soldier != null){
            soldier.takeDamage(damage);
        }
    }

    @Override
    public void run() {
        while(alive){
            try{
                Thread.sleep(1000);
            } catch (Exception e){

            }
            if(alive) {
                shoot(tile.findASoldier(range,1-owner));
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
