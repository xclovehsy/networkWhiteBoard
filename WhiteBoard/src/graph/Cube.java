package graph;

import java.awt.*;
import java.awt.geom.Line2D;

public class Cube extends Shape{
    private int width;

    //constructor
    public Cube() {
    }

    public Cube(Point local, Color color, Color fillColor, int width) {
        super(local, ShapeType.CUBE, color, fillColor);
        this.width = width;
    }

    //setter and getter
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void draw(java.awt.Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int x = local.x;
        int y = local.y;

        double x1=(double)2*width/15+x;
        double y1=(double)2*width/15+y;
        double x2=(double)12*width/15+x;
        double y2=(double)2*width/15+y;
        double x3=(double)4*width/15+x;
        double y3=(double)4*width/15+y;
        double x4=(double)14*width/15+x;
        double y4=(double)4*width/15+y;
        double x5=(double)2*width/15+x;
        double y5=(double)12*width/15+y;
        double x6=(double)12*width/15+x;
        double y6=(double)12*width/15+y;
        double x7=(double)4*width/15+x;
        double y7=(double)14*width/15+y;
        double x8=(double)14*width/15+x;
        double y8=(double)14*width/15+y;
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(2.f));
        g2d.draw(new Line2D.Double(x1,y1,x2,y2));
        g2d.draw(new Line2D.Double(x1,y1,x3,y3));
        g2d.draw(new Line2D.Double(x1,y1,x5,y5));
        g2d.draw(new Line2D.Double(x1,y1,x2,y2));
        g2d.draw(new Line2D.Double(x7,y7,x5,y5));
        g2d.draw(new Line2D.Double(x7,y7,x3,y3));
        g2d.draw(new Line2D.Double(x7,y7,x8,y8));
        g2d.draw(new Line2D.Double(x4,y4,x3,y3));
        g2d.draw(new Line2D.Double(x4,y4,x8,y8));
        g2d.draw(new Line2D.Double(x4,y4,x2,y2));
        float[] dashes= {10};
        g2d.setPaint(this.color);
        g2d.setStroke (new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND, 10, dashes, 0));
        g2d.draw(new Line2D.Double(x6,y6,x5,y5));
        g2d.draw(new Line2D.Double(x6,y6,x8,y8));
        g2d.draw(new Line2D.Double(x6,y6,x2,y2));

        g2d.setStroke(new BasicStroke(1.f));
    }
}
