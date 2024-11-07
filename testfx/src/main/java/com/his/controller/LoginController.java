package com.his.controller;

import com.his.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private HBox uhbox;

    @FXML
    private Button bt1;

    @FXML
    private Button bt2;

    @FXML
    private  Button bt3;

    public LoginController(HBox uhbox, Button bt1, Button bt2, Button bt3) {
        this.uhbox = uhbox;
        this.bt1 = bt1;
        this.bt2 = bt2;
        this.bt3 = bt3;
    }
    public LoginController(){

    }

    public HBox getUhbox() {
        return uhbox;
    }

    public Button getBt1() {
        return bt1;
    }

    public Button getBt2() {
        return bt2;
    }

    public Button getBt3() {
        return bt3;
    }

    public void show1(){
        uhbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("Pharmacy.fxml");
        loader.setLocation(url);
        AnchorPane anchorPane=null;
        try {
             anchorPane = loader.load();
             uhbox.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void show2(){
        uhbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("DrugInto.fxml");
        loader.setLocation(url);
        AnchorPane anchorPane=null;
        try {
            anchorPane = loader.load();
            uhbox.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void show3(){
        uhbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("DrugInformation.fxml");
        loader.setLocation(url);
        AnchorPane anchorPane=null;
        try {
            anchorPane = loader.load();
            uhbox.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getBt1().setDisable(false);
        getBt2().setDisable(true);
        getBt3().setDisable(true);

        if(PharmacyController.isInformation){
            getBt2().setDisable(false);
        }
        if(PharmacyController.isInto){
            getBt3().setDisable(false);
        }

    }
}
