package towerRoyal;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import towerRoyal.soldiers.Soldier;
import towerRoyal.soldiers.SoldierList;
import towerRoyal.map.Map;
import towerRoyal.towers.Tower;


public class Main extends Application {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1000;

    private Map map ;
    private Map newMap;
    private Player p1 = new Player();
    private Player p2 = new Player();
    private Thread p1Thread = new Thread(p1);
    private Thread p2Thread = new Thread(p2);

    private static Tower selectedTower;
    private static Player selectedPlayer;

    private static Soldier selectedSoldier;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        BorderPane startPane = new BorderPane();
        Pane mapPane = new Pane();
        BorderPane pickCardsPane = new BorderPane();
        BorderPane setTowers = new BorderPane();
        BorderPane createMapPane = new BorderPane();
        BorderPane playGroundPane = new BorderPane();

        Scene startScene = new Scene(startPane, WIDTH, HEIGHT);
        Scene mapScene = new Scene(mapPane, WIDTH, HEIGHT);
        Scene pickCardsScene = new Scene(pickCardsPane,WIDTH+200,HEIGHT);
        Scene setTowersScene = new Scene(setTowers,WIDTH,HEIGHT);
        Scene createMapScene = new Scene(createMapPane,WIDTH,HEIGHT);
        Scene playGroundScene = new Scene(playGroundPane,WIDTH,HEIGHT);

        keyBoardController(playGroundScene);

