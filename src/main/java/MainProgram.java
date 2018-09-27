import java.awt.*;
import java.awt.event.InputEvent;

public class MainProgram implements Runnable {

    SeleniumMethods sl;

    public MainProgram(){

    }

    public void screenCalibration() throws Exception{

        Robot robot=new Robot();
        int y= sl.getCoordinatesY("/html/body/div/p[2]/a");
        int x= sl.getCoordinatesX("/html/body/div/p[2]/a");

        while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(1000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println(y);
            y++;
        }
    }

    public void run(){
        sl=new SeleniumMethods();
        sl.pageOpener("https://www.skroutz.gr/c/40/kinhta-thlefwna.html?from=families");
        while (true) {
            System.out.println(sl.getScrolledY());
        }

    }
}
