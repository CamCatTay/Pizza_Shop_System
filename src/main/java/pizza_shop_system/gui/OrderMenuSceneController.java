package pizza_shop_system.gui;

import javafx.event.ActionEvent;

import java.io.IOException;

public class OrderMenuSceneController {
    private final SceneController sceneController = new SceneController();
    public void handleBackButtonClick() throws IOException {
        sceneController.switchToPreviousScene();
    }
}
