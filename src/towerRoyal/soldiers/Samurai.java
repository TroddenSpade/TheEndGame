package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Samurai extends Soldier{
    public Samurai(Player owner){
        super(owner,"SamuraI",20,250,3,400,2,Type.RANGE, Color.CRIMSON);
    }
}
