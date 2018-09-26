import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SeleniumMethods {
    WebDriver driver;
    public SeleniumMethods() {
        driver = new FirefoxDriver();
    }

    public void pageOpener (String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void onClick(String xpath) {
        //Message for you
        driver.findElement(By.xpath(xpath)).click();
    }

    public void pageCloser() {
        driver.close();
    }

    public String getText(String xpath){
        return driver.findElement(By.xpath(xpath)).getText();
    }
    public int getCoordinatesX(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getX());
    }

    public int getCoordinatesY(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getY());
    }
}
