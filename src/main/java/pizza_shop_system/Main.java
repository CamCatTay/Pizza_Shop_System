package pizza_shop_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pizza_shop_system.gui.NavigationBarController;
import pizza_shop_system.gui.SceneController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/pizza_shop_system/NavigationBar.fxml"));
        Parent navigationBar = navigationBarLoader.load();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navigationBar); // Keeps navigation bar fixed at the top

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load all scenes upfront to avoid reloading each time (Any scene not explicitly stated here CANNOT be switched to)
        SceneController sceneController = new SceneController(mainLayout);
        sceneController.addScene("Home", "/pizza_shop_system/Home.fxml");
        sceneController.addScene("Menu", "/pizza_shop_system/Menu.fxml");
        sceneController.addScene("Cart", "/pizza_shop_system/Cart.fxml");
        sceneController.addScene("Checkout", "/pizza_shop_system/Checkout.fxml");

        // Set main SceneController
        NavigationBarController navigationBarController = navigationBarLoader.getController();
        navigationBarController.setSceneController(sceneController);

        // Switch to default scene
        sceneController.switchScene("Home"); // Set the initial scene

    }

    public static void main(String[] args) {
        launch(args);
    }

}