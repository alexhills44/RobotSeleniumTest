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
        try {
            initializer();
            ms.randomDelay(1000,2000);
            setBetSizeToPlace();
            ms.randomDelay(1000,2000);
            clickBetConfirm();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            System.out.println("Bet Input Not Found ---->PlaceBet : setBetSizeToPlace()");
        }
    }

    private void clickBetConfirm() {
        // Try for 1.5 min
            try {
                ms.scrollToViewIFRAME("//*[contains(text(),'Στοιχηματίστε')]");
                ms.onLeftClick();

            } catch (Exception e) {
                System.out.println("Could not find Button 'Στοιχηματίστε'----> PlaceBet : clickBetConfirm()");
            }
    }

    void closeBetWindow() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        switch (new Random().nextInt(3)) {
            case 0:

                // Try for 1.5 min
                while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
                    try {
                        // TODO : TEST IT
                        ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_DIAGRAFH"));
                        ms.randomDelay(300, 1000);
                        ms.onLeftClick();
                        sl.switchToDefaultFrame();
                        stop=true;
                    } catch (Exception e) {
                        Logger.logStringtoLogFile("Error : could not find button 'Διαγραφή' ----> closeBetWindow()");
                    }
                }
            case  1:
                // Try for 1.5 min
                while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
                    try {
                        // TODO : TEST IT
                        ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_X_BUTTON_DIAGRAFH"));
                        ms.randomDelay(300, 1000);
                        ms.onLeftClick();
                        sl.switchToDefaultFrame();
                        stop=true;
                    } catch (Exception e) {
                        Logger.logStringtoLogFile("Error : could not find button 'X-Διαγραφή' ----> closeBetWindow()");
                    }
                }
            case 2:
                // Try for 1.5 min
                while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
                    try {
                        // TODO : TEST IT
                        sl.switchToDefaultFrame();
                        clickOnBet();
                        stop=true;
                    } catch (Exception e) {
                        Logger.logStringtoLogFile("Error : could not find button Bet ----> closeBetWindow()");
                    }
                }
//            case 2:
//                // Try for 1.5 min
//                while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
//                    try {
//                        // TODO : TEST IT
//                        ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_APODOXI_ALLAGWN"));
//                        ms.randomDelay(300, 1000);
//                        ms.onLeftClick();
//                        sl.switchToDefaultFrame();
//                        stop=true;
//                    } catch (Exception e) {
//                        Logger.logStringtoLogFile("Error : could not find button 'Αποδοχή Αλλαγών' ----> closeBetWindow()");
//                    }
//                }
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
