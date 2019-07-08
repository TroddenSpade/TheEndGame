package towerRoyal.soldiers;

import javafx.scene.paint.Color;
import towerRoyal.Player;
import towerRoyal.map.Tile;
import towerRoyal.towers.Tower;

import java.util.ArrayList;

public class Healer extends Soldier{
    private final double HEAL = 100;

    public Healer(Player owner){
        super(owner,"Healer",30,300,1,0,3,Type.RANGE, Color.MINTCREAM);
    }
    @Override
    public void move(){
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Tower tower = getTile().findADamagedTower(getRange(),getOwner().getPid());
        Soldier soldier = getTile().findAWoundedSoldier(getRange(),getOwner().getPid());
        if(tower != null){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(isAlive()) {
                tower.getHeal(HEAL);
            }
        }else if(soldier != null){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(isAlive()) {
                soldier.getHeal(HEAL);
            }
        }else{
            ArrayList<Tile> neighbours = getTile().findNeighbours(getOwner().getPid());
            neighbours.remove(getLast());
            if(neighbours.size() == 0){
                dead();
            }else {
                moveToNewTile(neighbours.get(0));
            }
            neighbours.clear();
        }
    }

    @Override
    public void run() {
        while(isAlive()){
            Tower tower = getTile().findADamagedTower(getRange(),getOwner().getPid());
            Soldier soldier = getTile().findAWoundedSoldier(getRange(),getOwner().getPid());
            if(tower != null){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(isAlive()) {
                    tower.getHeal(HEAL);
                }
            }else if(soldier != null){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(isAlive()) {
                    soldier.getHeal(HEAL);
                }
            }else{
                move();
            }
        }

    }
}
