package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class SeriousSam extends Soldier{
    public SeriousSam(Player owner){
        super(owner,"Serious Sam",30,1000,2,100,1,Type.MELEE, Color.BLUE);
    }
}
