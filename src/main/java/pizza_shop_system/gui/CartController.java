package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CartController extends BaseController {
    @FXML Button buttonCheckout;

    public void initialize() {
        buttonCheckout.setOnAction(e -> sceneController.switchScene("Checkout"));
    }

}
