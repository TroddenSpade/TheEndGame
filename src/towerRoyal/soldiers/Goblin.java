package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;

public class Goblin extends Soldier{
    public Goblin(Player owner){
        super(owner,"Goblin",10,200,3,350,1,Type.MELEE, Color.GREEN);
    }
}
