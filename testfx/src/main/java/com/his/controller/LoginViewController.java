package com.his.controller;


import com.his.Main;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    /**
     * 退出按钮
     */
    @FXML
    private Button exit;
    /**
     * 用户名输入窗口
     */
    @FXML
    private TextField unserInput;
    /**
     * 登录按钮
     */
    @FXML
    private Button login;
    /**
     * 登录按钮
     */
    @FXML
    private Button loginButton;
    /**
     * 密码输入窗口
     */
    @FXML
    private PasswordField password;
    /**
     * 我是患者按钮
     */
    @FXML
    private Button imPatient;
    /**
     * 医院地址按钮
     */
    @FXML
    private Button location;

    @FXML
    private AnchorPane logingPlatform;

    private Stage stage=new Stage();

    /**
     * 点击退出后退出程序
     *
     * @param event
     */
    @FXML
    void clikExit(ActionEvent event) {
        System.out.println("退出系统！");
        System.exit(0);
    }

    public Button getExit() {
        return exit;
    }

    public TextField getUnserInput() {
        return unserInput;
    }

    public Button getLogin() {
        return login;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setExit(Button exit) {
        this.exit = exit;
    }

    public void setUnserInput(TextField unserInput) {
        this.unserInput = unserInput;
    }

    public void setLogin(Button login) {
        this.login = login;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    /**
     * 登录按钮操作
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void login(ActionEvent event) throws IOException {
        String user = unserInput.getText();
        /**
         * 没有输入用户名
         */
        if (user.equals("")) {
            Tools.showAlert(null, null, "请输入用户名!");
        } else {
            System.out.println(user);
            String inputPassword = password.getText();
            /**
             * 没有输入密码
             */
            if (inputPassword.equals("")) {
                Tools.showAlert(null, null, "请输入密码!");
            } else {
                System.out.println(inputPassword);
                if (user.equals("gh") && inputPassword.equals("123")) {
                    /**
                     * 挂号医生账号
                     */
                    Main.level = "gh";
                    System.out.println("登录成功！");
                    Tools.stageCreate(Main.primaryStage,"pageTransform.fxml","主界面");
                    loginButton.getScene().getWindow().hide();
                } else if (user.equals("kz") && inputPassword.equals("123")) {
                    /***
                     * 看诊医生账号
                     */
                    Main.level = "kz";
                    System.out.println("登录成功！");
                    Tools.stageCreate(Main.primaryStage,"pageTransform.fxml","主界面");
                    loginButton.getScene().getWindow().hide();
                } else if (user.equals("yf") && inputPassword.equals("123")) {
                    /**
                     * 药房医生账号
                     */
                    Main.level = "yf";
                    System.out.println("登录成功！");
                    Tools.stageCreate(Main.primaryStage,"pageTransform.fxml","主界面");
                    loginButton.getScene().getWindow().hide();
                } else if (user.equals("admin") && inputPassword.equals("123")) {
                    /**
                     * 管理员账号
                     */
                    Main.level = "admin";
                    System.out.println("登录成功！");
                    Tools.stageCreate(Main.primaryStage,"pageTransform.fxml","主界面");
                    loginButton.getScene().getWindow().hide();
                } else {
                    /**
                     * 用户名或密码错误
                     */
                    Tools.showAlert(null, null, "用户名或密码错误");
                }
            }
        }
    }


    @FXML
    void imPatient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("PatientInformation.fxml");
        loader.setLocation(url);
        AnchorPane loginPane = loader.load();
        Scene scene = new Scene(loginPane);
        Stage stage = new Stage();
        stage.setTitle("查看单个患者信息");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void userInput(InputMethodEvent event) {

    }

    @FXML
    void userNameInput(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setCursor(Cursor.HAND);
                exit.setCursor(Cursor.HAND);
                imPatient.setCursor(Cursor.HAND);
            }
        });
        exit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exit.setCursor(Cursor.HAND);
            }
        });
        imPatient.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imPatient.setCursor(Cursor.HAND);
            }
        });
    }

    public void getLocation() throws IOException {
//        Tools.stageCreate(stage,"Location.fxml","医院位置");
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("Location.fxml");
        loader.setLocation(url);
        AnchorPane loginPane = loader.load();
        Scene scene = new Scene(loginPane);
        //Stage stage = new Stage();
        stage.setTitle("医院位置");
        stage.setScene(scene);
        stage.show();
    }
}