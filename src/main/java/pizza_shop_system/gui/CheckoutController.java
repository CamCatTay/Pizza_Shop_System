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
        paymentMethodComboBox.getItems().addAll("Credit Card", "Debit Card", "Check", "Cash");

        // Hide all input fields and labels initially
        hideAllPaymentFields();

        // Update visibility when payment method changes
        paymentMethodComboBox.setOnAction(e -> togglePaymentFields(paymentMethodComboBox.getValue()));

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
        hideAllPaymentFields(); // Hide everything first

        if (paymentMethod.equals("Credit Card") || paymentMethod.equals("Debit Card")) {
            cardNumberLabel.setVisible(true);
            cardNumberField.setVisible(true);
            cvvLabel.setVisible(true);
            cvvField.setVisible(true);
            expirationLabel.setVisible(true);
            expirationDateField.setVisible(true);
        } else if (paymentMethod.equals("Check")) {
            checkNumberLabel.setVisible(true);
            checkNumberField.setVisible(true);
        }
    }

    private void processPayment() {
        String selectedPayment = paymentMethodComboBox.getValue();
        if (selectedPayment == null) {
            System.out.println("Please select a payment method.");
            return;
        }

        if (selectedPayment.equals("Credit Card") || selectedPayment.equals("Debit Card")) {
            String cardNumber = cardNumberField.getText();
            String cvv = cvvField.getText();
            String expirationDate = expirationDateField.getText();

            if (cardNumber.isEmpty() || cardNumber.length() < 16 || cvv.length() != 3 || expirationDate.isEmpty()) {
                System.out.println("Invalid card information.");
                return;
            }
        } else if (selectedPayment.equals("Check")) {
            String checkNumber = checkNumberField.getText();
            if (checkNumber.isEmpty() || checkNumber.length() < 5) {
                System.out.println("Invalid check number.");
                return;
            }
        }

        System.out.println("Payment confirmed via " + selectedPayment);
        System.out.println("Order total: $" + orderTotal);
    }
}
