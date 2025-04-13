package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class HomeController extends BaseController {
    @FXML
    private Button orderButton;
    @FXML
    private ImageView logoView;

    @FXML
    private void initialize() {
        logoView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pizza_shop_system/images/Bobs_Logo.png"))));
        orderButton.setOnAction(e -> sceneController.switchScene("Menu"));
    }
}