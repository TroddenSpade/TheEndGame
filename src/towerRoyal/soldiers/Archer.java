package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Archer extends Soldier{
    public Archer(Player owner){

        super(owner,"Archer",15,300,1,200,2,Type.RANGE, Color.PURPLE);
    }
}
