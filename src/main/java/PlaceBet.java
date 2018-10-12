import java.util.Random;

class PlaceBet {

    //TODO MoveMouse to Iframe Random
    // Press to confirm the bet
    // TODO : Change so it Accepts Greek

    private SeleniumMethods sl;
    private MouseMovement ms;
    private double betSize;
    private String xpath;

    PlaceBet(SeleniumMethods sl0, MouseMovement ms0, double betSize0, String xpath0) {
        sl=sl0;
        ms=ms0;
        betSize=betSize0;
        xpath=xpath0;
    }

    // Searches for the textView and enters the amount of money to be played
    // then clicks the button for the bet to be placed
    void placeBetSize () {
        initializer();
        ms.randomDelay(1000,2000);
        setBetSizeToPlace();
        ms.randomDelay(1000,2000);
        clickBetConfirm();
    }

    private void initializer() {
        ms.moveMouseToXIframe();
        PropertiesHandler.setiFramePoint(sl.getCoordinates(PropertiesXpath.getProp("IFRAME")));
        PropertiesHandler.setiFramePointY(sl.getCoordinatesY(PropertiesXpath.getProp("IFRAME")));
        PropertiesHandler.setiFramePointX(sl.getCoordinatesX(PropertiesXpath.getProp("IFRAME")));
        sl.switchFrame(PropertiesXpath.getProp("IFRAME_ID"));
    }

    private void setBetSizeToPlace() {
        try {
            ms.scrollToViewIFRAME(PropertiesXpath.getProp("BETSIZE_INPUT"));
            ms.onLeftClick();
            ms.typeString(String.valueOf(betSize));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bet Input Not Found ---->PlaceBet : setBetSizeToPlace()");
        }
    }

    private void clickBetConfirm() {
        try {
            ms.scrollToViewIFRAME("//*[contains(text(),'Στοιχηματίστε')]");
            ms.onLeftClick();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bet has changed or has been Blocked ----> PlaceBet : clickBetConfirm()");
        }
    }

    void closeBetWindow() {
        int random=new Random().nextInt(3);
        if(random==0) {
            // TODO : TEST IT
            ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_DIAGRAFH"));
            ms.randomDelay(300, 1000);
            ms.onLeftClick();
            sl.switchToDefaultFrame();
        }
        else if(random==1) {
            // TODO : TEST IT

            ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_X_BUTTON_DIAGRAFH"));
            ms.randomDelay(300, 1000);
            ms.onLeftClick();
            sl.switchToDefaultFrame();
        }
         else if(random==2) {
            // TODO : TEST IT
            sl.switchToDefaultFrame();
            clickOnBet();
        }
         else{
                // TODO : TEST IT
                ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_APODOXI_ALLAGWN"));
                ms.randomDelay(300,1000);
                ms.onLeftClick();
                sl.switchToDefaultFrame();
        }
    }

    void clickOnBet() {
        // Clicks on the Bet value the Y handler found
        // Open Window to bet in -- Press the bet found
        ms.scrollToView(xpath);
        ms.randomDelay(300,700);
        ms.onLeftClick();
    }


}
