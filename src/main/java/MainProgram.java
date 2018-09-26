import java.awt.*;
import java.awt.event.InputEvent;

public class MainProgram implements Runnable{
    public MainProgram(){

    }
    public void run(){
        SeleniumMethods sl=new SeleniumMethods();
        sl.pageOpener("http://www.example.com");
        try {
            Thread.sleep(5000);
//            sl.onClick("/html/body/div/p[2]/a");
//            sl.getCoordinates("/html/body/div/p[2]/a");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Robot robot=new Robot();

            int y=sl.getCoordinatesY("/html/body/div/p[2]/a");
//            while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
//                robot.mouseMove(sl.getCoordinatesX("/html/body/div/p[2]/a"),y);
//                robot.mousePress(InputEvent.BUTTON1_MASK);
//                robot.delay(1000);
//                robot.mouseRelease(InputEvent.BUTTON1_MASK);
//                System.out.println(y);
//                y++;
//            }
            y=342;
            robot.mouseMove(sl.getCoordinatesX("/html/body/div/p[2]/a"),y);
            System.out.println(y);

        } catch (AWTException e) {
            e.printStackTrace();
        }


//        sl.pageCloser();

    }
}
