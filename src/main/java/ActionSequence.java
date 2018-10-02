import java.util.Random;

public class ActionSequence {

    private SeleniumMethods sl;
    private MouseMovement ms;

    private static String username =PropertiesHandler.getUsrNameB();
    private static String password =PropertiesHandler.getPswD();
    private static float BET_SIZE =PropertiesHandler.getBetSize();

    ActionSequence(SeleniumMethods sl0,MouseMovement ms0) {
        sl=sl0;
        ms=ms0;
        //getUserInfo();
    }

    // Call selenium method to open the browser at the given url
    public void openBet() {
        sl.pageOpener("https://www.bet365.gr/");
    }

    // Scrolls the mouse to the "Ελληνικά" button
    public void languageScreen () {
        ms.scrollToView("//*[contains(text(), 'Ελληνικά')]");
        ms.onLeftClick();
    }

    // Scrolls the mouse at the userName textView inputs the userName,
    // does the exact same thing for the passWord
    // and presses enter or the go button 50% chance
    // TODO : Add the Xpath of the Go button from the Loggin Screen, uncomment lines and delete ( ms.pressEnter();)
    public void setCredentials() {
        // username element
        System.out.println(sl.getCoordinates("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[1]/input"));
        ms.scrollToView("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[1]/input");
        ms.onLeftClick();
        ms.typeString(username);
        // password element
        System.out.println(sl.getCoordinates("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[2]/input[1]"));
        ms.scrollToView("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[2]/input[1]");
        ms.onLeftClick();
        ms.typeString(password);
        // press enter or click go Button
        Random rand = new Random();
//        if(rand.nextInt(1)==1) {
//            ms.pressEnter();
//        }else {
//            ms.scrollToView();
//            ms.onLeftClick();
//        }
        //Must be Deleted
        ms.pressEnter();

    }
    // Finds the "Σε-Εξέλιξη" button and clicks it
    public void inPlay() {
        // Σε-Εξέλιξη element
        ms.scrollToViewForZero("/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
        ms.onLeftClick();
    }

    // Searches for the button that contains the text "Μπάσκετ"
    // if it is in view then it clicks it
    // otherwise it clicks the scroll button until it finds it
    // TODO : has to be checked
    public void basketCategory () {
        boolean isRunning=true;
        while (isRunning) {
            try {
                ms.scrollToView("//*[contains(text(), 'Μπάσκετ')]");
                ms.onLeftClick();
                isRunning=false;
            }catch (Exception e) {
                System.out.println("Not in View");
                ms.scrollToView("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div[3]");
                ms.onLeftClick();
            }
        }

    }

    // Searches for the textView and enters the amount of money to be played
    // then clicks the button for the bet to be placed
    public void placeBetSize(float betSize) {
        // TextInput betSize
        ms.scrollToView("html/body/div[1]/div/ul/li[3]/ul/li/div[3]/div[1]/div[1]/input");
        ms.onLeftClick();
        ms.typeString(String.valueOf(betSize));
        ms.randomDelay(1000,1500);
        // Place Bet Button
        Random rand = new Random();
        int i = rand.nextInt(3)+1;
        // The i refers to 3 textView in the Button itself
        ms.scrollToView("html/body/div[1]/div/ul/li[9]/a[2]/div/span["+i+"]");
        ms.onLeftClick();
    }

    // then if the bet was succesful click ok
    // else compare the current value with the given value
    // TODO : set conditions for each bet status
    // TODO : Get Xpath for the Elements that display the tourName,teamName value and Odd
    // TODO : Uncomment Lines to continue
//    public Boolean betStatus() {
//        boolean hasResult=false;
//        while(!hasResult) {
//            try {
//                if (sl.getText("/html/body/div[1]/div/ul/li[2]/span").equals("Παίχθηκε")) {
//                    return true;
//                }else if (sl.getText("/html/body/div[1]/div/ul/li[9]/a[1]/div").equals("Αποδοχή Αλλαγών")) {
//                    // Check again for values and act accordingly
//                    return false;
//                }
//            }
//        }
//    }

    // TODO : get UserName PassWord and BetSize from the main Screen (StartScreenController)
    public void getUserInfo () {
        //MainProgram.getInfo....
    }
}
