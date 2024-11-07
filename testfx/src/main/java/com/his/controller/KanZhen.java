package com.his.controller;


import com.his.Main;
import com.his.entity.Doctor;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.print.Doc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author neu.cz
 */
public class KanZhen implements Initializable {
    public static boolean isCharge = true;
    /**
     * 指定患者信息文件
     */
    private static File kzFile = Tools.kzFile;
    /**
     * 地址输入窗口
     */
    @FXML
    private TextField addressInput;
    /**
     * 出生日期输入窗口
     */
    @FXML
    private DatePicker birthdayInput;
    /**
     * 年龄选择ChoiceBox
     */
    @FXML
    private ChoiceBox<Integer> choiceBox;
    /**
     * 身份证号输入窗口
     */
    @FXML
    private TextField idInput;
    /**
     * 姓名输入窗口
     */
    @FXML
    private TextField nameInput;
    /**
     * 性别选择ChoiceBox
     */
    @FXML
    private ChoiceBox<String> sexChoiceBox;
    /**
     * 提交按钮
     */
    @FXML
    private Button submit;
    /**
     * 看诊日期DatePicker
     */
    @FXML
    private DatePicker kzDate;
    /**
     * 查询已有患者信息按钮
     */
    @FXML
    private Button checkButton;
    /**
     * 返回按钮
     */
    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> registDept;

    @FXML
    private ComboBox<String> registDoctorName;

    @FXML
    private ComboBox<String> registLevel;

    @FXML
    private TextField ageInputText;

