package com.his.controller;

import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientInformation implements Initializable {

        @FXML
        private Label uage;

        @FXML
        private Label ubalance;

        @FXML
        private TextField ubingli;

        @FXML
        private Label ubrithday;

        @FXML
        private Label udate;

        @FXML
        private Label udeptname;

        @FXML
        private Label udoctorname;

        @FXML
        private Label udrugInformation;

        @FXML
        private Label udrugPrice;

        @FXML
        private Label ugender;

        @FXML
        private Label uhomeadress;

        @FXML
        private Label ulevel;

        @FXML
        private Label urealname;

        @FXML
        private Label uresult;

        @FXML
        private Button usearch;

        @FXML
        private Label usfzh;

        @FXML
        private Button fh;

        List<Patient> patients=new ArrayList<>();
    public void search(){
        patients= Tools.findAllPatient();
        String st=ubingli.getText();
        boolean flag= false;
        for(int i=0;i<patients.size();i++){
            if((st.equals(patients.get(i).getId()))&&(patients.get(i).isIskz())&&(patients.get(i).isIsyf())){
                flag =true;
                urealname.setText(patients.get(i).getRealname());
                ugender.setText(patients.get(i).getGender());
                usfzh.setText(patients.get(i).getCardnumber());
                uage.setText(patients.get(i).getAge()+"");
                ubalance.setText(patients.get(i).getBalance()+"");
                ubrithday.setText(patients.get(i).getBirthdate());
                udate.setText(patients.get(i).getRegistdate());
                udeptname.setText(patients.get(i).getRegistdate());
                ulevel.setText(patients.get(i).getRegistlevel()+"");
                udoctorname.setText(patients.get(i).getDoctorname());
                udrugInformation.setText(patients.get(i).getDrugInformation());
                udrugPrice.setText(patients.get(i).getDrugPrice()+"");
                uhomeadress.setText(patients.get(i).getHomeaddress());
                uresult.setText(patients.get(i).getDiagiosis());
                break;
            }
            else{
                flag=false;
            }
        }
        if(!flag){
            Tools.showAlert(null,null,"请先挂号，看诊，开药");
        }

    }
    public void fanhui(){
        fh.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tools.buttonHighlight(usearch);
        Tools.buttonHighlight(fh);
    }
}
