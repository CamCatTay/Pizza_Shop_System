package pizza_shop_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pizza_shop_system.gui.navigation.NavigationBarController;
import pizza_shop_system.gui.base.SceneController;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    // Adds each scene in the scenes folder to the scene controller so they can be loaded and switched to
    private void addScenes(SceneController sceneController) {
        File scenesFolder = new File("src/main/resources/pizza_shop_system/scenes");
        File[] listOfScenes = scenesFolder.listFiles();

        if (listOfScenes != null) {
            for (File sceneFile : listOfScenes) {
                if (sceneFile.isFile() && sceneFile.getName().endsWith(".fxml")) {
                    String sceneFileName = sceneFile.getName();
                    String sceneName = sceneFileName.substring(0, sceneFileName.lastIndexOf(".")); // Get the name of the scene before .fxml
                    String scenePath = "/pizza_shop_system/scenes/" + sceneFileName;
                    sceneController.addScene(sceneName, scenePath);
                }
            }
        } else {
            System.out.println("scenes folder is empty or does not exist.");
        }
    }

    // Attaches all stylesheets to the main scene
    private void attachStylesheets(Scene scene) {
        File stylesheetsFolder = new File("src/main/resources/pizza_shop_system/stylesheets");
        File[] listOfStylesheets = stylesheetsFolder.listFiles();

        if (listOfStylesheets != null) {
            for (File stylesheetFile : listOfStylesheets) {
                if (stylesheetFile.isFile() && stylesheetFile.getName().endsWith(".css")) {
                    String stylesheetPath = "/pizza_shop_system/stylesheets/" + stylesheetFile.getName();
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylesheetPath)).toExternalForm());
                }
            }
        } else {
            System.out.println("stylesheets folder is empty or does not exist.");
        }
    }

    private SceneController loadScenes(BorderPane mainLayout, FXMLLoader navigationBarLoader) {
        SceneController sceneController = new SceneController(mainLayout);
        addScenes(sceneController);

        // Set main SceneController inside the navigation bar
        NavigationBarController navigationBarController = navigationBarLoader.getController();
        navigationBarController.setSceneController(sceneController);

        return sceneController;
    }

    // Start the GUI
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Constants
        String DEFAULT_SCENE = "Home";
        int DEFAULT_WIDTH = 1000;
        int DEFAULT_HEIGHT = 800;

        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/pizza_shop_system/scenes/NavigationBar.fxml"));
        Parent navigationBar = navigationBarLoader.load();

        // Create the border pane that holds the nav bar at the top and other scenes in the center
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navigationBar); // Keeps navigation bar fixed at the top of all scenes

        Scene scene = new Scene(mainLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load all scenes upfront so we don't have to reload each time (Return scene controller so we can switch to default scene)
        SceneController sceneController = loadScenes(mainLayout, navigationBarLoader);

        // Attach ALL CSS stylesheets
        attachStylesheets(scene);

        // Switch to default scene
        sceneController.switchScene(DEFAULT_SCENE); // Set the default scene
    }

    public static void main(String[] args) {
        launch(args);
    }

}