public class YDiaforesHandler {

    private String[] inputMessage;
    private SeleniumMethods sl;

    private int competitionNumber = 1;
    private int matchNumber =1;
    private int teamNumber =1;
    @SuppressWarnings("all")
    private int ABBets =1; //span[2] handicap , span[3] odds
    private boolean isSingle=false;
    private String xpathToReturn="";

    YDiaforesHandler (String[] inputMessage0, SeleniumMethods sl0) {
        inputMessage=inputMessage0;
        sl = sl0;
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
                    }
                }catch (Exception e) {
//                    System.out.println("Tournament contains more than one Matches");
                }

                try {
                    if (sl.getText("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]").contains(inputMessage[1]) && !isSingle) {
                        isRunning=false;
                        System.out.println("2. FOUND MATCH");
                    }
                } catch (Exception e) {
//                    System.out.println("YHandicapHandler ----> Could not find the match Specified");
                }
                matchNumber++;
            }
            matchNumber--;
            // return xpath is if single : /html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div
            // or if notSingle : /html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+String.valueOf(competitionNumber)+"]/div[3]/div["+String.valueOf(matchNumber)+"]

        }else {
            throw new Exception("Tournament :"+inputMessage[0]+" Not found!");
        }
    }


}
