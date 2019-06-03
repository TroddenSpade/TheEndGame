package towerRoyal;

import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import towerRoyal.map.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        Player player1 = new Player("Parsa");


        Pane startP = new Pane();
        Pane mapP = new Pane();
        Pane pickCardsP = new Pane();

        Scene start = new Scene(startP, 700, 600);
        Scene map = new Scene(mapP, 700, 600);
        Scene pickCards = new Scene(pickCardsP,700,600);

        Button startButton = new Button("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(350);
        startButton.setOnAction(e -> primaryStage.setScene(map));
        Label startLabel = new Label("Start Screen");
        startP.getChildren().addAll(startButton,startLabel);

        Button setDefaultMap = new Button("Set Default Map");
        setDefaultMap.setLayoutX(350);
        setDefaultMap.setLayoutY(350);
        setDefaultMap.setOnAction(e -> primaryStage.setScene(pickCards));
        Label mapLabel = new Label("Map Screen");
        mapP.getChildren().addAll(setDefaultMap,mapLabel);

        Label pickLabel = new Label(player1.getEnergy() + " : health" );
        Text txt  = new Text();
        txt.setText(player1.getEnergy() + " : health");
        Button hit = new Button("hit");
        hit.setLayoutX(350);
        hit.setLayoutY(350);
        hit.setOnAction(e ->{
            player1.decreaseEnergy(30);
        });

        Thread player1Thread = new Thread(player1);

        player1Thread.start();
        pickCardsP.getChildren().addAll(pickLabel,hit,player1.getPlayerPane());

        primaryStage.setTitle("The End Game");
        primaryStage.setScene(start);
        primaryStage.show();
    }


}
