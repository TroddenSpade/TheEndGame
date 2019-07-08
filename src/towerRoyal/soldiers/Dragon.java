package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Dragon extends Soldier{
    public Dragon(Player owner){
        super(owner,"Dragon",35,500,2,350,3,Type.RANGE, Color.RED);
    }
}
