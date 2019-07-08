package towerRoyal.towers;

import javafx.application.Platform;
import towerRoyal.Main;
import towerRoyal.Player;
import towerRoyal.Random;
import towerRoyal.map.Map;
import towerRoyal.map.Tile;
import towerRoyal.soldiers.Soldier;

import java.util.ArrayList;
import java.util.Collections;

public class Builder extends Tower{

    private static final int SLEEP_TIME = 5000;

    public Builder(int owner){
        super(owner ,Type.BUILDER,500,
                System.getProperty("user.dir")+"/assets/"+"builder.png");
    }

    @Override
    public void run() {
        Player p = Main.getPlayer(getOwner());
        Map map = Main.getMap();
        while(isAlive()){
            try{
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            ArrayList<Tile> reds = new ArrayList<>(p.getReds());
            Collections.shuffle(reds);
            for(Tile tile : reds){
                if(!tile.hasSoldier()){
                    int r = Random.nextInt(4);
                    Soldier s = Main.getPlayer(1-getOwner()).getSoldiers().get(r).addSoldier(p);
                    tile.addSoldier(s);
                    s.setTile(tile,map);
                    Platform.runLater(()->{
                        map.getGroup().getChildren().add(s.getSoldierPane());
                    });
                    Thread t = new Thread(s);
                    t.start();
                    p.addToThreads(t);
                    break;
                }
            }
            reds.clear();
        }
    }
}
