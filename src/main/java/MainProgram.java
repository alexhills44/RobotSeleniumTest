import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Scanner;


public class MainProgram implements Runnable {

    private SeleniumMethods sl;
    private MouseMovement ms;
    private ActionSequence as;
    private int yOffset=PropertiesHandler.getYOffset();

    MainProgram() {

    }

    public void run() {
        screenCalibrationChecker();
        testSkroutz();
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
        //sl.getText("/html/body/div/p[2]/a").equals("More information...")
        while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(1000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println(y);
            y++;
            yOffset=y-y1;
        }
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
            PropertiesHandler.endProp();
            sl.pageCloser();
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
        ms.randomDelay(5000,10000);
        TipHandler tipHandler;
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            tipHandler = new TipHandler(scan.nextLine(),sl,ms,as);
            tipHandler.run();
        }


    }

    private void testSkroutz() {
//        sl=new SeleniumMethods();
//        ms=new MouseMovement(sl);
//        as = new ActionSequence(sl,ms);
//        sl.pageOpener("https://www.skroutz.gr/");
//        ms.scrollToView("//*[@id=\"search-bar-input\"]");
//        ms.onLeftClick();
////        System.out.println(PropertiesHandler.getPswB());
//        ms.typeString(PropertiesHandler.getPswB());

        System.out.println(new PropertiesXpath().getProp("SET_USERNAME"));





//        ms.scrollToView("/html/body/header/div/form/p/button");
//        ms.onLeftClick();
//        while (true) {
////            System.out.println(sl.getScrolledY());
////            ms.randomDelay(2000,2500);
////            System.out.println(sl.getScrolledY());
////            ms.randomDelay(2000,2500);
//            ms.scrollToView("/html/body/footer/div/div[7]/p/a[4]");
//        }

    }
}
