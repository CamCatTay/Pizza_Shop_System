package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pizza_shop_system.account.AccountService;
import java.io.IOException;

public class LoginController extends BaseController {
    private final AccountService accountService = new AccountService();

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton, signupButton;

    private void switchToSignUpScreen() {
        sceneController.switchScene("SignUp");
    }

    private void login() throws IOException {
        String result = accountService.login(emailField.getText(), passwordField.getText());

        // All possible results when attempting to log in
        switch (result) {
            case "Success" -> System.out.println("Log in Success");
            case "EmailDoesNotExist" -> System.out.println("Email Does Not Exist");
            case "IncorrectPassword" -> System.out.println("Incorrect Password");
            default -> System.out.println("Unknown Error");
        }
    }
    public void initialize() {
        loginButton.setOnAction(e -> {
            try {
                login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        signupButton.setOnAction(e -> switchToSignUpScreen());
    }
}