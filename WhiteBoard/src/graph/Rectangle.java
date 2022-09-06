package graph;

import java.awt.*;

public class Rectangle extends Shape{
    private int width, height;

    //constructor
    public Rectangle() {
    }

    public Rectangle(Point local, Color color, Color fillColor, int width, int height) {
        super(local, ShapeType.RECTANGLE, color, fillColor);
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
        //填充颜色
        if(fillColor!= null){
            g.setColor(fillColor);
            g.fillRect(local.x, local.y, this.getWidth(), this.getHeight());
        }

        //线条颜色
        g.setColor(this.getColor());
        g.drawRect(local.x, local.y, this.getWidth(), this.getHeight());

    }

}
