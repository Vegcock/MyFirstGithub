package com.his.controller;


import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class ChargeMenu implements Initializable {
    /**
     * 指定患者文件
     */
    private static File kzFile = Tools.kzFile;
    /**
     * 用一个静态Patient类保存查到的患者
     */
    private static Patient patient = null;
    /**
     * 用一个静态List<Patient>保存从文件中获取的全部患者信息
     */
    private static List<Patient> all = null;
    /**
     * 年龄Label
     */
    @FXML
    private Label ageLabel;
    /**
     * 余额Label
     */
    @FXML
    private Label balanceLabel;
    /**
     * 身份证号Label
     */
    @FXML
    private Label cardNumberLabel;
    /**
     * 充值按钮
     */
    @FXML
    private Button chargeButton;
    /**
     * 输入充值金额窗口
     */
    @FXML
    private TextField chargeTextField;
    /**
     * 查询按钮
     */
    @FXML
    private Button checkButton;
    /**
     * 性别Label
     */
    @FXML
    private Label genderLabel;
    /***
     * 姓名Label
     */
    @FXML
    private Label nameLabel;
    /**
     * 输入姓名窗口
     */
    @FXML
    private TextField nameTextField;
    /**
     * 费用Label
     */
    @FXML
    private Label feeLabel;

    /**
     * 点击充值按钮
     *
     * @param event
     */
    @FXML
    void chargeClick(ActionEvent event) {
        if (patient == null) {
            //没有查询患者信息就充值
            Tools.showAlert(null, null, "请先查询患者信息！");
        } else {
            String strBalance = chargeTextField.getText();
            try {
                double chargeBalance = Double.parseDouble(strBalance);
                if (chargeBalance <= 0) {
                    //充值的金额不是正数
                    Tools.showAlert(null, null, "请充值一个正数！");
                } else if (chargeBalance + patient.getBalance() < 0) {
                    //首次充值金额小于挂号费
                    Tools.showAlert(null, null, "首次充值金额请大于挂号费！");
                } else {
                    //成功操作
                    patient.setBalance(patient.getBalance() + chargeBalance);
                    Tools.kzSave(all);
                    Tools.showAlert(null, "充值成功！", "您的余额是："+patient.getBalance());
                    System.out.println("充值成功！");
                    KanZhen.isCharge = true;
                    BorderPane borderPane = PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
                    Tools.pageReload(borderPane);
                    AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
                    Tools.transformPage(anchorPane,"ChargeMenu.fxml");
                }
            } catch (NumberFormatException e) {
                //没有输入充值金额
                Tools.showAlert(null, null, "请输入充值金额！");
            }
        }
    }

    /**
     * 点击查询按钮
     *
     * @param event
     */
    @FXML
    void check(ActionEvent event) {
        String id = nameTextField.getText();
        System.out.println("CardNumber: " + id);
        if ("".equals(id)) {
            //没有输入身份证号
            Tools.showAlert(null, null, "请输入患者病例号！");
        } else {
            //查找患者信息并且显示
            int age = 0;
            String gender = null;
            String name = null;
            double balance = 0.0;
            String cardNumber = null;
            double fee = 0.0;
            all = Tools.findAllPatient();
            Patient targetPatient = null;
            targetPatient = Tools.getPatient(id, all);
            if (targetPatient == null) {
                Tools.showAlert(null, null, "未找到目标患者！");
                return;
            }
            patient = targetPatient;

            name = targetPatient.getRealname();
            age = targetPatient.getAge();
            gender = targetPatient.getGender();
            balance = targetPatient.getBalance();
            cardNumber = targetPatient.getCardnumber();
            fee = targetPatient.getRegistFee();

            System.out.println(name);
            System.out.println(age);
            System.out.println(gender);
            System.out.println(cardNumber);
            System.out.println(balance);

            nameLabel.setText(name);
            nameLabel.setVisible(true);
            ageLabel.setText("" + age);
            ageLabel.setVisible(true);
            genderLabel.setText(gender);
            genderLabel.setVisible(true);
            cardNumberLabel.setText(cardNumber);
            cardNumberLabel.setVisible(true);
            balanceLabel.setText("" + balance);
            balanceLabel.setVisible(true);
            feeLabel.setText(""+fee);
            feeLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置鼠标经过按钮改变鼠标样式
        checkButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkButton.setCursor(Cursor.HAND);
            }
        });
        chargeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chargeButton.setCursor(Cursor.HAND);
            }
        });
        nameLabel.setVisible(false);
        ageLabel.setVisible(false);
        genderLabel.setVisible(false);
        cardNumberLabel.setVisible(false);
        balanceLabel.setVisible(false);
        feeLabel.setVisible(false);

        Tools.buttonHighlight(checkButton);
        Tools.buttonHighlight(chargeButton);

    }
}
