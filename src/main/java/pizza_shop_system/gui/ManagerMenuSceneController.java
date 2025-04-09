package pizza_shop_system.gui;

import javafx.event.ActionEvent;

import java.io.IOException;

public class ManagerMenuSceneController {
    private final SceneController sceneController = new SceneController();
    public void switchToReportGeneratorScene(ActionEvent actionEvent) throws IOException {
        sceneController.switchToReportGeneratorScene(actionEvent);
    }
}
