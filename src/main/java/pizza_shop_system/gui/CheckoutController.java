package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CheckoutController extends BaseController {

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

    @FXML private Button confirmButton;

    private double orderTotal = 0.0;

    public void setTotal(double total) {
        this.orderTotal = total;
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    @FXML
    private void initialize() {
        // Populate payment method options
        paymentMethodComboBox.getItems().addAll("Credit Card", "Debit Card", "Check", "Cash");

        // Hide all payment fields initially
        hideAllPaymentFields();

        // Add a listener to toggle visibility based on payment method
        paymentMethodComboBox.setOnAction(e -> togglePaymentFields(paymentMethodComboBox.getValue()));

        // Add functionality to the Confirm button
        confirmButton.setOnAction(e -> processPayment());
    }

    private void hideAllPaymentFields() {
        cardNumberLabel.setVisible(false);
        cardNumberField.setVisible(false);
        cvvLabel.setVisible(false);
        cvvField.setVisible(false);
        expirationLabel.setVisible(false);
        expirationDateField.setVisible(false);
        checkNumberLabel.setVisible(false);
        checkNumberField.setVisible(false);
    }

    private void togglePaymentFields(String paymentMethod) {
        hideAllPaymentFields(); // Reset visibility

        if ("Credit Card".equals(paymentMethod) || "Debit Card".equals(paymentMethod)) {
            // Show Credit/Debit card fields
            cardNumberLabel.setVisible(true);
            cardNumberField.setVisible(true);
            cvvLabel.setVisible(true);
            cvvField.setVisible(true);
            expirationLabel.setVisible(true);
            expirationDateField.setVisible(true);

        } else if ("Check".equals(paymentMethod)) {
            // Show Check Number field
            checkNumberLabel.setVisible(true);
            checkNumberField.setVisible(true);
        }
    }

    private void processPayment() {
        // Ensure a payment method is selected
        String selectedPayment = paymentMethodComboBox.getValue();
        if (selectedPayment == null) {
            System.out.println("Please select a payment method.");
            return;
        }

        if ("Credit Card".equals(selectedPayment) || "Debit Card".equals(selectedPayment)) {
            if (!validateCardInformation()) {
                System.out.println("Invalid card information. Please try again.");
                return;
            }
        } else if ("Check".equals(selectedPayment)) {
            if (!validateCheckNumber()) {
                System.out.println("Invalid check number. Please try again.");
                return;
            }
        }

        // Confirm payment
        System.out.println("Payment confirmed via " + selectedPayment);
        System.out.println("Order total: $" + String.format("%.2f", orderTotal));
    }

    private boolean validateCardInformation() {
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();
        String expirationDate = expirationDateField.getText();

        // Basic validation for card details
        if (cardNumber.isEmpty() || cardNumber.length() < 16) {
            return false;
        }
        if (cvv.isEmpty() || cvv.length() != 3) {
            return false;
        }
        if (expirationDate.isEmpty() || !expirationDate.matches("(0[1-9]|1[0-2])\\/\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean validateCheckNumber() {
        String checkNumber = checkNumberField.getText();

        // Basic validation for check number
        return !checkNumber.isEmpty() && checkNumber.length() >= 5;
    }
}