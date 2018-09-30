public class TipHandler extends Thread{

    private String tip;
    private String[] tipArray;
    private SeleniumMethods sl;
    private PlayTip playTip;
    private MouseMovement ms;
    private ActionSequence as;
    private String teamName;
    private String tourName;
    private String xpath="";
    private String value;
    int betSize;


    private int teamNumber,tourNumber,matchOfTour,constant;

    TipHandler(String tip0,SeleniumMethods sl0,MouseMovement ms0,ActionSequence as0,String teamName0,String tourName0,String value0,int betSize0) {
        tip=tip0;
        sl=sl0;
        ms=ms0;
        as=as0;
        teamName=teamName0;
        tourName=tourName0;
        value=value0;
        betSize=betSize0;
    }

    // Splits the String to an Array by '---'
    // Handles the tip according to its type3 and as a second condition the content of the value4
    // Calls the handler and gets the values
    private void tipHandler () {
        // Tour0---Team1---BetSize2---BetType3---Value4---Comments5
        tipArray = tip.split("---");

        if(tipArray[3].equals("Νίκη")||tipArray[3].equals("Νικη")) {
            WinHandler winHandler = new WinHandler(sl,tipArray[0],tipArray[1],tipArray[4]);
            tourNumber = winHandler.getTourNumber();
            teamNumber = winHandler.getTeamNumber();
            matchOfTour = winHandler.getMatchOfTour();
            constant = winHandler.getCONSTANT();
            xpath = winHandler.findTip();

        }else if ((tipArray[3].equals("Χάντικαπ") ||tipArray[3].equals("Χαντικαπ")) && (tipArray[4].contains("-")||tipArray[4].contains("+"))) {
            HandicapHandler handicapHandler = new HandicapHandler(sl,tipArray[0],tipArray[1],tipArray[4]);
            tourNumber = handicapHandler.getTourNumber();
            teamNumber = handicapHandler.getTeamNumber();
            matchOfTour = handicapHandler.getMatchOfTour();
            constant = handicapHandler.getCONSTANT();
            xpath = handicapHandler.findTip();

        }else if ((tipArray[3].equals("Συνολικά") || tipArray[3].equals("Συνολικα")) &&(tipArray[4].contains("O")||tipArray[4].contains("U"))) {
            OverUnderHandler overUnderHandler = new OverUnderHandler(sl,tipArray[0],tipArray[1],tipArray[4]);
            tourNumber = overUnderHandler.getTourNumber();
            teamNumber = overUnderHandler.getTeamNumber();
            matchOfTour = overUnderHandler.getMatchOfTour();
            constant = overUnderHandler.getCONSTANT();
            xpath = overUnderHandler.findTip();

        }
    }

    // TODO : Take Xpath Place Bet
    @Override
    public void run() {
        tipHandler();
        if (!xpath.equals("")) {
            MouseMovement ms = new MouseMovement(sl);
            ms.scrollToView(xpath);
            ms.onLeftClick();
            betSize=1;
            as.placeBetSize(betSize);
        }
    }
}
