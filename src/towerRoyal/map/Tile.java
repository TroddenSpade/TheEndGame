package towerRoyal.map;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

enum Type {
    GREY(Color.GREY),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    RED(Color.RED),
    GREEN(Color.GREEN);

    private Color color;

    Type(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

public class Tile {
    private Type type;

    public Tile(Type type){
        this.type = type;
    }

    public Rectangle getTile(){
        Rectangle rectangle = new Rectangle(20,20);
        rectangle.setStroke(type.getColor());
        return rectangle;
    }
}
