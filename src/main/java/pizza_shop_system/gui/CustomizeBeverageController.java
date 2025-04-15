package pizza_shop_system.gui;


import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomizeBeverageController {

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
    }

}

