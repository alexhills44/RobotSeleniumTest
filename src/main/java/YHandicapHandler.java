class YHandicapHandler {

    private String[] inputMessage;
    private SeleniumMethods sl;

    private int competitionNumber = 1;
    private int matchNumber =1;
    private int teamNumber =1;
    @SuppressWarnings("all")
    private int ABBets =1; //span[2] handicap , span[3] odds
    private boolean isSingle=false;
    private String xpathToReturn="";

//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// US OPEN ///////////////////////////////////////////// FIRST MATCH =1 //////////
//    String match = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]";
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// US OPEN ///////////////////////////////////////////// FIRST MATCH =1 /////////////////////////////// WINNER //////////////////////////// SET WINNER //////////// IF NO HANDICAP OR OVER THEN SPAN=2 //////
//    String bets  = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span["+String.valueOf(ABBets)+"]";
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// US OPEN ///////////////////////////////////////////// FIRST MATCH =1 //////////////////////////////// GET TEAM NAME //////////////////// (SPLITS WITH TIME, POINTS ,ETC)
//    String names = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[1]/div/div[3]/div["+String.valueOf(teamNumber)+"]/span";
//    private String xpathMatchTime = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+teamNumber+"]/div[3]/div/div/div[1]/div/div[1]";
//    private String xpathMatchPeriod = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+teamNumber+"]/div[3]/div/div/div[1]/div/div[2]";
//
    YHandicapHandler(String[] inputMessage0, SeleniumMethods sl0) {
        inputMessage=inputMessage0;
        sl = sl0;
    }

    String getHandicap() throws Exception{
        competitionMatchFinder();
        handicapChecker();
        System.out.println("5. Finished");
        return xpathToReturn;
    }

    @SuppressWarnings("Duplicates")
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
            while(isRunning && cycles<300) {
                cycles++;
                try {
                    if (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div").contains(inputMessage[1])) {
                        isRunning=false;
                        System.out.println("2. FOUND MATCH");
                        isSingle=true;
                        doesntExist=false;
                    }
                }catch (Exception e) {
//                    System.out.println("Tournament contains more than one Matches");
                }

                try {
                    if (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]").contains(inputMessage[1]) && !isSingle) {
                        isRunning=false;
                        System.out.println("2. FOUND MATCH");
                        doesntExist=false;
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
                throw new Exception("Team name : "+inputMessage[1]+" Not found!");
            }
        }else {
            throw new Exception("Tournament :"+inputMessage[0]+" Not found!");
        }
    }

    // compare the handicap numbers
    private void handicapChecker() {
        int columnBets;
        int rowBets;
        if (!isSingle) {
            columnBets =1;
            rowBets =teamNumber;
            ABBets=2;
            float handicap = Float.parseFloat(inputMessage[3]);
            try {
                float handicapNow = Float.parseFloat(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]"));
                if (handicap>0) {
                    if(handicapNow>=(handicap-1) && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }else if(handicap<0 ) {
                    if (handicapNow>=(handicap-1) && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }
            }catch (Exception e) {
                System.out.println("Xpath Problem : YHandicapHandler ----> HandicapChecker");
            }
        } else {
            columnBets =1;
            rowBets =teamNumber;
            ABBets=2;
            float handicap = Float.parseFloat(inputMessage[3]);
            try {
                float handicapNow = Float.parseFloat(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]"));
                if (handicap>0) {
                    if(handicapNow>=(handicap-1) && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }else if(handicap<0 ) {
                    if (handicapNow>=(handicap-1) && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }
            }catch (Exception e) {
                System.out.println("Xpath Problem : YHandicapHandler ----> HandicapChecker");
            }
        }
    }
}
