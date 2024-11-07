package com.his;

import javafx.fxml.FXMLLoader;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static String level;

    public static Stage primaryStage = new Stage();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("LoginViewController.fxml");
        loader.setLocation(url);
        AnchorPane loginPane = loader.load();
        Scene scene = new Scene(loginPane);
        //Stage stage = new Stage();
        stage.setTitle("界面");
        stage.setScene(scene);
        stage.show();
    }
}