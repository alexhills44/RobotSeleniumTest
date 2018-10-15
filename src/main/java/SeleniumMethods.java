import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class SeleniumMethods {
    // This Class must be the same the whole time otherwise it will open a new Window
    private WebDriver driver;

    // TODO : Add user Profile in chrome
    // TODO : Make Chrome work
    //Open first Time
    SeleniumMethods() {
        if (PropertiesHandler.getUsrAgent()==0) {
            System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
//            FirefoxOptions options = new FirefoxOptions();
//            ProfilesIni allProfiles = new ProfilesIni();
//            FirefoxProfile selenium_profile = allProfiles.getProfile("default");
//            options.setProfile(selenium_profile);
//            options.addPreference("dom.ipc.plugins.enabled.libflashplayer.so",true);
            driver = new FirefoxDriver();
        }else if(PropertiesHandler.getUsrAgent()==1){
            System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("user-data-dir="+PropertiesHandler.getChromeProfilePath());
            options.addArguments("--always-authorize-plugins=true");
            driver = new ChromeDriver(options);
        }
    }//Κροατία - A1 Λίγκα---Μπόσκο---1---12

    //Open rest of the Times and pass in the cookies from the previous SeleniumMethods Object
    @SuppressWarnings("unused")
    SeleniumMethods(Set<Cookie> cookies) {
        FirefoxOptions options = new FirefoxOptions();
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile selenium_profile = allProfiles.getProfile("default");
        options.setProfile(selenium_profile);
        options.addPreference("dom.ipc.plugins.enabled.libflashplayer.so",true);
        driver = new FirefoxDriver(options);
        for(Cookie cookie:cookies) {
            driver.manage().addCookie(cookie);
        }
    }

    // Open Website and Window at fullscreen
    void pageOpener (String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    // Digital Click
    // Dangerous Territory
    @SuppressWarnings("unused")
    public void onClick(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    // Closes Browser window
    void pageCloser() {
        driver.close();
    }

    // Get Text from the element in the specified Xpath
    String getText(String xpath){
        return driver.findElement(By.xpath(xpath)).getText();
    }

    // Get the X Coordinates from the element in the specified Xpath
    int getCoordinatesX(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getX());
    }

    // Get the Y Coordinates from the element in the specified Xpath
    int getCoordinatesY(String xpath){
        return (driver.findElement(By.xpath(xpath)).getLocation().getY());
    }

    // Get awt.Point from the element in the specified Xpath
    Point getCoordinates(String xpath) {
        int x = getCoordinatesX(xpath);
        int y = getCoordinatesY(xpath);
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }

    // Digitally Scroll
    // Dangerous Territory
    @SuppressWarnings("unused")
    public void scrollBy(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+pixels+")");

    }

    // Get Long value of the amount of pixels Scrolled
    long getScrolledY() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return  (Long) js.executeScript("return window.pageYOffset;");
    }

    // Get the surface of the element
    Point getElementSurface (String xpath) {
        int x = driver.findElement(By.xpath(xpath)).getSize().getWidth();
        int y = driver.findElement(By.xpath(xpath)).getSize().getHeight();
        Point pointAwt= new Point();
        pointAwt.setLocation(x,y);
        return pointAwt;
    }

    void switchFrame(String id) {
        driver.switchTo().frame(id);
    }

    void switchToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    // Gets Cookies
    @SuppressWarnings("unused")
    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    public void tabSwitch(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber)); //switches to new tab
    }
}
