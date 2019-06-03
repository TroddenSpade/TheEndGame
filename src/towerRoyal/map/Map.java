package towerRoyal.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
    Tile[] map ;

    public static void mapReader(String name) throws Exception{
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
    }


}
