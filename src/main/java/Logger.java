import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Logger {

    Logger() {

    }

    public static void logStringtoLogFile(String string) {
        String fileName="LogText";
        try {
            FileWriter bw = new FileWriter(fileName,true);
            bw.write(string);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logStringToFile(String string,String fileName) {
        try {
            File file = new File(fileName);
            FileWriter bw = new FileWriter(file,true);
            bw.write(string);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
