import java.util.Scanner;

public class TipHandler {
    /**
     * This Class holds all the Classes and the Methods needed to play the
     */

    private String tip;
    private String[] tipArray;
    private SeleniumMethods sl;
    private MouseMovement ms;
    private ActionSequence as;
    private String teamName;
    private String tourName;
    private String xpath="";
    private String value;
    private float betSize = PropertiesHandler.getBetSize();
    private int betMulty =1;
    public static boolean hasBeenPlayed=false;

    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////
    /////////////////League---TeamName---Bet---Odds///////////////

    private int teamNumber,tourNumber,matchOfTour,constant,matchStartTime;
    TipHandler(String tip0,SeleniumMethods sl0,MouseMovement ms0,ActionSequence as0) {
        tip=tip0;
        sl=sl0;
        ms=ms0;
        as=as0;
    }

    // Splits the String to an Array by '---'
    // Handles the tip according to its type3 and as a second condition the content of the value4
    // Calls the handler and gets the values
    private void tipHandler () {
        // Tour0---Team1---BetSize2---BetType3---Value4---Comments5
        tipArray = tip.split("---");

        if(tipArray[3].contains("-") || tipArray[3].contains("+")) {
            System.out.println("Found that tip is Handicap");

            HandicapHandler handicapHandler = new HandicapHandler(tipArray,sl);
            xpath = handicapHandler.getHandicap();
//            tourNumber = winHandler.getTourNumber();
//            teamNumber = winHandler.getTeamNumber();
//            matchOfTour = winHandler.getMatchOfTour();
//            constant = winHandler.getCONSTANT();
//            xpath = winHandler.findTip();
//            matchTimeSet(winHandler.getMatchTime(),winHandler.getMatchPeriod());

        }else if (tipArray[3].contains("O") || tipArray[3].contains("U")||tipArray[3].contains("Ο") || tipArray[3].contains("υ")) {
            System.out.println("Found Over/Under");
            OverUnderHandler overUnderHandler = new OverUnderHandler(tipArray,sl);
            xpath = overUnderHandler.getOverUnder();
        }else {
            System.out.println("Found that tip is Win");
            WinHandler winHandler = new WinHandler(tipArray,sl);
            xpath = winHandler.getWin();
//            tourNumber = handicapHandler.getTourNumber();
//            teamNumber = handicapHandler.getTeamNumber();
//            matchOfTour = handicapHandler.getMatchOfTour();
//            constant = handicapHandler.getCONSTANT();
//            xpath = handicapHandler.findTip();
//            matchTimeSet(handicapHandler.getMatchTime(),handicapHandler.getMatchPeriod());

        }
//        else if ((tipArray[3].equals("Συνολικά") || tipArray[3].equals("Συνολικα")) &&(tipArray[4].contains("O")||tipArray[4].contains("U"))) {
//            OverUnderHandler overUnderHandler = new OverUnderHandler(sl,tipArray[0],tipArray[1],tipArray[4]);
//            tourNumber = overUnderHandler.getTourNumber();
//            teamNumber = overUnderHandler.getTeamNumber();
//            matchOfTour = overUnderHandler.getMatchOfTour();
//            constant = overUnderHandler.getCONSTANT();
//            xpath = overUnderHandler.findTip();
//            matchTimeSet(overUnderHandler.getMatchTime(),overUnderHandler.getMatchPeriod());
//
//        }
    }

    private void matchTimeSet (String matchTime,String matchPeriod) {
        //sets matchStartTime to amount of seconds played
        if(matchPeriod.contains("1")) {
            String[] matchTimeArray = matchTime.split(":");
            matchStartTime = Integer.valueOf(matchTimeArray[0])*60 + Integer.valueOf(matchTimeArray[1]);
        }else if (matchPeriod.contains("2")) {
            String[] matchTimeArray = matchTime.split(":");
            matchStartTime = (Integer.valueOf(matchTimeArray[0])*60 + Integer.valueOf(matchTimeArray[1]))+600;
        }else if (matchPeriod.contains("3")) {
            String[] matchTimeArray = matchTime.split(":");
            matchStartTime = (Integer.valueOf(matchTimeArray[0])*60 + Integer.valueOf(matchTimeArray[1]))+1200;
        }else if (matchPeriod.contains("4")) {
            String[] matchTimeArray = matchTime.split(":");
            matchStartTime = (Integer.valueOf(matchTimeArray[0])*60 + Integer.valueOf(matchTimeArray[1]))+1800;
        }
    }

    // Searches for the tip if it finds it then tries to play it if it succeeds then sets the autobet condition
    // if it doesnt succeed then removes bet by clicking on the element on the main bet page and tries again
    // if it doesnt find it
    public void run() {
        hasBeenPlayed=false;
        boolean flag=true;
        while (!hasBeenPlayed) {
            System.out.println("Entered Looooop");
            tipHandler();
            if (!xpath.equals("")) {
                // Open Window to bet in -- Press the bet found
                ms.scrollToView(xpath);
                ms.onLeftClick();
                // Place bet and press confirm bet
                ms.randomDelay(2000, 4000);
                if (flag) {
                    // Bet multy*betSize  betMulty==tipArray[2] ex.  Διπλο Πονταρισμα
                    flag=false;
                    betMulty = Integer.valueOf(tipArray[2]);
                    betSize = betSize * betMulty;
                    ActionSequence.setBetSize(betSize);
                }
                as.placeBetSize();
                ms.randomDelay(2000, 4000);

                try {
                    ms.randomDelay(3000,6000);
                    as.getOddAndValueFromBetPlacedIFRAME();
                    ms.scrollToViewIFRAME(PropertiesXpath.getProp("BW_OK_BUTTON"));
                    ms.onLeftClick();
                    hasBeenPlayed=true;
                    ms.randomDelay(1000,2000);
                    System.out.println("bet has been placed");
                    as.autoClose(tipArray);
                    System.out.println("autoClose has been placed");
                } catch (Exception e) {
                    e.printStackTrace();
                    sl.switchToDefaultFrame();
                    ms.scrollToView(xpath);
                    ms.onLeftClick();
                    System.out.println("Retry to place bet");
                }

            }else {
                System.out.println("ERROR TipHandler/run() : xpath=null   OR BET HAS NO VALUE");
            }
        }
    }

    public static void stopTipHandler() {
        hasBeenPlayed=true;
    }
}
