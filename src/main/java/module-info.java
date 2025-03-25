module org.example.pizza_shop_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.pizza_shop_system to javafx.fxml;
    exports org.example.pizza_shop_system;
}