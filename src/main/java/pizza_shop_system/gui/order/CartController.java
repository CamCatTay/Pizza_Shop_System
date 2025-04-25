package pizza_shop_system.gui.order;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pizza_shop_system.gui.base.BaseController;
import pizza_shop_system.order.services.OrderService;
import pizza_shop_system.utils.StringUtil;

import java.io.IOException;

public class CartController extends BaseController {
    private final OrderService orderService = new OrderService();
    private static CustomizePizzaController customizePizzaController;
    private static CustomizeBeverageController customizeBeverageController;

    @FXML private ScrollPane cartItemsScrollPane;
    @FXML private Button buttonCheckout;
    @FXML private VBox cartItemsVBox;
    @FXML private Label subTotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

    @FXML
    public void initialize() throws IOException {
        orderService.setCartController(this); // Set the cart controller in OrderService
        displayCurrentOrder(orderService.loadOrders()); // Load the current order

        buttonCheckout.setOnAction(_ -> sceneController.switchScene("Checkout"));
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

    private StringUtil stringUtil = new StringUtil();
    private String createPizzaName(JSONObject orderItem) {
        StringBuilder name = new StringBuilder();
        String size = stringUtil.captilizeWord(orderItem.optString("pizzaSize"));
        String crust = stringUtil.captilizeWord(orderItem.optString("crust"));
        JSONArray toppings = orderItem.getJSONArray("toppings");

        name.append(size).append(" ").append(crust).append(" Crust");

        for (int i = 0; i < toppings.length(); i++) {
            String topping = toppings.getString(i);
            name.append(" ").append(stringUtil.captilizeWord(topping));
        }

        name.append(" Pizza");
        return name.toString();
    }

    private HBox createItemRow(JSONObject orderItem) {
        try {

            // Try to get a name first because beverages use name element to determine price
            String name = orderItem.optString("name");

            // Pizza needs to have its name dynamically created
            if (orderItem.has("pizzaSize")) {
                name = createPizzaName(orderItem);
            }

            orderItem.put("name", name);

            // remove button
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(_ -> {
                try {
                    removeItemFromCart(orderItem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // edit button
            Button editButton = new Button("Edit");
            editButton.setOnAction(_ -> {
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

            Label nameLabel = new Label(name);
            nameLabel.setStyle("-fx-font-weight: bold;");
            nameLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);

            Label priceLabel = new Label(String.format("$%.2f", orderItem.getDouble("price")));
            priceLabel.setPrefWidth(80);
            priceLabel.setStyle("-fx-font-weight: bold;");

            System.out.println(priceLabel.getWidth());

            // Ensure it fills width of application screen
            cartItemsScrollPane.setFitToWidth(true);

            // Ensure it fills the width dynamically
            VBox itemInfoBox = new VBox(nameLabel);
            itemInfoBox.prefWidthProperty().bind(cartItemsScrollPane.widthProperty());

            // Ensure it expands as needed
            nameLabel.setMaxWidth(Region.USE_COMPUTED_SIZE);
            nameLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #FFD700;");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);

            // Push other elements (priceLabel, buttons) to the right
            Region region = new Region();
            region.setStyle("-fx-background-color: #FFC0CB; -fx-border-color: #000; -fx-border-width: 1px;");
            region.setPrefWidth(Region.USE_COMPUTED_SIZE);
            HBox.setHgrow(region, Priority.ALWAYS);

            priceLabel.setStyle(" -fx-background-color: green;");

            // Ensure the row fills the ScrollPane width
            HBox row = new HBox(10, itemInfoBox, region, priceLabel, removeButton, editButton);
            row.prefWidthProperty().bind(cartItemsScrollPane.widthProperty());
            row.setStyle("-fx-padding: 10; -fx-background-color: #f2f2f2; -fx-border-color: #ccc; -fx-border-width: 0 0 1px 0;");
            HBox.setHgrow(row, Priority.ALWAYS);

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
        // Just use customizations that are unique to menu item to determine what customization screen is needed
        if (orderItem.has("pizzaSize")) {
            try {
                customizePizzaController.customizePizza(orderItem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (orderItem.has("beverageSize")) {
            customizeBeverageController.customizeBeverage(orderItem);
        }
    }

    // Set customize pizza controller for customization
    public void setCustomizePizzaController(CustomizePizzaController customizePizzaController) {
        CartController.customizePizzaController = customizePizzaController;
    }

    // Set customize beverage controller for customization
    public void setCustomizeBeverageController(CustomizeBeverageController customizeBeverageController) {
        CartController.customizeBeverageController = customizeBeverageController;
    }

    // for testing
    public static void main(String[] args){

    }
}

