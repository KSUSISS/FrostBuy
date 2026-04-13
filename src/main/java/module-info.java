module ru.kafpin.frostbuy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens ru.kafpin.frostbuy to javafx.fxml;
    exports ru.kafpin.frostbuy;
}