import java.awt.*;
import java.awt.event.InputEvent;


public class MainProgram implements Runnable {

    private SeleniumMethods sl;
    private MouseMovement ms;
    private ActionSequence as;
    private int yOffset=PropertiesHandler.getYOffset();

    MainProgram() {

    }

    public void run() {
        screenCalibrationChecker();
        testJunk();
    }

    // Goes to example.com set the cursor at the element positions and adds at the Y Axis 1 pixel
    // until it hits the button and redirects to another page
    // then does a simple subtraction finish-start and gets the YOffset
    private void screenCalibration() throws Exception {
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

    // Checks if  the user wants to calibrate or no calibration has been done before
    // and then sets the properties value to false and sets the YOffset Propertie
    private void screenCalibrationChecker() {
        if (PropertiesHandler.isCalibrate() || PropertiesHandler.getYOffset()==0) {
            try {
                screenCalibration();
            } catch (Exception e) {
                e.printStackTrace();
            }
            PropertiesHandler.setCalibrate(false);
            PropertiesHandler.setYOffset(yOffset);
        }
    }

    private void testJunk () {
        sl=new SeleniumMethods();
        ms=new MouseMovement(sl);
        as = new ActionSequence(sl,ms);

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
