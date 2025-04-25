package pizza_shop_system.gui.navigation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pizza_shop_system.account.services.AccountService;
import pizza_shop_system.account.entities.User;
import pizza_shop_system.gui.base.BaseController;

import java.io.IOException;
import java.util.Objects;

public class NavigationBarController extends BaseController {

    public HBox navigationBar;
    @FXML private Button buttonHome;
    @FXML private Button buttonMenu;
    @FXML private Button buttonCart;
    @FXML private Button buttonBack;
    @FXML private Button buttonForward;
    @FXML private Button buttonLogin;
    @FXML private Button buttonAccount;
    @FXML private ImageView logoView;

    private AccountService accountService = new AccountService();

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


    @FXML
    public void initialize() {
        buttonHome.setOnAction(_ -> sceneController.switchScene("Home"));
        buttonMenu.setOnAction(_ -> sceneController.switchScene("Menu"));
        buttonLogin.setOnAction(_ -> sceneController.switchScene("Login"));
        buttonCart.setOnAction(_ -> sceneController.switchScene("Cart"));
        buttonAccount.setOnAction(_ -> {
            if (accountService.getActiveUserId() != 0) {
                sceneController.switchScene("AccountMenu");
            } else {
                sceneController.switchScene("Login");
            }
        });

        /*
        buttonAccount.setOnAction(_ -> {
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

         */

        buttonBack.setOnAction(_ -> sceneController.switchToPreviousScene());
        buttonForward.setOnAction(_ -> sceneController.switchToForwardScene());
        setHomeButtonImage();
        setBackButtonImage();
    }
}
