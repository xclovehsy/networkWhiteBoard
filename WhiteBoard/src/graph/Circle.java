package graph;

import java.awt.*;

/**
 * @author Ðì´Ï
 * @version 1.0
 * @date 2021-11-04 13:46
 */

public class Circle extends Shape{
    private int r;

    //constructor
    public Circle() {
    }

    public Circle(Point local, Color color, Color fillColor, int r) {
        super(local, ShapeType.CIRCLE, color, fillColor);
        this.r = r;
    }

    //setter and getter
    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public void draw(java.awt.Graphics g) {
        if(fillColor!= null){
            g.setColor(fillColor);
            g.fillOval(local.x, local.y,this.getR()*2, this.getR()*2);
        }
        g.setColor(this.getColor());
        g.drawOval(local.x, local.y,this.getR()*2, this.getR()*2);

    }
}
