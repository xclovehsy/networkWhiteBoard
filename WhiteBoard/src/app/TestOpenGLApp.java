package app;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-04 21:16
 */

public class TestOpenGLApp {
    public static void main(String[] args) {
//        GuiServer guiServer = new GuiServer();
//        guiServer.run();


        OpenGLApp openGLApp = new OpenGLApp("�ҵĻ���");
//        openGLApp.connect();
        openGLApp.setVisible(true);
        openGLApp.connect();
    }
}
