package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;

public class ManagerHomeController extends BaseController {

    @FXML
    Button buttonTakeOrders, buttonManageAccounts, buttonGenerateReports;

    @FXML
    public void initialize() {
        buttonTakeOrders.setOnAction(e -> sceneController.switchScene("Menu"));
        buttonManageAccounts.setOnAction(e -> sceneController.switchScene("ManageAccounts"));
        buttonGenerateReports.setOnAction(e -> sceneController.switchScene("Reports"));
    }
}
