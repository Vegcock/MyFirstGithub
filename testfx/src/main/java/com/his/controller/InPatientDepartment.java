package com.his.controller;
//since 2024/6/20 by neu.CZ


import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class InPatientDepartment implements Initializable {

    @FXML
    private TextField idInputText;

    @FXML
    private ListView<String> inPatientList;

    @FXML
    private Label ageLabel;

    @FXML
    private Label cardLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label nameLabel;


    /**
     * 添加患者到住院清单中
     *
     * @param event
     */
    @FXML
    void add(ActionEvent event) {
        String id = idInputText.getText();
        if ("".equals(id)) {
            //没输入病例号
            Tools.showAlert(null, null, "请输入患者病例号！");
        } else {
            List<Patient> patients = Tools.findAllPatient();
            Patient targetPatient = null;
            //二分查找目标患者
            targetPatient = Tools.getPatient(id, patients);
            if (targetPatient == null) {
                Tools.showAlert(null, null, "获取患者信息失败！");
            } else {
                if (targetPatient.isHoi() == true) {
                    Tools.showAlert(null, null, "该患者已经住院！");
                } else {
                    for (int i = 0; i < patients.size(); i++) {
                        if (patients.get(i).getId().equals(targetPatient.getId())) {
                            //设置患者isHoi为true
                            patients.get(i).setHoi(true);
                            Tools.kzSave(patients);
                            Tools.showAlert(null, null, "添加成功！");
                            break;
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * 将患者移出住院清单
     *
     * @param event
     */
    @FXML
    void remove(ActionEvent event) {
        try {
            String line = inPatientList.getSelectionModel().getSelectedItem();
            //获取患者信息，以words[]存以\t分隔的字符串，获取第一个，即姓名，再查找
            String[] words = line.split("\t");
            String targetName = words[0];
            List<Patient> patients = Tools.findAllPatient();
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getRealname().equals(targetName)) {
                    if (patients.get(i).isHoi()) {
                        //设置患者isHoi为false
                        patients.get(i).setHoi(false);
                        Tools.kzSave(patients);
                        Tools.showAlert(null, null, "移除成功！");
                        System.out.println(patients.get(i).isHoi());
                        return;
                    } else {
                        Tools.showAlert(null, null, "该患者未住院！");
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "请选择一个患者");
        }
    }

    /**
     * 查询目标患者信息
     *
     * @param event
     */
    @FXML
    void search(ActionEvent event) {
        try {
            String id = idInputText.getText();
            List<Patient> patients = Tools.findAllPatient();
            Patient targetPatient = null;
            try {
                //二分查找目标患者
                targetPatient = Tools.getPatient(id, patients);
            } catch (NumberFormatException e) {
                Tools.showAlert(null, null, "请输入患者病例号！");
                return;
            }
            if (targetPatient == null) {
                Tools.showAlert(null, null, "未找到目标患者！");
                return;
            } else {
                //显示患者信息
                nameLabel.setText(targetPatient.getRealname());
                ageLabel.setText(targetPatient.getAge() + "");
                cardLabel.setText(targetPatient.getCardnumber());
                genderLabel.setText(targetPatient.getGender());
                nameLabel.setVisible(true);
                ageLabel.setVisible(true);
                genderLabel.setVisible(true);
                cardLabel.setVisible(true);
            }
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "请输入患者病例号！");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //将患者信息添加到ListView上
        List<Patient> patients = Tools.findAllPatient();
        List<Patient> allInPatient = new ArrayList<>();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).isHoi()) {
                allInPatient.add(patients.get(i));
            }
        }
        //debug
        for (int i = 0; i < allInPatient.size(); i++) {
            System.out.println(allInPatient.get(i).getRealname());
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < allInPatient.size(); i++) {
            list.add(allInPatient.get(i).getRealname() + "\t" + allInPatient.get(i).getGender() + "\t\t" + allInPatient.get(i).getAge() + "\t\t" + allInPatient.get(i).getCardnumber() + "\t\t" + allInPatient.get(i).isIskz() + "\t\t\t" + allInPatient.get(i).isIsyf() + "\t\t\t" + allInPatient.get(i).getBalance() + "\t\t" + allInPatient.get(i).getId());

        }
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        inPatientList.setItems(obList);

        //初始设置Label全部不可间
        nameLabel.setVisible(false);
        ageLabel.setVisible(false);
        genderLabel.setVisible(false);
        cardLabel.setVisible(false);
    }
}
