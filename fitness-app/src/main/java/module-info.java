module com.app.fitnessapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.app.fitnessapp to javafx.fxml;
    exports com.app.fitnessapp;
}