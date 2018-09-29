import java.awt.*;
import java.awt.event.InputEvent;


public class MainProgram implements Runnable {

    SeleniumMethods sl;
    MouseMovement ms;
    ActionSequence as;
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
        as = new ActionSequence(sl,ms);
//        sl.pageOpener("https://www.example.com");
//        sl.pageOpener("https://amiunique.org/");
//        ms.scrollToView("//*[@id=\"link\"]");
//        ms.onLeftClick();
//        ms.randomDelay(10000,15000);
//        ms.scrollToView("//*[@id=\"detBut2\"]");
//        ms.onLeftClick();
        ms.moveMouseToMain();
        as.openBet();
        System.out.println("opened bet");
        ms.randomDelay(2000,5000);
        as.languageScreen();
        System.out.println("ellinika");
        ms.randomDelay(2000,5000);
        as.setCredentials();
        System.out.println("log in");
        ms.randomDelay(3000,7000);
        as.inPlay();
        ms.randomDelay(2000,5000);
        as.basketCategory();
        System.out.println("basket category");
    }
}
