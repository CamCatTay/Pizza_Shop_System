package pizza_shop_system.gui;

import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

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
}
