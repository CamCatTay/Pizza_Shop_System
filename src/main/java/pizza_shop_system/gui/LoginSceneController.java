package pizza_shop_system.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pizza_shop_system.account.LoginHandler;

import java.io.IOException;

public class LoginSceneController {
    private String submittedEmail;
    private String submittedPassword;
    private final SceneController sceneController = new SceneController();
    private final LoginHandler loginHandler = new LoginHandler();
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    public void switchToSignUpScene(ActionEvent actionEvent) throws IOException {
        sceneController.switchToSignUpScene(actionEvent);
    }

    @FXML
    public void handleEmailChanged() {
        this.submittedEmail = emailField.getText();
    }

    @FXML
    public void handlePasswordChanged() {
        this.submittedPassword = passwordField.getText();
    }

    public void handleLoginClick() {
        //Look in users.txt file for specified email and password. If either field is invalid alert user and return
        //If fields are valid get userID and switch to appropriate screen for customer, employee, or manager depending on account type. Pass userID to appropriate methods as well
        String userID = loginHandler.AttemptLogin(submittedEmail, submittedPassword);
        if (userID == null) {
            System.out.println("Login Failed");
        } else {
            System.out.println("Login Success, Hello user: " + userID);
        }
    }

}