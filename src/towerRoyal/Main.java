package towerRoyal;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import towerRoyal.map.Type;
import towerRoyal.soldiers.SoldierKinds;
import towerRoyal.map.Map;


public class Main extends Application {
    private static final int NUMBER_OF_SOLDIERS = 7;
    private static final int NUMBER_OF_CARDS_FOR_PLAYER = 4;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1000;

    private Map map ;
    private Map newMap;
    private Player p1;
    private Player p2;
    private Thread p1Thread;
    private Thread p2Thread;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        BorderPane startPane = new BorderPane();
        Pane mapPane = new Pane();
        TilePane pickCardsPane = new TilePane();
        BorderPane playPane = new BorderPane();
        BorderPane createMapPane = new BorderPane();

        Scene startScene = new Scene(startPane, WIDTH, HEIGHT);
        Scene mapScene = new Scene(mapPane, WIDTH, HEIGHT);
        Scene pickCardsScene = new Scene(pickCardsPane,WIDTH,HEIGHT);
        Scene playScene = new Scene(playPane,WIDTH,HEIGHT);
        Scene createMapScene = new Scene(createMapPane,WIDTH,HEIGHT);

        //////////////////////// Start Screen ////////////////////////
        VBox input = new VBox(5);
        Label startLabel = new Label("Start Screen");
        Label name = new Label("Enter Your Name :");
        TextField nameField = new TextField();
        nameField.setPrefWidth(200);
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            if(nameField.getText().trim().equals("")){
                name.setText("Enter Your Name : (Field Can not be Empty!)");
            }else {
                p1 = new Player(nameField.getText().trim());
                p1Thread = new Thread(p1);
                playPane.setTop(p1.getPlayerPane());
                primaryStage.setScene(mapScene);
            }
        });
        input.getChildren().addAll(startLabel,name,nameField,startButton);
        startPane.setCenter(input);


        /////////////////////// Map Screen ////////////////////////
        VBox parts = new VBox(100);
        VBox addMap = new VBox(5);
        VBox createMap = new VBox(5);
        Label addMapLabel = new Label("Enter Name Of Your Map (Path: ./TheEndGame/Maps/{MAP_NAME}.map)");
        TextField mapNameField = new TextField();
        Button addMapButton = new Button("Find My Map");
        addMapButton.setOnAction(e -> {
            try {
                this.map = Map.read(mapNameField.getText().trim());
                playPane.setCenter(this.map.getGroup());
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
        Label pickLabel = new Label("Pick Cards" );
        Button next = new Button("Next");

        HBox list = new HBox(10);
        RadioButton[] radioButtons = new RadioButton[NUMBER_OF_SOLDIERS];
        for(int i=0; i<NUMBER_OF_SOLDIERS; i++){
            radioButtons[i] = new RadioButton(SoldierKinds.values()[i].name());
            list.getChildren().add(radioButtons[i]);
        }
        next.setOnAction(e->{
            int numOfSelected = 0;
            for(int i=0; i<NUMBER_OF_SOLDIERS; i++){
                if(radioButtons[i].isSelected()){
                    numOfSelected++;
                }
            }
            if(numOfSelected == NUMBER_OF_CARDS_FOR_PLAYER){
                ArrayList<SoldierKinds> listOfCards = new ArrayList<>();
                for(int i=0; i<NUMBER_OF_SOLDIERS; i++){
                    if(radioButtons[i].isSelected()){
                        listOfCards.add(SoldierKinds.values()[i]);
                    }
                }
                p1.addCards(listOfCards);
                primaryStage.setScene(playScene);

            }else{
                pickLabel.setText("Pick Cards (Select " + NUMBER_OF_CARDS_FOR_PLAYER + " Cards)");
            }
        });
        pickCardsPane.getChildren().addAll(pickLabel,list,next);


        /////////////////////// Play Screen ////////////////////////
        Button playButton = new Button("Next");
        playButton.setOnAction(e->{
            p1Thread.start();
        });
        playPane.setBottom(playButton);

        primaryStage.setTitle("The End Game");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }


}
