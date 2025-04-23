module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.json;

    opens pizza_shop_system to javafx.fxml;
    exports pizza_shop_system;
    exports pizza_shop_system.account;
    opens pizza_shop_system.account to javafx.fxml;
    exports pizza_shop_system.gui;
    opens pizza_shop_system.gui to javafx.fxml;
    exports pizza_shop_system.orderSystem.payment;
    opens pizza_shop_system.orderSystem.payment to javafx.fxml;
    exports pizza_shop_system.menu;
    opens pizza_shop_system.menu to javafx.fxml;
}