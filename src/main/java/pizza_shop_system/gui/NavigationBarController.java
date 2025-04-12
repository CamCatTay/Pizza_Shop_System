package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavigationBarController extends BaseController {
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;
    @FXML private Button buttonBack;

    @FXML
    public void initialize() {
        buttonHome.setOnAction(e -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonCart.setOnAction(e -> sceneController.switchScene("Cart"));
        buttonBack.setOnAction(e -> sceneController.switchToPreviousScene());
    }
}
