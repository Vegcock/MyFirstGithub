package com.his.controller;


import com.his.entity.Doctor;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class DeleteMenu implements Initializable {
    /**
     * 指定患者信息文件
     */
    private static File kzFile = Tools.kzFile;
    /**
     * 返回按钮
     */
    @FXML
    private Button backButton;
    /**
     * 信息ListView
     */
    @FXML
    private ListView<String> dataList;
    /**
     * 删除按钮
     */
    @FXML
    private Button deleteButton;

    /**
     * 点击返回按钮
     *
     * @param event
     */
    @FXML
    void back(ActionEvent event) {

    }

    /**
     * 界面初始化：获取全部患者信息并显示在dataListView上
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置鼠标经过按钮改变鼠标样式
        deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteButton.setCursor(Cursor.HAND);
            }
        });

        //显示患者信息在ListView上
        List<Patient> all = Tools.findAllPatient();
        try {
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                names.add(all.get(i).getRealname() + "\t" + all.get(i).getGender() + "\t\t" + all.get(i).getAge() +
                        "\t\t" + all.get(i).getCardnumber() + "\t\t" + all.get(i).isIskz() + "\t\t\t" + all.get(i).isIsyf() +
                        "\t\t\t" + all.get(i).getBalance()+"\t\t"+all.get(i).getId());
            }
            ObservableList<String> obList = FXCollections.observableArrayList(names);
            dataList.setItems(obList);
        } catch (NullPointerException e) {
            //没有患者数据
        }

        Tools.buttonHighlight(deleteButton);
    }

    /**
     * 点击删除按钮
     *
     * @param event
     */
    @FXML
    void delete(ActionEvent event) {
        String line = dataList.getSelectionModel().getSelectedItem();
        if (line == null) {
            //没有选择删除的对象
            Tools.showAlert(null, null, "请选择一个患者！");
        } else {
            //正常操作
            String[] words = line.split("\t");
            //获取姓名
            String targetName = words[0];
            List<Patient> all = Tools.findAllPatient();
            Patient targetPatient = new Patient();
            for (int i = 0; i < all.size(); i++) {
                if (targetName.equals(all.get(i).getRealname())) {
                    targetPatient = all.get(i);
                    try {
                        //删除看诊医生历史记录里的患者信息
                        List<Doctor> allDoctor = Tools.findAll(Doctor.class, "dcFile.txt");
                        boolean flag = false;
                        for (int j = 0; j < allDoctor.size(); j++) {
                            List<Patient> patients = allDoctor.get(j).getPatients();
                            for (Patient thisPatient : patients) {
                                if (thisPatient.getRealname().equals(targetPatient.getRealname())) {
                                    allDoctor.get(j).getPatients().remove(thisPatient);
                                    //病例号重置
                                    KanZhen.ids[Integer.parseInt(targetPatient.getId())] = 0;
                                    flag = true;
                                    System.out.println("删除的医生患者信息：" + thisPatient.getRealname());
                                    Tools.saveResult(allDoctor, Doctor.class, "dcFile.txt");
                                    break;
                                }
                            }
                        }
                        if (flag == false) {
                            System.out.println("无患者医生信息");
                        }
                    } catch (IOException e) {
                        Tools.showAlert(null, null, "打开医生文件失败！");
                    }
                    all.remove(i);
                    break;
                }
            }
            Tools.kzSave(all);
            Tools.showAlert(null, null, "删除成功！");
        }
    }
}
