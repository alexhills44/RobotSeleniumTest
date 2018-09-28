import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.Point;

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

    public Point getCoordinates(String xpath) {
        int x = getCoordinatesX(xpath);
        int y = getCoordinatesY(xpath);
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }

    public void scrollBy(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+pixels+")");

    }

    public long getScrolledY() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long pixels = (Long) js.executeScript("return window.pageYOffset;");
        return  pixels;
    }

    public Point getElementSurface (String xpath) {
        int x = driver.findElement(By.xpath(xpath)).getSize().getWidth();
        int y = driver.findElement(By.xpath(xpath)).getSize().getHeight();
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }
}
