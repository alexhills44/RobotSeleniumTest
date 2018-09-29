import java.awt.*;

public class PlayTip {

    SeleniumMethods sl;
    MouseMovement ms;
    ActionSequence as;
    String teamName;
    String tourName;
    String xpath;
    String value;
    int betSize;
    Point point;

    Class classConstant;

    public PlayTip (SeleniumMethods sl0,MouseMovement ms0,ActionSequence as0,String teamName0,String tourName0,String value0,int betSize0,Class class0) {
        sl=sl0;
        ms=ms0;
        as=as0;
        teamName=teamName0;
        tourName=tourName0;
        value=value0;
        betSize=betSize0;
        classConstant=class0;
    }

    public boolean Checker(){

        int constant =2;
        int tourNumber,teamNumber,matchOfTour;
        xpath="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[3]/div["+matchOfTour+"]/div/div[2]/div["+constant+"]/div["+teamName+"}/span[2]";
        String xpathTour="/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[1]/div[1]";
        String xpathTeam = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div["+matchOfTour +"]/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
        String xpathTeamForZero = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
        String xpathConstant = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[3]/div["+matchOfTour +"]/div/div[2]/div["+CONSTANT+"]/div["+teamNumber+"]/span[2]";
        //Check for xpath team for Zero
        if(sl.getText(xpathTour).equals(tourName) && sl.getText(xpathTeam).equals(teamName) && sl.getText(xpathConstant).equals(value)){
            return true;
        }
        else{
            return false;
        }
    }

    public void clickTip() {
        point=sl.getCoordinates(xpath);
        ms.scrollToView(xpath);
        if(Checker()){
            ms.onLeftClick();
        }else{
            //Search again for Tip
        }

    }

}
