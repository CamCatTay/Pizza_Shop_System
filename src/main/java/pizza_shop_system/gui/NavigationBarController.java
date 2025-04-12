package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class NavigationBarController {
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;

    private SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
        initialize();
    }

    @FXML
    private void initialize() {
        buttonHome.setOnAction(e -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonCart.setOnAction(e -> sceneController.switchScene("Cart"));
    }
}
