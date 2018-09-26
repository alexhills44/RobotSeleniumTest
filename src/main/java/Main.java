

public class Main {
    public static void main(String[] args) throws Exception{
        System.setProperty("webdriver.gecko.driver", "src/geckodriver/geckodriver.exe");
        MainProgram mainProgram=new MainProgram();
        mainProgram.run();
    }
}
