package towerRoyal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import towerRoyal.soldiers.Soldier;
import towerRoyal.soldiers.SoldierList;
import towerRoyal.map.Map;
import towerRoyal.towers.Tower;

import java.io.*;


public class Main extends Application {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1000;

    private static Map map;
    private Map newMap;
    private static Player p1;
    private static Player p2;
    private static Thread p1Thread;
    private static Thread p2Thread;

    private static Tower selectedTower;
    private static Player selectedPlayer;
    private static Soldier selectedSoldier;

    private BorderPane startPane;
    private Pane mapPane;
    private BorderPane pickCardsPane;
    private BorderPane setTowers;
    private BorderPane createMapPane;
    private BorderPane playGroundPane;

    private Scene startScene;
    private Scene mapScene;
    private Scene pickCardsScene;
    private Scene setTowersScene;
    private Scene createMapScene;
    private Scene playGroundScene;

    public static void main(String[] args) {
        try {
            SocketClass.run();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initialSetting(primaryStage);
        primaryStage.setTitle("The End Game");
        primaryStage.show();
    }

    public void keyBoardController(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
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
                    map.mapSetSoldierFromPlayer(p1, 0);
                    break;
                case X:
                    map.mapSetSoldierFromPlayer(p1, 1);
                    break;
                case C:
                    map.mapSetSoldierFromPlayer(p1, 2);
                    break;
                case V:
                    map.mapSetSoldierFromPlayer(p1, 3);
                    break;
                case B:
                    map.mapSetSoldierFromPlayer(p1, 4);
                    break;

                case SLASH:
                    map.mapSetSoldierFromPlayer(p2, 4);
                    break;
                case PERIOD:
                    map.mapSetSoldierFromPlayer(p2, 3);
                    break;
                case COMMA:
                    map.mapSetSoldierFromPlayer(p2, 2);
                    break;
                case M:
                    map.mapSetSoldierFromPlayer(p2, 1);
                    break;
                case N:
                    map.mapSetSoldierFromPlayer(p2, 0);
                    break;
                default:
                    break;
            }
        });
    }

    public void initialSetting(Stage primaryStage){
        p1 = new Player();
        p2 = new Player();
        p1Thread = new Thread(p1);
        p2Thread = new Thread(p2);
        startScreen(primaryStage);
        setTowerMap(primaryStage);
        createMap(primaryStage);
        pickCards(primaryStage);
        gameScreen(primaryStage);
        setPlayGround();
        keyBoardController(playGroundScene);
        gameThread(primaryStage);
        primaryStage.setScene(startScene);
    }

    public void startScreen(Stage stage){
        startPane = new BorderPane();
        startScene = new Scene(startPane, WIDTH, HEIGHT);
        VBox input = new VBox(20);
        HBox setName1 = new HBox(10);
        HBox setName2 = new HBox(10);
        setName1.setAlignment(Pos.CENTER);
        setName2.setAlignment(Pos.CENTER);
        p1.setName(setName1);
        p2.setName(setName2);
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            stage.setScene(mapScene);
        });
        input.getChildren().addAll(setName1, setName2, startButton);
        input.setAlignment(Pos.CENTER);
        startPane.setCenter(input);
        stage.centerOnScreen();
    }

    public void setTowerMap(Stage stage){
        mapPane = new Pane();
        mapScene = new Scene(mapPane, WIDTH, HEIGHT);
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
                stage.setScene(pickCardsScene);
            } catch (Exception ex) {
                addMapLabel.setText("Enter Name Of Your Map (Path: ./TheEndGame/Maps/)\n" + ex.getMessage());
            }
        });
        addMap.getChildren().addAll(addMapLabel, mapNameField, addMapButton);

        Label createMapLabel = new Label("Create A New Map !");
        Button createMapButton = new Button("Create");
        createMapButton.setOnAction(e ->
                stage.setScene(createMapScene)
        );
        createMap.getChildren().addAll(createMapLabel, createMapButton);

        parts.getChildren().addAll(addMap, createMap);
        parts.setAlignment(Pos.CENTER);
        addMap.setAlignment(Pos.CENTER);
        createMap.setAlignment(Pos.CENTER);
        mapPane.getChildren().addAll(parts);
    }

    public void createMap(Stage stage){
        createMapPane = new BorderPane();
        createMapScene = new Scene(createMapPane, WIDTH, HEIGHT);
        HBox inputHeightBox = new HBox(10);
        HBox saveMapBox = new HBox(10);
        Label mapLengthLabel = new Label("Enter Your Maps Length");
        TextField inputLengthField = new TextField();
        Button getLengthMapButton = new Button("Create");
        getLengthMapButton.setOnAction(event -> {
            try {
                int lengthOfMyMap = Integer.parseInt(inputLengthField.getText());
                newMap = new Map(lengthOfMyMap);
                createMapPane.setCenter(newMap.monitoredGroupForSettingColor());
            } catch (Exception e) {
                mapLengthLabel.setText("Enter Your Maps Length\n" + e.getMessage());
            }
        });
        inputHeightBox.getChildren().addAll(mapLengthLabel, inputLengthField, getLengthMapButton);
        createMapPane.setTop(inputHeightBox);
        Label mapNameLabel = new Label("Enter a Name For Your Map");
        TextField newMapNameField = new TextField();
        Button saveMyMap = new Button("Save My Map");
        saveMyMap.setOnAction(event -> {
            if (newMap != null) {
                if (newMapNameField.getText().equals("")) {
                    mapNameLabel.setText("Enter a Name For Your Map\n" + "TextField Cannot be Empty!");
                } else {
                    try {
                        newMap.write(newMapNameField.getText());
                        stage.setScene(startScene);
                    } catch (Exception ex) {
                        mapNameLabel.setText("Enter a Name For Your Map\n" + ex.getMessage());
                    }
                }
            } else {
                mapNameLabel.setText("Enter a Name For Your Map\n" + "(Invalid Map !)");
            }
        });
        saveMapBox.getChildren().addAll(mapNameLabel, newMapNameField, saveMyMap);
        createMapPane.setBottom(saveMapBox);
    }

    public void pickCards(Stage stage){
        pickCardsPane = new BorderPane();
        pickCardsScene = new Scene(pickCardsPane, WIDTH + 200, HEIGHT);
        Button next = new Button("Next");
        VBox list1 = new VBox(10);
        VBox list2 = new VBox(10);
        SoldierList.soldierSelector(p1, list1);
        SoldierList.soldierSelector(p2, list2);
        next.setOnAction(e -> {
            stage.setScene(setTowersScene);
        });
        pickCardsPane.setLeft(list1);
        pickCardsPane.setRight(list2);
        pickCardsPane.setBottom(next);

    }

    public void gameScreen(Stage stage){
        setTowers = new BorderPane();
        setTowersScene = new Scene(setTowers, WIDTH, HEIGHT);
        VBox towersList1 = p1.getMyTowerList();
        VBox towersList2 = p2.getMyTowerList();
        Button playButton = new Button("Play !");
        playButton.setOnAction(e -> {
            stage.setScene(playGroundScene);
            playGroundPane.setCenter(map.monitoredGroupForSettingSoldiers());
            playGroundPane.setBottom(p1.getPlayerPane());
            playGroundPane.setTop(p2.getPlayerPane());
            playGroundPane.setRight(p1.getMySoldierList());
            playGroundPane.setLeft(p2.getMySoldierList());
            map.setRedTiles(p1, p2);
            p1Thread.start();
            p2Thread.start();
        });
        setTowers.setBottom(playButton);
        setTowers.setRight(towersList1);
        setTowers.setLeft(towersList2);
    }

    public void setPlayGround(){
        playGroundPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        menuBar.getMenus().add(file);
        file.getItems().add(save);
        save.setOnAction(e->{
            save();
        });

        VBox box = new VBox(menuBar,playGroundPane);
        playGroundScene = new Scene(box, WIDTH, HEIGHT);
//        playGroundPane.setTop(menuBar);
    }

    public void gameThread(Stage stage){
        Thread t = new Thread(() -> {
            while(p1.getLives()!=0 && p2.getLives()!=0){
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            Platform.runLater(() -> {
                BorderPane pane = new BorderPane();
                VBox box = new VBox(10);
                Scene scene = new Scene(pane,500, 500);
                Label label;
                if(p1.getLives()!=0){
                    label = new Label(p1.getName()+" is Winner");
                }else{
                    label = new Label(p2.getName() + " is Winner");
                }
                Button ret = new Button("return");
                box.getChildren().addAll(label,ret);
                box.setAlignment(Pos.CENTER);
                pane.setCenter(box);
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                stage.setScene(scene);
                stage.centerOnScreen();
                ret.setOnAction(e->{
                   initialSetting(stage);
                });
            });
        });
        t.start();
    }

    public static void setSelectedTower(Tower tower) {
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

    public static Player getPlayer(int p) {
        return p == 0 ? p1 : p2;
    }

    public static Map getMap() {
        return map;
    }

    public static void stopGame() {
        for (Thread t : p1.getThreads()) {
            t.stop();
        }
        for (Thread t : p2.getThreads()) {
            t.stop();
        }
        p1Thread.stop();
        p2Thread.stop();
    }

    public static void save(){
        stopGame();
        try {
            File file = new File(System.getProperty("user.dir")+
                    "/saved/players");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p1);
            oos.writeObject(p2);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            File file = new File(System.getProperty("user.dir")+
                    "/saved/map");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void load(Stage stage){
        try {
            File file = new File(System.getProperty("user.dir")+
                    "/saved/players");
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fos);
            p1 = (Player) oos.readObject();
            p2 = (Player) oos.readObject();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            File file = new File(System.getProperty("user.dir")+
                    "/saved/map");
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fos);
            map = (Map) oos.readObject();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}