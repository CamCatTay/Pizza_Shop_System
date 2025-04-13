package pizza_shop_system.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pizza_shop_system.gui.BaseController;

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

    public void loadCategory(String category) {
        menuContainer.getChildren().clear(); // Clear previous items
        List<MenuItem> items = menuLoader.getItemsByCategory(category);

        int columns = 3; // Number of columns per row
        int row = 0, col = 0;

        for (MenuItem item : items) {
            VBox itemBox = new VBox();
            itemBox.setSpacing(5);
            itemBox.setStyle("-fx-padding: 10px; -fx-border-color: gray; -fx-background-color: white;");

            Label itemLabel = new Label(item.getName());
            itemLabel.setMaxWidth(Double.MAX_VALUE);
            itemLabel.setWrapText(true);
            itemLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5px; -fx-text-fill: black;");

            Button addToOrderButton = new Button("Add to Order");

            if (category.equals("Pizza")) {
                Button customizeButton = new Button("Customize");
                itemBox.getChildren().addAll(itemLabel, addToOrderButton, customizeButton);
            } else {
                itemBox.getChildren().addAll(itemLabel, addToOrderButton);
            }

            // Add itemBox to GridPane
            menuContainer.add(itemBox, col, row);

            col++;
            if (col == columns) {
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
    }
}
