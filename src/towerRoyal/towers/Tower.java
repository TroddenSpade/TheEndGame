package towerRoyal.towers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import towerRoyal.map.Tile;

import java.io.FileInputStream;

public class Tower implements Runnable {
    public static final int IMAGE_HEIGHT = 64;

    private String name;
    private double health;
    private double damage;
    private int energy;
    private int range;
    private boolean working;
    private Type type;
    private Tile tile;
    private Label nameLabel;
    private Label damageLabel;
    private Label rangeLabel;
    private Label energyLabel;
    private Image image;
    private ImageView imageView;
    private HBox box = new HBox();

    public Tower(Type type,String name,double health,int energy,int range,double damage,String image){
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
        nameLabel = new Label(name);
        damageLabel = new Label("Damage: "+damage);
        rangeLabel = new Label("Range: "+range);
        energyLabel = new Label("Energy: " +energy);
        box.getChildren().addAll(nameLabel,energyLabel,damageLabel,rangeLabel);
    }

//    public Tower(double health,int energy,int range){
//        this.energy = energy;
//        this.health = health;
//        this.range = range;
//    }

    public Tower(Type type,double health,String image){
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

    public Type getType() {
        return type;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return name;
    }
}
