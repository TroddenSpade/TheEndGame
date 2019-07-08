package towerRoyal.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import towerRoyal.Main;
import towerRoyal.Random;
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
        if(id == 1 && y+1<length){
            if(map.getIntMap()[y+1][x] == 2 || map.getIntMap()[y+1][x] == 3 ){
                tiles.add(map.getMap()[y+1][x]);
            }
        }
        if(id == 0 && y-1>=0){
            if (map.getIntMap()[y - 1][x] == 2 || map.getIntMap()[y - 1][x] == 3) {
                tiles.add(map.getMap()[y - 1][x]);
            }
        }

        boolean r = Random.nextBoolean();
        if(r){
            if(x+1<length){
                if (map.getIntMap()[y][x + 1] == 2 || map.getIntMap()[y][x + 1] == 3) {
                    tiles.add(map.getMap()[y][x + 1]);
                }
            }
            if(x-1>=0){
                if (map.getIntMap()[y][x - 1] == 2 || map.getIntMap()[y][x - 1] == 3) {
                    tiles.add(map.getMap()[y][x - 1]);
                }
            }
        }else{
            if(x-1>=0) {
                if (map.getIntMap()[y][x - 1] == 2 || map.getIntMap()[y][x - 1] == 3) {
                    tiles.add(map.getMap()[y][x - 1]);
                }
            }
            if(x+1<length) {
                if (map.getIntMap()[y][x + 1] == 2 || map.getIntMap()[y][x + 1] == 3) {
                    tiles.add(map.getMap()[y][x + 1]);
                }
            }
        }

        if(id == 1 && y-1>=0){
            if(map.getIntMap()[y-1][x] == 2 || map.getIntMap()[y-1][x] == 3 ){
                tiles.add(map.getMap()[y-1][x]);
            }
        }
        if(id == 0 && y+1<length){
            if(map.getIntMap()[y+1][x] == 2 || map.getIntMap()[y+1][x] == 3 ){
                tiles.add(map.getMap()[y+1][x]);
            }
        }
        return new ArrayList<Tile>(tiles);
    }

    public Soldier findASoldier(int range, int pid){
        if(range>1){
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
        }else{
            for(int i=-1; i<=1; i++){
                if(x+i<0 || x+i>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y][x+i] == 2){
                    Soldier soldier = map.getMap()[y][x+i].getSoldier(pid);
                    if(soldier != null){
                        return soldier;
                    }
                }
            }
            for(int j=-1; j<=1; j++){
                if(y+j<0 || y+j>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y+j][x] == 2){
                    Soldier soldier = map.getMap()[y+j][x].getSoldier(pid);
                    if(soldier != null){
                        return soldier;
                    }
                }
            }
        }
        return null;
    }

    public Soldier findAWoundedSoldier(int range, int pid){
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
                    if(soldier != null && soldier.isWounded()){
                        return soldier;
                    }
                }
            }
        }
        return null;
    }

    public Tower findATower(int range, int pid){
        if(range>1){
            for(int i=-range; i<=range; i++){
                if(x+i<0 || x+i>=map.getLength()){
                    continue;
                }
                for(int j=-range; j<=range; j++){
                    if(y+j<0 || y+j>=map.getLength()){
                        continue;
                    }
                    if(map.getIntMap()[y+j][x+i] == 1 || map.getIntMap()[y+j][x+i] == 4){
                        Tower tower = map.getMap()[y+j][x+i].getTower(pid);
                        if(tower != null){
                            return tower;
                        }
                    }
                }
            }
        }else{
            for(int i=-range; i<=range; i++){
                if(x+i<0 || x+i>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y][x+i] == 1 || map.getIntMap()[y][x+i] == 4){
                    Tower tower = map.getMap()[y][x+i].getTower(pid);
                    if(tower != null){
                        return tower;
                    }
                }
            }
            for(int j=-range; j<=range; j++){
                if(y+j<0 || y+j>=map.getLength()){
                    continue;
                }
                if(map.getIntMap()[y+j][x] == 1 || map.getIntMap()[y+j][x] == 4){
                    Tower tower = map.getMap()[y+j][x].getTower(pid);
                    if(tower != null){
                        return tower;
                    }
                }
            }
        }
        return null;
    }

    public Tower findADamagedTower(int range, int pid){
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
                    if(tower != null && tower.isDamaged()){
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
