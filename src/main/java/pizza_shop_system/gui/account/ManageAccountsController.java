package pizza_shop_system.gui.account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.account.services.AccountService;
import pizza_shop_system.gui.base.BaseController;

import java.io.IOException;

public class ManageAccountsController extends BaseController {
    @FXML
    private VBox accountsVBox;
    @FXML
    private Label totalAccountsLabel;

    private final AccountService accountService = new AccountService();

    // Add a row to account vbox with attached id, name, and role of said account
    private void addAccountRow(int id, String name, String role) {

        // Create an HBox for the account row
        HBox row = new HBox(10);
        row.getStyleClass().add("account-row");

        // Create labels for ID, Name, and Role
        Label idLabel = new Label("User ID: " + id);
        idLabel.getStyleClass().add("account-info-label");

        Label nameLabel = new Label("Name: " + name);
        nameLabel.getStyleClass().add("account-info-label");

        Label roleLabel = new Label("Role: " + role);
        roleLabel.getStyleClass().add("account-info-label");

        // Create buttons for 'Edit' and 'Delete'
        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("account-button");
        editButton.setOnAction(e -> editAccount(id));

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("account-button");
        deleteButton.setOnAction(e -> {
            try {
                removeAccount(id);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add all elements to the HBox
        row.getChildren().addAll(idLabel, nameLabel, roleLabel, editButton, deleteButton);

        // Add the row to the VBox
        accountsVBox.getChildren().add(row);
    }

    // edit the information of selected account id
    private void editAccount(int id) {
        System.out.println("Editing account " + id + " not implemented yet");
    }

    // remove selected account id from files
    private void removeAccount(int id) throws IOException {
        accountService.removeUser(id);
        updateAccountsDisplay();
    }

    // display all the account information of all users in Users.json
    public void updateAccountsDisplay() throws IOException {
        accountsVBox.getChildren().clear(); // Clear previous accounts

        int activeUserId = accountService.getActiveUserId();
        JSONArray users = accountService.loadUsers();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            int userId = user.getInt("user_id");
            if (userId == activeUserId) {continue;} // skip over the current manager account so it cannot be removed by mistake
            addAccountRow(userId, user.getString("name"), user.getString("account_type"));
        }
    }

    @FXML
    private void initialize() throws IOException {
        accountService.setManageAccountsController(this);
    }

}
