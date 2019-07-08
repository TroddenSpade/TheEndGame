package towerRoyal.map;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import towerRoyal.Main;
import towerRoyal.Player;
import towerRoyal.soldiers.Soldier;
import towerRoyal.towers.Tower;

import java.io.*;

public class Map implements Serializable {
    private Tile[][] map;
    private int[][] intMap;
    private int length;
    private int tileHeight;
    private Group group = new Group();
    private static final int WIDTH = 700;

    public Map(int[][] table,int length){
        this.length = length;
        this.intMap = table;
        this.map = new Tile[length][length];
        this.tileHeight = (WIDTH/length);
        this.group.setLayoutX(tileHeight*length);
        this.group.setLayoutX(tileHeight*length);
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                map[i][j] = new Tile(Type.values()[table[i][j]],j,i,
                        WIDTH/length,this,length);
                group.getChildren().add(map[i][j]);
            }
        }
    }

    public Map(int length){
        this.length = length;
        this.tileHeight = ((int)Math.floor(WIDTH/length));
        this.map = new Tile[length][length];
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                map[i][j] = new Tile(Type.values()[0],j,i,
                        WIDTH/length);
                group.getChildren().add(map[i][j]);
            }
        }
    }


    public static Map read(String name) throws Exception{
        File file = new File(System.getProperty("user.dir") + "/Maps/" + name + ".map");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        int length = Integer.parseInt(line);

        int[][] mapArray = new int[length][length];

        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                mapArray[i][j] = Integer.parseInt(br.readLine());
            }
        }
        br.close();
        return new Map(mapArray,length);
    }

    public void write(String name) throws Exception{
        File file = new File(System.getProperty("user.dir") + "/Maps/" + name + ".map");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(this.length+"");
        bw.newLine();
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                bw.write(this.map[i][j].getType().ordinal()+"");
                bw.newLine();
            }
        }
        bw.close();
    };

    public Group monitoredGroupForSettingColor() {
        this.group.setOnMouseClicked(event -> {
            this.map[(int)event.getY()/tileHeight][(int)event.getX()/tileHeight].nextColor();
        });
        return group;
    }

    public Group monitoredGroupForSettingTower(){
        this.group.setOnMouseClicked(event -> {
            Tower tower = Main.getSelectedTower();
            Player p = Main.getSelectedPlayer();
            Tile tile = this.map[(int)event.getY()/tileHeight][(int)event.getX()/tileHeight];
            if(tower != null && tile.isEmpty()){
                if((tile.getType().equals(Type.GREEN) &&
                    tower.getType().equals(towerRoyal.towers.Type.BUILDER)) ||
                    (tile.getType().equals(Type.BLUE) &&
                    !tower.getType().equals(towerRoyal.towers.Type.BUILDER))){

                    if(tile.getType().equals(Type.GREEN)){
                        p.pickedBuilder();
                    }
                    tile.setTower(tower);
                    tower.setTile(tile,this);
                    p.removeTowerFromList(tower);
                    Thread t = new Thread(tower);
                    t.start();
                    p.addToThreads(t);
                    Main.setSelectedPlayer(null);
                    Main.setSelectedTower(null);
                    group.getChildren().add(tower.getTowerPane(tile));
                }
            }
        });
        return group;
    }

    public Group monitoredGroupForSettingSoldiers() {
        this.group.setOnMouseClicked(event -> {
            Soldier soldier = Main.getSelectedSoldier();
            Tile tile = this.map[(int)event.getY()/tileHeight][(int)event.getX()/tileHeight];
            if(soldier != null && !tile.hasSoldier()){
                if(tile.getType().equals(Type.RED)&&
                    soldier.getOwner().decreaseEnergy(soldier.getEnergy())){
                    soldier.setTile(tile,this);
                    group.getChildren().add(soldier.getSoldierPane());
                    Thread t = new Thread(soldier);
                    t.start();
                    soldier.getOwner().addToThreads(t);
                    Main.setSelectedSoldier(null);
                }
            }
        });
        return group;
    }

    public void mapSetSoldierFromPlayer(Player p,int numTile){
        Soldier soldier = p.getSelectedSoldier();
        Tile tile ;
        try{
            tile = p.getReds().get(numTile);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        if(soldier != null && !tile.hasSoldier()){
            if(soldier.getOwner().decreaseEnergy(soldier.getEnergy())){
                soldier.setTile(tile,this);
                tile.addSoldier(soldier);
                group.getChildren().add(soldier.getSoldierPane());
                Thread t = new Thread(soldier);
                t.start();
                p.addToThreads(t);
                p.setSelectedSoldier(null);
            }
        }
    }

    public void setRedTiles(Player p1,Player p2){
        for(int i=0; i<length; i++) {
            if (map[0][i].getType().equals(Type.RED)) {
                p2.addRedTile(map[0][i]);
            }
        }

        for(int i=0; i<length; i++){
            if(map[length-1][i].getType().equals(Type.RED)){
                p1.addRedTile(map[length-1][i]);
            }
        }
    }


    public void removeFromPane(BorderPane obj){
        group.getChildren().remove(obj);
    }

    public Group getGroup() {
        return group;
    }

    public int getLength() {
        return length;
    }

    public int[][] getIntMap() {
        return intMap;
    }

    public Tile[][] getMap() {
        return map;
    }
}
