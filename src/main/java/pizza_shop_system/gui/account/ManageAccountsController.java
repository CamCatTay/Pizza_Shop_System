package pizza_shop_system.gui.account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pizza_shop_system.account.entities.User;
import pizza_shop_system.account.services.AccountService;

import java.io.IOException;

public class ManageAccountsController extends BaseController {

    @FXML
    private VBox accountsVBox;

    @FXML
    private Label totalAccountsLabel;

    private AccountService accountService = new AccountService(); // Service to manage account operations

    @FXML
    private void initialize() {
        loadAccounts();
    }

    private void loadAccounts() {
        try {
            JSONArray users = accountService.loadUsers(); // Load all users
            accountsVBox.getChildren().clear(); // Clear existing entries

            for (int i = 1; i < users.length(); i++) { // Start from 1 to skip metadata
                JSONObject user = users.getJSONObject(i);
                addAccountRow(user);
            }

            totalAccountsLabel.setText("Total Accounts: " + (users.length() - 1)); // Adjust for metadata
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAccountRow(JSONObject user) {
        // Create an HBox for the account row
        HBox row = new HBox(10);
        row.getStyleClass().add("account-row");

        // Create labels for ID, Name, and Role
        Label idLabel = new Label("User ID: " + user.getInt("user_id"));
        Label nameLabel = new Label("Name: " + user.getString("name"));
        Label roleLabel = new Label("Role: " + user.getString("account_type"));

        // Create buttons for 'Edit' and 'Delete'
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEdit(user));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> handleDelete(user.getInt("user_id")));

        // Add all elements to the HBox
        row.getChildren().addAll(idLabel, nameLabel, roleLabel, editButton, deleteButton);

        // Add the row to the VBox
        accountsVBox.getChildren().add(row);
    }

    private void handleEdit(JSONObject user) {
        // Open an edit dialog or a new screen to edit user details
        EditUserDialog dialog = new EditUserDialog(user, accountService);
        dialog.showAndWait().ifPresent(result -> {
            if (result) {
                loadAccounts(); // Reload accounts after editing
            }
        });
    }

    private void handleDelete(int userId) {
        try {
            String response = accountService.deleteUser(userId);
            System.out.println(response);
            loadAccounts(); // Reload accounts after deletion
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
