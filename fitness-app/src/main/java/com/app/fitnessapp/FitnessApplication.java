package com.app.fitnessapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FitnessApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FitnessApplication.class.getResource("fitness-app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);
        stage.setTitle("Fitness Application!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        launch();
    }
}