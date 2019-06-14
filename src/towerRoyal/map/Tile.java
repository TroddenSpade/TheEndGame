package towerRoyal.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import towerRoyal.Main;
import towerRoyal.soldiers.Soldier;
import towerRoyal.towers.Tower;
import java.util.ArrayList;


public class Tile extends Rectangle{
    private Type type;
    private Tower tower;
    private int x;
    private int y;
    private int length;
    private Map map;
    private ArrayList<Soldier> soldiers = new ArrayList<>();

    public Tile(Type type,int x,int y,int height){
        super(x*height,y*height,height,height);
        this.x = x;
        this.y = y;
        this.type = type;
        this.setFill(type.getColor());
        this.setStroke(Color.BLACK);
    }

    public Tile(Type type,int x,int y,int height,Map map,int length){
        super(x*height,y*height,height,height);
        this.length = length;
        this.map = map;
        this.x = x;
        this.y = y;
        this.type = type;
        this.setFill(type.getColor());
        this.setStroke(Color.BLACK);
    }

    public void nextColor(){
        this.type = Type.values()[(this.type.ordinal()+1)%5];
        this.setFill(type.getColor());
    }

    public void setTower(Tower tower){
        this.tower = tower;
    }

    public Type getType() {
        return type;
    }

    public boolean hasSoldier(){
        return soldiers.size() == 0 ? false : true;
    }

    public ArrayList<Tile> findNeighbours(int id){
        ArrayList<Tile> tiles = new ArrayList<>();
        int c = 1;
        if(id == 1){
            c = -1;
        }
        for(int i=-1; i<2; i+=2){
            if(y+c*i >= 0 && y+c*i <length){
                if(map.getIntMap()[y+c*i][x] == 2 || map.getIntMap()[y+c*i][x] == 3 ){
                    tiles.add(map.getMap()[y+c*i][x]);
                }
            }
        }

        for(int i=-1; i<2; i+=2){
            if(x+i >= 0 && x+i <length){
                if(map.getIntMap()[y][x+i] == 2 || map.getIntMap()[y][x+i] == 3){
                    tiles.add(map.getMap()[y][x+i]);
                }
            }
        }
        return new ArrayList<Tile>(tiles);
    }

    public Soldier findAnSoldier(int range,int pid){
        for(int i=-range; i<=range; i++){
            if(x+i<0 || x+i>=map.getLength()){
                continue;
            }
            for(int j=-range; j<=range; j++){
                if(y+j<0 || y+j>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y+j][x+i] == 2){
                    Soldier soldier = map.getMap()[y+j][x+i].getSoldier(pid);
                    if(soldier != null){
                        return soldier;
                    }
                }
            }
        }
        return null;
    }

    public Tower findAnTower(int range,int pid){
        for(int i=-range; i<=range; i++){
            if(x+i<0 || x+i>=map.getLength()){
                continue;
            }
            for(int j=-range; j<=range; j++){
                if(y+j<0 || y+j>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y+j][x+i] == 1){
                    Tower tower = map.getMap()[y+j][x+i].getTower(pid);
                    if(tower != null){
                        return tower;
                    }
                }
            }
        }
        return null;
    }

    public Soldier getSoldier(int pid){
        for(Soldier soldier : soldiers){
            if(soldier.getOwner().getPid() == pid){
                return soldier;
            }
        }
        return null;
    }

    public Tower getTower(int pid){
        if(isEmpty())   return null;
        if(tower.getOwner() == pid){
            return tower;
        }
        return null;
    }

    public boolean isEmpty(){
        if(tower == null){
            return true;
        }
        return false;
    }

    public void removeSoldier(Soldier soldier){
        soldiers.remove(soldier);
    }

    public void addSoldier(Soldier soldier){
        soldiers.add(soldier);
    }

    public void removeTower(){
        this.tower= null;
    }


    @Override
    public String toString() {
        return x + " : " + y;
    }

    @Override
    public boolean equals(Object obj) {
        Tile newTile = (Tile)obj;
        if(this.getX() == newTile.getX() &&
            this.getY() == newTile.getY()){
            return true;
        }
        return false;
    }
}
