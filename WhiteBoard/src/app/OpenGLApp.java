package app;

//import Server.AcceptThread;
import graph.*;
import graph.Rectangle;
import graph.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-04 21:11
 */

public class OpenGLApp extends JFrame {
    Point begin, end;
    ShapeType shapeType = ShapeType.NULL;
    Color color = Color.BLACK;
    Color fillColor = null;
    graph.Graphics graphics = new graph.Graphics();
    graph.Shape shape;
    JButton button01, button02, button03, button04, button05, button06, button07, button08, colorButton, fillColorButton;
    JComboBox<String> colorBox, fillColorBox;
    JLabel colorLabel, fillColorLabel, countLabel, shapeLabel, operatorLabel;
    JMenuBar jMenuBar;
    boolean paintShape, paintReshape, isMove, isChange;
    int reShapeIndex;
    int w, h;
    Socket s = null;

    ObjectInputStream is;
    ObjectOutputStream os;
    AcceptThread acceptThread;

    public OpenGLApp(String title) {
        super(title);
        load();
    }

    public void connect(){
        try{
            System.out.println("连接到服务器，端口号5500 ");
            //连接到本机，端口号5500
            s = new Socket("localhost", 5500);

            os = new ObjectOutputStream(s.getOutputStream());

            is = new ObjectInputStream(s.getInputStream());
            acceptThread = new AcceptThread(is, this);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendData(){
        try {
            if(!(os == null) && !(graphics == null)){
                os.writeObject(this.graphics);
//                System.out.println("发送成功！");
                System.out.println("发送图形到服务器。。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        setBounds(100, 100, 1000, 600);

        /**
         * 设置窗口关闭事件 向服务器传送一个空指针
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    os.writeObject(new Circle());
                    os.flush();
                    acceptThread.stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                System.exit(0);
            }
        });

        addMouseListener(new MyMouseListener());
        setLayout(new FlowLayout());

        Container container = getContentPane();
        container.setLayout(null);
        jMenuBar = new JMenuBar();

        jMenuBar.setVisible(true);
        this.setJMenuBar(jMenuBar);

        shapeLabel = new JLabel("   图形：");
        jMenuBar.add(shapeLabel);

        SetButton();
        jMenuBar.add(button01);
        jMenuBar.add(button02);
        jMenuBar.add(button03);
        jMenuBar.add(button04);
        jMenuBar.add(button05);

        operatorLabel = new JLabel("   操作：");
        jMenuBar.add(operatorLabel);
        jMenuBar.add(button06);
        jMenuBar.add(button07);

        colorLabel = new JLabel("    ");
        jMenuBar.add(colorLabel);
        colorButton = new JButton("线条颜色");
        jMenuBar.add(colorButton);
        colorButton.addActionListener(new ColorListener(this));

        fillColorLabel = new JLabel("    ");
        fillColorButton = new JButton("填充颜色");
        jMenuBar.add(fillColorLabel);
        jMenuBar.add(fillColorButton);
        fillColorButton.addActionListener(new FillColorListener(this));

        countLabel = new JLabel("    图形数量：   ");
        jMenuBar.add(countLabel);

        jMenuBar.add(button08);

    }

    private int GetShapeIndex(Point p){
        LinkedList<Shape> list = graphics.getShapes();
        int len = list.size();
        for(int i = len-1; i>=0; i--){
            Shape s = list.get(i);
            switch (s.getShapeType()){
                case CUBE:
                    Cube cube  = (Cube)s;
                    h = w = cube.getWidth();
                    break;
                case CIRCLE:
                    Circle circle = (Circle) s;
                    h = w = circle.getR()*2;
                    break;
                case ELLIPSE:
                    Ellipse ellipse = (Ellipse) s;
                    h = ellipse.getHeight();
                    w = ellipse.getWidth();
                    break;
                case RECTANGLE:
                    Rectangle rectangle = (Rectangle) s;
                    h = rectangle.getHeight();
                    w = rectangle.getWidth();
                    break;
                case TRIANGLE:
//                    Triangle triangle = (Triangle) shape;
                    h = ((Triangle) s).getHeight();
                    w = ((Triangle) s).getWidth();
                    break;
                default:
                    h = w = 0;
            }
            double x = p.getX();
            double y = p.getY();
            double localX = s.getLocal().getX();
            double localY = s.getLocal().getY();
            if(x>=localX && x<=localX+w && y>=localY && y<=localY+h){
                return i;
            }
        }
        return -1;
    }
    private void Reshape(Point p){

    }

    private void SetButton() {
        MyActionListener actionListener = new MyActionListener(this);
        button01 = new JButton("圆形");
        button02 = new JButton("椭圆");
        button03 = new JButton("立方体");
        button04 = new JButton("长方形");
        button05 = new JButton("三角形");
        button06 = new JButton("清空");
        button07 = new JButton("撤销");
        button08 = new JButton("修改图形");

        button01.setVisible(true);
        button02.setVisible(true);
        button03.setVisible(true);
        button04.setVisible(true);
        button05.setVisible(true);
        button06.setVisible(true);
        button07.setVisible(true);
        button08.setVisible(true);

        button01.setActionCommand("Circle");
        button02.setActionCommand("Ellipse");
        button03.setActionCommand("Cube");
        button04.setActionCommand("Rectangle");
        button05.setActionCommand("Triangle");
        button06.setActionCommand("Clear");
        button07.setActionCommand("ReDo");


        button01.addActionListener(actionListener);
        button02.addActionListener(actionListener);
        button03.addActionListener(actionListener);
        button04.addActionListener(actionListener);
        button05.addActionListener(actionListener);
        button06.addActionListener(actionListener);
        button07.addActionListener(actionListener);

        //重绘图形按钮

        button08.addMouseListener(new ReshapeMouseAdapter(this));

    }

    public void showConnectError() {
        JOptionPane.showMessageDialog(
                this,
                "服务器断开连接。。。",
                "连接出错",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private static class ReshapeMouseAdapter extends MouseAdapter{
        private OpenGLApp openGLApp;
        public ReshapeMouseAdapter(OpenGLApp openGLApp) {
            this.openGLApp = openGLApp;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            openGLApp.paintShape = false;
//            System.out.println(index);
            openGLApp.paintReshape = true;
        }
    }

    /**
     * 添加图形
     */
    public void AddShapes(){
        try {  //防止end点为空
            if(!paintShape){
                return;
            }
            int x1 = begin.x;
            int x2 = end.x;
            int y1 = begin.y;
            int y2 = end.y;
            Point b = new Point(Math.min(x1, x2), Math.min(y1, y2));

            switch (this.shapeType) {
                case RECTANGLE:
                    shape = new graph.Rectangle(b, color, fillColor, Math.abs(x1 - x2), Math.abs(y1 - y2));
                    break;
                case TRIANGLE:
                    shape = new Triangle(b, color, fillColor, Math.abs(x1 - x2), Math.abs(y1 - y2));
                    break;
                case ELLIPSE:
                    shape = new Ellipse(b, color, fillColor, Math.abs(x1 - x2), Math.abs(y1 - y2));
                    break;
                case CIRCLE:
                    shape = new Circle(b, color, fillColor, Math.abs(x1 - x2) / 2);
                    break;
                case CUBE:
                    shape = new Cube(b, color, fillColor, (int) (Math.abs(x1 - x2) / (1 + 1 / 2.82)));
                    break;
                case NULL:
                    return;
            }
            graphics.AddShape(shape);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void paint(java.awt.Graphics g) {
        super.paint(g);
        System.out.println("调用paint");
        paintComponents(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        super.paint(g);
        graphics.DrawShapes(g);
        g.setColor(Color.BLACK);
        countLabel.setText("   图形数量：" + graphics.ShapeCount()+"   ");
    }

    /**
     * 形状按钮的监听内部类
     */
    private static class MyActionListener implements ActionListener {
        OpenGLApp openGLApp;

        public MyActionListener(OpenGLApp paintFrame) {
            this.openGLApp = paintFrame;
            openGLApp.paintShape = true;
        }


        @Override
        public void actionPerformed(ActionEvent e) {

            openGLApp.paintShape = true;
            if(openGLApp.paintReshape){
                if(openGLApp.graphics.getShapes().getLast().getColor() == Color.pink){
                    openGLApp.graphics.getShapes().removeLast();
                }
//                openGLApp.repaint();
                /**
                 * 传送数据！
                 */
                openGLApp.sendData();
                openGLApp.paintReshape = false;
            }

            switch (e.getActionCommand()) {
                case "Circle" -> openGLApp.shapeType = ShapeType.CIRCLE;
                case "Rectangle" -> openGLApp.shapeType = ShapeType.RECTANGLE;
                case "Cube" -> openGLApp.shapeType = ShapeType.CUBE;
                case "Triangle" -> openGLApp.shapeType = ShapeType.TRIANGLE;
                case "Clear" -> {
                    openGLApp.graphics.Clear();
//                    openGLApp.repaint();
                    /**
                     * 传送数据
                     */
                    openGLApp.sendData();

                }
                case "Ellipse" -> openGLApp.shapeType = ShapeType.ELLIPSE;
                case "ReDo" -> {
                    if (openGLApp.graphics.ShapeCount() == 0) {
                        return;
                    }
                    openGLApp.graphics.RemoveEndShape();
//                    openGLApp.repaint();
                    /**
                     * 传送数据
                     */
                    openGLApp.sendData();
                }
            }
        }
    }

    /**
     * 鼠标监听类内部类
     */
    private static class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            OpenGLApp openGLApp = (OpenGLApp)e.getSource();

            if(openGLApp.paintReshape) {
                openGLApp.isMove = false;
                openGLApp.isChange=  false;
                if(openGLApp.graphics.getShapes().size()!=0 &&openGLApp.graphics.getShapes().getLast().getShapeType() == ShapeType.ReShapeRect){
                    openGLApp.graphics.getShapes().removeLast();
                }
                openGLApp.reShapeIndex = openGLApp.GetShapeIndex(new Point(e.getX(), e.getY()));
                if(openGLApp.reShapeIndex!=-1){
//                    System.out.println("您选择了 " + openGLApp.reShapeIndex + " " + openGLApp.graphics.getShapes().get(openGLApp.reShapeIndex));
                    Shape newS = new ReShapeRect(openGLApp.graphics.getShapes().get(openGLApp.reShapeIndex).getLocal(), Color.RED, openGLApp.w, openGLApp.h);
                    openGLApp.graphics.AddShape(newS);
//                    openGLApp.repaint();
                    /**
                     * 传送数据
                     */
                    openGLApp.sendData();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            OpenGLApp openGLApp = (OpenGLApp)e.getSource();
            openGLApp.isChange = false;
            openGLApp.isMove=  false;

            if(openGLApp.paintReshape){
                int x = e.getX();
                int y = e.getY();
//                w h;
                if(openGLApp.reShapeIndex == -1){
                    return;
                }
                Shape s = openGLApp.graphics.getShapes().get(openGLApp.reShapeIndex);
                double localX= s.getLocal().getX();
                double localY = s.getLocal().getY();
                if(x>=localX+openGLApp.w && x<=localX+openGLApp.w+20 && y>=localY+ openGLApp.h && y<=localY+ openGLApp.h+20){
                    openGLApp.isChange = true;
//                    System.out.println("change");
                    return;
                }else if(x>=localX && x<=localX+ openGLApp.w&& y>=localY&& y<=localY+openGLApp.h){
                    openGLApp.isMove = true;
//                    System.out.println("move");
                    return;
                };
            }

            if(openGLApp.paintShape){
                openGLApp.begin = openGLApp.getMousePosition();
            }


        }



        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);


            OpenGLApp openGLApp = (OpenGLApp) e.getSource();

            if(openGLApp.paintShape){
                openGLApp.end = new Point(e.getX(), e.getY());
                openGLApp.AddShapes();

                /**
                 * 传送数据
                 */
                openGLApp.sendData();
            }

            if(openGLApp.paintReshape){
//                System.out.println("release");
                if(openGLApp.reShapeIndex == -1){
                    return;
                }
                Shape s = openGLApp.graphics.getShapes().get(openGLApp.reShapeIndex);


                if(openGLApp.isMove){

                    s.setLocal(new Point(e.getX()-openGLApp.w/2, e.getY()-openGLApp.h/2));
                    if(openGLApp.graphics.getShapes().getLast().getShapeType() == ShapeType.ReShapeRect){
                        openGLApp.graphics.getShapes().removeLast();
                    }
                    openGLApp.paintReshape = false;
                    openGLApp.isMove = false;
                    openGLApp.isChange =false;
//                    openGLApp.repaint();
                    /**
                     * 传送数据
                     */
                    openGLApp.sendData();
                    //----------------------------

                }

                if(openGLApp.isChange){

                    int x1 = openGLApp.begin.x;
                    int x2 = e.getX();
                    int y1 = openGLApp.begin.y;
                    int y2 = e.getY();



                    switch (s.getShapeType()){
                        case CUBE:
                            Cube cube = (Cube) s;
                            cube.setWidth((int) (Math.abs(x1 - x2) / (1 + 1 / 2.82)));
                            break;
                        case TRIANGLE:
                            Triangle triangle = (Triangle) s;
                            triangle.setHeight(Math.abs(y1 - y2));
                            triangle.setWidth(Math.abs(x1 - x2));
                            break;
                        case RECTANGLE:
                            Rectangle rectangle = (Rectangle) s;
                            rectangle.setWidth(Math.abs(x1 - x2));
                            rectangle.setHeight(Math.abs(y1 - y2));
                            break;
                        case ELLIPSE:
                            Ellipse ellipse = (Ellipse) s;
                            ellipse.setHeight(Math.abs(y1 - y2));
                            ellipse.setWidth(Math.abs(x1 - x2));
                            break;
                        case CIRCLE:
                            Circle circle = (Circle) s;
                            circle.setR(Math.abs(x1 - x2) / 2);
                            break;
                    }
                    if(openGLApp.graphics.getShapes().getLast().getShapeType() == ShapeType.ReShapeRect){
                        openGLApp.graphics.getShapes().removeLast();
                    }
                    openGLApp.paintReshape = false;
                    openGLApp.isChange = false;
                    openGLApp.isMove = false;
//                    openGLApp.repaint();
                    /**
                     * 传送数据
                     */
                    openGLApp.sendData();

                }
            }




        }
    }

    /**
     * 颜色复选框的监听内部类
     */
    private static class ColorListener implements ActionListener {
        OpenGLApp openGLApp;

        public ColorListener(OpenGLApp openGLApp) {
            this.openGLApp = openGLApp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示颜色选取器对话框, 返回选取的颜色（线程将被阻塞, 直到对话框被关闭）
            Color color = JColorChooser.showDialog(openGLApp, "选取颜色", null);

            // 如果用户取消或关闭窗口, 则返回的 color 为 null
            if (color == null) {
                return;
            }

            openGLApp.color = color;
            /**
             * 传送数据
             */
            openGLApp.sendData();
        }

    }

    /**
     * 颜色复选框的监听内部类
     */
    private static class FillColorListener implements ActionListener {
        OpenGLApp openGLApp;

        public FillColorListener(OpenGLApp openGLApp) {
            this.openGLApp = openGLApp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示颜色选取器对话框, 返回选取的颜色（线程将被阻塞, 直到对话框被关闭）
            Color color = JColorChooser.showDialog(openGLApp, "选取颜色", null);

            // 如果用户取消或关闭窗口, 则返回的 color 为 null
            if (color == null) {
                return;
            }

            openGLApp.fillColor = color;

            /**
             * 传送数据
             */
            openGLApp.sendData();
        }

    }

    public void setGraphics(graph.Graphics graphics) {
        this.graphics = graphics;
    }
}

class AcceptThread extends  Thread{
    ObjectInputStream is;
    OpenGLApp openGLApp;

    public AcceptThread(ObjectInputStream is, OpenGLApp openGLApp) {
        this.is = is;
        this.openGLApp = openGLApp;
        start();
    }

    public void run(){
        while(true){
            try {
                graph.Graphics g = (graph.Graphics) is.readObject();
                openGLApp.setGraphics(g);
                openGLApp.setVisible(true);
                openGLApp.repaint();

            } catch (IOException e) {
                e.printStackTrace();
                openGLApp.showConnectError();
                stop();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                openGLApp.showConnectError();
                stop();
            }
        }

    }
}
