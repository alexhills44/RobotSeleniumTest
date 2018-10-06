import java.awt.*;
import java.net.SocketTimeoutException;
import java.util.Random;

/**
 * This Class must contain only predefined mouse movements and clicks
 */

public class ActionSequence {

    private SeleniumMethods sl;
    private MouseMovement ms;

    private static String username =PropertiesHandler.getUsrNameB();
    private static String password =PropertiesHandler.getPswB();

    private float betSize;
    private String oddsCatched,valueCatched;



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
                System.out.println("Not in View");
                ms.scrollToView(PropertiesXpath.getProp("RIGHT_ARROW_BUTTON_CATEGORY"));
                ms.onLeftClick();
            }
        }

    }

    // Searches for the textView and enters the amount of money to be played
    // then clicks the button for the bet to be placed
    void placeBetSize() {
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

    // Opens and fills the close Amount
    // constant = defines where the request came from
    // valueCatched = get the Value that Success bet Window says we placed it at
    // betSize = get amount of money placed on that bet (BET_SIZE*betMulty)
    void autoClose(String[] tipArray) {
        sl.switchToDefaultFrame();
        ms.scrollToView(PropertiesXpath.getProp("STOIXHMATA"));
        ms.onLeftClick();
        try {
            ms.randomDelay(2000,4000);
            ms.scrollToView(PropertiesXpath.getProp("ANOIXTA"));
        } catch (Exception e) {
            ms.scrollToView("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[4]/div/div/div/div[1]/div/div/div[2]/div/div[3]");
        }
        ms.onLeftClick();
        ms.randomDelay(1000,2000);
        // Try to close Match if it is single
        try {
            System.out.println("Only one match is open");
            sl.getText(PropertiesXpath.getProp("BOX_SINGLE_ANOIXTA"));
            //Press Cog
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
            ms.randomDelay(1000,2000);
            // Press the textBox to enter close Statement
            ms.scrollToView(PropertiesXpath.getProp("PLACE_CLOSE_STATEMENT_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(1000,2000);
            // Type close Amount
            if (tipArray[3].contains("+")|tipArray[3].contains("-")|tipArray[3].contains("O")|tipArray[3].contains("U")) {
                String closeStatement = String.valueOf(setClosingAmount("1.8",betSize));
                ms.typeString(closeStatement);
            }else {
                String closeStatement = String.valueOf(setClosingAmount(tipArray[3],betSize));
                ms.typeString(closeStatement);
            }
            ms.randomDelay(1000,2000);
            // confirm the Close Statement
            ms.scrollToView(PropertiesXpath.getProp("DIMIOURGISTE_SINTHIKH_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(1000,2000);
            // press the cog again to close the popup window
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
            ms.randomDelay(1000,2000);
        }catch(Exception e) {
            System.out.println("More Matches are Open");
            // shows us which bet we are looking at
            int betPointer =0;
            // Tells the while Loop when to Stop
            boolean isRunning=true;
            // Runs until there is no other bet to look for
            while (isRunning) {
                betPointer++;
                try {
                    // Get text from the box that betPointer is pointing
                    String boxInfo = sl.getText("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[5]/div/div/div/div[2]/div[3]/div["+betPointer+"]");
                    // Xpath for the Cog button according to betPointer
                    System.out.println("has passed this point");
                    String xpathCog = "/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[7]/div/div/div/div[2]/div[3]/div["+betPointer+"]/div[3]/div/div[3]/div[2]/div/div";
                    //if it is in here it means it is a HANDICAP
                    if ((tipArray[3].contains("+")|tipArray[3].contains("-"))&&boxInfo.contains(oddsCatched)&&boxInfo.contains(valueCatched)&&boxInfo.contains(tipArray[1])) {
                        pressSetConfirmCogMultiple(xpathCog);
                        System.out.println("---------------- A -----------------");
                    //if it is in here it means it is a O/U
                    }else if ((tipArray[3].contains("O")|tipArray[3].contains("U"))&&boxInfo.contains(oddsCatched)&&boxInfo.contains(valueCatched)&&boxInfo.contains(tipArray[1])) {
                        pressSetConfirmCogMultiple(xpathCog);
                        System.out.println("---------------- B -----------------");
                     //if it is in here it means it is a WIN
                    }else if (boxInfo.contains(oddsCatched)&&boxInfo.contains(tipArray[1])){
                        pressSetConfirmCogMultiple(xpathCog);
                        System.out.println("---------------- C -----------------");
                    }

                }catch (Exception ex) {
                    // if there is no box in that position stop looking
                    System.out.println("has passed this point and exited");
                    isRunning=false;
                }
            }

        }
    }

    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[5]/div/div/div/div[2]/div[3]/div[1]
    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[5]/div/div/div/div[2]/div[3]/div[2]
    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[5]/div/div/div/div[2]/div[3]/div[3]
    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[5]/div/div/div/div[2]/div[3]/div[4]





    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[3]/div/div/div/div[2]/div[3]/div[1]/div[3]/div
    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[7]/div/div/div/div[2]/div[3]/div["+betPointer+"]/div[3]"
    ///html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[3]/div/div/div/div[2]/div[3]/div[2]/div[3]
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

    public void getOddAndValueFromBetPlacedIFRAME() {
        // Box that displays text when the bet is succesful
        String[] betInfo = sl.getText("/html/body/div[1]/div/ul/li[7]").split("\n");
        System.out.println("---------------- 1 -----------------");

        for(String s:betInfo) {
            if (s.contains("Νικητής Αγώνα")) {
                getOddFromBet(betInfo);
                valueCatched="";
                break;
            }else if(s.contains("Χάντικαπ 2πλής επιλογής")) {
                getOddFromBet(betInfo);
                getValueFromBet(betInfo);
                break;
            }else if (s.contains("Συνολικά 2πλής επιλογής")) {
                getOddFromBet(betInfo);
                getValueFromBet(betInfo);
                break;
            }
        }
    }

    private void getOddFromBet(String[] betInfo) {
        System.out.println("---------------- 2 -----------------");
        String[] lineArray = betInfo[0].split(" ");
        try {
            oddsCatched = lineArray[lineArray.length-1];
        } catch (Exception e) {
            System.out.println("Couldnt find oddCatched at lineArray[last]");
        }
    }

    private void getValueFromBet(String[] betInfo) {
        System.out.println("---------------- 3 -----------------");
        String[] lineArray = betInfo[0].split(" ");
        try {
            valueCatched = lineArray[lineArray.length-2];
        } catch (Exception e) {
            System.out.println("Couldnt find valueCatched at lineArray[last]");
        }
    }

    private void pressSetConfirmCogMultiple(String xpathCog) {
        System.out.println("---------------- 4 -----------------");
        // cogInput extension xpath
        String cogInput="/div/div[4]/div[1]/div[1]/span/input";
        // cogConfirm extension xpath
        String cogConfirm="/div/div[4]/div[3]/span";
        ms.scrollToView(xpathCog);
        ms.onLeftClick();
        ms.randomDelay(1000,2000);
        ms.scrollToView(xpathCog+cogInput);
        System.out.println("---------------- 5 -----------------");
        ms.onLeftClick();
        ms.typeString(String.valueOf(setClosingAmount(oddsCatched,betSize)));
        ms.randomDelay(1000,2000);
        System.out.println("---------------- 6 -----------------");
        ms.scrollToView(xpathCog+cogConfirm);
        ms.onLeftClick();
    }

    public void setBetSize(float betSize0) {
        betSize=betSize0;
    }

}
