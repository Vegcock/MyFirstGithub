package com.his.controller;

import com.his.controller.PageTransform;
import com.his.entity.Doctor;
import com.his.fileio.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wzh
 */
public class DoctorList implements Initializable {

    public static Stage stage = new Stage();

    public static boolean isPressed = false;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button buttonRegister;

    @FXML
    private ListView<HBoxCell> doctorList;

    /**
     * 新医生注册页面
     *
     * @param event
     */
    @FXML
    void registerButton(ActionEvent event) {
        BorderPane borderPane = PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
        Tools.pageReload(borderPane);
        AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
        Tools.transformPage(anchorPane,"doctorRegister.fxml");
    }


    /**
     * 列表子项
     */
    public static class HBoxCell extends HBox {
        TextField idText = new TextField();
        TextField nameText = new TextField();
        TextField levelText = new TextField();
        TextField roomText = new TextField();
        Button editButton = new Button();
        Button deleteButton = new Button();

        /**
         * 传入四个展示的医生属性
         * 传入两个按钮名字
         * @param id
         * @param name
         * @param level
         * @param room
         * @param buttonText1
         * @param buttonText2
         */
        HBoxCell(String id,String name,String level,String room, String buttonText1,String buttonText2) {
            super();

            idText.setText(id);
            idText.setEditable(false);
            idText.setMaxWidth(50);
            idText.setBackground(null);
            idText.setBorder(null);
            nameText.setText(name);
            nameText.setEditable(false);
            nameText.setMaxWidth(50);
            levelText.setText(level);
            levelText.setEditable(false);
            levelText.setMaxWidth(50);
            roomText.setText(room);
            roomText.setEditable(false);
            roomText.setMaxWidth(50);
            editButton.setBorder(null);
            deleteButton.setBorder(null);
            editButton.setBackground(null);
            deleteButton.setBackground(null);
            editButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    editButton.setUnderline(true);
                    setCursor(Cursor.HAND);
                }
            });
            editButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    editButton.setUnderline(false);
                    setCursor(Cursor.DEFAULT);
                }
            });
            deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    deleteButton.setUnderline(true);
                    setCursor(Cursor.HAND);
                }
            });
            deleteButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    deleteButton.setUnderline(false);
                    setCursor(Cursor.DEFAULT);
                }
            });


            HBox.setMargin(idText, new Insets(10, 10, 10, 10));
            HBox.setMargin(nameText, new Insets(10, 10, 10, 10));
            HBox.setMargin(levelText, new Insets(10, 10, 10, 10));
            HBox.setMargin(roomText, new Insets(10, 10, 10, 10));
            HBox.setMargin(editButton, new Insets(10, 10, 10, 250));
            HBox.setMargin(deleteButton, new Insets(10, 10, 10, 10));

            editButton.setText(buttonText1);
            deleteButton.setText(buttonText2);

            this.getChildren().addAll(idText,nameText,levelText,roomText, editButton,deleteButton);

            /**
             * 设置按钮点击事件，若要编辑信息则将textfeild的可编辑打开
             *                若要删除信息则从doctors这一list中删除后保存到文件
             */
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    nameText.setEditable(true);
                    levelText.setEditable(true);
                    roomText.setEditable(true);
                }

            });
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        List<Doctor> doctors = Tools.findAll(Doctor.class,"dcFile.txt");
                        for (int i = 0; i <doctors.size() ; i++) {
                            if(doctors.get(i).getId().equals(id)){
                                doctors.remove(i);
                            }
                        }
                        Tools.idInitialize();
                        Tools.saveResult(doctors,Doctor.class,"dcFile.txt");
                        Tools.showAlert(null,null,"删除成功");
                        AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
                        Tools.transformPage(anchorPane,"doctorList.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            /**
             * 监控textfield是否发生改变，若改变则更改文件相应内容
             */
            try {
                List<Doctor> doctors = Tools.findAll(Doctor.class,"dcFile.txt");
                nameText.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (nameText.isEditable() == true) {
                            if (oldValue && !newValue) {
                                for (int i = 0; i <doctors.size(); i++) {
                                    if(doctors.get(i).getId().equals(id)){
                                        try {
                                            doctors.get(i).setRealName(nameText.getText());
                                            Tools.saveResult(doctors,Doctor.class,"dcFile.txt");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                Tools.showAlert(null,null,"姓名修改成功!");
                                nameText.setEditable(false);
                            }
                        }
                    }
                });
                levelText.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (levelText.isEditable() == true) {
                            if (oldValue && !newValue) {
                                for (int i = 0; i <doctors.size(); i++) {
                                    if(doctors.get(i).getId().equals(id)){
                                        doctors.get(i).setGrade(levelText.getText());
                                        try {
                                            Tools.saveResult(doctors,Doctor.class,"dcFile.txt");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                Tools.showAlert(null,null,"级别修改成功!");
                                levelText.setEditable(false);
                            }
                        }
                    }
                });
                roomText.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (roomText.isEditable() == true) {
                            if (oldValue && !newValue) {
                                for (int i = 0; i <doctors.size(); i++) {
                                    if(doctors.get(i).getId().equals(id)){
                                        doctors.get(i).setRoom(roomText.getText());
                                        try {
                                            Tools.saveResult(doctors,Doctor.class,"dcFile.txt");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                Tools.showAlert(null,null,"科室修改成功!");
                                roomText.setEditable(false);
                            }
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tools.idInitialize();
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = Tools.findAll(Doctor.class, "dcFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<HBoxCell> list = new ArrayList<>();
        for (int i = 0; i < doctors.size(); i++) {
            String id = doctors.get(i).getId();
            String nameText = doctors.get(i).getRealName();
            String levelText = doctors.get(i).getGrade();
            String roomText = doctors.get(i).getRoom();
            list.add(new HBoxCell(id,nameText,levelText,roomText, "编辑 ","删除"));
        }
        /**
         * 在listview上展示hbox内容
         */
        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(list);
        doctorList.setItems(myObservableList);

        Tools.buttonHighlight(buttonRegister);

    }

}
