package pizza_shop_system.gui;

import pizza_shop_system.orderSystem.OrderService;
import pizza_shop_system.payment.Payment;
import pizza_shop_system.account.AccountService;
import pizza_shop_system.account.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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

    @FXML private RadioButton deliveryRadioButton;
    @FXML private RadioButton pickupRadioButton;

    @FXML private Label addressLabel;
    @FXML private TextField addressField;

    @FXML private Button confirmButton;

    private double orderTotal;

    private final OrderService orderService = new OrderService();

    @FXML
    private void initialize() {
        ToggleGroup orderTypeGroup = new ToggleGroup();
        deliveryRadioButton.setToggleGroup(orderTypeGroup);
        pickupRadioButton.setToggleGroup(orderTypeGroup);
        pickupRadioButton.setSelected(true);

        paymentMethodComboBox.getItems().addAll("Credit Card", "Debit Card", "Check", "Cash");

        hideAllPaymentFields();
        addressLabel.setVisible(false);
        addressField.setVisible(false);

        deliveryRadioButton.setOnAction(e -> showDeliveryFields());
        pickupRadioButton.setOnAction(e -> hideDeliveryFields());
        paymentMethodComboBox.setOnAction(e -> handlePaymentMethodSelection());

        confirmButton.setOnAction(e -> {
            try {
                processOrder();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setTotal(double total) {
        this.orderTotal = total;
        totalLabel.setText("Total: $" + String.format("%.2f", total));
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

    private void handlePaymentMethodSelection() {
        String paymentMethod = paymentMethodComboBox.getValue();
        hideAllPaymentFields();

        if ("Credit Card".equals(paymentMethod) || "Debit Card".equals(paymentMethod)) {
            cardNumberLabel.setVisible(true);
            cardNumberField.setVisible(true);
            cvvLabel.setVisible(true);
            cvvField.setVisible(true);
            expirationLabel.setVisible(true);
            expirationDateField.setVisible(true);
        } else if ("Check".equals(paymentMethod)) {
            checkNumberLabel.setVisible(true);
            checkNumberField.setVisible(true);
        }
    }

    private void showDeliveryFields() {
        addressLabel.setVisible(true);
        addressField.setVisible(true);
    }

    private void hideDeliveryFields() {
        addressLabel.setVisible(false);
        addressField.setVisible(false);
    }

    private void processOrder() throws IOException {
        String orderType = deliveryRadioButton.isSelected() ? "Delivery" : "Pickup";
        String paymentMethod = paymentMethodComboBox.getValue();

        if (paymentMethod == null || paymentMethod.isEmpty()) {
            System.out.println("Please select a payment method.");
            return;
        }

        if ("Delivery".equals(orderType) &&
                (addressField.getText().isEmpty() || addressField.getText().isBlank())) {
            System.out.println("Please enter a valid delivery address.");
            return;
        }

        Payment payment = new Payment(orderService);
        boolean success = false;

        switch (paymentMethod) {
            case "Credit Card":
            case "Debit Card": //Added debit card here just in case
                if (cardNumberField.getText().isEmpty() ||
                        cvvField.getText().isEmpty() ||
                        expirationDateField.getText().isEmpty()) {
                    System.out.println("Please fill in all credit card details.");
                    return;
                }

                AccountService accountService = new AccountService();
                User currentUser = accountService.getActiveUser();
                if (currentUser == null) {
                    System.out.println("No user logged in.");
                    return;
                }

                payment.processCard(currentUser);
                success = payment.getIsProcessed();
                break;

            case "Check": //These assume exact amount is paid
                if (checkNumberField.getText().isEmpty()) {
                    System.out.println("Please enter a valid check number.");
                    return;
                }

                payment.processCheck(orderTotal);
                success = payment.getIsProcessed();
                break;

            case "Cash": //Assumes exact amount will prob need to update with userInput to calc change
                payment.processCash(orderTotal);
                success = payment.getIsProcessed();
                break;

            default:
                System.out.println("Unsupported payment type.");
                return;
        }

        if (success) {
            sceneController.switchScene("Home");
            System.out.println("Order confirmed! Total: $" + orderTotal);
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    }

}
