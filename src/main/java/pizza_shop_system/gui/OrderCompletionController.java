package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pizza_shop_system.order.Receipt;

public class OrderCompletionController extends BaseController {

    @FXML private TextArea receiptTextArea;
    @FXML private Button orderAgainButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        orderAgainButton.setOnAction(e -> orderAgain());

        exitButton.setOnAction(e -> exitApplication());
    }

    public void displayReceipt(Receipt receipt) {
        String receiptContent = receipt.generateReceipt();
        receiptTextArea.setText(receiptContent);
    }

    private void orderAgain() {
        System.out.println("Redirecting to the order page...");
        sceneController.switchScene("Menu");
    }

    private void exitApplication() {
        System.out.println("Exiting application...");
    }
}