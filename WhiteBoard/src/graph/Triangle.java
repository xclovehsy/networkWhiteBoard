package graph;

import java.awt.*;

public class Triangle extends Shape{
    private int width, height;

    //constructor
    public Triangle() {
    }

    public Triangle(Point local, Color color, Color fillColor, int width, int height) {
        super(local, ShapeType.TRIANGLE, color, fillColor);
        this.width = width;
        this.height = height;
    }
    //setter and getter
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw(java.awt.Graphics g){
        g.setColor(this.getColor());

        //»­ÈýÌõÏß
        g.drawLine(local.x+width/2, local.y, local.x+width, local.y+height);
        g.drawLine(local.x+width/2, local.y, local.x, local.y+height);
        g.drawLine(local.x, local.y+height, local.x+width, local.y+height);
    }
    
}
