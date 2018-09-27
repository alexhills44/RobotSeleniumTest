import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class MainProgram implements Runnable {

    SeleniumMethods sl;
    MouseMovement ms;
    int yOffset=77;

    public MainProgram(){

    }

    public void screenCalibration() throws Exception{
        sl=new SeleniumMethods();
        sl.pageOpener("https://www.example.com");
        Robot robot=new Robot();
        int y= sl.getCoordinatesY("/html/body/div/p[2]/a");
        int y1=sl.getCoordinatesY("/html/body/div/p[2]/a");
        int x= sl.getCoordinatesX("/html/body/div/p[2]/a");

        while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(1000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println(y);
            y++;
            // save y so we can get it at startup
            //get distance
        }
        yOffset=y-y1;
    }



    public void run(){
        sl=new SeleniumMethods();
        ms=new MouseMovement(sl);
        sl.pageOpener("https://www.skroutz.gr/c/40/kinhta-thlefwna.html");
        //"/html/body/main/div[1]/section/div[2]/div/div/ul/li[1]/a"
        //sl.getElementSurface("/html/body/div[2]/ol/li[1]");
        //randomCursorMoveMethod1("/html/body/main/div[1]/section/div[2]/div/div/ul/li[1]/a");
        ms.scrollToView("/html/body/div[3]/div/div[2]/a");
        ms.onLeftClick();
//        try {
//            Robot robot = new Robot();
//            robot.mouseWheel(+1000);
//            Thread.sleep(1000);
//            System.out.println(sl.getScrolledY());
//            Thread.sleep(2000);
//            robot.mouseWheel(-1000);
//            Thread.sleep(1000);
//            System.out.println(sl.getScrolledY());
//        } catch (AWTException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
