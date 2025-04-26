package pizza_shop_system.gui.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class SceneController {
    private final BorderPane mainLayout;
    private final HashMap<String, Parent> scenes = new HashMap<>();
    private String currentSceneName;
    private String previousSceneName;
    
    // Stack to maintain the history of scenes for forward navigation
    private final Stack<String> sceneHistory = new Stack<>();

    public SceneController(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void addScene(String name, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            root.getStyleClass().add(name); // Add a style class for each scene so all their elements can be edited easily
            scenes.put(name, root);

            // Pass SceneController reference to each controller
            try {
                BaseController controller = loader.getController();
                if (controller != null) {
                    controller.setSceneController(this);
                }
            } catch (ClassCastException e) {
                System.out.println("Controller does not extend BaseController: " + name);
            }

        } catch (IOException e) {
            System.out.println("Failed to add scene -> " + name + ": " + e.getClass());
        } catch (NullPointerException e) {
            System.out.println("Error: Ensure " + fxmlFile + " exists and path is correct. " + e.getClass());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + fxmlFile + " is not a valid path. " + e.getClass());
        }
    }

    public void switchScene(String name) {
        if (scenes.containsKey(name)) {
            if (!name.equals(currentSceneName)) {
                // Push the current scene onto the history stack before switching
                if (currentSceneName != null) {
                    sceneHistory.push(currentSceneName);
                }
                previousSceneName = currentSceneName;
                currentSceneName = name;

                // Update center of the BorderPane with selected scene
                mainLayout.setCenter(scenes.get(name)); // Only update center so navigation bar stays
            }
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void switchToPreviousScene() {
        if (previousSceneName != null) {
            mainLayout.setCenter(scenes.get(previousSceneName));
            currentSceneName = previousSceneName;
            previousSceneName = sceneHistory.isEmpty() ? null : sceneHistory.pop(); // Get the last scene from history
        }
    }

    public void switchToForwardScene() {
        System.out.println("Forward");
        if (!sceneHistory.isEmpty()) {
            String forwardSceneName = sceneHistory.peek(); // Peek at the next scene
            if (forwardSceneName != null) {
                mainLayout.setCenter(scenes.get(forwardSceneName));
                previousSceneName = currentSceneName;
                currentSceneName = forwardSceneName;
                sceneHistory.pop(); // Remove the scene from stack once visited
            }
        }
    }
}
