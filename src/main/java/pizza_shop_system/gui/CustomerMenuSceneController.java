package pizza_shop_system.gui;

import pizza_shop_system.account.ActiveUser;
import java.io.IOException;

public class CustomerMenuSceneController {
    private final SceneController sceneController = new SceneController();
    public void handleLogoutClicked() throws IOException {
        ActiveUser.getInstance().setCurrentUser(null);
        sceneController.switchToLoginScene();
    }

    public void handleManageAccountButtonClick() throws IOException {
        sceneController.switchToManageCustomerAccountScene();
    }

    public void handleOrderMenuButtonClick() throws IOException {
        sceneController.switchToOrderMenuScene("/pizza_shop_system/CustomerMenuScene.fxml");
    }
}
