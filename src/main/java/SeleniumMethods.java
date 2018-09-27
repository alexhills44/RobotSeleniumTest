import org.openqa.selenium.*;
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

    public void getCoordinates( String xpath) {
        System.out.println( driver.findElement(By.xpath(xpath)).getLocation());
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
}
