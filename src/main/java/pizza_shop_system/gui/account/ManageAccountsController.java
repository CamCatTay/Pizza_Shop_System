package pizza_shop_system.gui.account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pizza_shop_system.gui.base.BaseController;

public class ManageAccountsController extends BaseController {

    @FXML
    private VBox accountsVBox;

    @FXML
    private Label totalAccountsLabel;

    // Account records (This is just for example in sprint 1 we will take them from users file later)
    private final String[][] accountData = {
            {"001", "John Doe", "Customer"},
            {"002", "Jane Smith", "Employee"},
            {"003", "Michael Johnson", "Manager"}
    };

    @FXML
    private void initialize() {
        // Load accounts dynamically
        for (String[] account : accountData) {
            addAccountRow(account[0], account[1], account[2]);
        }

        // Update total accounts label
        totalAccountsLabel.setText("Total Accounts: " + accountData.length);
    }

    private void addAccountRow(String id, String name, String role) {
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
        editButton.setOnAction(e -> handleEdit(id));

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("account-button");
        deleteButton.setOnAction(e -> handleDelete(id));

        // Add all elements to the HBox
        row.getChildren().addAll(idLabel, nameLabel, roleLabel, editButton, deleteButton);

        // Add the row to the VBox
        accountsVBox.getChildren().add(row);
    }

    private void handleEdit(String id) {
        System.out.println("Edit button clicked for User ID: " + id);
        // Add logic here to edit account (e.g., open a dialog)
    }

    private void handleDelete(String id) {
        System.out.println("Delete button clicked for User ID: " + id);
        // Add logic here to delete account
    }
}