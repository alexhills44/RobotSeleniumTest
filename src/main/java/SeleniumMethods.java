import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumMethods {
    WebDriver driver;
    public SeleniumMethods() {
        driver = new FirefoxDriver();
    }

    public void pageOpener (String url) {
        driver.get(url);
    }

    public void onClick(String xpath) {
        driver.findElement(By.xpath(xpath));
    }

    public void pageCloser() {
        driver.close();
    }

}
