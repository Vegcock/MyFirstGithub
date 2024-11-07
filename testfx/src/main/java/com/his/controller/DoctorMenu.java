package com.his.controller;

import com.his.entity.Doctor;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wzh
 */
public class DoctorMenu implements Initializable {

    public static Stage stage = new Stage();

    /**
     * 判断键盘某键是否长按
     */
    public static boolean isPressed = false;

    @FXML
    private AnchorPane anchorpane;

    /**
     * 医生编号
     */
    @FXML
    private Text idText;

    /**
     * 医生所在科室
     */
    @FXML
    private Text roomText;

    /**
     * 医生级别
     */
    @FXML
    private Text levelText;

    /**
     * 医生名字
     */
    @FXML
    private Text nameText;

    /**
     * 当前科室病人列表
     */
    @FXML
    private ListView<String> kzList;

    /**
     * 医生历史看诊记录列表
     */
    @FXML
    private ListView<String> kzHistory;

    /**
     * 医生今日已诊断数量
     */
    @FXML
    private Text diagnose;

    /**
     * 账号更换
     */
    @FXML
    private Button countChange;

    public Text getIdText() {
        return idText;
    }

    public void setIdText(Text idText) {
        this.idText = idText;
    }

    public Text getRoomText() {
        return roomText;
    }

    public void setRoomText(Text roomText) {
        this.roomText = roomText;
    }

    public Text getLevelText() {
        return levelText;
    }

    public void setLevelText(Text levelText) {
        this.levelText = levelText;
    }

    public Text getNameText() {
        return nameText;
    }

    public void setNameText(Text nameText) {
        this.nameText = nameText;
    }

    public Text getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Text diagnose) {
        this.diagnose = diagnose;
    }

    public void countChangeButton(){
        DoctorLogin.isLegal = true;
        DoctorLogin.isLogin = false;
        countChange.getScene().getWindow().hide();
        Tools.stageCreate(stage,"pageTransform.fxml","界面");
    }
    /**
     * 界面初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * 记录当前是否为非法登录，若未登录直接进入页面则要求重新登录
         */
        if (DoctorLogin.isLogin == false) {
            DoctorLogin.isLegal = false;
            Tools.showAlert(null, null, "请先登录一个医生账号");
            Tools.stageCreate(stage, "doctorLogin.fxml", "登录界面");
            stage.close();
            stage.showAndWait();
        }
        /**
         * 合法登录后进行页面初始化
         */
        String count = DoctorLogin.count;
        String password = DoctorLogin.password;
        String room = null;
        String doctorname = null;
        try {
            List<Doctor> doctors = new ArrayList<>();
            doctors = Tools.findAll(Doctor.class, "dcFile.txt");
            for (int i = 0; i < doctors.size(); i++) {
                if ((doctors.get(i).getConut().equals(count)) && (doctors.get(i).getPassword().equals(password))) {
                    idText.setText(doctors.get(i).getId());
                    levelText.setText(doctors.get(i).getGrade());
                    nameText.setText(doctors.get(i).getRealName());
                    roomText.setText(doctors.get(i).getRoom());
                    diagnose.setText("" + doctors.get(i).getTotalPatient());
                    room = doctors.get(i).getRoom();
                    doctorname = doctors.get(i).getRealName();
                    /**
                     * 当前医生历史看诊记录初始化
                     */
                    ArrayList<String> historys = new ArrayList<>();
                    for (int j = 0; j < doctors.get(i).getTotalPatient(); i++) {
                        historys.add(doctors.get(i).getPatients().get(j).getRealname() + "\t" +
                                doctors.get(i).getPatients().get(j).getCardnumber() + "\t" +
                                doctors.get(i).getPatients().get(j).getDiagiosis());
                    }
                    ObservableList<String> historylist = FXCollections.observableArrayList(historys);
                    kzHistory.setItems(historylist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Patient> patients = new ArrayList<>();
        try {
            patients = Tools.findAll(Patient.class, "kzFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 当前科室病人列表初始化
         */
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getDeptname().equals(room)) {
                names.add(patients.get(i).getRealname() + "\t\t" + patients.get(i).isIskz() + "\t" + patients.get(i).getCardnumber());
            }
        }
        ObservableList<String> oblist = FXCollections.observableArrayList(names);
        kzList.setItems(oblist);

        kzList.setPlaceholder(new Label("表中无内容"));
        kzHistory.setPlaceholder(new Label("表中无内容"));

        /**
         * 按钮选中显示
         */
        Tools.buttonHighlight(countChange);

    }


}
