package com.his.controller;
//since 2024/6/20 by neu.CZ


import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class EditPatient implements Initializable {
    private static String id;
    private static boolean isChange = false;
    @FXML
    private TextField addressText;

    @FXML
    private TextField ageText;

    @FXML
    private TextField cardNumberText;

    @FXML
    private TextField genderText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    @FXML
    private TextField birthDateText;

    @FXML
    private Button confirmButton;

    @FXML
    private Button genderButton;

    @FXML
    private Button ageButton;

    @FXML
    private Button nameButton;

    @FXML
    private Button cardNumberButton;

    @FXML
    private Button birthdayDateButton;

    @FXML
    private Button addressButton;

    /**
     * 确认点击事件
     * @param event
     */
    @FXML
    void confirm(ActionEvent event) {
        //当做出改变时，提示并设置成不可改变
        if (isChange) {
            Tools.showAlert(null, null, "修改成功！");
            isChange = false;
            nameText.setEditable(false);
            ageText.setEditable(false);
            genderText.setEditable(false);
            addressText.setEditable(false);
            cardNumberText.setEditable(false);
            birthDateText.setEditable(false);
        } else {
            Tools.showAlert(null, null, "请先进行修改！");
        }

    }

    /**
     * 查询点击事件
     * @param event
     */
    @FXML
    void searchAction(ActionEvent event) {
        String name = null;
        String age = null;
        String gender = null;
        String targetId = search.getText();
        List<Patient> patients = Tools.findAllPatient();
        Patient targetPatient = new Patient();

        //冒泡排序patients
        try {
            patients = Tools.sortPatients(patients);
            Tools.kzSave(patients);
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "当前没有任何患者！");
            return;
        }


        //二分查找目标patient
        patients = Tools.findAllPatient();
        try {
            targetPatient = Tools.getPatient(targetId, patients);
        } catch (NumberFormatException e) {
            Tools.showAlert(null, null, "请输入患者病例号！");
            return;
        }

        if (targetPatient == null) {
            Tools.showAlert(null, null, "未找到目标患者！");
            return;
        }
        //获取查找到的信息并显示
        id = targetPatient.getId();
        name = targetPatient.getRealname();
        age = targetPatient.getAge()+"";
        gender = targetPatient.getGender();
        nameText.setText(name);
        ageText.setText(age);
        genderText.setText(gender);
        birthDateText.setText(targetPatient.getBirthdate());
        addressText.setText(targetPatient.getHomeaddress());
        cardNumberText.setText(targetPatient.getCardnumber());

        //对每个TextField进行监听，如果值被修改则更新患者信息，下同
        ageText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (ageText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        boolean flag = false;
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                try {
                                    patients.get(i).setAge((Integer.parseInt(ageText.getText())));
                                } catch (NumberFormatException e) {
                                    Tools.showAlert(null, null, "请输入合法的年龄！");
                                    return;
                                }
                                Tools.kzSave(patients);
                                flag = true;
                                isChange = true;
                                break;
                            }
                        }
                        if (!flag) {
                            System.out.print("未找到 ");
                            System.out.println("id:"+id);
                        }
                    }
                }
            }
        });
        nameText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (nameText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        boolean flag = false;
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                String newName = nameText.getText();
                                //如果名字是两个字则增加两个空格以对齐
                                if (newName.length() == 2) {
                                    newName = newName.charAt(0) + "  " + newName.charAt(1);
                                }
                                patients.get(i).setRealname(newName);
                                flag = true;
                                Tools.kzSave(patients);
                                isChange = true;
                                break;
                            }
                        }
                        if (!flag) {
                            System.out.println("未找到");
                        }
                    }
                }
            }
        });
        genderText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (genderText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                String newGender = genderText.getText();
                                if (!("男".equals(newGender) || "女".equals(newGender) || "未知".equals(newGender) || "不存在".equals(newGender))) {
                                    Tools.showAlert(null, "请输入以下四个选择!", "男/女/未知/不存在");
                                    return;
                                } else {
                                    patients.get(i).setGender(newGender);
                                    Tools.kzSave(patients);
                                    isChange = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
        birthDateText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (birthDateText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                patients.get(i).setBirthdate(birthDateText.getText());
                                Tools.kzSave(patients);
                                isChange = true;
                                break;
                            }
                        }
                    }
                }
            }
        });
        cardNumberText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cardNumberText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                String cardInput = cardNumberText.getText();
                                try {
                                    int digitCard = Integer.parseInt(cardInput);
                                } catch (NumberFormatException e) {
                                    Tools.showAlert(null, null, "请输入合法的身份证号！");
                                    return;
                                }
                                patients.get(i).setCardnumber(cardInput);
                                Tools.kzSave(patients);
                                isChange = true;
                                break;
                            }
                        }
                    }
                }
            }
        });
        addressText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (addressText.isEditable() == true) {
                    if (oldValue && !newValue) {
                        List<Patient> patients = Tools.findAllPatient();
                        for (int i = 0; i < patients.size(); i++) {
                            if (patients.get(i).getId().equals(id)) {
                                patients.get(i).setHomeaddress(addressText.getText());
                                Tools.kzSave(patients);
//                                Tools.showAlert(null, null, "家庭地址修改成功！");
                                isChange = true;
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @FXML
    void ageEdit(ActionEvent event) {
        ageText.setEditable(true);
    }

    @FXML
    void genderEdit(ActionEvent event) {
        genderText.setEditable(true);
    }

    @FXML
    void nameEdit(ActionEvent event) {
        nameText.setEditable(true);
    }

    @FXML
    void birthDateEdit(ActionEvent event) {
        birthDateText.setEditable(true);
    }

    @FXML
    void cardNumberEdit(ActionEvent event) {
        cardNumberText.setEditable(true);
    }

    @FXML
    void addressEdit(ActionEvent event) {
        addressText.setEditable(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化设置全部不可修改
        ageText.setEditable(false);
        genderText.setEditable(false);
        nameText.setEditable(false);
        birthDateText.setEditable(false);
        cardNumberText.setEditable(false);
        addressText.setEditable(false);

        Tools.buttonHighlight(searchButton);
        Tools.buttonHighlight(confirmButton);
        Tools.buttonHighlight(addressButton);
        Tools.buttonHighlight(ageButton);
        Tools.buttonHighlight(nameButton);
        Tools.buttonHighlight(genderButton);
        Tools.buttonHighlight(birthdayDateButton);
        Tools.buttonHighlight(cardNumberButton);
    }
}