        //////////////////////// Start Screen ////////////////////////
        VBox input = new VBox(5);
        Label startLabel = new Label("Start Screen");
        HBox setName1 = new HBox(10);
        HBox setName2 = new HBox(10);
        p1.setName(setName1);
        p2.setName(setName2);
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
                primaryStage.setScene(mapScene);
        });
        input.getChildren().addAll(startLabel,setName1,setName2,startButton);
        startPane.setCenter(input);


        /////////////////////// Set Tower Map Screen ////////////////////////
        VBox parts = new VBox(100);
        VBox addMap = new VBox(5);
        VBox createMap = new VBox(5);
        Label addMapLabel = new Label("Enter Name Of Your Map (Path: ./TheEndGame/Maps/{MAP_NAME}.map)");
        TextField mapNameField = new TextField();
        Button addMapButton = new Button("Find My Map");
        addMapButton.setOnAction(e -> {
            try {
                this.map = Map.read(mapNameField.getText().trim());
                setTowers.setCenter(this.map.monitoredGroupForSettingTower());
                primaryStage.setScene(pickCardsScene);
            }catch (Exception ex){
                addMapLabel.setText("Enter Name Of Your Map (Path: ./TheEndGame/Maps/)\n" + ex.getMessage());
            }
        });
        addMap.getChildren().addAll(addMapLabel,mapNameField,addMapButton);

        Label createMapLabel = new Label("Create A New Map !");
        Button createMapButton = new Button("Create");
        createMapButton.setOnAction(e ->
                primaryStage.setScene(createMapScene)
        );
        createMap.getChildren().addAll(createMapLabel,createMapButton);

        parts.getChildren().addAll(addMap,createMap);
        mapPane.getChildren().addAll(parts);


        /////////////////////// Create Map ////////////////////////
        HBox inputHeightBox = new HBox(10);
        HBox saveMapBox = new HBox(10);
        Label mapLengthLabel = new Label("Enter Your Maps Length");
        TextField inputLengthField = new TextField();
        Button getLengthMapButton = new Button("Create");
        getLengthMapButton.setOnAction(event -> {
            try{
                int lengthOfMyMap = Integer.parseInt(inputLengthField.getText());
                newMap = new Map(lengthOfMyMap);
                createMapPane.setCenter(newMap.monitoredGroupForSettingColor());
            }catch (Exception e) {
                mapLengthLabel.setText("Enter Your Maps Length\n" + e.getMessage());
            }
        });
        inputHeightBox.getChildren().addAll(mapLengthLabel,inputLengthField,getLengthMapButton);
        createMapPane.setTop(inputHeightBox);
        Label mapNameLabel = new Label("Enter a Name For Your Map");
        TextField newMapNameField = new TextField();
        Button saveMyMap = new Button("Save My Map");
        saveMyMap.setOnAction(event -> {
            if (newMap != null) {
                if(newMapNameField.getText().equals("")){
                    mapNameLabel.setText("Enter a Name For Your Map\n"+"TextField Cannot be Empty!");
                }else{
                    try {
                        newMap.write(newMapNameField.getText());
                        primaryStage.setScene(startScene);
                    }catch (Exception ex){
                        mapNameLabel.setText("Enter a Name For Your Map\n"+ex.getMessage());
                    }
                }
            }else{
                mapNameLabel.setText("Enter a Name For Your Map\n"+"(Invalid Map !)");
            }
        });
        saveMapBox.getChildren().addAll(mapNameLabel,newMapNameField,saveMyMap);
        createMapPane.setBottom(saveMapBox);

        /////////////////////// PickCards Screen ////////////////////////
        Button next = new Button("Next");
        VBox list1 = new VBox(10);
        VBox list2 = new VBox(10);
        SoldierList.soldierSelector(p1,list1);
        SoldierList.soldierSelector(p2,list2);
        next.setOnAction(e->{
            primaryStage.setScene(setTowersScene);
        });
        pickCardsPane.setLeft(list1);
        pickCardsPane.setRight(list2);
        pickCardsPane.setBottom(next);


        /////////////////////// Set Towers Screen ////////////////////////
        VBox towersList1 = p1.getMyTowerList();
        VBox towersList2 = p2.getMyTowerList();
        Button playButton = new Button("Play !");
        playButton.setOnAction(e->{
            primaryStage.setScene(playGroundScene);
            playGroundPane.setCenter(map.monitoredGroupForSettingSoldiers());
            playGroundPane.setBottom(p1.getPlayerPane());
            playGroundPane.setTop(p2.getPlayerPane());
            playGroundPane.setRight(p1.getMySoldierList());
            playGroundPane.setLeft(p2.getMySoldierList());
            map.setRedTiles(p1,p2);
            p1Thread.start();
            p2Thread.start();
        });
        setTowers.setBottom(playButton);
        setTowers.setRight(towersList1);
        setTowers.setLeft(towersList2);


        ////////////////////////// Play Ground ////////////////////////////


        primaryStage.setTitle("The End Game");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public void keyBoardController(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case DIGIT1:
                    p1.setSelectedSoldier(p1.getSoldiers().get(0).addSoldier(p1));
                    break;
                case DIGIT2:
                    p1.setSelectedSoldier(p1.getSoldiers().get(1).addSoldier(p1));
                    break;
                case DIGIT3:
                    p1.setSelectedSoldier(p1.getSoldiers().get(2).addSoldier(p1));
                    break;
                case DIGIT4:
                    p1.setSelectedSoldier(p1.getSoldiers().get(3).addSoldier(p1));
                    break;
//                case DIGIT5:
//                    p1.setSelectedSoldier(p1.getSoldiers().get(4).addSoldier(p1));
//                    break;

                case DIGIT6:
                    p2.setSelectedSoldier(p2.getSoldiers().get(0).addSoldier(p2));
                    break;
                case DIGIT7:
                    p2.setSelectedSoldier(p2.getSoldiers().get(1).addSoldier(p2));
                    break;
                case DIGIT8:
                    p2.setSelectedSoldier(p2.getSoldiers().get(2).addSoldier(p2));
                    break;
                case DIGIT9:
                    p2.setSelectedSoldier(p2.getSoldiers().get(3).addSoldier(p2));
                    break;
//                case DIGIT0:
//                    p2.setSelectedSoldier(p2.getSoldiers().get(4).addSoldier(p2));
//                    break;

                case Z:
                    map.mapSetSoldierFromPlayer(p1,0);
                    break;
                case X:
                    map.mapSetSoldierFromPlayer(p1,1);
                    break;
                case C:
                    map.mapSetSoldierFromPlayer(p1,2);
                    break;
                case V:
                    map.mapSetSoldierFromPlayer(p1,3);
                    break;
                case B:
                    map.mapSetSoldierFromPlayer(p1,4);
                    break;

                case SLASH:
                    map.mapSetSoldierFromPlayer(p2,4);
                    break;
                case PERIOD:
                    map.mapSetSoldierFromPlayer(p2,3);
                    break;
                case COMMA:
                    map.mapSetSoldierFromPlayer(p2,2);
                    break;
                case M:
                    map.mapSetSoldierFromPlayer(p2,1);
                    break;
                case N:
                    map.mapSetSoldierFromPlayer(p2,0);
                    break;
                default:
                    break;
            }
        });
    }

    public static void setSelectedTower(Tower tower){
        selectedTower = tower;
    }

    public static void setSelectedPlayer(Player selectedPlayer) {
        Main.selectedPlayer = selectedPlayer;
    }

    public static Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public static Tower getSelectedTower() {
        return selectedTower;
    }

    public static void setSelectedSoldier(Soldier selectedSoldier) {
        Main.selectedSoldier = selectedSoldier;
    }

    public static Soldier getSelectedSoldier() {
        return selectedSoldier;
    }
}
