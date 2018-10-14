public class YDiaforesHandler {

    private String[] inputMessage;
    private String[] values;
    private SeleniumMethods sl;
    private MouseMovement ms;

    private int competitionNumber = 1;
    private int matchNumber =1;
    private int teamNumber =1;
    @SuppressWarnings("all")
    private int ABBets =1; //span[2] handicap , span[3] odds
    private boolean isSingle=false;
    private String xpathToReturn="";
    private String xpathMatch="";
    private String xPathToDiffrence="";
    private static String valuesXpathExtension="/div[2]/div/div[1]";
    private float betSize=PropertiesHandler.getBetSize();
    private String oddsCaught;
    private boolean removeFromList=false;
    private int state =0;
    String tip;

    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[1]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[2]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[3]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div[1]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div[2]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div[3]
    //Βραζιλία - Παουλίστα Γυναικών---Ινστιτούτο Μπάμπι Γυναίκες---1---14-16,17-20,21+
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div[1]/div[6]
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div[3]/div[6]

    YDiaforesHandler (String tip,String[] inputMessage0, SeleniumMethods sl0) {
        inputMessage=inputMessage0;
        sl = sl0;
        ms= new MouseMovement(sl);
        this.tip=tip;
    }

    String getDiafores () throws Exception{
        competitionMatchFinder();
        goToMatchBets();
        ms.randomDelay(3000,6000);
        tipArrayBreak();
        loopThroughBetCategories();
        // play bet
        playDiafores();
        goToMainScreen();

        return "";
    }

    @SuppressWarnings("Duplicates")
    // Unlike the Other competitionMatchFinder this method only needs to find the match so it can click on it
    //sets the proper competition ,match and team pointer
    private void competitionMatchFinder() throws Exception{
        boolean isRunning = true;
        boolean doesntExist =true;
        int cycles=0;
        while (isRunning && cycles<300) {
            cycles++;
            try {
                if(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]").contains(inputMessage[0])) {
                    isRunning=false;
                    doesntExist=false;
                    System.out.println("1. FOUND COMPETITION");
                }
            } catch (Exception e) {
//                System.out.println("YHandicapHandler ----> Could not find the tournament Specified");
            }
            competitionNumber++;
        }
        if (!doesntExist) {
            doesntExist=true;
            isRunning=true;
            competitionNumber--;
            cycles=0;
            while(isRunning && cycles<50) {
                cycles++;
                try {
                    if (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div").contains(inputMessage[1])) {
                        isRunning = false;
                        System.out.println("2. FOUND MATCH");
                        isSingle = true;
                        doesntExist = false;
                        xpathMatch = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div[" + String.valueOf(competitionNumber) + "]/div[3]/div[1]/div/div[1]";
                    }
                }catch (Exception e) {
//                    System.out.println("Tournament contains more than one Matches");
                }

                try {
                    if (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]").contains(inputMessage[1]) && !isSingle) {
                        isRunning=false;
                        System.out.println("2. FOUND MATCH");
                        doesntExist=false;
                        xpathMatch="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[1]";
                    }
                } catch (Exception e) {
//                    System.out.println("YHandicapHandler ----> Could not find the match Specified");
                }
                matchNumber++;
            }
            if (!doesntExist) {
                isRunning=true;
                matchNumber--;
                while(isRunning) {

                    if (!isSingle) {
                        try {
                            System.out.println(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[1]/div/div[3]/div["+String.valueOf(teamNumber)+"]/span"));
                            if(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[1]/div/div[3]/div["+String.valueOf(teamNumber)+"]/span").equals(inputMessage[1])) {
                                isRunning=false;
                                System.out.println("3. FOUND TEAM");
                                System.out.println("4. TEAM NUMBER IS : "+teamNumber);
                            }
                        } catch (Exception e) {
//                            System.out.println("YHandicapHandler ----> Could not find the team Specified");
                        }
                        teamNumber++;
                    } else {
                        try {
                            System.out.println(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[1]/div/div[3]/div["+String.valueOf(teamNumber)+"]/span"));
                            if(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[1]/div/div[3]/div["+String.valueOf(teamNumber)+"]/span").equals(inputMessage[1])) {
                                isRunning=false;
                                System.out.println("3. FOUND TEAM");
                                System.out.println("4. TEAM NUMBER IS : "+teamNumber);
                            }
                        } catch (Exception e) {
//                            System.out.println("YHandicapHandler ----> Could not find the team Specified");
                        }
                        teamNumber++;
                    }
                }
                teamNumber--;
            }else {
                Logger.logStringtoLogFile("Error : could not find team ----> YHandicapHandler()");
                throw new Exception("Team name : "+inputMessage[1]+" Not found!");
            }
        }else {
            Logger.logStringtoLogFile("Error : could not find tournament ----> YHandicapHandler()");
            throw new Exception("Tournament :"+inputMessage[0]+" Not found!");
        }
    }

    private void goToMatchBets() {
        ms.scrollToView(xpathMatch);
        ms.randomDelay(200,600);
        ms.onLeftClick();
    }

    private void loopThroughBetCategories() {
        boolean found=false;
        int i=0;
        while(!found && i<100) {
            i++;
            try {
                String xpathCategory="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div["+i+"]";
                System.out.println();
                String textFromCategory =sl.getText(xpathCategory);
                if (textFromCategory.contains("Διαφορά Νίκης")) {
                    if (!textFromCategory.contains("Περίοδος") && textFromCategory.contains(inputMessage[1])) {
                        xPathToDiffrence=xpathCategory;
                        found=true;
                    }
                }
            }catch (Exception e) {

            }
        }
        if (!found) {
            Logger.logStringtoLogFile("Error could not find point diffrence Win ----> YDiaforesHandler()");
        }
    }

    private void tipArrayBreak() {
        try {
            values = inputMessage[3].split(",");
            betSize=(betSize*Float.parseFloat(inputMessage[2]))/values.length;
            betSize=Math.round(betSize*100f)/100f;
            System.out.println("Betting amount for Diffrences is : "+betSize);
        } catch (Exception e) {
            Logger.logStringtoLogFile("Error : could not split values for YDiaforesHandsler ----> loopThroughBetCategories()");
        }
    }

    private void playDiafores() {
        int i;
            for (String a: values) {
                //play that bet on this xpath
                // "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[11]/div[2]/div/div["+teamNumber+1+"]+"/div["+i+"]"
                i = setPointerDiaforesXpath(a);
                Logger.logStringtoLogFile("Placing bet for : "+a+" : "+i);
                if (i!=0) {
                    while (state!=2) {
                        System.out.println("Betting on : "+a);
                        //betTip(xPathToDiffrence+"/div[2]/div/div["+(teamNumber+1)+"]/div["+i+"]");
                        state=2;
                    }
                    state=0;
                }
                ////////////"/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div["+i+"]"
                if (values[values.length-1].contains(a)) {
                    removeFromList=true;
                }
            }

    }

    @SuppressWarnings("Duplicates")
    private void betTip (String xpath) {
        System.out.println("Tryed to Bet");
        boolean stop=false;
        while (!stop) {
            // Sets the bet Size once in the loop
            int index = Main.tipList.indexOf(tip);
            PlaceBet pb = new PlaceBet(sl,ms,betSize,xpath);
            pb.clickOnBet();
            ms.randomDelay(2000, 4000);
            // Place bet and press confirm bet
            pb.placeBetSize();
            ms.randomDelay(3000, 6000);
            final long NANOSEC_PER_SEC = 1000L*1000*1000;
            long startTime = System.nanoTime();
            state=0;
            while (state==0 && (System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC) {
                try {
                    sl.getText("/html/body/div[1]/div/ul/li[9]/a[1]/div");
                    state =1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    getTextFromSuccessWindow();
                    state=2;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (state==2) {
                // Tries to get Text from the Success window, if it succeeds it sets valueCached and oddsCached
//            getTextFromSuccessWindow();
                ms.randomDelay(1000, 2000);
                //Calls the autoclose Class to se the auto close amount
                new AutoClose(sl,ms,inputMessage, oddsCaught, "",betSize).autoClose();
                System.out.println("autoClose has been placed");
                Logger.logStringtoLogFile("Auto Close has been placed SUCCESSFULLY!");
                // Stops the while loop
                if (removeFromList) {
                    Main.tipSendTime.remove(index);
                    Main.tipList.remove(tip);
                }
            }else if (state==1){
                pb.closeBetWindow();
                Logger.logStringtoLogFile("Retrying to place bet...");
                System.out.println("Retrying to place bet");
            }
        }
    }

    @SuppressWarnings("all") //For loop is never used warning
    private void getOddAndValueFromBetPlacedIFRAME() {
        // Box that displays text when the bet is successful
        String[] betInfo = sl.getText("/html/body/div[1]/div/ul/li[7]").split("\n");
        getOddFromBet(betInfo);
    }

    private void getOddFromBet(String[] betInfo) {
        String[] lineArray = betInfo[0].split(" ");
        try {
            oddsCaught = lineArray[lineArray.length-1];
        } catch (Exception e) {
            Logger.logStringtoLogFile("Error : could not find odd from bet ----> getOddFromBet()");
        }
    }

    @SuppressWarnings("Duplicates")
    private void getTextFromSuccessWindow() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
            try {
                getOddAndValueFromBetPlacedIFRAME();
                ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_OK_BUTTON"));
                ms.onLeftClick();
                System.out.println("Bet has been placed SUCCESSFULLY!");
                stop=true;
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error : could not find ok button ----> getTextFromSuccessWindow()");
            }
        }
    }

    private void goToMainScreen() {
        ms.scrollToView("/html/body/div[1]/div/div[2]/div[1]/div/div/div[1]/span[1]/div[1]");
        ms.randomDelay(200,600);
        ms.onLeftClick();
    }

    private int setPointerDiaforesXpath(String s) {
        if (s.equals("1-2")) {
            return 2;
        }else if(s.equals("3-6")) {
            System.out.println("1");
            return 3;
        }else if(s.equals("7-9")) {
            System.out.println("2");
            return 4;
        }else if(s.equals("10-13")) {
            System.out.println("3");
            return 5;
        }else if(s.equals("14-16")) {
            System.out.println("4");
            return 6;
        }else if(s.equals("17-20")) {
            return 7;
        }else if(s.equals("21+")) {
            return 8;
        }else {
            return 0;
        }
    }
    // 1st element /html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[6]/div[2]/div/div[2]/div[2]/span[2]

}
