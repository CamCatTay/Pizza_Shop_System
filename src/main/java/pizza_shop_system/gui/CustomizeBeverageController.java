package pizza_shop_system.gui;
/*
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.awt.event.ActionEvent;

import pizza_shop_system.menu.MenuItem;

import java.io.IOException;


public class CustomizeBeverageController extends BaseController{

    @FXML private ToggleButton smallSizeBtn;
    @FXML private ToggleButton mediumSizeBtn;
    @FXML private ToggleButton largeSizeBtn;
    @FXML private ToggleGroup sizeGroup = new ToggleGroup();

    @FXML private ToggleButton iceNoneBtn;
    @FXML private ToggleButton iceLightBtn;
    @FXML private ToggleButton iceRegularBtn;
    @FXML private ToggleButton iceExtraBtn;
    @FXML private ToggleGroup iceGroup = new ToggleGroup();

    // Quantity and order
    @FXML private ChoiceBox<Integer> quantityChoiceBox;

    @FXML private Button addToOrderButton;
    @FXML private Label priceLabel;
    private final double baseDrinkPrice = 1.5;

    private Order currentOrder = new Order();

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    @FXML
    public void initialize() {

        //Quantity choices
        quantityChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        quantityChoiceBox.setValue(1);

        //Default selections
        smallSizeBtn.setSelected(true);
        iceRegularBtn.setSelected(true);

        smallSizeBtn.setToggleGroup(sizeGroup);
        mediumSizeBtn.setToggleGroup(sizeGroup);
        largeSizeBtn.setToggleGroup(sizeGroup);

        iceNoneBtn.setToggleGroup(iceGroup);
        iceLightBtn.setToggleGroup(iceGroup);
        iceRegularBtn.setToggleGroup(iceGroup);
        iceExtraBtn.setToggleGroup(iceGroup);
        iceRegularBtn.setSelected(true);

        //Price multiplier initial
        updatePriceLabel(baseDrinkPrice * quantityChoiceBox.getValue());

        quantityChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateTotalPrice();
        });

        sizeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            updateTotalPrice();
        });

    }

    private void updatePriceLabel(double total){
        priceLabel.setText("$" + String.format("%.2f", total));
    }

    private void updateTotalPrice() {
        int quantity = quantityChoiceBox.getValue();
        double pricePerDrink = getSizePriceMultiplier();
        double total = pricePerDrink * quantity;
        updatePriceLabel(total);
    }

    private double getSizePriceMultiplier(){
        ToggleButton selected = (ToggleButton) sizeGroup.getSelectedToggle();
        if(selected == null) return baseDrinkPrice;

        switch(selected.getText()){
            case "Medium": return 2.00;
            case "Large": return 2.50;
            default: return baseDrinkPrice;
        }

    }

    @FXML
    private void handleAddToOrder() throws IOException {
        String size = ((ToggleButton) sizeGroup.getSelectedToggle()).getText();
        String ice = ((ToggleButton) iceGroup.getSelectedToggle()).getText();
        int quantity = quantityChoiceBox.getValue();
        double price = getSizePriceMultiplier();

        String name = size + " Beverage";
        String description = "Ice: " + ice;
        String itemID = "1";
        String category = "Beverage";

        MenuItem drink = new MenuItem(itemID, category, price, quantity, name, description, null);

        System.out.println("Beverage added: " + name + ", Qty: " + quantity + ", Ice: " + ice);

        currentOrder.addItem(drink);
        currentOrder.saveToFile();
    }
}
 */
