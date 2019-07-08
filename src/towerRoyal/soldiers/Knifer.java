package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Knifer extends Soldier{
    public Knifer(Player owner){
        super(owner,"Knifer",5,10,2,1,1,Type.MELEE, Color.GOLD);
    }
}
