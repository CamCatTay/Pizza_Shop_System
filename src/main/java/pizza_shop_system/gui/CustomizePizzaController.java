package pizza_shop_system.gui;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.orderSystem.OrderService;

import java.io.IOException;

public class CustomizePizzaController extends BaseController {

    @FXML
    HBox sizeContainer, crustContainer;
    @FXML
    GridPane toppingsContainer;
    @FXML
    ToggleButton personalButton, smallButton, mediumButton, largeButton;
    @FXML
    ToggleButton regularButton, thinButton, stuffedButton;
    @FXML
    ToggleGroup crustToggleGroup = new ToggleGroup();
    @FXML
    ToggleGroup sizeToggleGroup = new ToggleGroup();
    @FXML
    private ChoiceBox<Integer> quantityChoiceBox;

    private final OrderService orderService = new OrderService();
    private final MenuController menuController = new MenuController();
    private JSONObject customizations;

    // Add buttons to their appropriate toggle groups
    private void setupToggleButtons() {
        personalButton.setToggleGroup(sizeToggleGroup);
        smallButton.setToggleGroup(sizeToggleGroup);
        mediumButton.setToggleGroup(sizeToggleGroup);
        largeButton.setToggleGroup(sizeToggleGroup);

        regularButton.setToggleGroup(crustToggleGroup);
        thinButton.setToggleGroup(crustToggleGroup);
        stuffedButton.setToggleGroup(crustToggleGroup);
    }

    // Add all the options to quantity choice box up to MAX_QUANTITY
    private void setupQuantityChoiceBox() {
        // Max quantity of an item that can be added at once
        int MAX_QUANTITY = 10;
        for (int i = 1; i <= MAX_QUANTITY; i++) {
            quantityChoiceBox.getItems().add(i);
        }
        quantityChoiceBox.setValue(1); // Default value
    }

    private void setupCustomizePizzaGUI() throws IOException {
        setupToggleButtons();
        setupQuantityChoiceBox();
    }

    @FXML
    public void initialize() throws IOException {
        menuController.setCustomizePizzaController(this);
        customizations = orderService.loadCustomizations();
        setupCustomizePizzaGUI();
    }

    // Takes in an orderItem/MenuItem to get default customizations and switches to this scene for customization
    public void customizePizza(JSONObject orderItem) throws IOException {
        String pizzaSize = orderItem.getString("pizzaSize");
        String crust = orderItem.getString("crust");
        JSONArray toppings = orderItem.getJSONArray("toppings");
        for (int i = 0; i < toppings.length(); i++) {
            String toppingName = toppings.getString(i);

        }
        sceneController.switchScene("CustomizePizza");
    }


}
