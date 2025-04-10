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

    public void switchToLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pizza_shop_system/LoginScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUpScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pizza_shop_system/SignUpScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAccountScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pizza_shop_system/AccountScene.fxml"))); //Change path to whatever is needed for Account
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToReportGeneratorScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pizza_shop_system/ReportGeneratorScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // This method directly references the scene instead of getting it from ActionEvent (Useful if we aren't switching scenes using a GUI event).
    public void switchToCustomerMenuScene() throws IOException {
        Stage stage = (Stage) Stage.getWindows().stream().filter(window -> window instanceof Stage).findFirst().orElse(null);

        if (stage != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource("/pizza_shop_system/CustomerMenuScene.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Error: No active stage found.");
        }
    }
}
