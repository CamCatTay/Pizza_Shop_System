package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.orderSystem.JSONUtil;
import pizza_shop_system.orderSystem.OrderService;

import java.io.IOException;

public class MenuController extends BaseController {
    @FXML private GridPane menuContainer;

    // Customize how menu items are displayed
    private static final int MAX_COLUMNS = 3;
    private static final int MAX_ROWS = 4;
    private static final int CELL_SIZE = 150;
    private static final int CELL_SPACING = 5;

    @FXML private Button buttonPizza;
    @FXML private Button buttonBeverage;

    private final JSONUtil jsonUtil = new JSONUtil();
    private final OrderService orderService = new OrderService();

    private JSONObject loadMenuItems() throws IOException {
        String MENU_ITEMS_FILE_PATH = "data_files/MenuItems.json";
        return jsonUtil.loadJSONObject(MENU_ITEMS_FILE_PATH);
    }

    // After displayMenuItemsByCategory creates a JSONArray for categoryItems this method will load them into the menu scene
    private void loadCategoryItemsIntoMenu(JSONArray categoryItems) {

        int totalItems = categoryItems.length();
        int columns = Math.min(MAX_COLUMNS, totalItems);
        int row = 0, col = 0;

        for (int i = 0; i < categoryItems.length(); i++) {
            if (row >= MAX_ROWS) break; // Prevent adding extra rows beyond max limit
            JSONObject menuItem = categoryItems.getJSONObject(i);

            VBox itemBox = new VBox();
            itemBox.setSpacing(5);
            itemBox.setStyle("-fx-padding: 10px; -fx-border-color: gray; -fx-background-color: white;");
            itemBox.setPrefSize(CELL_SIZE, CELL_SIZE);

            Label itemLabel = new Label(menuItem.getString("name"));
            itemLabel.setWrapText(true);
            itemLabel.setMaxWidth(Double.MAX_VALUE);
            itemLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5px; -fx-text-fill: black;");

            Button addToOrderButton = new Button("Add to Order");
            addToOrderButton.getStyleClass().add("button-add-to-order");

            // add to order action
            addToOrderButton.setOnAction(event -> {
                try {
                    // Clone default properties of menu item into a new order item
                    JSONObject orderItem = new JSONObject(menuItem.toString());
                    orderService.addOrderItem(orderItem); // Add order item to the current order
                } catch (IOException e) {
                    System.out.print("Error adding order item");
                    throw new RuntimeException(e);
                }
            });

            Button customizeButton = new Button("Customize");
            customizeButton.getStyleClass().add("button-customize");

            // customize action
            customizeButton.setOnAction(event -> {
                // Implement switch to customize screen. Check a variable in item to determine what item type it is (to know which scene to switch to)
                // or loop through each category until its found
            });

            // Put all menu item elements into VBox
            itemBox.getChildren().addAll(itemLabel, addToOrderButton, customizeButton);

            // Put VBox in GridPane
            menuContainer.add(itemBox, col, row);

            col++;
            if (col >= columns) { // Move to next row when column limit is reached
                col = 0;
                row++;
            }
        }
    }

    // displays menu items along with add to order and customize button. category chooses what items to display
    // category name should match MenuItems.json category name
    private void displayMenuItemsByCategory(String category) {
            menuContainer.getChildren().clear(); // Clear previously displayed menu items
            menuContainer.setHgap(CELL_SPACING);
            menuContainer.setVgap(CELL_SPACING);

            try {
                JSONObject data = loadMenuItems(); // contains items and nextId
                JSONObject items = data.getJSONObject("items");

                if (items.has(category)) {
                    JSONArray categoryItems = items.getJSONArray(category);
                    loadCategoryItemsIntoMenu(categoryItems);

                } else { // If category does not exist then default to display all menu items
                    JSONArray allCategoryItems = new JSONArray(); // Add all items into an array
                    for (String categoryName : items.keySet()) {
                        JSONArray categoryItems = items.getJSONArray(categoryName);
                        for (int i = 0; i < categoryItems.length(); i++) {
                            allCategoryItems.put(categoryItems.getJSONObject(i));
                        }
                    }
                    loadCategoryItemsIntoMenu(allCategoryItems);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void initialize() {
        buttonPizza.setOnAction(e -> displayMenuItemsByCategory("pizza"));
        buttonBeverage.setOnAction(e -> displayMenuItemsByCategory("beverage"));
        displayMenuItemsByCategory("all");
    }
}
