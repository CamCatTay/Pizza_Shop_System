package pizza_shop_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpSceneController {
    private String submittedEmail;
    private String submittedPassword;
    private String submittedVerifyPassword;
    private String submittedName;
    private String submittedAddress;
    private String submittedPhoneNumber;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField verifyPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    private final SceneController sceneController = new SceneController();

    public void switchToLoginScene(ActionEvent actionEvent) throws IOException {
        sceneController.switchToLoginScene(actionEvent);
    }

    @FXML
    public void handleEmailChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handlePasswordChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handleVerifyPasswordChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handleNameChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handleAddressChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handlePhoneNumberChanged() {
        this.submittedEmail = emailField.getText();
    }

    public void handleSignUpClick() {

    }
}
