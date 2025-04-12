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

    public SceneController(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void addScene(String name, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            scenes.put(name, root); // Store scene content instead of full Scene object
        } catch (IOException e) {
            System.out.println("Failed to add scene -> " + name + ": " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Warning: This FXML file was not loaded. Ensure " + fxmlFile + " exists and path is correct.");
            System.out.println("If it exists and path is correct. Then ensure the controller for that scene is correctly referencing elements within it");
        }
    }

    public void switchScene(String name) {
        if (scenes.containsKey(name)) {
            mainLayout.setCenter(scenes.get(name)); // Only update center so navigation bar stays
        } else {
            System.out.println("Scene not found: " + name);
        }
    }
}
