package pizza_shop_system.gui;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pizza_shop_system.orderSystem.OrderService;

import java.io.IOException;

public class CartController extends BaseController {
    private final OrderService orderService = new OrderService();
    private static CustomizePizzaController customizePizzaController;

    @FXML private Button buttonCheckout;
    @FXML private VBox cartItemsVBox;
    @FXML private Label subTotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

    @FXML
    public void initialize() throws IOException {
        orderService.setCartController(this); // Set the cart controller in OrderService
        displayCurrentOrder(orderService.loadOrders()); // Load the current order

        buttonCheckout.setOnAction(e -> {
            sceneController.switchScene("Checkout");
        });
    }

    public void displayCurrentOrder(JSONObject orderData) throws IOException {
        cartItemsVBox.getChildren().clear(); // Clear cart items

        JSONObject currentOrder = orderService.getCurrentOrder(orderData);
        JSONArray orderItems = currentOrder.getJSONArray("orderItems");

        for (int i = 0; i < orderItems.length(); i++) {
            JSONObject orderItem = orderItems.getJSONObject(i);
            HBox row = createItemRow(orderItem);
            cartItemsVBox.getChildren().add(row);
        }

        double tax = currentOrder.optDouble("tax");
        double total = currentOrder.optDouble("total");
        double subtotal = currentOrder.optDouble("subtotal");

        subTotalLabel.setText(String.format("$%.2f", subtotal));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));

        buttonCheckout.setDisable(orderItems.isEmpty()); // Disable the checkout button if there are no order items
    }

    private HBox createItemRow(JSONObject orderItem) {
        try {
            Label nameLabel = new Label(orderItem.getString("name"));
            nameLabel.setStyle("-fx-font-weight: bold;");
            nameLabel.setPrefWidth(250);

            Label priceLabel = new Label(String.format("$%.2f", orderItem.getDouble("price")));
            priceLabel.setPrefWidth(80);
            priceLabel.setStyle("-fx-font-weight: bold;");

            // remove button
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e -> {
                try {
                    removeItemFromCart(orderItem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // edit button
            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> {
                try {
                    editItemInCart(orderItem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            // For displaying the customizations of an item. Implement after customizations are complete.
            /*
            VBox descriptionBox = new VBox();
            if (item.getToppings() != null && !item.getToppings().isEmpty()) {
                Label toppingsLabel = new Label("Toppings: " + String.join(", ", item.getToppings()));
                toppingsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");
                descriptionBox.getChildren().add(toppingsLabel);
            }
             */

            VBox itemInfoBox = new VBox(nameLabel);
            itemInfoBox.setSpacing(2);
            itemInfoBox.setPrefWidth(300);

            HBox row = new HBox(10, itemInfoBox, priceLabel, removeButton, editButton);
            row.setStyle("-fx-padding: 10; -fx-background-color: #f2f2f2; -fx-border-color: #ccc; -fx-border-width: 0 0 1px 0;");
            return row;
        } catch (Exception e) {
            System.out.println("Error creating row for item: " + orderItem.toString());
            e.printStackTrace();
        }
        return null;
    }

    private void removeItemFromCart(JSONObject orderItem) throws IOException {
        orderService.removeOrderItem(orderItem.getInt("orderItemId"));
    }

    private void editItemInCart(JSONObject orderItem) throws IOException {
        customizePizzaController.customizePizza(orderItem);
    }

    public void setCustomizePizzaController(CustomizePizzaController customizePizzaController) {
        CartController.customizePizzaController = customizePizzaController;
    }

    // for testing
    public static void main(String[] args){

    }
}

