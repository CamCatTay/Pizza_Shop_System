package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pizza_shop_system.order.Order;
import pizza_shop_system.order.OrderStatus;

import java.util.ArrayList;
import java.util.Objects;

public class NavigationBarController extends BaseController {
    public HBox navigationBar;
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;
    @FXML private Button buttonBack;
    @FXML private Button buttonLogin;
    @FXML private ImageView logoView;

    private void setHomeButtonImage() {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pizza_shop_system/images/Bobs_Logo.png"))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(40);
        buttonHome.setGraphic(imageView);
    }

    private void setBackButtonImage() {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pizza_shop_system/images/left-arrow.png"))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(30);
        imageView.setFitHeight(25);

        // Make arrow white
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1);
        imageView.setEffect(colorAdjust);

        buttonBack.setGraphic(imageView);
    }

    private void switchToCartWithOrder() {
        try {
            ArrayList<Order> allOrders = Order.loadAllOrders();

            if (allOrders.isEmpty()) {
                System.out.println("No orders exist. Showing empty cart.");
                showEmptyCart();
                return;
            }

            Order matchedOrder = allOrders.stream()
                    .filter(order -> order.getStatus() == OrderStatus.INCOMPLETE)
                    .findFirst()
                    .orElse(null);

            if (matchedOrder == null) {
                System.out.println("No active cart found. Showing empty cart.");
                showEmptyCart();
                return;
            }

            sceneController.switchSceneWithData("Cart", controller -> {
                if (controller instanceof CartController) {
                    ((CartController) controller).setCurrentOrder(matchedOrder);
                }
            });

        } catch (Exception e) {
            System.out.println("Error switching to cart: " + e.getMessage());
        }
    }

    private void showEmptyCart() {
        sceneController.switchSceneWithData("Cart", controller -> {
            if (controller instanceof CartController) {
                ((CartController) controller).setCurrentOrder(new Order());
            }
        });
    }

    @FXML
    public void initialize() {
        buttonHome.setOnAction(e -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonCart.setOnAction(e -> switchToCartWithOrder());
        buttonLogin.setOnAction(e -> sceneController.switchScene("Login"));
        buttonBack.setOnAction(e -> sceneController.switchToPreviousScene());

        setHomeButtonImage();
        setBackButtonImage();
    }
}
