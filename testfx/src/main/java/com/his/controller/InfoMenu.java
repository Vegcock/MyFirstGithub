package com.his.controller;


import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class InfoMenu implements Initializable {
    private static File kzFile = Tools.kzFile;
    /**
     * 信息ListView
     */
    @FXML
    private ListView<String> infoList;
    /**
     * 返回按钮
     */
    @FXML
    private Button backButton;

    /**
     * 界面初始化：加载患者信息显示再ListView上
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //显示患者信息在ListView上
        List<Patient> all = Tools.findAllPatient();
        try {
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                names.add(all.get(i).getRealname() + "\t" + all.get(i).getGender() + "\t\t" + all.get(i).getAge() + "\t\t" + all.get(i).getCardnumber() + "\t\t" + all.get(i).isIskz() + "\t\t\t" + all.get(i).isIsyf() + "\t\t\t" + all.get(i).getBalance()+"\t\t"+all.get(i).getId());
            }
            ObservableList<String> obList = FXCollections.observableArrayList(names);
            infoList.setItems(obList);
        } catch (NullPointerException e) {
            //当前无患者信息
        }

    }

    /**
     * 点击返回按钮
     *
     * @param event
     */
    @FXML
    void back(ActionEvent event) {
        backButton.getScene().getWindow().hide();
    }
}
