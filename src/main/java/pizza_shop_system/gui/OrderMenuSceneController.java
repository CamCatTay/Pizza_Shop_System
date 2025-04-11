package pizza_shop_system.gui;

import java.io.IOException;

public class OrderMenuSceneController {
    private final SceneController sceneController = new SceneController();
    public void handleBackButtonClick() throws IOException {
        sceneController.switchToPreviousScene();
    }
}
