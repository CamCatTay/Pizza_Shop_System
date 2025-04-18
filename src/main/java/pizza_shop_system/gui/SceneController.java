package pizza_shop_system.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class SceneController {
    private final BorderPane mainLayout;
    private final HashMap<String, Parent> scenes = new HashMap<>();
    private final Stack<String> sceneHistory = new Stack<>();
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
            try {
                BaseController controller = loader.getController();
                if (controller != null) {
                    controller.setSceneController(this);
                }
            } catch (ClassCastException e) {
                System.out.println("WARNING: Controller does not extend BaseController: " + name);
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
            if(currentSceneName != null && !currentSceneName.equals(name)) {
                sceneHistory.push(currentSceneName);
            }
            currentSceneName = name;
            mainLayout.setCenter(scenes.get(name)); // Only update center so navigation bar stays
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void switchToPreviousScene() {
        if(!sceneHistory.isEmpty()) {
            String previousSceneName = sceneHistory.pop();
            currentSceneName = previousSceneName;
            mainLayout.setCenter(scenes.get(previousSceneName));
        } else {
            System.out.println("Previous scene not found");
        }
    }

    //For Cart Nav
    public void switchSceneWithData(String name, java.util.function.Consumer<Object> controllerConsumer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pizza_shop_system/scenes/" + name + ".fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();

            if (controller instanceof BaseController) {
                ((BaseController) controller).setSceneController(this);
            }

            if (controllerConsumer != null) {
                controllerConsumer.accept(controller);
            }

            if (currentSceneName != null && !currentSceneName.equals(name)) {
                sceneHistory.push(currentSceneName);
            }

            currentSceneName = name;
            mainLayout.setCenter(root);
        } catch (IOException e) {
            System.out.println("Failed to switch scene with data: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("FXML file not found for: " + name + ". Check the path and file name.");
        }
    }

    //IF we want to nav back to home page
    public void clearSceneHistory() {
        sceneHistory.clear();
    }
}
