package com.his.controller;


import com.his.entity.Drug;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PharmacyController implements Initializable {
    @FXML
    private PasswordField sfzh;

    @FXML
    private Label uage;

    @FXML
    private Label ugender;

    @FXML
    private Label uiskz;

    @FXML
    private Label uname;

    @FXML
    private Label udrug;

    @FXML
    private Label uprice;

    @FXML
    private Label uadv;

    @FXML
    private ChoiceBox<String> drugChoiceBox;

    @FXML
    private Label udrugchoice;

    @FXML
    private Label ubalance;

    @FXML
    private Button fh;

    @FXML
    private Button bt1;

    @FXML
    private Button bt2;

    public static boolean isInformation=false;

    public static Stage stage1=new Stage();

    public static Stage stage2=new Stage();

    public static boolean isInto=false;

    public PasswordField getSfzh() {
        return sfzh;
    }

    public void setSfzh(PasswordField sfzh) {
        this.sfzh = sfzh;
    }

    public Label getUage() {
        return uage;
    }

    public void setUage(Label uage) {
        this.uage = uage;
    }

    public Label getUgender() {
        return ugender;
    }

    public void setUgender(Label ugender) {
        this.ugender = ugender;
    }

    public Label getUiskz() {
        return uiskz;
    }

    public void setUiskz(Label uiskz) {
        this.uiskz = uiskz;
    }

    public Label getUname() {
        return uname;
    }

    public void setUname(Label uname) {
        this.uname = uname;
    }

    public Label getUdrug() {
        return udrug;
    }

    public void setUdrug(Label udrug) {
        this.udrug = udrug;
    }

    public Label getUprice() {
        return uprice;
    }

    public void setUprice(Label uprice) {
        this.uprice = uprice;
    }

    public Label getUadv() {
        return uadv;
    }

    public void setUadv(Label uadv) {
        this.uadv = uadv;
    }

    public ChoiceBox<String> getDrugChoiceBox() {
        return drugChoiceBox;
    }

    public void setDrugChoiceBox(ChoiceBox<String> drugChoiceBox) {
        this.drugChoiceBox = drugChoiceBox;
    }

    public Label getUdrugchoice() {
        return udrugchoice;
    }

    public void setUdrugchoice(Label udrugchoice) {
        this.udrugchoice = udrugchoice;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static File getDrugFile() {
        return drugFile;
    }

    public static void setDrugFile(File drugFile) {
        PharmacyController.drugFile = drugFile;
    }

    public String[] getDrug() {
        return drug;
    }

    public void setDrug(String[] drug) {
        this.drug = drug;
    }

    private List<Patient>patients;
    private List<Drug>drugs;
    private double price;
    private String s;
    private String information;
    private double totalprice;
    public static String in;
    /**
     * 点击按钮显示信息
     */
    public void show1(){
        patients= Tools.findAllPatient();
        String usfzh=sfzh.getText();
        boolean flag=false;
        for(int i=0;i<patients.size();i++){
            if(usfzh.equals(patients.get(i).getId())){
                flag=true;
                uname.setText(patients.get(i).getRealname());
                uage.setText(patients.get(i).getAge()+"");
                ugender.setText(patients.get(i).getGender());
                uiskz.setText(patients.get(i).isIskz()+"");
                uadv.setText(patients.get(i).getDiagiosis());
                ubalance.setText(patients.get(i).getBalance()+"");
            }
        }
        if(!flag){
            Tools.showAlert("药房","错误","找不到用户");
        }
    }



    public static File drugFile=new File("drugFile.txt");
    private String []drug={"镇痛药","抗生素","抗过敏药","阿司匹林","青霉素","头孢"};
    private String getDrugChoice(ActionEvent actionEvent) {
        String myDrugChoice = drugChoiceBox.getValue();
        return myDrugChoice;
    }

    /**
     * 初始化choicebox
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!drugFile.exists()) {
            //系统第一次运行，或者，数据被人破坏
            try {
                drugFile.createNewFile();
                List<Drug> drugs = new ArrayList<Drug>();
                Tools.drugSave(drugs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(isInto == true){
            bt1.setDisable(true);
        }
        if(isInformation==true){
            bt2.setDisable(true);
        }

        //药品选择初始化
        drugChoiceBox.getItems().addAll(drug);
        drugChoiceBox.getSelectionModel().select(0);
        drugChoiceBox.setOnAction(this::getDrugChoice);
    }

    /**
     * 医生开药
     */
    public void show3(){
        drugs=Tools.findAllDrug();
        for(int i=0;i<drugs.size();i++){
            if(drugChoiceBox.getValue().equals(drugs.get(i).getName().replace(" ", ""))){
                udrugchoice.setText(drugChoiceBox.getValue()+" "+"剩余药品数："+drugs.get(i).getNum());
                price =drugs.get(i).getPrice();
                uprice.setText(drugs.get(i).getPrice()+"");
                drugs.get(i).setNum(drugs.get(i).getNum());
                Tools.drugSave(drugs);
            }
        }
    }

    /**
     * 药品添加
     * @throws IOException
     */
    public void show4() throws IOException {
//        FXMLLoader loader =new FXMLLoader();
//        URL url= App.class.getResource("DrugInto.fxml");
//        loader.setLocation(url);
//        AnchorPane loginPane=loader.load();
//        Scene scene=new Scene(loginPane);
//        Stage thirdStage=new Stage();
//        thirdStage.setTitle("药品入库");
//        thirdStage.setScene(scene);
//        thirdStage.show();
        isInformation=true;
        BorderPane borderPane = PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
        Tools.pageReload(borderPane);
        bt1.setDisable(true);
        AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
        Tools.transformPage(anchorPane,"Pharmacy.fxml");

    }

    /**
     * 药品查询
     * @throws IOException
     */
    public void show5() throws IOException {
//        FXMLLoader loader =new FXMLLoader();
//        URL url= App.class.getResource("DrugInformation.fxml");
//        loader.setLocation(url);
//        AnchorPane loginPane=loader.load();
//        Scene scene=new Scene(loginPane);
//        Stage forthStage=new Stage();
//        forthStage.setTitle("药品信息");
//        forthStage.setScene(scene);
//        forthStage.show();
        isInto=true;
        BorderPane borderPane = PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
        Tools.pageReload(borderPane);
        AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
        Tools.transformPage(anchorPane,"Pharmacy.fxml");
    }
    /**
     * 点击一键出库
     */
    public void show2(){
        patients=Tools.findAllPatient();
        String usfzh=sfzh.getText();
        boolean flag=false;
        for(int i=0;i<patients.size();i++){
            if(patients.get(i).getId().equals(sfzh.getText())){
            if (patients.get(i).isIskz() == false) {
                Tools.showAlert(null, null, "你没看诊开集贸药呢");
                return;
            } else {
                if (usfzh.equals(patients.get(i).getId()) && patients.get(i).isIskz()) {
                    patients.get(i).setIsyf(true);
                    flag = true;
                    if (patients.get(i).getBalance() - price > 0) {

                    } else {
                        Tools.showAlert(null, null, "余额不足,请先缴费在取药");
                        flag = false;
                        return;
                    }
                }
            }
        }}
        drugs=Tools.findAllDrug();
        for(int i=0;i<drugs.size();i++){
            if(drugChoiceBox.getValue().equals(drugs.get(i).getName().replace(" ",""))){
                drugs.get(i).downnum(1);
                drugs.get(i).setNum(drugs.get(i).getNum());
                s=drugs.get(i).getName();
                totalprice+=drugs.get(i).getPrice();
                Tools.drugSave(drugs);
            }
        }
        if(flag){
            for(int i=0;i<patients.size();i++){
                if (usfzh.equals(patients.get(i).getId()) && patients.get(i).isIskz()) {
                    patients.get(i).setBalance(patients.get(i).getBalance() - price);
                    patients.get(i).setDrugInformation(s);
                    information=patients.get(i).getDrugInformation();
                    patients.get(i).setDrugprice(totalprice);
                }
            }
            in=information;
            Tools.showAlert(null,"出库成功",information);
            Tools.kzSave(patients);
        } else{
            Tools.showAlert(null,null,"出库失败");
        }
        show3();
        show1();
    }
//    public void fanhui(){
//        fh.getScene().getWindow().hide();
//    }
}
