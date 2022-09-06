package server;

import graph.Graphics;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-23 19:14
 */

public class GUIServer {
    static Graphics g;
    static LinkedList<ServerThread> threads = new LinkedList<>();
    static int cnt = 0;

    public void run(){
        try {
            System.out.println("等待连接..");
            ServerSocket serverSocket = new ServerSocket(5500);
            Socket s = null;
            while(true){
                s = serverSocket.accept();
                ServerThread serverThread = new ServerThread(s);
                serverThread.setName("画板" + (cnt+1));
                System.out.println("画板" + (cnt+1) +"连接成功！");
                threads.add(serverThread);
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renew(){
        for(int i = 0; i<cnt; i++){
            threads.get(i).renew(g);
        }
        save();
    }

    public static void save(){
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Data.txt"));
            os.writeObject(g);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        GUIServer guiServer = new GUIServer();
        guiServer.run();
    }
}

class ServerThread extends Thread{
    private Socket s;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public ServerThread(Socket socket) throws IOException{
        super();
        this.s = socket;
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        start();
    }



    public void renew(Graphics g){
        try {
            os.writeObject(g);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream("Data.txt"));
                GUIServer.g = (Graphics) is.readObject();
                is.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            GUIServer.renew();

            while(true){
                Object object = is.readObject();
                if(object instanceof Graphics){
                    Graphics g = (Graphics) object;
                    GUIServer.g = g;
                    GUIServer.renew();
                }else{
                    break;
                }

            }

            System.out.println(this.getName() + "连接关闭！");


            is.close();
            os.close();
            s.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
