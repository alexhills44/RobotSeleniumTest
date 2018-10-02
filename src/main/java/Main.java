import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    // TODO : Get Tip from Dunkmantips.com
    // TODO : Move Project to Proper Folder
    // TODO : Create JAR
    // TODO : Add autoClose Bet
    // TODO : Add LogFile
    // TODO : When we search to play 2 or more bets simultaneously open on a new browser window and load with cookies (autoLogin)

    static PropertiesHandler properties;

    public static void main(String[] args) throws Exception{
        Initialization();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartScene.fxml"));
//            loader.setController(new ZStartSceneController());
            Parent root = loader.load();
            primaryStage.setTitle("Dunkman Tips");
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Initialization () {

        properties = new PropertiesHandler();
    }
}
