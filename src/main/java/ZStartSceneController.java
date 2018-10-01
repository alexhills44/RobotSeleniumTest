import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class ZStartSceneController implements Initializable{
    
    @FXML
    private TextField usrDT,usrB,betSizeField;
    @FXML
    private ChoiceBox<String> usrAgent;
    private ResourceBundle resourceBundle;
    private Locale locale;
    @FXML
    private PasswordField pswDT,pswB;
    @FXML
    private CheckBox rememberMe,calibrate;


    @FXML
    public void startButton(ActionEvent event) {
        getValuesFromFields();

//        MainProgram mainProgram=new MainProgram();
//        mainProgram.run();
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
            usrDT.setText(PropertiesHandler.getUsrNameD());
            usrB.setText(PropertiesHandler.getUsrNameB());
            pswDT.setText(PropertiesHandler.getPswD());
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
            PropertiesHandler.setUsrNameD(usrDT.getText());
            PropertiesHandler.setUsrNameB(usrB.getText());
            PropertiesHandler.setPswD(pswDT.getText());
            PropertiesHandler.setPswB(pswB.getText());
            PropertiesHandler.setRememberMe(rememberMe.isSelected());
            PropertiesHandler.setBetSize(Integer.valueOf(betSizeField.getText()));
            PropertiesHandler.setCalibrate(calibrate.isSelected());
            // Firefox is chosen then se Value to 0, if Chrome is chosen then set the value to 1
            if (usrAgent.getValue().equals("Firefox")) {
                PropertiesHandler.setUsrAgent(0);
            }else if (usrAgent.getValue().equals("Chrome")) {
                PropertiesHandler.setUsrAgent(1);
            }
            // Must always be called
        }
    }

}
