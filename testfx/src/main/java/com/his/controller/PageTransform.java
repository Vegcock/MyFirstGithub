package com.his.controller;

import com.his.Main;
import com.his.fileio.Tools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PageTransform implements Initializable {

    public static Map<String,AnchorPane> anchorPanesControllers = new HashMap<>();
    public static Map<String,BorderPane> borderPaneControllers = new HashMap<>();

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button doctorList;

    @FXML
    private Button doctorLogin;

    @FXML
    private Button doctorMenu;

    @FXML
    private Button patientSearch;

    @FXML
    private Button registrationScene;

    @FXML
    private Button patientMessageCheck;

    @FXML
    private Button patientMessageEdit;

    @FXML
    private Button patientMessageDelete;

    @FXML
    private TitledPane diagnose;

    @FXML
    private TitledPane registration;

    @FXML
    private TitledPane pharmacy;

    @FXML
    private TitledPane charge;

    @FXML
    private TitledPane hospitalize;

    @FXML
    private TitledPane exitPane;

    @FXML
    private Button pharmacyDrugOutput;

    @FXML
    private Button pharmacySearch;

    @FXML
    private Button pharmacyDrugInput;

    @FXML
    private Button charging;

    @FXML
    private Button doctorRegister;

    @FXML
    private Button hospitalizeInfomation;

    @FXML
    private Button exit;

    @FXML
    private Button change;

    public static boolean isPressed = false;

    public void registrationButton() {
        Tools.transformPage(anchorPane, "KanZhen.fxml");
    }

    public void patientMessageCheckButton() {
        Tools.transformPage(anchorPane, "InfoMenu.fxml");
    }

    public void patientMessageEditButton() {
        Tools.transformPage(anchorPane, "editPatient.fxml");
    }

    public void patientMessageDeleteButton() {
        Tools.transformPage(anchorPane, "DeleteMenu.fxml");
    }

    public void doctorLoginButton() {
        Tools.transformPage(anchorPane, "doctorLogin.fxml");
    }

    public void doctorRegisterButton(){
        Tools.transformPage(anchorPane,"doctorRegister.fxml");
    }

    public void doctorListButton() {
        Tools.transformPage(anchorPane, "doctorList.fxml");
    }

    public void doctorMenuButton() {
        Tools.transformPage(anchorPane, "doctorMenu.fxml");
    }

    public void patientSearchButton() {
        Tools.transformPage(anchorPane, "kzView.fxml");
    }

    public void pharmacyDrugOutputButton() {
        Tools.transformPage(anchorPane, "Pharmacy.fxml");
    }

    public void pharmacyDrugInputButton() {
        Tools.transformPage(anchorPane, "DrugInto.fxml");
    }

    public void pharmacySearchButton() {
        Tools.transformPage(anchorPane, "DrugInformation.fxml");
    }

    public void chargingButton() {
        Tools.transformPage(anchorPane, "ChargeMenu.fxml");
    }

    public void hospitalizeInfomationButton(){
        Tools.transformPage(anchorPane,"InPatientDepartment.fxml");
    }

    public void exitButton(){
        System.exit(1);
    }

    public void changeButton(){
        change.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("LoginViewController.fxml");
        loader.setLocation(url);
        AnchorPane loginPane = null;
        try {
            loginPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(loginPane);
        Stage stage = new Stage();
        stage.setTitle("界面");
        stage.setScene(scene);
        stage.show();
    }


    double zoomFactor = 1.0;

    static double scaleValue;
    static double xOffset;
    static double yOffset;
    static double SCALE_DELTA;
    void paneVariation(AnchorPane pane,BorderPane mainpane){
        scaleValue = 1.0;
        xOffset = 0;
        yOffset = 0;
        SCALE_DELTA = 1.1;
        //ctrl+滚轮缩放  +  滚轮上下滚动
        mainpane.setOnScroll(event1 -> {
//            if(!event1.isControlDown()){
//                double deltaY = -event1.getDeltaY();
//                pane.setTranslateY(((pane.getTranslateY() - deltaY)));
//                event1.consume();
//            }
            if(event1.isControlDown()) {
                double deltaY = event1.getDeltaY();
                double bescaleValue = scaleValue;
                if (deltaY < 0) {
                    scaleValue /= SCALE_DELTA;
                } else {
                    scaleValue *= SCALE_DELTA;
                }
                double moveX=pane.getTranslateX()-(bescaleValue-scaleValue)*pane.getWidth()/2;
                double moveY=pane.getTranslateY()-(bescaleValue-scaleValue)*pane.getHeight()/2;
                pane.setTranslateX(moveX);
                pane.setTranslateY(moveY);
                pane.setScaleX(scaleValue);
                pane.setScaleY(scaleValue);
                event1.consume();
            }
        });

        // ctrl+鼠标拖动时更新位置
        mainpane.setOnMousePressed(event3 -> {
            xOffset = event3.getSceneX() - pane.getTranslateX();
            yOffset = event3.getSceneY() - pane.getTranslateY();
            event3.consume();
        });
        mainpane.setOnMouseDragged(event3 -> {
            if(event3.isControlDown()) {
                pane.setTranslateX(event3.getSceneX() - xOffset);
                pane.setTranslateY(event3.getSceneY() - yOffset);
                event3.consume();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanesControllers.put(this.getClass().getSimpleName(),anchorPane);
        borderPaneControllers.put(this.getClass().getSimpleName(),borderPane);

        switch (Main.level) {
            case "gh":
                registration.setDisable(false);
                charge.setDisable(false);
                hospitalize.setDisable(false);
                break;
            case "kz":
                diagnose.setDisable(false);
                break;
            case "yf":
                pharmacy.setDisable(false);
                break;
            case "admin":
                registration.setDisable(false);
                diagnose.setDisable(false);
                pharmacy.setDisable(false);
                charge.setDisable(false);
                hospitalize.setDisable(false);
                break;
            default:
                break;
        }


        if(KanZhen.isCharge == false){
            registration.setDisable(true);
            diagnose.setDisable(true);
            pharmacy.setDisable(true);
        }

        if (DoctorLogin.isLogin == true) {
            doctorMenu.setDisable(false);
            patientSearch.setDisable(false);
            doctorLogin.setDisable(true);
        }

        pharmacyDrugOutput.setDisable(false);
        pharmacyDrugInput.setDisable(true);
        pharmacySearch.setDisable(true);

        if (PharmacyController.isInformation) {
            pharmacyDrugInput.setDisable(false);
        }
        if (PharmacyController.isInto) {
            pharmacySearch.setDisable(false);
        }

        Tools.buttonHighlight(registrationScene);
        Tools.buttonHighlight(patientMessageCheck);
        Tools.buttonHighlight(patientMessageEdit);
        Tools.buttonHighlight(patientMessageDelete);

        Tools.buttonHighlight(doctorLogin);
        Tools.buttonHighlight(doctorRegister);
        Tools.buttonHighlight(doctorList);
        Tools.buttonHighlight(doctorMenu);
        Tools.buttonHighlight(patientSearch);

        Tools.buttonHighlight(pharmacyDrugInput);
        Tools.buttonHighlight(pharmacyDrugOutput);
        Tools.buttonHighlight(pharmacySearch);

        Tools.buttonHighlight(charging);

        Tools.buttonHighlight(hospitalizeInfomation);

        Tools.buttonHighlight(exit);
        Tools.buttonHighlight(change);

        paneVariation(anchorPane, borderPane);



    }
}

