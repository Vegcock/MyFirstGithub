package com.his.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactMenu implements Initializable {
    /**
     * WebView
     */
    @FXML
    private WebView webView;
    /**
     * 返回按钮
     */
    @FXML
    private Button backButton;

    public WebView getWebView() {
        return webView;
    }

    public Button getBackButton() {
        return backButton;
    }

    @FXML
    void back(ActionEvent event) {
        getBackButton().getScene().getWindow().hide();
    }

    /**
     * 界面初始化：加载目标网页
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getWebView().getEngine().load("https://m.baidu.com/bh/m/detail/ar_10997716172916588991");
    }
}
