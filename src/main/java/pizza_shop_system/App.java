package pizza_shop_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pizza_shop_system.gui.navigation.NavigationBarController;
import pizza_shop_system.gui.base.SceneController;
import pizza_shop_system.utils.DataFileManager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class App extends Application {

    // Adds all scenes in scenes folder recursively
    private void addScenes(SceneController sceneController, String baseScenesPath) {
        try {
            URL url = getClass().getResource(baseScenesPath);
            if (url == null) {
                System.out.println("Scenes path not found: " + baseScenesPath);
                return;
            }

            if (url.getProtocol().equals("file")) {
                // Development mode (running from IntelliJ)
                File folder = new File(url.toURI());
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".fxml")) {
                            String sceneName = file.getName().replace(".fxml", "");
                            String scenePath = baseScenesPath + "/" + file.getName();
                            sceneController.addScene(sceneName, scenePath);
                        } else if (file.isDirectory()) {
                            // RECURSION to go into subfolders
                            addScenes(sceneController, baseScenesPath + "/" + file.getName());
                        }
                    }
                }
            } else if (url.getProtocol().equals("jar")) {
                // Packaged mode (running from .jar)
                String pathInsideJar = baseScenesPath.substring(1); // remove leading '/'
                String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(pathInsideJar) && name.endsWith(".fxml")) {
                            // Scene Name: take only the file name, without folders
                            String sceneFileName = name.substring(name.lastIndexOf("/") + 1);
                            String sceneName = sceneFileName.replace(".fxml", "");
                            String scenePath = "/" + name; // Always use absolute path when loading from resource
                            sceneController.addScene(sceneName, scenePath);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Attaches all stylesheets in stylesheets recursively
    private void attachStylesheets(Scene scene, String baseStylesheetPath) {
        try {
            URL url = getClass().getResource(baseStylesheetPath);
            if (url == null) {
                System.out.println("Stylesheets path not found: " + baseStylesheetPath);
                return;
            }

            if (url.getProtocol().equals("file")) {
                // Development mode (inside IDE)
                File folder = new File(url.toURI());
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".css")) {
                            String stylesheetPath = baseStylesheetPath + "/" + file.getName();
                            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylesheetPath)).toExternalForm());
                        } else if (file.isDirectory()) {
                            attachStylesheets(scene, baseStylesheetPath + "/" + file.getName());
                        }
                    }
                }
            } else if (url.getProtocol().equals("jar")) {
                // Packaged mode (inside JAR)
                String pathInsideJar = baseStylesheetPath.substring(1); // remove leading '/'
                String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(pathInsideJar) && name.endsWith(".css")) {
                            String stylesheetPath = "/" + name;
                            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylesheetPath)).toExternalForm());
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private SceneController loadScenes(BorderPane mainLayout, FXMLLoader navigationBarLoader) {
        SceneController sceneController = new SceneController(mainLayout);
        addScenes(sceneController, "/pizza_shop_system/scenes");

        // Set main SceneController inside the navigation bar
        NavigationBarController navigationBarController = navigationBarLoader.getController();
        navigationBarController.setSceneController(sceneController);

        return sceneController;
    }

    // Start the GUI
    @Override
    public void start(Stage primaryStage) throws IOException {

        DataFileManager.initializeDataFiles();

        // Constants
        String DEFAULT_SCENE = "Home";
        int DEFAULT_WIDTH = 1000;
        int DEFAULT_HEIGHT = 800;
        boolean START_IN_FULLSCREEN = true;

        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/pizza_shop_system/scenes/navigation/NavigationBar.fxml"));
        Parent navigationBar = navigationBarLoader.load();

        // Create the border pane that holds the nav bar at the top and other scenes in the center
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navigationBar); // Keeps navigation bar fixed at the top of all scenes

        Scene scene = new Scene(mainLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(START_IN_FULLSCREEN);
        primaryStage.show();

        // Load all scenes upfront so we don't have to reload each time (Return scene controller so we can switch to default scene)
        SceneController sceneController = loadScenes(mainLayout, navigationBarLoader);

        // Attach ALL CSS stylesheets
        attachStylesheets(scene, "/pizza_shop_system/stylesheets");

        // Switch to default scene
        sceneController.switchScene(DEFAULT_SCENE); // Set the default scene
    }

    public static void main(String[] args) {
        launch(args);
    }

}