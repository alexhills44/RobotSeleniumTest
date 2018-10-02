import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesXpath {
    private static Properties prop;
    private static FileInputStream input;

    PropertiesXpath () {
        prop = new Properties();
    }

    public static String getProp(String key) {
        try {
            input = new FileInputStream(new File("xpathConstants.properties"));
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
