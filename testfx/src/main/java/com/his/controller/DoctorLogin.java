package com.his.controller;

import com.his.Main;
import com.his.controller.PageTransform;
import com.his.entity.Doctor;
import com.his.fileio.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sun.util.resources.cldr.lo.CalendarData_lo_LA;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wzh
 */
public class DoctorLogin implements Initializable {

    /**
     * 传入的stage属性
     */
    public static Stage stage = new Stage();
    /**
     * 根据level属性看是否为非法访问
     */
    public static boolean isLegal = true;
    /**
     * 账号密码的储存
     */
    public static String count;
    public static String password;
    /**
     * 判断在运行界面前是否进行登录
     */
    public static boolean isLogin = false;

    @FXML
    private TextField countText;

    @FXML
    private TextField doctorPasswordText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button buttonReturn;

    @FXML
    private Button returnLogin;

    /**
     * 画布
     */
    @FXML
    private AnchorPane anchorpane;

    public TextField getCountText() {
        return countText;
    }

    public void setCountText(TextField countText) {
        this.countText = countText;
    }

    public TextField getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(PasswordField passwordText) {
        this.passwordText = passwordText;
    }

    /**
     * 退出按钮
     */
    public void returnButton(){
        returnLogin.getScene().getWindow().hide();
    }

    /**
     * 登录，放在登录按钮点击事件
     */
    public void loginEvent(){
        try {
            /**
             * 记录账号密码
             */
            count = countText.getText();
            if (("").equals(count)) {
                Tools.showAlert(null, null, "请输入账号！");
            } else {
                password = passwordText.getText();
                if (("").equals(password)) {
                    Tools.showAlert(null, null, "请输入密码！");
                } else {
                    List<Doctor> doctors = new ArrayList<>();
                    doctors = Tools.findAll(Doctor.class,"dcFile.txt");
                    for (int i = 0; i <doctors.size() ; i++) {
                        if(doctors.get(i).getConut().equals(count) && doctors.get(i).getPassword().equals(password)) {
                            //登录成功,状态更新
                            isLogin = true;
                            Tools.showAlert(null,null,"登录成功");
                            BorderPane borderPane = PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
                            Tools.pageReload(borderPane);
                        }
                    }
                    if(isLogin == false){
                        Tools.showAlert(null,null,"用户名或密码错误");
                    }
                }
            }
        }catch (NullPointerException | IOException e){
            Tools.showAlert(null,null,"请输入完整信息！");
            e.printStackTrace();
        }
    }
    /**
     * 账号登录
     * @throws IOException
     */
    public void login() throws IOException {
       loginEvent();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * passwordfield可视化组件
         */
        Button isVisible = new Button();
        Image eyeOpen = new Image("@../../images/show.png" );
        Image eyeClose = new Image("@../../images/unshow.png");
        ImageView image = new ImageView(eyeClose);
        isVisible.setGraphic(image);
        isVisible.setLayoutX(doctorPasswordText.getLayoutX() + 156);
        isVisible.setLayoutY(doctorPasswordText.getLayoutY() );
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
         * 按钮被选中显示
         */
        Tools.buttonHighlight(buttonReturn);
        Tools.buttonHighlight(returnLogin);

    }
}
