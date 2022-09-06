package graph;

/**
 * @author Ðì´Ï
 * @version 1.0
 * @date 2021-11-10 21:00
 */

import java.awt.*;

public class ReShapeRect extends Shape{
    private int width, height;

    //constructor
    public ReShapeRect() {
    }

    public ReShapeRect(Point local, Color color, int width, int height) {
        super(local, ShapeType.ReShapeRect, color, null);
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
        //ÏßÌõÑÕÉ«
//        g.setColor(this.getColor());
//        g.drawRect(local.x, local.y, this.getWidth(), this.getHeight());

        Graphics2D g2=(Graphics2D)g;
        float[] dash={5,5};
        BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,dash,0.0f);
        g2.setStroke(bs);
        g2.setColor(Color.RED);
        g2.drawRect(local.x, local.y, width,height);
        g2.fillRect(local.x+width, local.y+height, 7,7);
        bs = new BasicStroke();
        g2.setStroke(bs);

    }

}

