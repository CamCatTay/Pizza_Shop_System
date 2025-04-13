package pizza_shop_system.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {
    private final BorderPane mainLayout;
    private final HashMap<String, Parent> scenes = new HashMap<>();
    private String previousSceneName;
    private String currentSceneName;

    public SceneController(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void addScene(String name, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            scenes.put(name, root);

            // Pass SceneController reference to each controller
            BaseController controller = loader.getController();
            if (controller != null) {
                controller.setSceneController(this);
            }
        } catch (IOException e) {
            System.out.println("Failed to add scene -> " + name + ": " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Warning: This FXML file was not loaded. Ensure " + fxmlFile + " exists and path is correct.");
            System.out.println("If it exists and path is correct. Then ensure the controller for that scene is correctly referencing elements within it");
        }
    }

    public void switchScene(String name) {
        if (scenes.containsKey(name)) {
            if (!name.equals(currentSceneName)) {
                // Set the previous scene name for back button
                previousSceneName = currentSceneName;
                currentSceneName = name;

                // Update center of the BorderPane with selected scene
                mainLayout.setCenter(scenes.get(name)); // Only update center so navigation bar stays
            }
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    // For back button. Note: Could use a stack which would allow the back button to trace a users scene switches all the way back to the initial home (Like a website)
    public void switchToPreviousScene() {
        if (previousSceneName != null) {
            mainLayout.setCenter(scenes.get(previousSceneName));
            currentSceneName = previousSceneName;
        }
    }
}
