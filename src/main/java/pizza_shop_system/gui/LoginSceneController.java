package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pizza_shop_system.account.ActiveUser;
import pizza_shop_system.account.LoginHandler;

import java.io.IOException;

public class LoginSceneController {
    private String submittedEmail;
    private String submittedPassword;
    private final LoginHandler loginHandler = new LoginHandler();
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;


    @FXML
    public void handleEmailChanged() {
        this.submittedEmail = emailField.getText();
    }

    @FXML
    public void handlePasswordChanged() {
        this.submittedPassword = passwordField.getText();
    }

    public void handleLoginClick() throws IOException {
        //Look in users.txt file for specified email and password. If either field is invalid alert user and return
        //If fields are valid get userID and switch to appropriate screen for customer, employee, or manager depending on account type. Pass userID to appropriate methods as well
        boolean loginSuccess = loginHandler.AttemptLogin(submittedEmail, submittedPassword);
        if (loginSuccess) {
            // Active user has been set in login handler. Use ActiveUser to retrieve it
            System.out.println("Login Success, Hello user: " + ActiveUser.getInstance().getCurrentUser());
            String accountType = ActiveUser.getInstance().getActiveUserData().split(",")[1].trim();
            System.out.println(accountType);
        } else {
            System.out.println("Login Failed. Invalid email or password.");
        }
    }

}