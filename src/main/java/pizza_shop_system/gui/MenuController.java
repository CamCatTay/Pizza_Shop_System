package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pizza_shop_system.menu.MenuItem;
import pizza_shop_system.menu.MenuLoader;

import java.util.List;

public class MenuController extends BaseController {
    @FXML private GridPane menuContainer;
    @FXML private Button buttonPizza;
    @FXML private Button buttonBeverage;
    @FXML private Button buttonDessert;

    private final MenuLoader menuLoader = new MenuLoader();

    public MenuController() {
        menuLoader.loadMenu("data_files/menu_items.txt");
    }

    private static final int MAX_COLUMNS = 3;
    private static final int MAX_ROWS = 4;
    private static final int CELL_SIZE = 150;
    private static final int CELL_SPACING = 5;

    public void loadCategory(String category) {
        menuContainer.getChildren().clear(); // Clear previous items
        List<MenuItem> items = menuLoader.getItemsByCategory(category);

        menuContainer.setHgap(CELL_SPACING);
        menuContainer.setVgap(CELL_SPACING);

        int totalItems = items.size();
        int columns = Math.min(MAX_COLUMNS, totalItems);

        int row = 0, col = 0;

        for (MenuItem item : items) {
            if (row >= MAX_ROWS) break; // Prevent adding extra rows beyond max limit

            VBox itemBox = new VBox();
            itemBox.setSpacing(5);
            itemBox.setStyle("-fx-padding: 10px; -fx-border-color: gray; -fx-background-color: white;");
            itemBox.setPrefSize(CELL_SIZE, CELL_SIZE);

            Label itemLabel = new Label(item.getName());
            itemLabel.setWrapText(true);
            itemLabel.setMaxWidth(Double.MAX_VALUE);
            itemLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5px; -fx-text-fill: black;");

            Button addToOrderButton = new Button("Add to Order");
            addToOrderButton.getStyleClass().add("button-add-to-order");

            if (category.equals("Pizza")) {
                Button customizeButton = new Button("Customize");
                customizeButton.getStyleClass().add("button-customize");

                itemBox.getChildren().addAll(itemLabel, addToOrderButton, customizeButton);
            } else {
                itemBox.getChildren().addAll(itemLabel, addToOrderButton);
            }

            // Add itemBox to GridPane
            menuContainer.add(itemBox, col, row);

            col++;
            if (col >= columns) { // Move to next row when column limit is reached
                col = 0;
                row++;
            }
        }
    }

    @FXML
    private void initialize() {
        buttonPizza.setOnAction(e -> loadCategory("Pizza"));
        buttonBeverage.setOnAction(e -> loadCategory("Beverage"));
        buttonDessert.setOnAction(e -> loadCategory("Dessert"));
        loadCategory("Pizza"); // Load initial category
    }
}
