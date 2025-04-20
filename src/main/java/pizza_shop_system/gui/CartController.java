package pizza_shop_system.gui;

import pizza_shop_system.menu.MenuItem;
import pizza_shop_system.order.CurrentOrder;
import pizza_shop_system.order.Order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CartController extends BaseController {

    @FXML private Button buttonCheckout;
    @FXML private VBox cartItemsVBox;

    @FXML private Label subTotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

    private CurrentOrder currentOrder;

    @FXML
    public void initialize() {
        currentOrder = CurrentOrder.getInstance();

        buttonCheckout.setOnAction(e -> {
            double subtotal = currentOrder.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            double tax = subtotal * 0.10;
            double total = subtotal + tax;

            sceneController.switchSceneWithData("Checkout", controller -> {
                if (controller instanceof CheckoutController checkoutController) {
                    checkoutController.setTotal(total);
                }
            });
        });
    }

    public void loadCurrentOrder() {
        displayCartItems();
    }

    public void clearCart() {
        currentOrder.reset();
        displayCartItems();
    }

    private void displayCartItems() {
        cartItemsVBox.getChildren().clear();
        double subtotal = 0.0;

        List<MenuItem> items = currentOrder.getItems();
        System.out.println("Items to display in cart: " + items.size());


        for (MenuItem item : items) {
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

        buttonCheckout.setDisable(items.isEmpty());
    }

    private HBox createItemRow(MenuItem item, double itemTotal) {
        try {
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
        } catch (Exception e) {
            System.out.println("Error creating row for item: " + item.getName());
            e.printStackTrace();
        }
        return null;
    }

    private void removeItemFromCart(MenuItem itemToRemove) {
        currentOrder.removeItem(itemToRemove);

        try {
            if (currentOrder.getItems().isEmpty()) {
                Order.removeOrderById(currentOrder.getOrderID());
                currentOrder.reset(); // Start fresh
            } else {
                Order.updateOrder(currentOrder.toOrder());
            }

            displayCartItems();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
