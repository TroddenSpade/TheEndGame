package towerRoyal.soldiers;

import towerRoyal.Player;

public class Knight extends Soldier{
    public Knight(Player owner){
        super(owner,"Knight",30,600,2,400,1,Type.MELEE);
    }
}
