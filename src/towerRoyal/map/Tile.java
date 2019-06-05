package towerRoyal.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import towerRoyal.soldiers.Soldier;

import java.util.ArrayList;


public class Tile extends Rectangle{
    private Type type;
    private ArrayList<Soldier> mySoldiers = new ArrayList<>();
    private ArrayList<Soldier> oppoSoldiers = new ArrayList<>();

    public Tile(Type type,int x,int y,int height){
        super(x,y,height,height);
        this.type = type;
        this.setFill(type.getColor());
        this.setStroke(Color.BLACK);
    }

    public void nextColor(){
        this.type = Type.values()[(this.type.ordinal()+1)%5];
        this.setFill(type.getColor());
    }

    public Type getType() {
        return type;
    }
}
