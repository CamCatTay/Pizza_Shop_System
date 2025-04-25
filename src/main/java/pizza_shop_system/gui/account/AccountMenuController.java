package pizza_shop_system.gui.account;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.account.services.AccountService;
import pizza_shop_system.gui.base.BaseController;

import java.io.IOException;

public class AccountMenuController extends BaseController {
    @FXML
    private Label accountTypeLabel, saveStatusLabel;
    @FXML
    private TextField nameField, emailField;
    @FXML
    private PasswordField oldPasswordField, newPasswordField;
    @FXML
    private Button saveButton, generateReportsButton, manageAccountsButton;
    @FXML
    private StackPane managerMenusContainer;

    private AccountService accountService = new AccountService();

    public void updateAccountInformationDisplay() {

    }

    private void setSaveStatusLabel(String text, int waitTime) {
        new Thread(() -> {
            try {
                Platform.runLater(() -> saveStatusLabel.setText(text)); // Wrapped in Platform.runLater so it executes on UI thread
                Thread.sleep(waitTime * 1000L);
                Platform.runLater(() -> saveStatusLabel.setText(""));
            } catch (InterruptedException e) {
                System.out.println("Could not set text of status label " + e.getClass());
            }
        }).start();


    }

    private void saveAccountInformation() throws IOException {

        int activeUserId = accountService.getActiveUserId();

        // Save name and email
        accountService.updateUser(activeUserId, "name", nameField.getText());
        accountService.updateUser(activeUserId, "email", emailField.getText());

        // Check if old password is correct and if it is set the users password to new password
        JSONArray users = accountService.loadUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getInt("user_id") == activeUserId && !oldPasswordField.getText().isEmpty()) {
                if (user.getString("password").equals(oldPasswordField.getText())) {
                    accountService.updateUser(accountService.getActiveUserId(), "password", newPasswordField.getText());
                } else {
                   setSaveStatusLabel("Old password is incorrect", 3);
                }
                break;
            }
        }

        // Everything successfully saved
        setSaveStatusLabel("Saved!", 1);
    }

    @FXML
    public void initialize() {
        managerMenusContainer.setVisible(false);

        // set on actions for buttons
        generateReportsButton.setOnAction(_ -> sceneController.switchScene("GenerateReports"));
        manageAccountsButton.setOnAction(_ -> sceneController.switchScene("ManageAccounts"));
        saveButton.setOnAction(_ -> {
            try {
                saveAccountInformation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
