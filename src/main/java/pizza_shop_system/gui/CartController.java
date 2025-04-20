package pizza_shop_system.gui;

import pizza_shop_system.menu.MenuItem;
import pizza_shop_system.order.Order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartController extends BaseController {

    @FXML private Button buttonCheckout;
    @FXML private VBox cartItemsVBox;

    @FXML private Label subTotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

    private Order currentOrder;

    @FXML
    public void initialize() {
        buttonCheckout.setOnAction(e -> sceneController.switchScene("Checkout"));
    }

    // Called from another controller to pass the current order into the cart view
    public void setCurrentOrder(Order order) {
        try {
            List<Order> matchingOrders = Order.fromJsonByOrderID(order.getOrderID());

            if (matchingOrders.isEmpty()) {
                this.currentOrder = order;
            } else {
                Order baseOrder = matchingOrders.get(0);

                this.currentOrder = new Order(
                        baseOrder.getPaymentMethod(),
                        order.getOrderID(),
                        new ArrayList<>(),
                        baseOrder.getOrderType(),
                        baseOrder.getStatus(),
                        baseOrder.getAccount()
                );
                this.currentOrder.setDatePlaced(baseOrder.getDatePlaced());

                for (Order o : matchingOrders) {
                    this.currentOrder.getOrderedItems().addAll(o.getOrderedItems());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            this.currentOrder = order;
        }

        displayCartItems();
    }

    private void displayCartItems() {
        cartItemsVBox.getChildren().clear();
        double subtotal = 0.0;

        for (MenuItem item : currentOrder.getOrderedItems()) {
            double itemTotal = item.getPrice() * item.getQuantity();
            subtotal += itemTotal;

            HBox row = createItemRow(item, itemTotal);
            cartItemsVBox.getChildren().add(row);
        }

        double tax = subtotal * 0.10;
        double total = subtotal + tax;

        subTotalLabel.setText(String.format("$%.2f", subtotal));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));

        buttonCheckout.setDisable(currentOrder.getOrderedItems().isEmpty());
    }

    private HBox createItemRow(MenuItem item, double itemTotal) {
        Label nameLabel = new Label(item.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setPrefWidth(250);

        Label quantityLabel = new Label("x" + item.getQuantity());
        quantityLabel.setPrefWidth(40);

        Label priceLabel = new Label(String.format("$%.2f", itemTotal));
        priceLabel.setPrefWidth(80);
        priceLabel.setStyle("-fx-font-weight: bold;");

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeItemFromCart(item));

        VBox descriptionBox = new VBox();
        if (item.getToppings() != null && !item.getToppings().isEmpty()) {
            Label toppingsLabel = new Label("Toppings: " + String.join(", ", item.getToppings()));
            toppingsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");
            descriptionBox.getChildren().add(toppingsLabel);
        }

        VBox itemInfoBox = new VBox(nameLabel, descriptionBox);
        itemInfoBox.setSpacing(2);
        itemInfoBox.setPrefWidth(300);

        HBox row = new HBox(10, itemInfoBox, quantityLabel, priceLabel, removeButton);
        row.setStyle("-fx-padding: 10; -fx-background-color: #f2f2f2; -fx-border-color: #ccc; -fx-border-width: 0 0 1px 0;");
        return row;
    }

    private void removeItemFromCart(MenuItem itemToRemove) {
        if (currentOrder != null) {
            currentOrder.getOrderedItems().remove(itemToRemove);

            try {
                if (currentOrder.getOrderedItems().isEmpty()) {
                    // Remove the order file if it's empty
                    Order.removeOrderById(currentOrder.getOrderID());
                    currentOrder = new Order(); // Start fresh
                } else {
                    // Save updated order
                    Order.updateOrder(currentOrder);
                }

                displayCartItems();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }
}
