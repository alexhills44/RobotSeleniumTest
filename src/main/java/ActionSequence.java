import java.util.Random;

/**
 * This Class must contain only predefined mouse movements and clicks
 */

class ActionSequence {

    private SeleniumMethods sl;
    private MouseMovement ms;

    private static String username = PropertiesHandler.getUsrNameB();
    private static String password = PropertiesHandler.getPswB();

    ActionSequence(SeleniumMethods sl0, MouseMovement ms0) {
        sl=sl0;
        ms=ms0;
        //getUserInfo();
    }

    // Call selenium method to open the browser at the given url
    void openBet() {
        sl.pageOpener("https://www.bet365.gr/");
    }

    // Scrolls the mouse to the "Ελληνικά" button
    void languageScreen () {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 1.5*60*NANOSEC_PER_SEC && !stop) {
//        ms.scrollToView(PropertiesXpath.getProp("ELLINIKA"));
            try {
                System.out.println("language");
                ms.scrollToView("//*[contains(text(), 'Ελληνικά')]");
                ms.onLeftClick();
                stop=true;
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error 404 : Element not found ----> languageScreen()");
                System.out.println("Error 404 : Element not found ----> languageScreen()");
            }
        }
    }////*[contains(text(), 'Διαφορά Νίκης')]

    // Scrolls the mouse at the userName textView inputs the userName,
    // does the exact same thing for the passWord
    // and presses enter or the go button 50% chance
    void setCredentials() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 1.5*60*NANOSEC_PER_SEC && !stop) {
            try {
                // username element
                System.out.println("username");
                ms.scrollToView(PropertiesXpath.getProp("SET_USERNAME"));
                ms.onLeftClick();
                ms.typeString(username);
                // password element
                System.out.println("password");
                ms.scrollToView(PropertiesXpath.getProp("SET_PASSWORD"));
                ms.onLeftClick();
                ms.typeString(password);
                // press enter or click go Button
                Random rand = new Random();
                if(rand.nextBoolean()) {
                    System.out.println("go");
                    ms.pressEnter();
                }else {
                    System.out.println("go");
                    ms.scrollToView(PropertiesXpath.getProp("GO_BUTTON_LOG_IN"));
                    ms.onLeftClick();
                }
                stop=true;
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error 404 : Element not found ----> setCredentials()");
                System.out.println("Error 404 : Element not found ----> setCredentials()");
            }
        }

    }

    // Finds the "Σε-Εξέλιξη" button and clicks it
    void inPlay() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 1.5*60*NANOSEC_PER_SEC && !stop) {
            try {
                System.out.println("in-play");
                // Σε-Εξέλιξη element
//                ms.scrollToViewForZero(PropertiesXpath.getProp("IN_PLAY"));
                ms.scrollToViewForZero("/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
                ms.onLeftClick();
                stop=true;
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error 404 : Element not found ----> inPlay()");
                System.out.println("Error 404 : Element not found ----> inPlay()");
            }
        }
    }

    // Searches for the button that contains the text "Μπάσκετ"
    // if it is in view then it clicks it
    // otherwise it clicks the scroll button until it finds it
    // TODO : has to be checked if it is not in View
    void basketCategory () {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 1.5*60*NANOSEC_PER_SEC && !stop) {
            System.out.println("basket category");
            try {
                sl.getText("//*[contains(text(), 'Ποδόσφαιρο')]");
                stop=true;
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error 404 : Element not found ----> basketCategory()");
                System.out.println("Error 404 : Element not found ----> basketCategory()");
            }
        }
        while (!sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[1]").contains("Μπάσκετ")) {
            if(sl.getCoordinatesX("//*[contains(text(), 'Μπάσκετ')]")>sl.getElementSurface("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div[2]").getX()) {
                System.out.println("Not in View");
                ms.scrollToView(PropertiesXpath.getProp("RIGHT_ARROW_BUTTON_CATEGORY"));
                ms.onLeftClick();
                Logger.logStringtoLogFile("Not in View ----> basketCategory()");
            }
            ms.scrollToView("//*[contains(text(), 'Μπάσκετ')]");
            System.out.println("clicked on basket");
            ms.onLeftClick();
        }
    }

    // this is done so the mouse scrolls on the MatchPane
    void moveMouseToMatchesPane() {
        ms.scrollToView("/html/body/div[1]/div/div[2]/div[1]");
    }
}
