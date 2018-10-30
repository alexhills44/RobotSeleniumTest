import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;


public class Main extends Application{

    static Vector<String> tipList = new Vector<String>();
    static Vector<Long> tipSendTime = new Vector<Long>();
    static boolean diafores=false;

    // TODO : Create SeleniumMethod to Handle Elements not found and log erros
    // TODO : Get Tip from Dunkmantips.com
    // TODO : Create JAR
    // TODO : Add LogFile
    // TODO : Add YDiaforesHandler

    public static void main(String[] args) throws IOException {
        System.setErr( new PrintStream("C:\\Users\\Alex\\Desktop\\error.txt"));
        System.setOut( new PrintStream("C:\\Users\\Alex\\Desktop\\out.txt"));
        Initialization();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartScene.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Lazy Bet");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

        }catch (Exception e) {
            e.printStackTrace();
            Logger.logStringtoLogFile("Error : Could not load GUI : "+e.getMessage());
        }
    }
    //C:\Users\Costas\Desktop\Alex TeamViewr\AutoPlayBet365\src\main\resources\FXML\StartScene.fxml

    private static void Initialization () {
        new PropertiesXpath();
        new PropertiesHandler();
    }
}
