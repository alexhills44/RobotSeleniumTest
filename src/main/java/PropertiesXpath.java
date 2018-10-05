import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesXpath {
    private static Properties prop;
    private static FileInputStream input;
    private static FileOutputStream output;

    PropertiesXpath () {
        prop = new Properties();
    }

    public static String getProp(String key) {
        try {
            input = new FileInputStream(new File("xpathConstants.properties"));
            prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));
            return prop.getProperty(key);
        } catch (FileNotFoundException e) {
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
        return null;
    }

    public static void setProp() {
        try {
            output = new FileOutputStream("xpathConstants.properties");
            prop.setProperty("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","/html/body/div[1]/div/div[1]/div/div[1]/div[1]/nav/a[2]");
            prop.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
