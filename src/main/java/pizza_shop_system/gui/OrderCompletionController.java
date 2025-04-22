package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class OrderCompletionController extends BaseController {

    @FXML private TextArea receiptTextArea;
    @FXML private Button orderAgainButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        orderAgainButton.setOnAction(e -> orderAgain());
        exitButton.setOnAction(e -> exitApplication());
        loadExampleReceipt();
    }

    private void orderAgain() {
        System.out.println("Redirecting to the order page...");
        sceneController.switchScene("Menu");
    }

    private void exitApplication() {
        System.out.println("Exiting application...");
    }

    private void loadExampleReceipt() {
        String exampleReceipt = """
                ******** Receipt **********
                Pizza Palace
                123 Main Street, Pizzatown
                Phone: 555-123-4567
                Order Number: A2345
                Date/Time: 4-20-2025 18:30
                Customer: John Doe
                -----------------------------
                Margherita Pizza x1 - $15.99
                Garlic Breadsticks x1 - $5.99
                Soft Drink x1 - $2.50
                -----------------------------
                Subtotal: $20.00
                Tax: $1.48
                Tip: $2.00
                Total Amount: $24.48
                Payment Method: Cash
                *****************************
                """;

        receiptTextArea.setText(exampleReceipt);
    }

}