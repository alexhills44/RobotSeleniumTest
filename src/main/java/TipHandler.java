class TipHandler {
    /**
     * This Class holds all the Classes and the Methods needed to play the
     */

    private String tip;
    private String[] tipArray;
    private SeleniumMethods sl;
    private MouseMovement ms;
    private String xpath="";
    private float betSize = PropertiesHandler.getBetSize();
    private boolean stopLoop =false;
    private String valueCaught, oddsCaught;
    private MainProgram mainProgram;
    final long NANOSEC_TO_MIN = 1000L*1000*1000*60;

    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////

    //example of diffrrence
    //League---TeamName---1/3---14-16,17-20,21+---comments


    TipHandler(String tip0, SeleniumMethods sl0, MouseMovement ms0, MainProgram mainProgram) {
        tip=tip0;
        sl=sl0;
        ms=ms0;
        this.mainProgram=mainProgram;
    }

    // Splits the String to an Array by '---'
    // Handles the tip according to its type3 and as a second condition the content of the value4
    // Calls the handler and gets the values
    private void tipHandler () {
        tipArray = tip.split("---");
        if (tipArray[3].contains(",")&&tipArray[3].contains("-")) {
            try {
                System.out.println("Found Over/Under");
                YDiaforesHandler diaforesHandler = new YDiaforesHandler(tip,tipArray, sl);
                xpath = diaforesHandler.getDiafores();
            } catch (Exception e) {
                stopLoop = true;
                System.out.println("YDiaforesHandler ----> " + e.getMessage());
            }
        } else if (tipArray[3].contains("O") || tipArray[3].contains("U") || tipArray[3].contains("Ο") || tipArray[3].contains("υ")) {
            try {
                System.out.println("Found Over/Under");
                YOverUnderHandler overUnderHandler = new YOverUnderHandler(tipArray, sl);
                xpath = overUnderHandler.getOverUnder();
            } catch (Exception e) {
                stopLoop=true;
                System.out.println("YOverUnderHandler ----> "+e.getMessage());
            }
        } else if (tipArray[3].contains("-") || tipArray[3].contains("+")) {
            try {
                System.out.println("Found that tip is Handicap");
                YHandicapHandler handicapHandler = new YHandicapHandler(tipArray, sl);
                xpath = handicapHandler.getHandicap();
            } catch (Exception e) {
                stopLoop=true;
                System.out.println("YHandicapHandler ----> "+e.getMessage());
            }
        }else {
            try {
                System.out.println("Found that tip is Win");
                YWinHandler winHandler = new YWinHandler(tipArray, sl);
                xpath = winHandler.getWin();
            } catch (Exception e) {
                stopLoop=true;
                System.out.println("YWinHandler ----> "+e.getMessage());
            }
        }
    }

    // Searches for the tip if it finds it then tries to play it if it succeeds then sets the autobet condition
    // if it doesnt succeed then removes bet by clicking on the element on the main bet page and tries again
    // if it doesnt find it
    void run () {
        ms.randomDelay(8000,12000);
        int index = Main.tipList.indexOf(tip);
        tipHandler();
        if (!xpath.equals("")) {
            betTip(index);
        } else {
            Logger.logStringtoLogFile("Error : Xpath NULL ----> TipHandler().run()");
        }

        // Return to main Method something to show it that the bet cannot be played or has been played
        // and move to the next tip if available
    }

    private void betTip (int index) {
        // Sets the bet Size once in the loop
        setBetSize();
        PlaceBet pb = new PlaceBet(sl,ms,betSize,xpath);
        pb.clickOnBet();
        ms.randomDelay(2000, 4000);
        // Place bet and press confirm bet
        pb.placeBetSize();
        ms.randomDelay(3000, 6000);
        try {
            // Tries to get Text from the Success window, if it succeeds it sets valueCached and oddsCached
            getTextFromSuccessWindow();
            ms.randomDelay(1000, 2000);
            //Calls the autoclose Class to se the auto close amount
            new AutoClose(sl,ms,tipArray, oddsCaught, valueCaught,betSize).autoClose();
            System.out.println("autoClose has been placed");
            Logger.logStringtoLogFile("Auto Close has been placed SUCCESSFULLY!");
            // Stops the while loop
            Main.tipSendTime.remove(index);
            Main.tipList.remove(tip);
        } catch (Exception e) {
            pb.closeBetWindow();
            Logger.logStringtoLogFile("Retrying to place bet...");
            System.out.println("Retrying to place bet");
        }
    }

    @SuppressWarnings("all") //For loop is never used warning
    private void getOddAndValueFromBetPlacedIFRAME() {
        // Box that displays text when the bet is successful
        String[] betInfo = sl.getText("/html/body/div[1]/div/ul/li[7]").split("\n");
        for(String s:betInfo) {
            if(s.contains("Χάντικαπ 2πλής επιλογής")) {
                getOddFromBet(betInfo);
                getValueFromBet(betInfo);
                break;
            }else if (s.contains("Συνολικά 2πλής επιλογής")) {
                getOddFromBet(betInfo);
                getValueFromBet(betInfo);
                break;
            }else {
                getOddFromBet(betInfo);
                valueCaught ="";
                break;
            }
        }
    }

    private void getOddFromBet(String[] betInfo) {
        String[] lineArray = betInfo[0].split(" ");
        try {
            oddsCaught = lineArray[lineArray.length-1];
        } catch (Exception e) {
            Logger.logStringtoLogFile("Error : could not find odd from bet ----> getOddFromBet()");
        }
    }

    private void getValueFromBet(String[] betInfo) {
        String[] lineArray = betInfo[0].split(" ");
        try {
            valueCaught = lineArray[lineArray.length-2];
        } catch (Exception e) {
            Logger.logStringtoLogFile("Error : could not find value from bet ----> getValueFromBet()");
        }
    }

    private boolean setBetSize(boolean flag) {
        if (flag) {
            // Bet multy*betSize  betMulty==tipArray[2] ex.  Διπλο Πονταρισμα
            int betMulty = Integer.valueOf(tipArray[2]);
            betSize = betSize * betMulty;
        }
        return false;
    }

    private void setBetSize() {
        int betMulty = Integer.valueOf(tipArray[2]);
        betSize = betSize * betMulty;
    }

    private void getTextFromSuccessWindow() {
        ms.randomDelay(1000,2000);
        getOddAndValueFromBetPlacedIFRAME();
        ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_OK_BUTTON"));
        ms.onLeftClick();
        System.out.println("Bet has been placed SUCCESSFULLY!");
    }
}
