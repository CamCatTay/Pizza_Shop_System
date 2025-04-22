package pizza_shop_system.gui;

/*
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import pizza_shop_system.order.Order;
import pizza_shop_system.menu.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomizePizzaController extends BaseController {

    @FXML private ToggleButton personalSizeBtn;
    @FXML private ToggleButton smallSizeBtn;
    @FXML private ToggleButton mediumSizeBtn;
    @FXML private ToggleButton largeSizeBtn;
    @FXML ToggleGroup sizeGroup = new ToggleGroup();

    @FXML private ToggleButton regularCrustBtn;
    @FXML private ToggleButton thinCrustBtn;
    @FXML private ToggleButton stuffedCrustBtn;
    @FXML ToggleGroup crustGroup = new ToggleGroup();

    @FXML private CheckBox pepperoniCheck;
    @FXML private CheckBox chickenCheck;
    @FXML private CheckBox sausageCheck;
    @FXML private CheckBox hamCheck;
    @FXML private CheckBox baconCheck;
    @FXML private CheckBox anchoviesCheck;
    @FXML private CheckBox onionCheck;
    @FXML private CheckBox jalapenoCheck;
    @FXML private CheckBox garlicCheck;
    @FXML private CheckBox bellPepperCheck;
    @FXML private CheckBox tomatoCheck;
    @FXML private CheckBox artichokeCheck;

    @FXML private ChoiceBox<Integer> quantityChoiceBox;
    @FXML private Label priceLabel;

    private final double basePizzaPrice = 10.00;
    private List<CheckBox> toppingsCheck;

    private Order currentOrder;

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    @FXML
    public void initialize() {
        quantityChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        quantityChoiceBox.setValue(1);

        // Default Selections
        mediumSizeBtn.setSelected(true);
        regularCrustBtn.setSelected(true);

        // Toggle Group Assignments
        personalSizeBtn.setToggleGroup(sizeGroup);
        smallSizeBtn.setToggleGroup(sizeGroup);
        mediumSizeBtn.setToggleGroup(sizeGroup);
        largeSizeBtn.setToggleGroup(sizeGroup);

        regularCrustBtn.setToggleGroup(crustGroup);
        thinCrustBtn.setToggleGroup(crustGroup);
        stuffedCrustBtn.setToggleGroup(crustGroup);

        // Toppings List
        toppingsCheck = Arrays.asList(
                pepperoniCheck, chickenCheck, sausageCheck, hamCheck, baconCheck, anchoviesCheck,
                onionCheck, jalapenoCheck, garlicCheck, bellPepperCheck, tomatoCheck, artichokeCheck
        );

        for (CheckBox check : toppingsCheck) {
            check.setOnAction(event -> handleToppingSelection());
        }

        updatePriceLabel(basePizzaPrice * quantityChoiceBox.getValue());

        quantityChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
        crustGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
        sizeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
    }

    @FXML
    private void handleAddToOrder(ActionEvent event) throws IOException {
        if (currentOrder == null) {
            currentOrder = new Order(); // Fallback (should not usually happen)
        }

        MenuItem pizza = createCustomPizzaItem();
        currentOrder.addItem(pizza);
        currentOrder.saveToFile();

        // Optional feedback:

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pizza added to order!");
        alert.showAndWait();

    }




    private void handleToppingSelection() {
        int selectedCount = (int) toppingsCheck.stream().filter(CheckBox::isSelected).count();
        boolean disableExtras = selectedCount >= 4;

        for (CheckBox checkBox : toppingsCheck) {
            if (!checkBox.isSelected()) {
                checkBox.setDisable(disableExtras);
            }
        }
    }

    private void updatePriceLabel(double total) {
        priceLabel.setText("$" + String.format("%.2f", total));
    }

    private void updateTotalPrice() {
        int quantity = quantityChoiceBox.getValue();
        double pricePerPizza = getSizePriceMultiplier();

        ToggleButton selectedCrust = (ToggleButton) crustGroup.getSelectedToggle();
        if (selectedCrust != null && selectedCrust.getText().toLowerCase().contains("stuffed")) {
            pricePerPizza += 2.00;
        }

        double total = pricePerPizza * quantity;
        updatePriceLabel(total);
    }

    private double getSizePriceMultiplier() {
        ToggleButton selected = (ToggleButton) sizeGroup.getSelectedToggle();
        if (selected == null) return basePizzaPrice;

        switch (selected.getText()) {
            case "Personal": return 6.00;
            case "Small": return 8.00;
            case "Medium": return 10.00;
            case "Large": return 12.50;
            default: return basePizzaPrice;
        }
    }

    private MenuItem createCustomPizzaItem() {
        String size = ((ToggleButton) sizeGroup.getSelectedToggle()).getText();
        String crust = ((ToggleButton) crustGroup.getSelectedToggle()).getText();

        String itemName = size + " Pizza with " + crust + " Crust";
        int quantity = quantityChoiceBox.getValue();

        List<String> selectedToppings = toppingsCheck.stream()
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .filter(t -> t != null && !t.trim().isEmpty())
                .toList();

        String description = selectedToppings.isEmpty() ? "No toppings" : String.join(", ", selectedToppings);
        double basePrice = getSizePriceMultiplier();

        if (crust.contains("Stuffed")) basePrice += 2.00;

        String itemID = "custom-" + java.util.UUID.randomUUID();

        return new MenuItem(itemID, "Pizza", basePrice, quantity, itemName, description, new ArrayList<>(selectedToppings));
    }
}

 */
