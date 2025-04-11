package pizza_shop_system.gui;

import javafx.event.ActionEvent;
import pizza_shop_system.account.ActiveUser;

import java.io.IOException;

public class ManagerMenuSceneController {
    private final SceneController sceneController = new SceneController();
    public void switchToReportGeneratorScene() throws IOException {
        sceneController.switchToReportGeneratorScene();
    }

    public void handleTakeOrdersButtonClick() throws IOException {
        sceneController.switchToOrderMenuScene("/pizza_shop_system/ManagerMenuScene.fxml");
    }

    public void handleLogoutButtonClick() throws IOException {
        ActiveUser.getInstance().setCurrentUser(null);
        sceneController.switchToLoginScene();
    }
}
