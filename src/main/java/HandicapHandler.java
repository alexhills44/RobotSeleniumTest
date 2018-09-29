import java.awt.*;

public class HandicapHandler {

    private SeleniumMethods sl;
    private String tourName,teamName,value;
    private static int CONSTANT=1;
    boolean success=true;

    int tourNumber=1,teamNumber=1,matchOfTour=1;
    String xpathTour = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[1]/div[1]";
    String xpathTeam = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div["+matchOfTour +"]/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
    String xpathTeamForZero = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
    String xpathConstant = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[3]/div["+matchOfTour +"]/div/div[2]/div["+CONSTANT+"]/div["+teamNumber+"]/span[2]";

    public HandicapHandler(SeleniumMethods sl0,String tourName0,String teamName0,String value0){
        sl=sl0;
        tourName=tourName0;
        teamName=teamName0;
        value=value0;
    }

    public void findTour() {
        while (!sl.getText(xpathTour).equals(tourName)) {
            tourNumber++;
            xpathTour = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[1]/div[1]";

            // Check for Error
            if(tourNumber>300) {
                success=false;
                break;
            }
        }
        //Print Error Message
        if(success) {
            System.out.println("Found Tournament");
        }else {
            System.out.println("Error : HandicapHandler/findTour == Tournament NOT Found!");
        }
    }

    public void findTeam() {
        if(success) {
            try {
                while (!sl.getText(xpathTeamForZero).equals(teamName)) {
                    teamNumber++;
                    // Check for Error
                    if (teamNumber ==3) {
                        success=false;
                        break;
                    }
                }
                xpathTeam =xpathTeamForZero;
            }catch (Exception e) {
                while(!sl.getText(xpathTeam).equals(teamName)) {
                    teamNumber++;
                    if (teamNumber==3) {
                        teamNumber=1;
                        matchOfTour++;
                    }
                    // Check for Error
                    if (matchOfTour>100) {
                        success=false;
                        break;
                    }
                }
            }
            //Print Error Message
            if(success) {
                System.out.println("Found Team");
            }else {
                System.out.println("Error : HandicapHandler/findTeam == Match NOT Found!");
            }
        }
    }

    public Point findHandicap() {
        // if findHandler Fails then it return point(0,0)
        Point point=new Point(0,0);
        if (success) {
            float readValue;
            float inputValue =100000;
            try {
                readValue = Float.parseFloat(sl.getText(xpathConstant));
                inputValue = Float.parseFloat(value);
            } catch (NumberFormatException e) {
                readValue=0;
            }
            if (inputValue!=100000) {
                while(inputValue<readValue || readValue==0) {
                    try {
                        readValue = Float.parseFloat(sl.getText(xpathConstant));
                        inputValue = Float.parseFloat(value);
                    } catch (NumberFormatException e) {
                        readValue=0;
                    }
                }
                point=sl.getCoordinates(xpathConstant);
            }
        }
        return point;
    }

    public float valueToNumber(String value0) {
        float valueFloat=0f;
        try {
            if(value0.contains("+")) {
                valueFloat = Float.parseFloat(value0);
            }else if (value0.contains("-")) {
                valueFloat = Float.parseFloat(value0);
                valueFloat = valueFloat-(2*valueFloat);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueFloat;
    }
}
