package com.his.controller;

import com.alibaba.fastjson.JSONArray;

import com.his.entity.Patient;
import com.his.fileio.Tools;

import com.his.entity.Doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wzh
 * @param <ToolS>
 */
public class kzView<ToolS> implements Initializable {

    public static Stage stage = new Stage();

    @FXML
    private Text doctorRoomText;

    @FXML
    private Text doctorNameText;

    @FXML
    private Text doctorLevelText;

    @FXML
    private TextField patientNumText;

    @FXML
    private Text patientGenderText;

    @FXML
    private Text patientAgeText;

    @FXML
    private Button buttonSave;

    @FXML
    private Text patientNumber;

    @FXML
    private Button buttonsearch;

    @FXML
    private Text patientNameText;

    @FXML
    private TextArea suggestionDoctorText;

    @FXML
    private TextArea suggestionDrugText;

    @FXML
    private DatePicker kzDate;

    public Text getDoctorNameText() {
        return doctorNameText;
    }

    public void setDoctorNameText(Text doctorNameText) {
        this.doctorNameText = doctorNameText;
    }

    public DatePicker getKzDate() {
        return kzDate;
    }

    public void setKzDate(DatePicker kzDate) {
        this.kzDate = kzDate;
    }

    public void setPatientNumText(TextField patientNumText) {
        this.patientNumText = patientNumText;
    }

    public TextField getPatientNumText() {
        return patientNumText;
    }

    public TextArea getSuggestionDoctorText() {
        return suggestionDoctorText;
    }

    public TextArea getSuggestionDrugText() {
        return suggestionDrugText;
    }

    public void setSuggestionDoctorText(TextArea suggestionDoctorText) {
        this.suggestionDoctorText = suggestionDoctorText;
    }

    public void setSuggestionDrugText(TextArea suggestionDrugText) {
        this.suggestionDrugText = suggestionDrugText;
    }

    static File file = new File("kzFile.txt");
    static Patient patient;

    static {
        if (!file.exists()) {
            try {
                file.createNewFile();
                List<Patient> patients = new ArrayList<>();
                String patientJSON = JSONArray.toJSONString(patients);
                FileOutputStream fileOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询患者按钮
     *
     * @param event
     * @throws IOException
     */

    @FXML
    void search(ActionEvent event) throws IOException {
        List<Patient> patients = Tools.findAll(Patient.class, "kzFile.txt");
        try {
            String patientId = patientNumText.getText();
            boolean flag = false;
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getId().equals(patientId)) {
                    patient = patients.get(i);
                    flag = true;
                    if (patient.isIskz() == false) {
                        patientNameText.setText(patient.getRealname());
                        patientAgeText.setText("" + patient.getAge());
                        patientNumber.setText(patient.getCardnumber());
                        patientGenderText.setText(patient.getGender());
                        break;
                    } else if (patient.isIskz() == true) {
                        Tools.showAlert(null, null, "该患者已看诊");
                        break;
                    }
                }
            }
            if (flag == false) {
                Tools.showAlert(null, null, "未查询到该患者");
            }
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "请输入病例号");
        }
    }

    /**
     * 保存患者信息按钮
     *
     * @param event
     * @throws IOException
     */


    @FXML
    void save(ActionEvent event) throws IOException {
        try {
            String count = DoctorLogin.count;
            String password = DoctorLogin.password;

            String doctorname = doctorNameText.getText();
            LocalDate birthday = kzDate.getValue();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String kzDate = birthday.format(fmt);

            String kzDoctorMessage = suggestionDoctorText.getText();
            String kzDrugMessage = suggestionDrugText.getText();

            List<Doctor> doctors = Tools.findAll(Doctor.class, "dcFile.txt");
            List<Patient> patients = Tools.findAll(Patient.class, "kzfile.txt");
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getCardnumber().equals(patient.getCardnumber())) {
                    patients.get(i).setDiagiosis("医生建议:" + kzDoctorMessage + "  开方建议:" + kzDrugMessage + "  看诊时间:" + kzDate);
                    patient.setDiagiosis("医生建议:" + kzDoctorMessage + "  开方建议:" + kzDrugMessage + "  看诊时间:" + kzDate);
                    patients.get(i).setIskz(true);
                    patient.setIskz(true);
                    patients.get(i).setDoctorname(doctorname);
                    patient.setDoctorname(doctorname);
                }
            }

            for (int i = 0; i < doctors.size(); i++) {
                if ((doctors.get(i).getConut().equals(count)) && (doctors.get(i).getPassword().equals(password))) {
                    doctors.get(i).add(patient);
                }
            }

            Tools.saveResult(doctors, Doctor.class, "dcFile.txt");
            Tools.saveResult(patients, Patient.class, "kzFile.txt");
            Tools.showAlert(null, "", "保存成功");
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "请完整填写信息");
        }
    }

    /**
     * 医生信息初始化模块
     *
     * @throws IOException
     */

    public void doctorMessageInitialize() throws IOException {
        String count = DoctorLogin.count;
        String password = DoctorLogin.password;
        List<Doctor> doctors = new ArrayList<>();
        doctors = Tools.findAll(Doctor.class, "dcFile.txt");
        boolean flag = false;
        for (int i = 0; i < doctors.size(); i++) {
            if ((doctors.get(i).getConut().equals(count)) && (doctors.get(i).getPassword().equals(password))) {
                flag = true;
                doctorLevelText.setText(doctors.get(i).getGrade());
                doctorNameText.setText(doctors.get(i).getRealName());
                doctorRoomText.setText(doctors.get(i).getRoom());
            }
        }
        if (flag == false) {
            Tools.showAlert(null, null, "未查询到该医生");
        }
    }

    /*
    界面初始化
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //判读是否为非法访问
        if (DoctorLogin.isLogin == false) {
            /**
             * 将DoctorLogin的isLegal项改为非法访问
             */
            DoctorLogin.isLegal = false;
            Tools.showAlert(null, null, "请先登录一个医生账号");
            Tools.stageCreate(stage, "doctorLogin.fxml", "登录界面");
        }
        try {
            doctorMessageInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tools.buttonHighlight(buttonSave);
        Tools.buttonHighlight(buttonsearch);

    }
}
