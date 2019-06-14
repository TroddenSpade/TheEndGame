package towerRoyal.soldiers;

import towerRoyal.Player;

public class Shield extends Soldier{
    public Shield(Player owner){
        super(owner,"Shield",10,1000,1,150,1,Type.MELEE);
    }
}
