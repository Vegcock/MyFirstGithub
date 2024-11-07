package com.his.fileio;
//since 2024/6/14 by neu.CZ

import com.alibaba.fastjson.JSONArray;
import com.his.Main;
import com.his.controller.PageTransform;
import com.his.entity.Doctor;
import com.his.entity.Drug;
import com.his.entity.Patient;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static File kzFile = new File("kzFile.txt");
    public static File drugFile = new File("drugFile.txt");

    /**
     * 二分查找目标患者
     * @param targetId 目标病例号
     * @param patients 患者List
     * @return 返回一个患者类
     */
    public static Patient getPatient(String targetId, List<Patient> patients) {
        int left = 0;
        int right = patients.size()-1;
        Patient targetPatient = null;
        while (left <= right) {
            int middle = (left + right)/2;
            if (Integer.parseInt(patients.get(middle).getId()) > Integer.parseInt(targetId)) {
                right = middle - 1;
            } else if (Integer.parseInt(patients.get(middle).getId()) < Integer.parseInt(targetId)) {
                left = middle + 1;
            } else {
                targetPatient = patients.get(middle);
                System.out.println("二分查找成功！");
                break;
            }
        }
        return targetPatient;
    }
    /**
     * 对patients冒泡排序
     * @param patients
     * @return
     */
    public static List<Patient> sortPatients(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = 0; j < patients.size() - 1 - i; j++) {
                if (Integer.parseInt(patients.get(j).getId()) > Integer.parseInt(patients.get(j+1).getId())) {
                    Patient patient = patients.get(j);
                    patients.get(j).equals(patients.get(j+1));
                    patients.get(j+1).equals(patient);
                }
            }
        }
        System.out.println("冒泡排序成功！");
        return patients;
    }
    /**
     * 读取文件进内存
     *
     * @return 返回一个ArrayList
     */
    public static List<Drug> findAllDrug() {
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream2 = new FileInputStream(drugFile);
            byte[] bytes = new byte[1024 * 8];
            int len;
            String temp = "";
            while ((len = fileInputStream2.read(bytes)) != -1) {
                temp = temp + new String(bytes, 0, len, "UTF-8");
            }
            List<Drug> drugs = JSONArray.parseArray(temp, Drug.class);
            return drugs;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*finally {
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        return null;
    }

    /**
     * 保存进文件
     *
     * @param drugs
     */
    public static void drugSave(List<Drug> drugs) {
        String studentsJSON = JSONArray.toJSONString(drugs);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(drugFile);
            fileOutputStream.write(studentsJSON.getBytes("UTF-8"));
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取文件数据进内存
     *
     * @return 返回一个ArrayList
     */
    public static List<Patient> findAllPatient() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(kzFile);
            byte[] bytes = new byte[1024 * 8];
            int len = 0;
            String temp = "";
            while ((len = fileInputStream.read(bytes)) != -1) {
                temp = temp + new String(bytes, 0, len, "UTF-8");
            }
            List<Patient> students = JSONArray.parseArray(temp, Patient.class);
            return students;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存进文件
     *
     * @param patients
     */
    public static void kzSave(List<Patient> patients) {
        String studentsJSON = JSONArray.toJSONString(patients);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(kzFile);
            fileOutputStream.write(studentsJSON.getBytes("UTF-8"));
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 医生id的初始化
     */
    public static void idInitialize() {
        try {
            List<Doctor> doctors = findAll(Doctor.class, "dcFile.txt");
            for (int i = 0; i < doctors.size(); i++) {
                doctors.get(i).setId("" + (1001 + i));
            }
            saveResult(doctors, Doctor.class, "dcFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件数据进内存
     *
     * @return 返回一个ArrayList
     */
    public static <T> List<T> findAll(Class<T> clazz, String filename) throws IOException {
        File file = new File(filename);
        FileOutputStream fileOutStream = null;
        if (!file.exists()) {
            file.createNewFile();
            List<T> list = new ArrayList<>();
            fileOutStream = new FileOutputStream(file);
            String json = JSONArray.toJSONString(list);
            fileOutStream.write(json.getBytes("UTF-8"));
            fileOutStream.flush();
            return list;
        } else {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 8];
                int len = 0;
                StringBuilder stringBuilder = new StringBuilder();
                while ((len = fileInputStream.read(bytes)) != -1) {
                    stringBuilder.append(new String(bytes, 0, len, "UTF-8"));
                }
                String temp = stringBuilder.toString();
                List<T> list = JSONArray.parseArray(temp, clazz);
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * 保存进文件
     *
     * @param clazz
     */
    public static <T> void saveResult(List<T> list, Class<T> clazz, String filename) throws IOException {
        File file = new File(filename);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String studentsJSON = JSONArray.toJSONString(list);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(studentsJSON.getBytes("UTF-8"));
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 提示窗
     *
     * @param title   提示窗标题
     * @param content 提示内容
     */
    public static void showAlert(String title, String headerText, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * 新窗口的弹出
     *
     * @param stage     传入stage可以更好编写父子窗口，实现父子窗口的焦点转换
     * @param fileName
     * @param titleName
     */
    public static void stageCreate(Stage stage, String fileName, String titleName) {
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource(fileName);
        loader.setLocation(url);
        BorderPane loginPane = null;
        try {
            loginPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("wrong");
        }
        Scene scene = new Scene(loginPane);
        stage.setTitle(titleName);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean isPressed = false;
    static double zoomFactor = 1.0;

    public static void pageReload(BorderPane borderPane){
        borderPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("PageTransform.fxml");
        loader.setLocation(url);
        try {
            BorderPane rightPane = loader.load();
            rightPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), null, null)));
            borderPane.getChildren().add(rightPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transformPage(AnchorPane anchorPane, String filename) {
        anchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource(filename);
        loader.setLocation(url);
        try {
            AnchorPane rightPane = loader.load();
            rightPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), null, null)));
            anchorPane.getChildren().add(rightPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void buttonHighlight(Button button) {
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"), BorderStrokeStyle.SOLID, null, null)));
                button.setCursor(Cursor.HAND);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setCursor(Cursor.DEFAULT);
                button.setBorder(null);
            }
        });
    }



}
