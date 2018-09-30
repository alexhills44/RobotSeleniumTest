import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

import java.awt.Point;
import java.util.Set;

public class SeleniumMethods {
    // This Class must be the same the whole time otherwise it will open a new Window
    private WebDriver driver;

    //Open first Time
    public SeleniumMethods() {
        FirefoxOptions options = new FirefoxOptions();
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile selenium_profile = allProfiles.getProfile("Default");
        options.setProfile(selenium_profile);
        options.addPreference("dom.ipc.plugins.enabled.libflashplayer.so",true);
        driver = new FirefoxDriver(options);
    }

    //Open rest of the Times and pass in the cookies from the previous SeleniumMethods Object
    public SeleniumMethods(Set<Cookie> cookies) {
        FirefoxOptions options = new FirefoxOptions();
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile selenium_profile = allProfiles.getProfile("Default");
        options.setProfile(selenium_profile);
        options.addPreference("dom.ipc.plugins.enabled.libflashplayer.so",true);
        driver = new FirefoxDriver(options);
        for(Cookie cookie:cookies) {
            driver.manage().addCookie(cookie);
        }
    }

    // Open Website and Window at fullscreen
    public void pageOpener (String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    // Digital Click
    @SuppressWarnings("Dangerous Territory")
    public void onClick(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    // Closes Browser window
    public void pageCloser() {
        driver.close();
    }

    // Get Text from the element in the specified Xpath
    public String getText(String xpath){
        return driver.findElement(By.xpath(xpath)).getText();
    }

    // Get the X Coordinates from the element in the specified Xpath
    public int getCoordinatesX(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getX());
    }

    // Get the Y Coordinates from the element in the specified Xpath
    public int getCoordinatesY(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getY());
    }

    // Get awt.Point from the element in the specified Xpath
    public Point getCoordinates(String xpath) {
        int x = getCoordinatesX(xpath);
        int y = getCoordinatesY(xpath);
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }

    // Digitally Scroll
    @SuppressWarnings("Dangerous Territory")
    public void scrollBy(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+pixels+")");

    }

    // Get Long value of the amount of pixels Scrolled
    public long getScrolledY() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return  (Long) js.executeScript("return window.pageYOffset;");
    }

    // Get the surface of the element
    public Point getElementSurface (String xpath) {
        int x = driver.findElement(By.xpath(xpath)).getSize().getWidth();
        int y = driver.findElement(By.xpath(xpath)).getSize().getHeight();
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }

    // Gets Cookies
    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }
}
