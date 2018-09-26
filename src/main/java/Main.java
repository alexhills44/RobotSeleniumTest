

public class Main {
    public static void main(String[] args) throws Exception{
        System.setProperty("webdriver.gecko.driver", "src/geckodriver/geckodriver.exe");
        SeleniumMethods sl=new SeleniumMethods();
        sl.pageOpener("http://www.example.com");
        Thread.sleep(5000);
        sl.onClick("/html/body/div/p[2]/a");
    }
}
