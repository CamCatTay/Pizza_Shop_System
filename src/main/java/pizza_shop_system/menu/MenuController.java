package pizza_shop_system.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pizza_shop_system.gui.BaseController;

import java.util.List;

public class MenuController extends BaseController {
    @FXML private VBox menuContainer;
    @FXML private Button buttonPizza;
    @FXML private Button buttonBeverages;
    @FXML private Button buttonDesserts;

    private final MenuLoader menuLoader = new MenuLoader();

    public MenuController() {
        menuLoader.loadMenu("data_files/menu_items.txt");
    }

    public void loadCategory(String category) {
        menuContainer.getChildren().clear(); // Clear previous items
        List<MenuItem> items = menuLoader.getItemsByCategory(category);

        for (MenuItem item : items) {
            VBox itemBox = new VBox();
            itemBox.setSpacing(5);

            Label itemLabel = new Label(item.getName() + " - $" + item.getPrice());
            itemLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5px;");

            if (category.equals("Pizza")) {
                Button addToOrderButton = new Button("Add to Order");
                Button customizeButton = new Button("Customize");

                addToOrderButton.setOnAction(e -> System.out.println("Added to order: " + item.getName()));
                customizeButton.setOnAction(e -> System.out.println("Customize item: " + item.getName()));

                itemBox.getChildren().addAll(itemLabel, addToOrderButton, customizeButton);

            } else if (category.equals("Beverage")) {
                Button addToOrderButton = new Button("Add to Order");

                itemBox.getChildren().addAll(itemLabel, addToOrderButton);

            } else if (category.equals("Dessert")) {
                Button addToOrderButton = new Button("Add to Order");

                itemBox.getChildren().addAll(itemLabel, addToOrderButton);
            }

            menuContainer.getChildren().add(itemBox);
        }
    }

    @FXML
    private void initialize() {
        buttonPizza.setOnAction(e -> loadCategory("Pizza"));
        buttonBeverages.setOnAction(e -> loadCategory("Beverage"));
        buttonDesserts.setOnAction(e -> loadCategory("Dessert"));
    }
}
