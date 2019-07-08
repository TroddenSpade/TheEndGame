package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Swordsman extends Soldier{
    public Swordsman(Player owner){
        super(owner,"Swordsman",20,500,1,350,1,Type.MELEE, Color.SILVER);
    }
}
