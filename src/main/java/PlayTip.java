

public class PlayTip {

    private SeleniumMethods sl;
    private MouseMovement ms;
    private ActionSequence as;
    private String teamName;
    private String tourName;
    private String xpath;
    private String value;
    private int betSize;

    HandicapHandler classConstant;

    public PlayTip (SeleniumMethods sl0,MouseMovement ms0,ActionSequence as0,String teamName0,String tourName0,String value0,int betSize0) {
        sl=sl0;
        ms=ms0;
        as=as0;
        teamName=teamName0;
        tourName=tourName0;
        value=value0;
        betSize=betSize0;
    }



    // TODO : Decide whether we do it Or not
    // TODO : Get Xpath for the Elements that display the tourName,teamName value and Odd
    // TODO : Compare with Input and do accordingly
    // Checks the Place Bet window to see in info are Correct
    public void checkTip(){

    }

    public void clickTip() {

    }

}
