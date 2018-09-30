import java.awt.*;

public class OverUnderHandler {

    private SeleniumMethods sl;
    private String tourName,teamName,value;
    private int CONSTANT=2;
    private boolean success=true;
    private int tourNumber=1,teamNumber=1,matchOfTour=1;

    // Sets the xpath for each attribute to take :: Look at NoteFile for more info
    private String xpathTour = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[1]/div[1]";
    private String xpathTeam = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div["+matchOfTour +"]/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
    private String xpathTeamForZero = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
    private String xpathConstant = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber+"]/div[3]/div["+matchOfTour +"]/div/div[2]/div["+CONSTANT+"]/div["+teamNumber+"]/span[2]";

    OverUnderHandler(SeleniumMethods sl0,String tourName0,String teamName0,String value0){
        sl=sl0;
        tourName=tourName0;
        teamName=teamName0;
        value=value0;
    }

    // combines all three methods in to one
    public String findTip() {
        findTour();
        findTeam();
        return findOverUnder();
    }

    // Searches for the tourName by looping throught the elements of the table (increments tourNumber)
    // if it loops more than 300 times it throws an ERROR
    private void findTour() {
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

    // Searches for the teamName with the same logic as the tournament ,
    // it also checks if the tournament table only contains one match
    // if it does it changes the xpath later the findOdd classes searches correctly
    private void findTeam() {
        if(success) {
            try {
                while (!sl.getText(xpathTeamForZero).equals(teamName)) {
                    teamNumber++;
                    xpathTeamForZero = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
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
                    xpathTeam = "/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[1]/div[3]/div["+tourNumber +"]/div[3]/div["+matchOfTour +"]/div/div[1]/div/div[3]/div["+teamNumber +"]/span";
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

    //Searches for the value continuously until it finds it
    //if it the inputValue from the tip cant be read then it stays at the default value 100000 and throws error
    private String findOverUnder() {
        // if findOverUnder Fails then it return point(0,0)
        String xpath = "";
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
                if(value.contains("O")) {
                    while(inputValue<=readValue || readValue==0) {
                        try {
                            readValue = Float.parseFloat(sl.getText(xpathConstant));
                            inputValue = Float.parseFloat(value);
                        } catch (NumberFormatException e) {
                            readValue=0;
                        }
                    }
                    xpath = xpathConstant;
                }else if (value.contains("U")) {
                    while(inputValue>=readValue || readValue==0) {
                        try {
                            readValue = Float.parseFloat(sl.getText(xpathConstant));
                            inputValue = Float.parseFloat(value);
                        } catch (NumberFormatException e) {
                            readValue=0;
                        }
                    }
                    xpath = xpathConstant;
                }
            }
        }
        return xpath;
    }



    //These are getter Methods
    public int getCONSTANT() {
        return CONSTANT;
    }
    public int getTourNumber() {
        return tourNumber;
    }
    public int getTeamNumber() {
        return teamNumber;
    }
    public int getMatchOfTour() {
        return matchOfTour;
    }
}
