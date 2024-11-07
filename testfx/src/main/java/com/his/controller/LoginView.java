package com.his.controller;

import com.his.Main;
import com.his.Person;
import com.his.entity.Patient;
import com.his.fileio.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginView implements Initializable {

    @FXML
    private AnchorPane _pane;

    @FXML
    private PasswordField _password;

    @FXML
    private TextField _id;

    @FXML
    private TableColumn<Person, String> _tableC1;

    @FXML
    private TableColumn<Person, String> _tableC2;

    @FXML
    private TableColumn<Person, String> _tableC3;

    @FXML
    private TableColumn<Person, String> _tableC4;

    @FXML
    private TableView<Person> _table;

    @FXML
    private ListView<HBoxCell> _listView;

    @FXML
    private Button _login;

    @FXML
    private HBox _rightHbox;

    @FXML
    void idEvent(ActionEvent event) {

    }

    @FXML
    void pwEvent(ActionEvent event) {

    }

    private static boolean isPressed = false;
    private static double currentX = 1;
    private static double currentY = 1;


    public PasswordField get_password() {
        return _password;
    }

    public TextField get_id() {
        return _id;
    }

    public void set_password(PasswordField _password) {
        this._password = _password;
    }

    public void set_id(TextField _id) {
        this._id = _id;
    }

    public void login() throws IOException {
//
//        String id = _id.getText();
//        String password = _password.getText();
//        if (id.equals("123") && password.equals("123")) {
//            System.out.println("登录成功");
//            FXMLLoader loader = new FXMLLoader();
//            URL url = Main.class.getResource("DiagnoseView.fxml");
//            loader.setLocation(url);
//            AnchorPane loginPane = loader.load();
//            Scene scene = new Scene(loginPane);
//            Stage stage = new Stage();
//            stage.setTitle("看诊界面");
//            stage.setScene(scene);
//            stage.show();
//        } else {
//            Tools.showAlert(null, "failed", "登录失败");
//            System.exit(1);
//        }

        _pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent eventPress) {
                if (eventPress.getCode() == KeyCode.CONTROL) {
                    isPressed = true;
                    _pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                                double mouseX = event.getX();
                                double mouseY = event.getY();

                                Scale scale = new Scale(1, 1, mouseX,mouseY);
                                _pane.getTransforms().add(scale);
                                Scene scene = _login.getScene();

                                scene.setOnScroll(eventScroll -> {
                                    double zoomFactor ;
                                    if (eventScroll.getDeltaY() < 0) {
                                        zoomFactor = -0.1;
                                    }else{
                                        zoomFactor = 0.1;
                                    }
                                    if(isPressed == true) {
                                        scale.setX(scale.getX() + zoomFactor);
                                        scale.setY(scale.getY() + zoomFactor);

                                        eventScroll.consume();
                                    }
                                });
                            }
                    });
                }
            }
        });
        _pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                isPressed = false;
            }
        });

    }


    public static class HBoxCell extends HBox {
        TextField textField = new TextField();
        Button button = new Button();

        HBoxCell(String textFieldTitle, String buttonText) {
            super();

            textField.setText(textFieldTitle);
            textField.setEditable(false);
            textField.setMaxWidth(50);
            HBox.setMargin(textField, new Insets(10, 10, 10, 10));
            HBox.setMargin(button, new Insets(10, 10, 10, 50));

            button.setText(buttonText);

            this.getChildren().addAll(textField, button);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });


            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (textField.isEditable() == true) {
                        if (oldValue && !newValue) {
                            System.out.println("1");
                        }
                    }
                }
            });
        }
    }

    public void button(){
        _rightHbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("DiagnoseView.fxml");
        loader.setLocation(url);
        try {
            ScrollPane _rightPane = loader.load();
            _rightHbox.getChildren().add(_rightPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void button2(){
        _rightHbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource("doctorLogin.fxml");
        loader.setLocation(url);
        try {
            ScrollPane _rightPane = loader.load();
            _rightHbox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(_rightPane);
            _rightHbox.getChildren().add(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _tableC1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        _tableC2.setCellValueFactory(new PropertyValueFactory<>("Age"));
        _table.getItems().add(new Person("1", 11));
        _table.getItems().add(new Person("2", 22));
        _table.getItems().add(new Person("3", 33));
        _table.getItems().add(new Person("4", 44));
        _table.setPlaceholder(new Label("空白"));

        List<Patient> patients = new ArrayList<>();
        try {
            patients = Tools.findAll(Patient.class, "kzFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = "";
        List<HBoxCell> list = new ArrayList<>();
        for (int i = 0; i < patients.size(); i++) {
            text = patients.get(i).getRealname();
            list.add(new HBoxCell(text, "Button " + 1));
        }
        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(list);
        _listView.setItems(myObservableList);



    }

}
