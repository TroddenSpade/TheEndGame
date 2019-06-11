package towerRoyal.soldiers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public enum SoldierList {
    ARCHER(new Archer(),System.getProperty("user.dir")+"/assets/"+"archer.jpg"),
    DRAGON(new Dragon(),System.getProperty("user.dir")+"/assets/"+"dragon.jpg"),
    GOBLIN(new Goblin(),System.getProperty("user.dir")+"/assets/"+"goblin.jpg"),
    SWORDSMAN(new Swordsman(),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg"),
    Samurai(new Samurai(),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg"),
    KNIGHT(new Knight(),System.getProperty("user.dir")+"/assets/"+"dragon.jpg"),
    SHIELD(new Shield(),System.getProperty("user.dir")+"/assets/"+"shield.jpg"),
    SeriousSam(new SeriousSam(),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg"),
    HEALER(new Healer(),System.getProperty("user.dir")+"/assets/"+"healer.png"),
    AutoNoob(new AutoNoob(),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg"),
    GayGunner(new GayGunner(),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg");

    private Soldier soldier;
    private Image image;
    private ImageView imageView;

    public final static int NUMBER_OF_SOLDIERS = 11;

    SoldierList(Soldier soldier,String image){
        this.soldier = soldier;
        try {
            FileInputStream fis = new FileInputStream(image);
            this.image = new Image(fis);
            this.imageView = new ImageView(this.image);
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Soldier getSoldier() {
        if(soldier instanceof Archer){
            return new Archer();
        }else if(soldier instanceof Dragon){
            return new Dragon();

        }else if(soldier instanceof Goblin){
            return new Goblin();

        }else if(soldier instanceof Healer){
            return new Healer();

        }else if(soldier instanceof Knight){
            return new Knight();

        }else if(soldier instanceof Shield){
            return new Shield();

        }else if(soldier instanceof Swordsman){
            return new Swordsman();

        }else if(soldier instanceof AutoNoob){
            return new AutoNoob();

        }else if(soldier instanceof GayGunner){
            return new GayGunner();

        }else if(soldier instanceof Samurai){
            return new Samurai();

        }else if(soldier instanceof SeriousSam){
            return new SeriousSam();

        }
        return null;
    }

    public String getName() {
        return soldier.getName();
    }

    public double getEnergy() {
        return soldier.getEnergy();
    }

    public double getDamage() {
        return soldier.getDamage();
    }

    public double getHealth() {
        return soldier.getHealth();
    }

    public int getRange() {
        return soldier.getRange();
    }

    public int getVelocity() {
        return soldier.getVelocity();
    }

    public ImageView getImageView() {
        return imageView;
    }
}
