package pizza_shop_system;

import javafx.event.ActionEvent;
import java.io.IOException;

public class SignUpSceneController {
    private final SceneController sceneController = new SceneController();

    public void switchToLoginScene(ActionEvent actionEvent) throws IOException {
        sceneController.switchToLoginScene(actionEvent);
    }
}
