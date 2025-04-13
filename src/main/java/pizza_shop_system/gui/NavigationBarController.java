package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavigationBarController extends BaseController {
    public HBox navigationBar;
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;
    @FXML private Button buttonBack;
    @FXML private Button buttonLogin;

    @FXML
    public void initialize() {
        buttonHome.setOnAction(e -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonCart.setOnAction(e -> sceneController.switchScene("Cart"));
        buttonLogin.setOnAction(e -> sceneController.switchScene("Login"));
        buttonBack.setOnAction(e -> sceneController.switchToPreviousScene());
    }
}