    /**
     * 年龄选项
     */
    private Integer[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    /**
     * 性别选项
     */
    private String[] sexChoice = {"男", "女", "未知", "不存在"};
    /**
     * 看诊科室选项
     */
    public static String[] roomChoice = {"门诊", "急诊", "太平间"};
    private ArrayList<String> registDeptChoice = new ArrayList<>();
    /**
     * 医生选项
     */
    private String[] doctorChoice = {"医生1", "医生2", "医生3"};
    private ArrayList<String> registDoctorChoice = new ArrayList<>();
    /**
     * 看诊级别选项
     */
    public static Integer[] levelChoice = {1, 2, 3};
    private ArrayList<String> registLevelChoice = new ArrayList<>();

    /**
     * 加载年龄选项
     *
     * @param actionEvent
     * @return
     */
    private Integer getId(ActionEvent actionEvent) {
        Integer myId = choiceBox.getValue();
        return myId;
    }

    /**
     * 病例号静态数组,最多存1000个病人
     * 每次初始化读取一次文件，将获得到的已经使用过的病例号存在ids里面
     * 新创建患者的时候判断哪一位是0，则用哪一位
     * 做到病例号不重复且可重复使用
     */
    public static int[] ids = new int[1000];

    /**
     * 加载性别选项
     *
     * @param actionEvent
     * @return
     */
    private String getSexChoice(ActionEvent actionEvent) {
        String mySexChoice = sexChoiceBox.getValue();
        return mySexChoice;
    }

    public ArrayList<String> getRegistDeptChoice() {
        return registDeptChoice;
    }

    public void setRegistDeptChoice(ArrayList<String> registDeptChoice) {
        this.registDeptChoice = registDeptChoice;
    }

    public ArrayList<String> getRegistDoctorChoice() {
        return registDoctorChoice;
    }

    public void setRegistDoctorChoice(ArrayList<String> registDoctorChoice) {
        this.registDoctorChoice = registDoctorChoice;
    }

    public ArrayList<String> getRegistLevelChoice() {
        return registLevelChoice;
    }

    public void setRegistLevelChoice(ArrayList<String> registLevelChoice) {
        this.registLevelChoice = registLevelChoice;
    }

    /**
     * 点击提交按钮
     *
     * @param event
     */
    @FXML
    void clickSubmit(ActionEvent event) {
        try {
            String name = nameInput.getText();
            //没有输入姓名
            if (name == null) {
                Tools.showAlert(null, null, "请输入姓名！");
                return;
            }

            //如果姓名只有两个名字，名字中间加上两个空格以对齐
            if (name.length() == 2) {
                name = "" + name.charAt(0) + "  " + name.charAt(1);
            }

            //获取输入的信息
            String address = addressInput.getText();
            LocalDate birthday = birthdayInput.getValue();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birthDate = birthday.format(fmt);
            Long digitalBirthDate = Long.parseLong(birthDate.replace("-", ""));
            Date localDate = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(localDate);
            System.out.println(date);
            long digitalDate = Long.parseLong(date.replace("-", ""));
            //出生日期是今天以后
            if (digitalBirthDate > digitalDate) {
                Tools.showAlert(null, null, "出生日期不能是今天以后！");
                return;
            }
            int age = 0;
            String sex = sexChoiceBox.getValue();
            try {
                age = Integer.parseInt(ageInputText.getText());
            } catch (NumberFormatException e) {
                Tools.showAlert(null, null, "请输入合法的年龄！");
                return;
            }
            String cardNumber = idInput.getText();
            try {
                Integer.parseInt(cardNumber);
            } catch (NumberFormatException e) {
                Tools.showAlert(null, null, "请输入合法的身份证号！");
                return;
            }
            String kzDoctor = registDoctorName.getValue();
            Integer kzLevel = Integer.parseInt(registLevel.getValue());
            //设置看诊费用。1：5块；2：10块；3：20块
            double kzFee = 0.0;
            if (kzLevel == 1) {
                kzFee = 5.0;
            } else if (kzLevel == 2) {
                kzFee = 10.0;
            } else {
                kzFee = 20.0;
            }
            String kzRoom = registDept.getValue();
            LocalDate kzLocalDate = this.kzDate.getValue();
            String kzDate = kzLocalDate.format(fmt);
            //看诊日期不是今天
            if (!date.equals(kzDate)) {
                Tools.showAlert(null, null, "选择当前日期作为看诊日期！");
                return;
            }
            //设置病例号
            String id = null;
            for (int i = 1; i < ids.length; i++) {
                if (ids[i] == 0) {
                    id = i + "";
                    ids[i] = i;
                    break;
                }
            }
            //debug
            System.out.println(name);
            System.out.println(address);
            System.out.println(birthDate);
            System.out.println(sex);
            System.out.println(age);
            System.out.println(cardNumber);
            System.out.println(kzDate);
            System.out.println(id);

            //创建新患者对象
            //String realname, String gender, String cardnumber, String birthdate, Integer age, String homeaddress, String deptname, String doctorname, String registlevel, Double registfee, String registdate, String diagiosis, String prescription, Double drugprice, boolean iskz, boolean isyf
            Patient patient = new Patient(name, sex, cardNumber, birthDate, age, address, kzRoom, kzDoctor, kzLevel, kzFee, kzDate, "", "", 0.0, false, false, 0.0 - kzFee, false, id, false,null);

            //将当前病人添加到挂号文件中
            try {
                addPatient(patient);
                Tools.showAlert("挂号成功", null, "您的病例号是：" + id);
                Tools.showAlert(null, null, "请前往充值窗口进行缴费。");
                isCharge = false;

                //跳转到充值窗口让患者交挂号费
                BorderPane borderPane= PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
                Tools.pageReload(borderPane);
                AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
                Tools.transformPage(anchorPane, "ChargeMenu.fxml");
            } catch (NullPointerException e) {
                List<Patient> patients = new ArrayList<>();
                Tools.kzSave(patients);


                Tools.showAlert("挂号成功", null, "您的病例号是：" + id);
                Tools.showAlert(null, null, "请前往充值窗口进行缴费。");

                //跳转到充值窗口让患者交挂号费
                BorderPane borderPane= PageTransform.borderPaneControllers.get(PageTransform.class.getSimpleName());
                Tools.pageReload(borderPane);
                AnchorPane anchorPane = PageTransform.anchorPanesControllers.get(PageTransform.class.getSimpleName());
                Tools.transformPage(anchorPane, "ChargeMenu.fxml");
            }finally {
                System.out.println(isCharge);
            }

            //隐藏当前窗口（相当于关闭）
//            submit.getScene().getWindow().hide();
        } catch (NullPointerException e) {
            //没有填写完整的患者信息就提交
            Tools.showAlert(null, null, "请填写完整患者信息！");
        }
    }

    /**
     * 点击查询当前患者信息按钮
     *
     * @param event
     */
    @FXML
    void check(ActionEvent event) {
        List<Patient> all = Tools.findAllPatient();
        try {
            if (all.isEmpty()) {
                //没有任何挂号信息
                Tools.showAlert(null, null, "当前没有患者！");
                return;
            }
            String output = "姓名\t身份证号\t年龄\n";
            for (int i = 0; i < all.size(); i++) {
                String str = null;
                output += "" + all.get(i).getRealname() + "\t" + all.get(i).getCardnumber() + "\t" + all.get(i).getAge() + "\n";
            }
            Tools.showAlert("现有数据如下", null, output);
        } catch (NullPointerException e) {
            Tools.showAlert(null, null, "当前没有任何患者！");
        }

    }

    /**
     * 点击返回按钮
     *
     * @param event
     */
    @FXML
    void back(ActionEvent event) {
        backButton.getScene().getWindow().hide();
    }

    /**
     * 界面初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置鼠标经过按钮改变鼠标样式
        submit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                submit.setCursor(Cursor.HAND);
            }
        });
        checkButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkButton.setCursor(Cursor.HAND);
            }
        });

        //如果没有文件先创建文件并向里面填一个空的List<Patient>
        if (!kzFile.exists()) {
            //系统第一次运行，或者，数据被人破坏
            try {
                kzFile.createNewFile();
                List<Patient> patients = new ArrayList<Patient>();
                Tools.kzSave(patients);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //年龄选择初始化
        choiceBox.getItems().addAll(id);
        choiceBox.getSelectionModel().select(0);
        choiceBox.setOnAction(this::getId);

        //性别选择初始化
        sexChoiceBox.getItems().addAll(sexChoice);
        sexChoiceBox.getSelectionModel().select(0);
        sexChoiceBox.setOnAction(this::getSexChoice);

        //初始化病例号ids数组
        List<Patient> patients = Tools.findAllPatient();
        try {
            for (int i = 0; i < patients.size(); i++) {
                int target = Integer.parseInt(patients.get(i).getId());
                ids[target] = target;
            }
        } catch (NullPointerException e) {

        }


        //comboBox三级联动，两个监视器看前两个comboBox是否被修改
        try {
            //添加科室
            List<Doctor> allDoctor = Tools.findAll(Doctor.class, "dcFile.txt");
            for (int i = 0; i < allDoctor.size(); i++) {
                //判断医生的科室是否被添加进去过
                if (!registDeptChoice.contains(allDoctor.get(i).getRoom())) {
                    //如果没被添加进去过，则添加进registDeptChoice里，并添加进下拉列表
                    registDeptChoice.add(allDoctor.get(i).getRoom());
                    registDept.getItems().add(allDoctor.get(i).getRoom());
                }
            }
            //添加级别
            registDept.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String deptName) {
                    List<String> registLevels = new ArrayList<>();
                    registLevel.getItems().clear();
                    for (int i = 0; i < allDoctor.size(); i++) {
                        if (allDoctor.get(i).getRoom().equals(deptName)) {
                            if (!registLevels.contains(allDoctor.get(i).getGrade())) {
                                registLevels.add(allDoctor.get(i).getGrade());
                                registLevel.getItems().add(allDoctor.get(i).getGrade());
                            }
                        }
                    }
                }
            });
            //添加姓名
            registLevel.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String level) {
                    String deptName = registDept.getValue();
                    registDoctorName.getItems().clear();
                    for (int i = 0; i < allDoctor.size(); i++) {
                        if (allDoctor.get(i).getGrade().equals(level)) {
                            if (allDoctor.get(i).getRoom().equals(deptName) && allDoctor.get(i).getGrade().equals(level)) {
                                registDoctorName.getItems().add(allDoctor.get(i).getRealName());
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tools.buttonHighlight(checkButton);
        Tools.buttonHighlight(submit);

    }

    /**
     * 添加目标患者到文件中
     *
     * @param patient
     */
    public void addPatient(Patient patient) {
        List<Patient> patients = Tools.findAllPatient();
        patients.add(patient);
        Tools.kzSave(patients);
    }
}
