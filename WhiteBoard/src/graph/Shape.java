package graph;
import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    Point local;
    ShapeType shapeType;
    Color color, fillColor;

    //constructor
    public Shape() {
    }

    public Shape(Point local, ShapeType shapeType, Color color, Color fillColor) {
        this.local = local;
        this.shapeType = shapeType;
        this.color = color;
        this.fillColor = fillColor;
    }

    //setter and getter
    public Point getLocal() {
        return local;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public Color getColor() {
        return color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setLocal(Point local) {
        this.local = local;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public abstract void draw(java.awt.Graphics g);

}
