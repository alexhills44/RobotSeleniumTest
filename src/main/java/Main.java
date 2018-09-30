

public class Main {

    // TODO : Add the FXML File
    // TODO : Move Project to Proper Folder
    // TODO : Create JAR
    // TODO : Add Chrome Capabilities and give the user the choice
    // TODO : On first time launch do a calibration
    // TODO : Add autoClose Bet
    // TODO : Add userFile
    // TODO : Add LogFile
    // TODO : When we search to play 2 or more bets simultaneously open on a new browser window and load with cookies (autoLogin)

    public static void main(String[] args) throws Exception{
        System.setProperty("webdriver.gecko.driver", "src/geckodriver/geckodriver.exe");
        MainProgram mainProgram=new MainProgram();
        mainProgram.run();
    }
}
