module ru.kafpin.frostbuy {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.kafpin.frostbuy to javafx.fxml;
    exports ru.kafpin.frostbuy;
}