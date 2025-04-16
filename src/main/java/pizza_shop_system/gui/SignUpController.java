package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pizza_shop_system.account.AccountService;

import java.io.IOException;

public class SignUpController extends BaseController {
    private final AccountService accountService = new AccountService();

    @FXML
    private TextField emailField, nameField, addressField, phoneNumberField;
    @FXML
    private PasswordField passwordField, verifyPasswordField;
    @FXML
    private Button signupButton, loginButton;

    private void signUp() throws IOException {
        String result = accountService.signUp(emailField.getText(), passwordField.getText(), verifyPasswordField.getText(), nameField.getText(), addressField.getText(), phoneNumberField.getText());

        // All possible results when attempting to sign up
        switch (result) {
            case "Success" -> System.out.println("Sign Up Success");
            case "DuplicateEmail" -> System.out.println("Duplicate Email");
            case "PasswordsDoNotMatch" -> System.out.println("Passwords Do Not Match");
            default -> System.out.println("Unknown Error");
        }
    }

    private void switchToLoginScreen() {
        sceneController.switchScene("Login");
    }

    public void initialize() {
        signupButton.setOnAction(e -> {
            try {
                signUp();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.setOnAction(e -> switchToLoginScreen());
    }
}
