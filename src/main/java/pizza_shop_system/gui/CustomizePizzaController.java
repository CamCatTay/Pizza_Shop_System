package pizza_shop_system.gui;

import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.List;

public class CustomizePizzaController {

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

    @FXML
    public void initialize() {

        quantityChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        quantityChoiceBox.setValue(1);

        //Defaults
        mediumSizeBtn.setSelected(true);
        regularCrustBtn.setSelected(true);

        //Size Buttons
        personalSizeBtn.setToggleGroup(sizeGroup);
        smallSizeBtn.setToggleGroup(sizeGroup);
        mediumSizeBtn.setToggleGroup(sizeGroup);
        largeSizeBtn.setToggleGroup(sizeGroup);

        //Crust Buttons
        regularCrustBtn.setToggleGroup(crustGroup);
        thinCrustBtn.setToggleGroup(crustGroup);
        stuffedCrustBtn.setToggleGroup(crustGroup);

        toppingsCheck = Arrays.asList(pepperoniCheck, chickenCheck, sausageCheck, hamCheck, baconCheck, anchoviesCheck,
                onionCheck, jalapenoCheck, garlicCheck, bellPepperCheck, tomatoCheck, artichokeCheck);

        for(CheckBox check : toppingsCheck) {
            check.setOnAction(event -> handleToppingSelection());
        }

        updatePriceLabel(basePizzaPrice * quantityChoiceBox.getValue());

        quantityChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateTotalPrice();
        });

        sizeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            updateTotalPrice();
        });
    }

    private void handleToppingSelection() {
        int selectedCount = (int) toppingsCheck.stream()
                .filter(CheckBox::isSelected)
                .count(); //Normally use long for count but casting to int to maybe save some space, most likely negligible

        boolean disableExtras = selectedCount >= 4;

        for (CheckBox checkBox : toppingsCheck) {
            if (!checkBox.isSelected()) {
                checkBox.setDisable(disableExtras);
            }
        }

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
        if(selected == null) return basePizzaPrice;

        switch(selected.getText()){
            case "Personal": return 6.00;
            case "Small": return 8.00;
            case "Medium": return 10.00;
            case "Large": return 12.50;
            default: return basePizzaPrice;
        }

    }
}
