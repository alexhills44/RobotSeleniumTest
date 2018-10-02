import java.io.*;
import java.util.Properties;

public class PropertiesHandler {

    // The Properties File cannot be Edited,we can only read or replace it
    // So we have to Store the Data temporarely

    private static String usrNameD,usrNameB,pswD,pswB;
    private static int betSize =0;
    private static int YOffset = 0;
    // usrAgent=0 is Firefox, usrAgent=1 is Chrome
    private static int usrAgent =0;
    private static boolean calibrate=true;
    private static boolean rememberMe = false;
    private static OutputStream output = null;
    private InputStream input = null;
    private static String chromeProfilePath="";
    private static Properties prop;

    // Once a new Instance of the Class is Created it automatically put the Properties in the Variables
    PropertiesHandler () {
        prop = new Properties();
        getProp();
    }

    // Has to be Called to Set the values on the Properties File
    public static void endProp() {
        setProp();
    }

    // Sets the Properties on the .propertie File
    private static void setProp() {
        try {
            output = new FileOutputStream("config.properties");
            prop.setProperty("USER_NAME_DUNKMAN",usrNameD);
            prop.setProperty("USER_NAME_BET",usrNameB);
            prop.setProperty("PASSWORD_DUNKMAN",pswD);
            prop.setProperty("PASSWORD_BET",pswB);
            prop.setProperty("BET_SIZE",String.valueOf(betSize));
            prop.setProperty("USER_AGENT",String.valueOf(usrAgent));
            prop.setProperty("CALIBRATION",String.valueOf(calibrate));
            prop.setProperty("REMEMBER_ME",String.valueOf(rememberMe));
            prop.setProperty("Y_OFFSET",String.valueOf(YOffset));
            prop.setProperty("CHROME_PROFILE_PATH",chromeProfilePath);
            prop.store(output, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Gets the Properties from the .propertie File
    private void getProp() {
        try {
            input = new FileInputStream(new File("config.properties"));
            //Load Properties File
            prop.load(input);
            usrNameD=prop.getProperty("USER_NAME_DUNKMAN");
            usrNameB=prop.getProperty("USER_NAME_BET");
            pswD=prop.getProperty("PASSWORD_DUNKMAN");
            pswB=prop.getProperty("PASSWORD_BET");
            usrAgent=Integer.parseInt(prop.getProperty("USER_AGENT"));
            betSize = Integer.parseInt(prop.getProperty("BET_SIZE"));
            calibrate=Boolean.parseBoolean(prop.getProperty("CALIBRATION"));
            rememberMe=Boolean.parseBoolean(prop.getProperty("REMEMBER_ME"));
            YOffset=Integer.parseInt(prop.getProperty("Y_OFFSET"));
            chromeProfilePath=prop.getProperty("CHROME_PROFILE_PATH");
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Getter and Setter Methods
    public static String getUsrNameD() {
        return usrNameD;
    }
    public static String getUsrNameB() {
        return usrNameB;
    }
    public static String getPswD() {
        return pswD;
    }
    public static String getPswB() {
        return pswB;
    }
    public static int getBetSize() {
        return betSize;
    }
    public static int getUsrAgent() {
        return usrAgent;
    }
    public static int getYOffset() {
        return YOffset;
    }
    public static boolean isCalibrate() {
        return calibrate;
    }
    public static boolean isRememberMe() {return rememberMe;}
    public static void setUsrNameD(String usrNameD) {
        PropertiesHandler.usrNameD = usrNameD;

    }
    public static void setUsrNameB(String usrNameB) {
        PropertiesHandler.usrNameB = usrNameB;

    }
    public static void setPswD(String pswD) {
        PropertiesHandler.pswD = pswD;

    }
    public static void setPswB(String pswB) {
        PropertiesHandler.pswB = pswB;

    }
    public static void setBetSize(int betSize) {
        PropertiesHandler.betSize = betSize;

    }
    public static void setUsrAgent(int usrAgent) {
        PropertiesHandler.usrAgent = usrAgent;

    }
    public static void setYOffset(int YOffset) {
        PropertiesHandler.YOffset=YOffset;

    }
    public static void setCalibrate(boolean calibrate) {
        PropertiesHandler.calibrate = calibrate;

    }
    public static void setRememberMe(boolean rememberMe) {
        PropertiesHandler.rememberMe = rememberMe;

    }
    public static String getChromeProfilePath() {
        return chromeProfilePath;
    }
    public static void setChromeProfilePath(String chromeProfilePath) {
        PropertiesHandler.chromeProfilePath = chromeProfilePath;

    }
}
