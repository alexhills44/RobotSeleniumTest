public class ActionSequence {

    SeleniumMethods sl;
    MouseMovement ms;

    private static String username = "pietrospan";
    private static String password = "captainhook13";
    private static float BET_SIZE = 0.25f;

    public ActionSequence(SeleniumMethods sl0,MouseMovement ms0) {
        sl=sl0;
        ms=ms0;
        //getUserInfo();
    }

    public void openBet() {
        sl.pageOpener("https://www.bet365.gr/");
    }

    public void languageScreen () {
        ms.scrollToView("//*[contains(text(), 'Ελληνικά')]");
        ms.onLeftClick();
    }

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
        // press enter
        ms.pressEnter();

    }

    public void inPlay() {
        // Σε-Εξέλιξη element
        ms.scrollToViewForZero("/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
        ms.onLeftClick();
    }

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
    //basket
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div[2]/div/div[12]
    //right arrow
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div[3]
    //left arrow
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div[1]
    //table of sports
    ///html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]

    public void getUserInfo () {
        //MainProgram.getInfo....
    }
}
