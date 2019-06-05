package towerRoyal.map;

import javafx.scene.paint.Color;

public enum Type {
    GREY(Color.LIGHTGREY),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    RED(Color.RED),
    GREEN(Color.GREEN);

    private Color color;
    private static int NUMBER_OF_COLORS = 5;

    Type(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}