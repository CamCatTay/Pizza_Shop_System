package pizza_shop_system.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;

    // This method directly references the scene instead of getting it from ActionEvent (Useful if we aren't switching scenes using a GUI event).
    private void SwitchToScene(String sceneReference) throws IOException {
        Stage stage = (Stage) Stage.getWindows().stream().filter(window -> window instanceof Stage).findFirst().orElse(null);

        if (stage != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(sceneReference)));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Error: No active stage found.");
        }
    }
    public void switchToLoginScene() throws IOException {
        SwitchToScene("/pizza_shop_system/LoginScene.fxml");
    }

    public void switchToSignUpScene() throws IOException {
        SwitchToScene("/pizza_shop_system/SignUpScene.fxml");
    }

    public void switchToAccountScene(ActionEvent event) throws IOException{
        SwitchToScene("/pizza_shop_system/AccountScene.fxml");
    }

    public void switchToReportGeneratorScene(ActionEvent event) throws IOException {
        SwitchToScene("/pizza_shop_system/ReportGeneratorScene.fxml");
    }

    public void switchToCustomerMenuScene() throws IOException {
        SwitchToScene("/pizza_shop_system/CustomerMenuScene.fxml");
    }
}
