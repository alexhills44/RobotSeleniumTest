class AutoClose {
    private float betSize;
    private String oddsCatched,valueCatched;
    private SeleniumMethods sl;
    private MouseMovement ms;
    private String[] tipArray;

    AutoClose (SeleniumMethods sl0, MouseMovement ms0, String[] tipArray0, String oddsCatched0, String valueCatched0, float betSize) {
        sl=sl0;
        ms=ms0;
        tipArray=tipArray0;
        oddsCatched=oddsCatched0;
        valueCatched=valueCatched0;
        this.betSize =betSize;
    }

    // Opens and fills the close Amount
    // constant = defines where the request came from
    // valueCatched = get the Value that Success bet Window says we placed it at
    // betSize = get amount of money placed on that bet (BET_SIZE*betMulty)
    private void navigateToClosingWindow() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
            try {
                sl.switchToDefaultFrame();
                ms.scrollToView(PropertiesXpath.getProp("STOIXHMATA"));
                ms.onLeftClick();
                try {
                    ms.randomDelay(2000, 4000);
                    ms.scrollToView(PropertiesXpath.getProp("ANOIXTA"));
                    stop=true;
                } catch (Exception e) {
                    e.printStackTrace();
                    ms.scrollToView("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[4]/div/div/div/div[1]/div/div/div[2]/div/div[3]");
                    stop=true;
                }
                ms.onLeftClick();
            } catch (Exception e) {
                Logger.logStringtoLogFile("Error 404 : Element (STOIXHMATA or ANOIXTA) not found ----> navigateToClosingWindow()");
                e.printStackTrace();
            }
        }
    }

    private void setAutoCloseSequenceSingle() {
        final long NANOSEC_PER_SEC = 1000L*1000*1000;
        long startTime = System.nanoTime();
        boolean stop=false;
        // Try for 1.5 min
        while ((System.nanoTime()-startTime)< 0.5*60*NANOSEC_PER_SEC && !stop) {
            try {
                sl.getText(PropertiesXpath.getProp("BOX_SINGLE_ANOIXTA"));
                stop = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //Press Cog
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
            ms.randomDelay(1000, 2000);
            // Press the textBox to enter close Statement
            ms.scrollToView(PropertiesXpath.getProp("PLACE_CLOSE_STATEMENT_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(1000, 2000);
            setClosingStatementSingle();
            ms.randomDelay(1000, 2000);
            // confirm the Close Statement
            ms.scrollToView(PropertiesXpath.getProp("DIMIOURGISTE_SINTHIKH_SINGLE"));
            ms.onLeftClick();
            ms.randomDelay(1000, 2000);
            // press the cog again to close the popup window
            ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
            ms.onLeftClick();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logStringtoLogFile("Error 404 : Element (BOX_SINGLE_ANOIXTA) not found ----> setAutoCloseSequenceSingle()");
        }
    }

    void autoClose() {
        navigateToClosingWindow();
        ms.randomDelay(1000,2000);
        // Try to close Match if it is single
//        try {
//            setAutoCloseSequenceSingle();
//            Logger.logStringtoLogFile("autoclose is single");
//        }catch(Exception e) {
        //Press Cog
        ms.scrollToView(PropertiesXpath.getProp("COG_SINGLE_ANOIXTA"));
        ms.onLeftClick();
        ms.randomDelay(1000, 2000);
            Logger.logStringtoLogFile("autoclose is NOT single");
            // shows us which bet we are looking at
            int betPointer =0;
            // Tells the while Loop when to Stop
            boolean isRunning=true;
            // Runs until there is no other bet to look for
            while (isRunning) {
                betPointer++;
                boolean foundView=false;
                int viewPointer=0;
                while (!foundView && viewPointer<10) {
                    viewPointer++;
                    try {
                        // Get text from the box that betPointer is pointing
                        String boxInfo = sl.getText("html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div["+viewPointer+"]/div/div/div/div[2]/div[3]/div[1]/div[3]/div/div["+betPointer+"]");
                        //if it is in here it means it is a HANDICAP
                        if ((tipArray[3].contains("+")|tipArray[3].contains("-"))&&boxInfo.contains(oddsCatched)&&boxInfo.contains(valueCatched)) {
                            pressSetConfirmCogMultiple(viewPointer,betPointer);
                            foundView=true;
                        //if it is in here it means it is a O/U
                        }else if ((tipArray[3].contains("O")|tipArray[3].contains("U"))&&boxInfo.contains(oddsCatched)&&boxInfo.contains(valueCatched)) {
                            pressSetConfirmCogMultiple(viewPointer,betPointer);
                            foundView=true;
                        //if it is in here it means it is a WIN
                        }else if (boxInfo.contains(oddsCatched)){
                            pressSetConfirmCogMultiple(viewPointer,betPointer);
                            foundView=true;
                        }
                    }catch (Exception ex) {
                        // if there is no box in that position stop looking
                        ex.printStackTrace();
                        isRunning=false;
                    }
                }
            }

//        }
    }

    // Passes the Closing amount according to the odd Catched
    private int setClosingAmount (String odd,float betSize) {
        float odds = Float.valueOf(odd);
        if (odds>=8) {
            System.out.println("--8");
            return (int)(3*betSize);
        }else if(odds>=6) {
            System.out.println("--6");
            return (int)(2.5*betSize);
        }else if (odds>2.6) {
            System.out.println("--2.6");
            return (int)(2.3*betSize);
        }else if (odds>2) {
            System.out.println("--2");
            return (int)(2*betSize);
        }else if (odds>=1.8) {
            System.out.println("--1.8");
            return (int)(1.7*betSize);
        }else {
            System.out.println("--else");
            return 7;
        }
    }


    private void pressSetConfirmCogMultiple(int viewPointer,int betPointer) {
        enterAmountClose(viewPointer,betPointer);
        ms.randomDelay(1000, 2000);
        pressConfirmButton(viewPointer,betPointer);
        ms.randomDelay(1000, 2000);
    }
    private void pressConfirmButton(int viewPointer,int betPointer) {
        try {
            ms.scrollToView("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[" + viewPointer + "]/div/div/div/div[2]/div[3]/div[" + betPointer + "]/div[3]/div/div[3]/div[2]/div/div/div/div[4]/div[3]");
            ms.onLeftClick();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logStringtoLogFile("Error : ----> pressConfirmationButton()");
        }
    }
    private void enterAmountClose(int viewPointer,int betPointer) {
        try {
            ms.scrollToView("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[" + viewPointer + "]/div/div/div/div[2]/div[3]/div[" + betPointer + "]/div[3]/div/div[3]/div[2]/div/div/div/div[4]/div[1]/div[1]/span/input");
            ms.onLeftClick();
            ms.randomDelay(2500,3000);
            setClosingAmount(oddsCatched);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logStringtoLogFile("Error : ----> enterAmountClose()");
        }
    }
    private void setClosingAmount (String odd0) {

        float odds = Float.valueOf(odd0);

        System.out.println("Bet Odds : " + odds);
        System.out.println("Bet Size : " + betSize);
        if (odds >= 7.99) {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(3 * betSize));
            ms.typeString(String.valueOf(3 * betSize));
        } else if (odds >= 5.99) {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(2.5 * betSize));
            ms.typeString(String.valueOf(2.5 * betSize));
        } else if (odds > 2.59) {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(2.3 * betSize));
            ms.typeString(String.valueOf(2.3 * betSize));
        } else if (odds > 1.99) {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(1.8 * betSize));
            ms.typeString(String.valueOf(1.8 * betSize));
        } else if (odds >= 1.79) {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(1.7 * betSize));
            ms.typeString(String.valueOf(1.7 * betSize));
        } else {
            System.out.println("Closing Amount to be Placed : "+String.valueOf(1.5 * betSize));
            ms.typeString(String.valueOf(1.5 * betSize));
        }
    }
    private void setClosingStatementSingle() {
        // Type close Amount
        if (tipArray[3].contains("+")|tipArray[3].contains("-")|tipArray[3].contains("O")|tipArray[3].contains("U")) {
            String closeStatement = String.valueOf(setClosingAmount("1.8",betSize));
            ms.typeString(closeStatement);
        }else {
            String closeStatement = String.valueOf(setClosingAmount(tipArray[3],betSize));
            ms.typeString(closeStatement);
        }
    }


}
