package pizza_shop_system.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private static String previousScene;

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

    public void switchToPreviousScene() throws IOException {
        if (previousScene != null) {
            SwitchToScene(previousScene);
        }
    }
    public void switchToLoginScene() throws IOException {
        SwitchToScene("/pizza_shop_system/LoginScene.fxml");
    }

    public void switchToSignUpScene() throws IOException {
        SwitchToScene("/pizza_shop_system/SignUpScene.fxml");
    }

    public void switchToCustomerMenuScene() throws IOException {
        SwitchToScene("/pizza_shop_system/CustomerMenuScene.fxml");
    }

    public void switchToManageCustomerAccountScene() throws IOException {
        SwitchToScene("/pizza_shop_system/ManageCustomerAccountScene.fxml");
    }

    public void switchToOrderMenuScene(String previousScene) throws IOException {
        SceneController.previousScene = previousScene;
        SwitchToScene("/pizza_shop_system/OrderMenuScene.fxml");
    }

    public void switchToManagerMenuScene() throws IOException {
        SwitchToScene("/pizza_shop_system/ManagerMenuScene.fxml");
    }

    public void switchToReportGeneratorScene() throws IOException {
        SwitchToScene("/pizza_shop_system/ReportGeneratorScene.fxml");
    }
}
