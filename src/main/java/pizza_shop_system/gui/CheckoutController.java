package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pizza_shop_system.order.CurrentOrder;
import pizza_shop_system.order.Order;
import pizza_shop_system.order.OrderStatus;

import java.io.IOException;

public class CheckoutController extends BaseController{

    @FXML private Label totalLabel;
    @FXML private ComboBox<String> paymentMethodComboBox;

    @FXML private Label cardNumberLabel;
    @FXML private TextField cardNumberField;
    @FXML private Label cvvLabel;
    @FXML private TextField cvvField;
    @FXML private Label expirationLabel;
    @FXML private TextField expirationDateField;

    @FXML private Label checkNumberLabel;
    @FXML private TextField checkNumberField;

    @FXML private RadioButton deliveryRadioButton;
    @FXML private RadioButton pickupRadioButton;
    @FXML private ToggleGroup orderTypeGroup;

    @FXML private Label addressLabel;
    @FXML private TextField addressField;

    @FXML private Button confirmButton;

    private double orderTotal;

    /**
     * Initializes the controller after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Create and assign ToggleGroup for delivery and pickup options
        orderTypeGroup = new ToggleGroup();
        deliveryRadioButton.setToggleGroup(orderTypeGroup);
        pickupRadioButton.setToggleGroup(orderTypeGroup);

        // Set the default selection to Pickup
        pickupRadioButton.setSelected(true);

        // Populate payment method options
        paymentMethodComboBox.getItems().addAll("Credit Card", "Debit Card", "Check", "Cash");

        // Hide all conditional fields initially
        hideAllPaymentFields();
        addressLabel.setVisible(false);
        addressField.setVisible(false);

        // Set up event listeners for Delivery and Pickup
        deliveryRadioButton.setOnAction(e -> showDeliveryFields());
        pickupRadioButton.setOnAction(e -> hideDeliveryFields());

        // Set up event listener for Payment Method selection
        paymentMethodComboBox.setOnAction(e -> handlePaymentMethodSelection());

        // Confirm button processing
        confirmButton.setOnAction(e -> processOrder());
    }

    /**
     * Passes the total to the controller and updates the displayed total value.
     */
    public void setTotal(double total) {
        this.orderTotal = total;
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    /**
     * Hides all payment fields initially.
     */
    private void hideAllPaymentFields() {
        // Hide credit card fields
        cardNumberLabel.setVisible(false);
        cardNumberField.setVisible(false);
        cvvLabel.setVisible(false);
        cvvField.setVisible(false);
        expirationLabel.setVisible(false);
        expirationDateField.setVisible(false);

        // Hide check payment fields
        checkNumberLabel.setVisible(false);
        checkNumberField.setVisible(false);
    }

    /**
     * Shows appropriate payment fields based on the selected payment method.
     */
    private void handlePaymentMethodSelection() {
        String paymentMethod = paymentMethodComboBox.getValue();
        hideAllPaymentFields(); // Hide all fields first

        if ("Credit Card".equals(paymentMethod) || "Debit Card".equals(paymentMethod)) {
            // Show credit card fields
            cardNumberLabel.setVisible(true);
            cardNumberField.setVisible(true);
            cvvLabel.setVisible(true);
            cvvField.setVisible(true);
            expirationLabel.setVisible(true);
            expirationDateField.setVisible(true);
        } else if ("Check".equals(paymentMethod)) {
            // Show check payment fields
            checkNumberLabel.setVisible(true);
            checkNumberField.setVisible(true);
        }
        // For Cash, no additional fields are necessary
    }

    /**
     * Shows delivery-specific fields when Delivery is selected.
     */
    private void showDeliveryFields() {
        addressLabel.setVisible(true); // Show delivery address fields
        addressField.setVisible(true);
    }

    /**
     * Hides delivery-specific fields when Pickup is selected.
     */
    private void hideDeliveryFields() {
        addressLabel.setVisible(false); // Hide delivery address fields
        addressField.setVisible(false);
    }

    /**
     * Handles order processing when the Confirm button is clicked.
     */
    private void processOrder() {
        String orderType = deliveryRadioButton.isSelected() ? "Delivery" : "Pickup";
        String paymentMethod = paymentMethodComboBox.getValue();

        // Basic validations
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            System.out.println("Please select a payment method.");
            return;
        }
        if ("Delivery".equals(orderType) &&
                (addressField.getText().isEmpty() || addressField.getText().isBlank())) {
            System.out.println("Please enter a valid delivery address.");
            return;
        }
        if (("Credit Card".equals(paymentMethod) || "Debit Card".equals(paymentMethod))
                && (cardNumberField.getText().isEmpty() || cvvField.getText().isEmpty() || expirationDateField.getText().isEmpty())) {
            System.out.println("Please fill in all credit card details.");
            return;
        }
        if ("Check".equals(paymentMethod) && checkNumberField.getText().isEmpty()) {
            System.out.println("Please enter a valid check number.");
            return;
        }

        // Convert CurrentOrder to an actual Order instance
        CurrentOrder cart = CurrentOrder.getInstance();
        Order finalizedOrder = cart.toOrder();

        try {
            finalizedOrder.setStatus(OrderStatus.IN_PROGRESS);
            Order.updateOrder(finalizedOrder);
            System.out.println("Order saved successfully.");
            System.out.println("Your order #" + finalizedOrder.getOrderID() + " has been confirmed!");
            CurrentOrder.reset();
            sceneController.switchScene("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Order Type: " + orderType);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Order confirmed. Total: $" + orderTotal);


    }

}