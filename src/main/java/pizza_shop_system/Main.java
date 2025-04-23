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
        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/pizza_shop_system/scenes/NavigationBar.fxml"));
        Parent navigationBar = navigationBarLoader.load();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navigationBar); // Keeps navigation bar fixed at the top

        Scene scene = new Scene(mainLayout, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load all scenes upfront to avoid reloading each time (Any scene not explicitly stated here CANNOT be switched to)
        SceneController sceneController = new SceneController(mainLayout);
        sceneController.addScene("Home", "/pizza_shop_system/scenes/Home.fxml");
        sceneController.addScene("Menu", "/pizza_shop_system/scenes/Menu.fxml");
        sceneController.addScene("Cart", "/pizza_shop_system/scenes/Cart.fxml");
        sceneController.addScene("Checkout", "/pizza_shop_system/scenes/Checkout.fxml");
        sceneController.addScene("Login", "/pizza_shop_system/scenes/Login.fxml");
        sceneController.addScene("SignUp", "/pizza_shop_system/scenes/SignUp.fxml");
        sceneController.addScene("CustomizePizza", "/pizza_shop_system/scenes/CustomizePizza.fxml");
        sceneController.addScene("CustomizeBeverage", "/pizza_shop_system/scenes/CustomizeBeverage.fxml");
        sceneController.addScene("ManagerHome", "/pizza_shop_system/scenes/ManagerHome.fxml");
        sceneController.addScene("Reports", "/pizza_shop_system/scenes/Reports.fxml");
        sceneController.addScene("ManageAccounts", "/pizza_shop_system/scenes/ManageAccounts.fxml");
        sceneController.addScene("OrderCompletion", "/pizza_shop_system/scenes/OrderCompletion.fxml");

        // Set main SceneController
        NavigationBarController navigationBarController = navigationBarLoader.getController();
        navigationBarController.setSceneController(sceneController);

        // Switch to default scene
        sceneController.switchScene("Home"); // Set the initial scene

        // Attach ALL CSS stylesheets
        scene.getStylesheets().add(getClass().getResource("/pizza_shop_system/stylesheets/menu.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/pizza_shop_system/stylesheets/cart.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/pizza_shop_system/stylesheets/navigationbar.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/pizza_shop_system/stylesheets/customize.css").toExternalForm());

    }

    public static void main(String[] args) {
        launch(args);
    }

}