package pizza_shop_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController {
    private String currentEmail;
    private String currentPassword;
    private final SceneController sceneController = new SceneController();

    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;

    //Utilize scene controller class to switch scenes, so I don't have to import all those classes again.
    public void switchToSignUpScene(ActionEvent actionEvent) throws IOException {
        sceneController.switchToSignUpScene(actionEvent);
    }

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