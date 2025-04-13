package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OrderCompletionController extends BaseController {

    @FXML private Label orderNumberLabel;
    @FXML private Label orderTotalLabel;
    @FXML private Label deliveryTimeLabel;

    @FXML private Button orderAgainButton;
    @FXML private Button exitButton;

    // Dynamically set order details
    public void setOrderDetails(String orderNumber, double total, String estimatedDeliveryTime) {
        orderNumberLabel.setText("Order Number: #" + orderNumber);
        orderTotalLabel.setText("Total: $" + String.format("%.2f", total));
        deliveryTimeLabel.setText("Estimated Delivery Time: " + estimatedDeliveryTime);
    }

    @FXML
    private void initialize() {
        orderAgainButton.setOnAction(e -> sceneController.switchScene("Menu"));
        exitButton.setOnAction(e -> exitApplication());
    }

    private void exitApplication() {
        System.out.println("Exiting application...");
        // Add logic to close the current scene or exit the application
    }
}