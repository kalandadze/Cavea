module com.example.cavea {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;


    opens com.example.cavea to javafx.fxml;
    exports com.example.cavea;
    exports com.example.cavea.controllers;
    opens com.example.cavea.controllers to javafx.fxml;
}