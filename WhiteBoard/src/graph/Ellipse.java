package graph;

import java.awt.*;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-04 13:50
 */

public class Ellipse extends Shape{
    private int width, height;

    //constructor
    public Ellipse() {
    }

    public Ellipse(Point local, Color color, Color fillColor, int width, int height) {
        super(local, ShapeType.ELLIPSE, color, fillColor);
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
    public void draw(java.awt.Graphics g) {
        //填充颜色
        if(fillColor!= null){
            g.setColor(fillColor);
            g.fillOval(local.x, local.y, this.getWidth(), this.getHeight());
        }

        //线条颜色
        g.setColor(this.getColor());
        g.drawOval(local.x, local.y, this.getWidth(), this.getHeight());
    }
}
