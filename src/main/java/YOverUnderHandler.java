class YOverUnderHandler {

    private String[] inputMessage;
    private SeleniumMethods sl;

    private int competitionNumber = 1;
    private int matchNumber =1;
    private int teamNumber =1;
    @SuppressWarnings("all")
    private int ABBets =1; //span[2] handicap , span[3] odds
    private boolean isSingle=false;
    private String xpathToReturn="";

    YOverUnderHandler(String[] inputMessage0, SeleniumMethods sl0) {
        inputMessage=inputMessage0;
        sl = sl0;
    }

    String getOverUnder() throws Exception {
        competitionMatchFinder();
        overUnderChecker();
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
            while(isRunning && cycles<50) {
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
                Logger.logStringtoLogFile("Error : could not find team ----> YOverUnderHandler()");
                throw new Exception("Team name : "+inputMessage[1]+" Not found!");
            }
        }else {
            Logger.logStringtoLogFile("Error : could not find tournament ----> YOverUnderHandler()");
            throw new Exception("Tournament :"+inputMessage[0]+" Not found!");
        }
    }

    // compare the handicap numbers
    private void overUnderChecker() {
        int rowBets;
        int columnBets;
        if (!isSingle) {
            columnBets =2;
            rowBets =teamNumber;
            ABBets=2;
            float overUnder = Float.parseFloat(removeLetter(inputMessage[3]));
            try {
                float overUnderNow = Float.parseFloat(removeLetter(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]")));
                if (inputMessage[3].contains("O")) {
                    rowBets=1;
                    if(overUnderNow<=overUnder+2 && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }else  if (inputMessage[3].contains("U")) {
                    rowBets=2;
                    if (overUnderNow>=overUnder-2 && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }
            }catch (Exception e) {
                Logger.logStringtoLogFile("Error : could not read value ----> YOverUnderHandler()");
            }
        } else {
            columnBets =2;
            rowBets =teamNumber;
            ABBets=2;
            float overUnder = Float.parseFloat(removeLetter(inputMessage[3]));
            try {
                float overUnderNow = Float.parseFloat(removeLetter(sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]")));
                if (inputMessage[3].contains("O")) {
                    rowBets=1;
                    if(overUnderNow<=overUnder+2 && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }else  if (inputMessage[3].contains("U")) {
                    rowBets=2;
                    if (overUnderNow>=overUnder-2 && (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[3]")!=null)) {
                        xpathToReturn ="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div/div/div[2]/div["+String.valueOf(columnBets)+"]/div["+String.valueOf(rowBets)+"]/span[2]";
                    }
                }
            }catch (Exception e) {
                Logger.logStringtoLogFile("Error : could not read value ----> YOverUnderHandler()");
            }
        }
    }

    private String removeLetter(String s) {
        if (s.contains("O")) {
            return s.replace("O","");
        }else {
            return s.replace("U","");
        }
    }
}
