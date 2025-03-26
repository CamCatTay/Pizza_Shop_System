package pizza_shop_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSceneController {
    private String currentEmail;
    private String currentPassword;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;

    @FXML
    private void handleLoginButtonClick() {
        //Do something if email or password are not entered
        //Do something if both are entered and valid
        //Do something if both are entered but invalid
    }

    @FXML
    private void handleSignupButtonClick() {

    }

    @FXML
    private void handleEmailTextSubmitted(String email) {
        this.currentEmail = email;
    }

    @FXML
    private void handlePasswordTextSubmitted(String password) {
        this.currentPassword = password;
    }

}