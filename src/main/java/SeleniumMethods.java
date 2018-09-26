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
    }

    public void onClick(String xpath) {
        //Message for you
        driver.findElement(By.xpath(xpath)).click();
    }

    public void pageCloser() {
        driver.close();
    }

    public void getCoordinates(String xpath){
        System.out.println((driver.findElement(By.xpath(xpath)).getLocation().getX()));
        System.out.println((driver.findElement(By.xpath(xpath)).getLocation().getY()));
//        WebElement element=driver.findElement(By.xpath(xpath));
//        Point point=element.getLocation();
    }
}
