import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ZStartSceneController implements Initializable{
    
    @FXML
    private TextField usrB,betSizeField;
    @FXML
    private ChoiceBox<String> usrAgent;
    @FXML
    private PasswordField pswB;
    @FXML
    private CheckBox rememberMe,calibrate;




    @FXML
    public void startButton() {
        getValuesFromFields();

        ExecutorService x = Executors.newFixedThreadPool(20);
        x.execute(new ReadTerminal());
    }

    // Initializes the Scene Values note: controller must implement Initializable
    // If we Need to Add more User Agents here we Come
    public void initialize(URL location, ResourceBundle resources) {
        usrAgent.getItems().addAll("Firefox","Chrome");
        setValuesOnFields();
    }

    // Takes the Text from the PropertiesHandler and sets the Values of the JavaFX Elements
    private void setValuesOnFields () {
        if (PropertiesHandler.isRememberMe()) {
            usrB.setText(PropertiesHandler.getUsrNameB());
            pswB.setText(PropertiesHandler.getPswB());
            betSizeField.setText(String.valueOf(PropertiesHandler.getBetSize()));
            rememberMe.setSelected(PropertiesHandler.isRememberMe());
            calibrate.setSelected(PropertiesHandler.isCalibrate());
            // When usrAgent is 0 use Firefox , if usrAgent is 1 use Chrome
            if (PropertiesHandler.getUsrAgent()==0) {
                usrAgent.setValue("Firefox");
            }else {
                usrAgent.setValue("Chrome");
            }
        }
    }

    // Set the Properties Values According to the TextField values
    private void getValuesFromFields () {
        if (rememberMe.isSelected()) {
            PropertiesHandler.setUsrNameB(usrB.getText());
            PropertiesHandler.setPswB(pswB.getText());
            PropertiesHandler.setRememberMe(rememberMe.isSelected());
            PropertiesHandler.setBetSize(Float.valueOf(betSizeField.getText()));
            PropertiesHandler.setCalibrate(calibrate.isSelected());
            // Firefox is chosen then se Value to 0, if Chrome is chosen then set the value to 1
            if (usrAgent.getValue().equals("Firefox")) {
                PropertiesHandler.setUsrAgent(0);
            }else if (usrAgent.getValue().equals("Chrome")) {
                PropertiesHandler.setUsrAgent(1);
            }
            // Must always be called when changes on Prop file have been made
            PropertiesHandler.endProp();
        }
    }

}
