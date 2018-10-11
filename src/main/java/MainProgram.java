import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainProgram implements Runnable {

    private SeleniumMethods sl;
    private MouseMovement ms;
    private ActionSequence as;
    private int yOffset= PropertiesHandler.getYOffset();
    boolean removeFromList =false;
    static boolean isBeingPlayed=true;

    MainProgram() {

    }
    // Σουηδία - Basketligan Γυναίκες---Χόμπσο Μπάσκετ Γυναικών---2---U 160.5

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
        //sl.getText("/html/body/div/p[2]/a").equals("More information...")
        while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(2000);
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

    // TODO : Fix the array list and tip handling and que
    private void testJunk () {
        // Allows only one thread to be active at any given time
        ExecutorService x = Executors.newFixedThreadPool(1);
        sl=new SeleniumMethods();
        ms=new MouseMovement(sl);
        as = new ActionSequence(sl,ms);
        openToBasket();

        while(!ReadTerminal.stopProgram) {
            // tipList isnt empty
            if (!Main.tipList.isEmpty()) {
                // For every tip in the list do
                Vector<String> tempTipList= Main.tipList;
                for (String tip :tempTipList) {
                    x.execute(new TipHandler(tip,sl,ms,this));
                    while(isBeingPlayed) {

                    }
                }

            }
        }
    }

    private void openToBasket(){
        ms.moveMouseToMain();
        as.openBet();
        System.out.println("opened bet");
        ms.randomDelay(2000,5000);
        as.languageScreen();
        System.out.println("ellinika");
        ms.randomDelay(2000,5000);
        as.setCredentials();
        System.out.println("log in");
        ms.randomDelay(4000,5000);
        as.inPlay();
        ms.randomDelay(2000,5000);
        as.basketCategory();
        System.out.println("basket category");
        ms.randomDelay(3000,7000);
        as.moveMouseToMatchesPane();
        ms.randomDelay(2000,5000);
    }

    private void testSkroutz() {

    }


}