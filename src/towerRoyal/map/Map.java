package towerRoyal.map;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import towerRoyal.Main;
import towerRoyal.Player;
import towerRoyal.towers.Tower;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;

public class Map{
    private Tile[][] map;
    private int length;
    private int tileHeight;
    private Group group = new Group();
    private static final int WIDTH = 700;

    public Map(int[][] table,int length){
        this.length = length;
        this.map = new Tile[length][length];
        this.tileHeight = (WIDTH/length);
        this.group.setLayoutX(tileHeight*length);
        this.group.setLayoutX(tileHeight*length);
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                map[i][j] = new Tile(
                        Type.values()[table[i][j]],
                        j*((int)WIDTH/length),
                        i*((int)WIDTH/length),
                        ((int)WIDTH/length));
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
                map[i][j] = new Tile(
                        Type.values()[0],
                        ((int)WIDTH/length)*j,
                        ((int)WIDTH/length)*i,
                        ((int)WIDTH/length));
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
                    tower.setTile(tile);
                    p.removeTowerFromList(tower);
                    Main.setSelectedPlayer(null);
                    Main.setSelectedTower(null);
                    ImageView imv = tower.getImageView();
                    imv.setLayoutX(tile.getX());
                    imv.setLayoutY(tile.getY());
                    imv.setFitHeight(tileHeight);
                    imv.setFitWidth(tileHeight);
                    group.getChildren().add(imv);

                }
            }
        });
        return group;
    }


    public Group getGroup() {
        return group;
    }

    public int getLength() {
        return length;
    }
}
