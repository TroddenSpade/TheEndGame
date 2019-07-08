package towerRoyal.soldiers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import towerRoyal.Player;

import java.io.FileInputStream;
import java.util.ArrayList;

public enum SoldierList {
    ARCHER(new Archer(null),System.getProperty("user.dir")+"/assets/"+"archer.png"),
    DRAGON(new Dragon(null),System.getProperty("user.dir")+"/assets/"+"dragon.jpg"),
    GOBLIN(new Goblin(null),System.getProperty("user.dir")+"/assets/"+"goblin.jpg"),
    SWORDSMAN(new Swordsman(null),System.getProperty("user.dir")+"/assets/"+"swordsman.jpg"),
    Samurai(new Samurai(null),System.getProperty("user.dir")+"/assets/"+"samurai.png"),
    KNIGHT(new Knight(null),System.getProperty("user.dir")+"/assets/"+"knight.png"),
    SHIELD(new Shield(null),System.getProperty("user.dir")+"/assets/"+"shield.jpg"),
    SeriousSam(new SeriousSam(null),System.getProperty("user.dir")+"/assets/"+"seriousSam.png"),
    HEALER(new Healer(null),System.getProperty("user.dir")+"/assets/"+"healer.png"),
    AutoNoob(new AutoNoob(null),System.getProperty("user.dir")+"/assets/"+"autonoob.png"),
    GayGunner(new GayGunner(null),System.getProperty("user.dir")+"/assets/"+"gaygunner.png"),
    Knifer(new Knifer(null),System.getProperty("user.dir")+"/assets/"+"knifer.png");

    private Soldier soldier;
    private Image image;

    private final static int NUMBER_OF_SOLDIERS = 12;
    private static final int NUMBER_OF_CARDS_FOR_PLAYER = 4;

    SoldierList(Soldier soldier,String image){
        this.soldier = soldier;
        try {
            FileInputStream fis = new FileInputStream(image);
            this.image = new Image(fis);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public Soldier addSoldier(Player owner) {
        if(soldier instanceof Archer){
            return new Archer(owner);
        }else if(soldier instanceof Dragon){
            return new Dragon(owner);

        }else if(soldier instanceof Goblin){
            return new Goblin(owner);

        }else if(soldier instanceof Healer){
            return new Healer(owner);

        }else if(soldier instanceof Knight){
            return new Knight(owner);

        }else if(soldier instanceof Shield){
            return new Shield(owner);

        }else if(soldier instanceof Swordsman){
            return new Swordsman(owner);

        }else if(soldier instanceof AutoNoob){
            return new AutoNoob(owner);

        }else if(soldier instanceof GayGunner){
            return new GayGunner(owner);

        }else if(soldier instanceof Samurai){
            return new Samurai(owner);

        }else if(soldier instanceof SeriousSam){
            return new SeriousSam(owner);

        }else if(soldier instanceof Knifer){
            return new Knifer(owner);

        }
        return null;
    }

    public static void soldierSelector(Player player,VBox list){
        Button accept = new Button("Accept");
        Label label = new Label("Select Soldiers For "+player.getName()+" :");
        list.getChildren().add(label);
        RadioButton[] radioButtons = new RadioButton[NUMBER_OF_SOLDIERS];
        for(int i=0; i<SoldierList.NUMBER_OF_SOLDIERS; i++){
            radioButtons[i] =
                    new RadioButton(SoldierList.values()[i].getName()+"\t\t"
                            + "Damage:"+SoldierList.values()[i].getDamage()+"\t"
                            + "Range:"+SoldierList.values()[i].getRange()+"\t"
                            + "Velocity:"+SoldierList.values()[i].getVelocity()+"\t"
                            + "Health:"+SoldierList.values()[i].getHealth()+"\t"
                            + "Energy:"+SoldierList.values()[i].getEnergy()+"\t");
            list.getChildren().add(radioButtons[i]);
        }
        accept.setOnAction(e->{
            int numOfSelected = 0;
            for(int i=0; i<NUMBER_OF_SOLDIERS; i++){
                if(radioButtons[i].isSelected()){
                    numOfSelected++;
                }
            }
            if(numOfSelected == NUMBER_OF_CARDS_FOR_PLAYER){
                ArrayList<SoldierList> listOfCards = new ArrayList<>();
                for(int i=0; i<NUMBER_OF_SOLDIERS; i++){
                    if(radioButtons[i].isSelected()){
                        listOfCards.add(SoldierList.values()[i]);
                    }
                }
                player.setSoldiers(listOfCards);
                label.setText("Select Soldiers For "+player.getName()+" :"+"(SET)");
                list.getChildren().remove(accept);
            }else{
                label.setText("Select Soldiers For "+player.getName()+" :"+
                        "(Select " + NUMBER_OF_CARDS_FOR_PLAYER + " Cards)");
            }
        });
        list.getChildren().add(accept);
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
        ImageView imageView = new ImageView(this.image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        return imageView;
    }
}
