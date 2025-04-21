package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pizza_shop_system.account.AccountService;
import pizza_shop_system.account.User;
import pizza_shop_system.order.CurrentOrder;
import pizza_shop_system.order.Order;
import pizza_shop_system.order.OrderStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class NavigationBarController extends BaseController {

    public HBox navigationBar;
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;
    @FXML private Button buttonBack;
    @FXML private Button buttonLogin;
    @FXML private Button buttonAccount;
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

            // Check for an INCOMPLETE order only if CurrentOrder isn't already empty
            Order matchedOrder = allOrders.stream()
                    .filter(order -> order.getStatus() == OrderStatus.INCOMPLETE)
                    .findFirst()
                    .orElse(null);

            CurrentOrder currentOrder = CurrentOrder.getInstance();

            if (matchedOrder != null) {
                currentOrder.loadFrom(matchedOrder);
            }

            sceneController.switchSceneWithData("Cart", controller -> {
                if (controller instanceof CartController cartController) {
                    cartController.loadCurrentOrder(); // refresh UI
                }
            });

        } catch (Exception e) {
            System.out.println("Error switching to cart: " + e.getMessage());
        }
    }


    @FXML
    public void initialize() {
        buttonHome.setOnAction(e -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonCart.setOnAction(e -> switchToCartWithOrder());
        buttonLogin.setOnAction(e -> sceneController.switchScene("Login"));

        buttonAccount.setOnAction(e -> {
            try {
                AccountService accountService = new AccountService();
                User currentUser = accountService.getActiveUser();
                if (currentUser != null) {
                    String accountType = currentUser.getAccountType();

                    if("manager".equals(accountType)) {
                        sceneController.switchScene("ManagerHome");
                    } else {
                        sceneController.switchScene("CustomerHome"); //Need to set up scene for this
                    }
                }
            } catch (IOException er) {
                System.err.println("Error switching to Account: " + er.getMessage());
                er.printStackTrace();
            }
        });

        buttonBack.setOnAction(e -> sceneController.switchToPreviousScene());

        setHomeButtonImage();
        setBackButtonImage();
    }
}
