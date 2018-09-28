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

    public void languageScreen () {
        ms.scrollToView("//*[contains(text(), 'Ελληνικά')]");
        ms.onLeftClick();
    }

    public void setCredentials() {
        // username element
        ms.scrollToView("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[1]/input");
        ms.onLeftClick();
        ms.typeString(username);
        // password element
        ms.scrollToView("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/div/div[2]/input[2]");
        ms.onLeftClick();
        ms.typeString(password);
        // press enter
        ms.pressEnter();
        // Σε-Εξέλιξη element
        ms.scrollToView("/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
        ms.onLeftClick();
    }

    public void basketCategory () {
        ms.scrollToView("//*[contains(text(), 'Μπάσκετ')]");
        while (sl.getCoordinatesX("//*[contains(text(), 'Μπάσκετ')]")>600) {
            ms.scrollToView(button to move menu);
            ms.onLeftClick();
        }
        ms.scrollToView("//*[contains(text(), 'Μπάσκετ')]");
        ms.onLeftClick();

    }

    public void getUserInfo () {
        //MainProgram.getInfo....
    }
}
