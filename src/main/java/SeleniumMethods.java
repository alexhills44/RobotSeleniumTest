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
        //Message for you
        driver.findElement(By.xpath(xpath)).click();
    }

    public void pageCloser() {
        driver.close();
    }

}
