package com.his.controller;


import com.his.controller.LoginController;
import com.his.entity.Drug;
import com.his.fileio.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DrugInformationController implements Initializable {

    @FXML
    private ListView<String> udrugInformation;

    @FXML
    private Button backButton;

    @FXML
    private HBox uhbox;

    private List<Drug> drugs=new ArrayList<>();


//    @FXML
//    void back(ActionEvent event) {
////        backButton.getScene().getWindow().hide();
//        LoginController loginController=new LoginController();
//        loginController.show3();
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drugs= Tools.findAllDrug();
        String []x=new String[6];
        for(int i = 0;i<drugs.size();i++){
            x[i]=drugs.get(i).getName()+"\t\t\t\t   "+drugs.get(i).getNumber()+"\t\t\t\t\t"+drugs.get(i).getPrice()
                    +"\t\t\t\t\t "+drugs.get(i).getNum();
        }
        ObservableList<String> items = FXCollections.observableArrayList(x);
        ListView<String> list = new ListView<>(items);
        udrugInformation.setItems(items);
    }
}
