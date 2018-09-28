import java.awt.*;
import java.awt.event.InputEvent;


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
//        sl.pageOpener("https://www.example.com");
        sl.pageOpener("https://www.skroutz.gr");
        ms.scrollToView("//*[@id=\"search-bar-input\"]");
        ms.onLeftClick();
        ms.typeString("iphone");
        ms.pressEnter();
    }
}
