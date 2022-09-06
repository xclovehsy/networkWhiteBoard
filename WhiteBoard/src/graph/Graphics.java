package graph;

import java.io.Serializable;
import java.util.*;

public class Graphics implements Serializable {
    private final LinkedList<Shape> shapes;

    public Graphics(){
        shapes = new LinkedList<>();
    }

    public void DrawShapes(java.awt.Graphics g){

        for(Shape shape : shapes){
            shape.draw(g);
        }
    }

    public void RemoveEndShape(){
        shapes.removeLast();
    }

    public void AddShape(Shape shape){
        shapes.add(shape);
    }
    
    public void setShape(int index, Shape shape){
        shapes.set(index, shape);
    }

    public void Clear(){
        shapes.clear();
    }

    //getter

    public LinkedList<Shape> getShapes() {
        return shapes;
    }

    public int ShapeCount(){
        return shapes.size();
    }
}
