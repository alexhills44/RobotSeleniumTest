import java.awt.*;
import java.util.Random;

/**
 * This Class must contain only predefined mouse movements and clicks
 */

public class ActionSequence {

    private SeleniumMethods sl;
    private MouseMovement ms;

    private static String username =PropertiesHandler.getUsrNameB();
    private static String password =PropertiesHandler.getPswB();
    String valueCatched;


    ActionSequence(SeleniumMethods sl0,MouseMovement ms0) {
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
//        ms.scrollToView(PropertiesXpath.getProp("ELLINIKA"));
        ms.scrollToView("//*[contains(text(), 'Ελληνικά')]");
        ms.onLeftClick();
    }

    // Scrolls the mouse at the userName textView inputs the userName,
    // does the exact same thing for the passWord
    // and presses enter or the go button 50% chance
    void setCredentials() {
        // username element
        new PropertiesXpath();
        ms.scrollToView(PropertiesXpath.getProp("SET_USERNAME"));
        ms.onLeftClick();
        ms.typeString(username);
        // password element
        ms.scrollToView(PropertiesXpath.getProp("SET_PASSWORD"));
        ms.onLeftClick();
        ms.typeString(password);
        // press enter or click go Button
        Random rand = new Random();
        if(rand.nextBoolean()) {
            ms.pressEnter();
        }else {
            ms.scrollToView(PropertiesXpath.getProp("GO_BUTTON_LOG_IN"));
            ms.onLeftClick();
        }
        //Must be Deleted
        ms.pressEnter();
    }

    // Finds the "Σε-Εξέλιξη" button and clicks it
    void inPlay() {
        // Σε-Εξέλιξη element
//        ms.scrollToViewForZero(PropertiesXpath.getProp("IN_PLAY"));
        ms.scrollToViewForZero("/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
        ms.onLeftClick();

    }

    // Searches for the button that contains the text "Μπάσκετ"
    // if it is in view then it clicks it
    // otherwise it clicks the scroll button until it finds it
    // TODO : has to be checked if it is not in View
    void basketCategory () {
        boolean isRunning=true;
        while (isRunning) {
            try {
//                ms.scrollToView(PropertiesXpath.getProp("BASKET_CATEGORY"));
                ms.scrollToView("//*[contains(text(), 'Μπάσκετ')]");
                ms.onLeftClick();
                isRunning=false;
            }catch (Exception e) {
                new PropertiesXpath();
                System.out.println("Not in View");
                ms.scrollToView(PropertiesXpath.getProp("RIGHT_ARROW_BUTTON_CATEGORY"));
                ms.onLeftClick();
            }
        }

    }

    // Searches for the textView and enters the amount of money to be played
    // then clicks the button for the bet to be placed
    void placeBetSize(float betSize) {
        // Place the value we want to bet
        ms.moveMouseToXIframe();
        PropertiesHandler.setiFramePoint(sl.getCoordinates(PropertiesXpath.getProp("IFRAME")));
        PropertiesHandler.setiFramePointY(sl.getCoordinatesY(PropertiesXpath.getProp("IFRAME")));
        PropertiesHandler.setiFramePointX(sl.getCoordinatesX(PropertiesXpath.getProp("IFRAME")));
        sl.switchFrame(PropertiesXpath.getProp("IFRAME_ID"));

        try {
            ms.scrollToViewIFRAME(PropertiesXpath.getProp("BETSIZE_INPUT"));
            ms.onLeftClick();
            ms.typeString(String.valueOf(betSize));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bet Input Not Found");
        }
        //TODO MoveMouse to Iframe Random
        // Press to confirm the bet
        // TODO : Change so it Accepts Greek
        try {
            ms.randomDelay(1000,2000);
            ms.scrollToViewIFRAME("//*[contains(text(),'Στοιχηματίστε')]");
            ms.onLeftClick();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bet has changed or has been Blocked");
        }
    }

    // then if the bet was succesful click ok
    // else compare the current value with the given value
    Boolean betStatus() {
        return sl.getText(PropertiesXpath.getProp("BW_INFO")).contains("ΠαίχΘηκε");
    }

    // Opens and fills the close Amount
    // constant = defines where the request came from
    // valueCatched = get the Value that Success bet Window says we placed it at
    // betSize = get amount of money placed on that bet (BET_SIZE*betMulty)
    void autoClose(float odds,float betsize) {
        sl.switchToDefaultFrame();
        ms.scrollToView(PropertiesXpath.getProp("STOIXHMATA"));
        ms.onLeftClick();
        ms.randomDelay(2000,5000);
        ms.scrollToView(PropertiesXpath.getProp("ANOIXTA"));
        ms.onLeftClick();
        ms.randomDelay(2000,5000);

        // Try to close Match if it is single
        try {
            sl.getText(PropertiesXpath.getProp("BOX_SINGLE_ANOIXTA"));
            //Press Cog
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
            ms.randomDelay(2000,4000);
            // Press the textBox to enter close Statement
            ms.scrollToView(PropertiesXpath.getProp("PLACE_CLOSE_STATEMENT_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(2000,4000);
            // Type close Amount
            if (odds<0) {
                String closeStatement = String.valueOf(setClosingAmount("1.8",betsize));
                ms.typeString(closeStatement);
            }else {
                String closeStatement = String.valueOf(setClosingAmount(String.valueOf(odds),betsize));
                ms.typeString(closeStatement);
            }
            ms.randomDelay(2000,4000);
            // confirm the Close Statement
            ms.scrollToView(PropertiesXpath.getProp("DIMIOURGISTE_SINTHIKH_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(2000,4000);
            // press the cog again to close the popup window
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
            ms.randomDelay(2000,4000);
            // Make statement
            ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[4]/div/div/div/div[2]/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div/div[3]/div[4]/span
            ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[4]/div/div/div/div[2]/div[3]/div[1]/div[3]/div/div[3]/div[2]/div/div/div/div[3]/div[4]/span
            //
        }catch(Exception e) {
            System.out.println("More Matches are Open");
            sl.getText("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[4]/div/div/div/div[2]/div[3]/div[1]");
        }
    }

    // Passes the Closing amount according to the odd Catched
    private int setClosingAmount (String odd,float betSize) {
        float odds = Float.valueOf(odd);
        if (odds>=8) {
            return (int)(3*betSize);
        }else if(odds>=6) {
            return (int)(2.5*betSize);
        }else if (odds>2.6) {
            return (int)(2.3*betSize);
        }else if (odds>2) {
            return (int)(2*betSize);
        }else if (odds>=1.8) {
            return (int)(1.7*betSize);
        }else {
            return (int)(1.5*betSize);
        }
    }

    //Contains all the actions to place the Close Condition
    private void autoCloseActions (String odd,float betSize,int xpathTableNumber) {

    }

    //Gets the odd for the given table Element
    private String getOddFromAnoiktaBox (int xpathTableNumber) {
        return sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1") + xpathTableNumber + PropertiesXpath.getProp("BW_BOX_MATCH_PART_2") + PropertiesXpath.getProp("GET_ODD_ANOIKTA_BOX"));
    }

}
