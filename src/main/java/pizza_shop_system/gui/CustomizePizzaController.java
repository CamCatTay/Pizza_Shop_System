package pizza_shop_system.gui;

import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.List;

public class CustomizePizzaController {

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

    private List<CheckBox> toppingsCheck;

    @FXML
    public void initialize() {
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
