package com.his.controller;

import com.his.entity.Doctor;
import com.his.entity.KanZhenUtil;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Paint;
;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DoctorRegister implements Initializable {


    /**
     * 医生科室
     */

    @FXML
    private ChoiceBox<String> doctorRoomChoiceBox;
    /**
     * 医生密码
     */
    @FXML
    private TextField doctorPasswordText;

    @FXML
    private PasswordField passwordText;

    /**
     * 医生级别
     */
    @FXML
    private ChoiceBox<Integer> doctorLevelChoiceBox;

    /**
     * 医生账号
     */
    @FXML
    private TextField doctorCountText;

    /**
     * 医生名字
     */
    @FXML
    private TextField doctorNameText;

    /**
     * 按钮名字
     */
    @FXML
    private Button buttonRegister;

    /**
     * 画布
     */
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button clearButton;


    private String[] doctorRoom = KanZhenUtil.roomChoice;

    private Integer[] doctorGrade = KanZhenUtil.levelChoice;

    public Integer[] getDoctorGrade(ActionEvent actionEvent) {
        return doctorGrade;
    }

    public void setDoctorGrade(Integer[] doctorGrade) {
        this.doctorGrade = doctorGrade;
    }

    public String[] getDoctorRoom(ActionEvent actionEvent) {
        return doctorRoom;
    }

    public void setDoctorRoom(String[] doctorRoom) {
        this.doctorRoom = doctorRoom;
    }

    public TextField getDoctorPasswordText() {
        return doctorPasswordText;
    }

    public void setDoctorPasswordText(TextField doctorPasswordText) {
        this.doctorPasswordText = doctorPasswordText;
    }

    public TextField getDoctorCountText() {
        return doctorCountText;
    }

    public void setDoctorCountText(TextField doctorCountText) {
        this.doctorCountText = doctorCountText;
    }

    public TextField getDoctorNameText() {
        return doctorNameText;
    }

    public void setDoctorNameText(TextField doctorNameText) {
        this.doctorNameText = doctorNameText;
    }

    public PasswordField getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(PasswordField passwordText) {
        this.passwordText = passwordText;
    }

    /**
     * 医生注册模块
     *
     * @param event
     * @throws IOException
     */

    @FXML
    void register(ActionEvent event) throws IOException {
        List<Patient> patients = Tools.findAll(Patient.class, "kzFile.txt");
        List<Doctor> doctors = Tools.findAll(Doctor.class, "dcFile.txt");
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        String doctorName = doctorNameText.getText();
        String doctorCount = doctorCountText.getText();
        String doctorPassword = passwordText.getText();
        doctorPasswordText.setText(doctorPassword);
        if (doctorNameText.getText().length() == 0 ||passwordText.getText().length() == 0 || doctorCountText.getText().length() == 0 || doctorPasswordText.getText().length() == 0) {
            Tools.showAlert(null, null, "请输入完整信息！");
        } else {
            String doctorId = "" + (doctors.size() + 1001);
            Doctor doctor = new Doctor(doctorCount, doctorPassword, doctorId, doctorRoomChoiceBox.getValue(), doctorName, doctorLevelChoiceBox.getValue() + "");
            doctors.add(doctor);
            Tools.saveResult(doctors, Doctor.class, "dcFile.txt");
            Tools.showAlert(null, null, "注册成功");
        }
    }

    /**
     * 清空所有信息
     */
    public void clearButton(){
        doctorPasswordText.setText(null);
        doctorNameText.setText(null);
        doctorCountText.setText(null);
        passwordText.setText(null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        doctorRoomChoiceBox.getItems().addAll(doctorRoom);
        doctorRoomChoiceBox.getSelectionModel().select(0);
        doctorRoomChoiceBox.setOnAction(this::getDoctorRoom);

        doctorLevelChoiceBox.getItems().addAll(doctorGrade);
        doctorLevelChoiceBox.getSelectionModel().select(0);
        doctorLevelChoiceBox.setOnAction(this::getDoctorGrade);

        /**
         * passwordfield可视化
         */
        Button isVisible = new Button();
        Image eyeOpen = new Image("@../../images/show.png");
        Image eyeClose = new Image("@../../images/unshow.png");
        ImageView image = new ImageView(eyeClose);
        isVisible.setGraphic(image);
        isVisible.setLayoutX(passwordText.getLayoutX()+125);
        isVisible.setLayoutY(passwordText.getLayoutY()-3);
        isVisible.setPrefHeight(23);
        isVisible.setBackground(null);
        isVisible.setPrefWidth(27);
        isVisible.setBorder(null);
        anchorpane.getChildren().add(isVisible);
        isVisible.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(image.getImage() == eyeClose){
                    String s = passwordText.getText();
                    doctorPasswordText.setVisible(true);
                    doctorPasswordText.setText(s);
                    passwordText.setVisible(false);
                    image.setImage(eyeOpen);
                }else{
                    String s = doctorPasswordText.getText();
                    doctorPasswordText.setVisible(false);
                    passwordText.setText(s);
                    passwordText.setVisible(true);
                    image.setImage(eyeClose);
                }
            }
        });
        /**
         * 按钮选中显示
         */
        Tools.buttonHighlight(clearButton);
        Tools.buttonHighlight(buttonRegister);
    }

}
