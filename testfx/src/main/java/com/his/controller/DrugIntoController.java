package com.his.controller;


import com.his.entity.Drug;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DrugIntoController implements Initializable {

    @FXML
    private TextField utext;

    @FXML
    private ChoiceBox<String>udrug;

    @FXML
    private Button but;

    @FXML
    private AnchorPane up;



    public static List<Drug>drugs;
    public static File drugFile=new File("drugFile.txt");
    private String []drug={"镇痛药","抗生素","抗过敏药","阿司匹林","青霉素","头孢"};
    private int inClickCount=0;
    private String getDrugChoice(ActionEvent actionEvent) {
        String myDrugChoice = udrug.getValue();
        return myDrugChoice;
    }

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
        up.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y =event.getY();
//               System.out.println("相对于控件的x：y="+x+":"+y);
                if(x>500&&x<548&&y>407&&y<430){
                    inClickCount++;
                }
                if(inClickCount>0){
                    Tools.showAlert(null,null, PharmacyController.in);
                }
            }
        });

        //药品选择初始化
        udrug.getItems().addAll(drug);
        udrug.getSelectionModel().select(0);
        udrug.setOnAction(this::getDrugChoice);

        Tools.buttonHighlight(but);
    }
    public void show1() throws IOException {
        drugs=Tools.findAllDrug();
        boolean flag=false;
        for(int i=0;i<drugs.size();i++){
            if(udrug.getValue().equals(drugs.get(i).getName().replace(" ", ""))){
                if(Integer.parseInt(utext.getText())>=0){
                    drugs.get(i).upnum(Integer.parseInt(utext.getText()));
                    flag=true;
                }
                else{
                    Tools.showAlert(null,null,"请输入大于零的数");
                    flag=false;
                }

            }
        }
        if(flag){
            Tools.showAlert(null,null,"入库成功");
//            but.getScene().getWindow().hide();
        }
        else{
            Tools.showAlert(null,null,"入库失败");
//            but.getScene().getWindow().hide();
        }
        Tools.drugSave(drugs);
    }
}
