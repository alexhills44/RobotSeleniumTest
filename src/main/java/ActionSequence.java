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
    void autoClose(String teamName,int constant,float betsize) {
        // Change window Stoixhmata/Anoixta
        ms.scrollToView(PropertiesXpath.getProp("STOIXHMATA"));
        ms.onLeftClick();
        ms.randomDelay(400,700);
        ms.scrollToView(PropertiesXpath.getProp("ANOIXTA"));
        ms.onLeftClick();
        boolean running=true;
        // points on which element of the table we are looking
        int i=0;
        // Runs until it finds exception or until it finds what it is looking for
        while (running) {
            i++;
            try {
                //if match contains team name
                if (sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains(teamName)) {
                    // Checks where the request came from
                    // HandicapHandler
                    if (constant==1) {
                        // Χάντικαπ 2πλής επιλογής
                        if (sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains("Χάντικαπ 2πλής επιλογής") &&
                                //Checks if we are talking about the same bet by comparing the value that it has been played at
                                sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains(valueCatched) ) {
                            //Perform standard mouse movement and clicks and type in the value
                            //Closing Condition has been Placed
                            autoCloseActions(getOddFromAnoiktaBox(i),betsize,i);
                            running=false;
                        }
                        //OverUnderHandler
                    } else if (constant==2) {
                        // Συνολικά 2πλης επιλογής
                        if (sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains("Συνολικά 2πλης επιλογής") &&
                                //Checks if we are talking about the same bet by comparing the value that it has been played at
                                sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains(valueCatched) ) {
                            //Perform standard mouse movement and clicks and type in the value
                            //Closing Condition has been Placed
                            autoCloseActions(getOddFromAnoiktaBox(i),betsize,i);
                            running=false;
                        }
                        //WinHandler
                    }else if (constant==3) {
                        // Να Νικήσει τον Αγώνα
                        if (sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains("Να Νικήσει τον Αγώνα") &&
                                //Checks if we are talking about the same bet by comparing the value that it has been played at
                                sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1")+ i+PropertiesXpath.getProp("BW_BOX_MATCH_PART_2")).contains(valueCatched) ) {
                            //Perform standard mouse movement and clicks and type in the value
                            //Closing Condition has been Placed
                            autoCloseActions(getOddFromAnoiktaBox(i),betsize,i);
                            running=false;
                        }
                    }
                }
            } catch (Exception e) {
                running=false;
            }
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
        ms.scrollToView(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1") + xpathTableNumber + PropertiesXpath.getProp("BW_BOX_MATCH_PART_2") + PropertiesXpath.getProp("KLEISE_STOIXHMA_ATTACHMENT"));
        ms.onLeftClick();
        ms.randomDelay(500,1000);
        ms.scrollToView(PropertiesXpath.getProp("AYTO_CLOSE_AMOUNT"));
        ms.onLeftClick();
        ms.typeString(String.valueOf(setClosingAmount(odd,betSize)));
        ms.randomDelay(500,1000);
        ms.scrollToView(PropertiesXpath.getProp("CREATE_CONDITION_BUTTON"));
        ms.onLeftClick();
    }

    //Gets the odd for the given table Element
    private String getOddFromAnoiktaBox (int xpathTableNumber) {
        return sl.getText(PropertiesXpath.getProp("BW_BOX_MATCH_PART_1") + xpathTableNumber + PropertiesXpath.getProp("BW_BOX_MATCH_PART_2") + PropertiesXpath.getProp("GET_ODD_ANOIKTA_BOX"));
    }

}
