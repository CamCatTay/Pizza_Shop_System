package pizza_shop_system.gui.authentication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pizza_shop_system.account.services.AccountService;
import pizza_shop_system.gui.account.AccountMenuController;
import pizza_shop_system.gui.base.BaseController;

import java.io.IOException;

public class LoginController extends BaseController {
    private final AccountService accountService = new AccountService();

    @FXML
    private VBox resultsContainer;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton, signupButton;

    private void switchToSignUpScreen() {
        sceneController.switchScene("SignUp");
    }

    // Email or password was invalid so notify user of this
    private void displayIncorrectEmailOrPassword() {
        Label label = new Label("Incorrect Email or Password");
        resultsContainer.getChildren().add(label);
    }

    // Clear displays of login screen
    private void displaySuccessfulLogin() {
        resultsContainer.getChildren().clear(); // Clear since login worked
        emailField.setText("");
        passwordField.setText("");
    }

    // Attempt to log in and display the result
    private void login() throws IOException {
        resultsContainer.getChildren().clear(); // Clear previous results

        String result = accountService.login(emailField.getText(), passwordField.getText());

        switch (result) {
            case "Success" -> {
                displaySuccessfulLogin();
                System.out.println("Log in Success");

                // Check account type of active user and switch accordingly
                var user = accountService.getActiveUser();

                if (user != null) {
                    if ("manager".equalsIgnoreCase(user.getAccountType())) {
                        sceneController.switchScene("AccountMenu");
                    } else {
                        sceneController.switchScene("Menu");
                    }
                } else {
                    System.out.println("No active user found.");
                }
            }
            case "EmailDoesNotExist" -> {
                displayIncorrectEmailOrPassword();
                System.out.println("Email Does Not Exist");
            }
            case "IncorrectPassword" -> {
                displayIncorrectEmailOrPassword();
                System.out.println("Incorrect Password");
            }
            default -> System.out.println("Unknown Error");
        }
    }

    @FXML
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