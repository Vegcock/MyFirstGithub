package com.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.his.OkHttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.StringTokenizer;

public class LocationController {

    @FXML
    private Label uProvince;

    @FXML
    private Label uCity;

    @FXML
    private Label uAdcode;

    @FXML
    private Button uget;

    @FXML
    private Label uRecangle;



    public void getLocation(){//                                                     01d62e926675e2f2d28ca12b96cdd98c
        String result= OkHttpUtils.builder().url("https://restapi.amap.com/v3/ip?key=01d62e926675e2f2d28ca12b96cdd98c").get().sync();
        JSONObject jsonObject=JSONObject.parseObject(result);
        String province = jsonObject.getString("province");
        uProvince.setText(province);
        uCity.setText(jsonObject.getString("city"));
        uAdcode.setText(jsonObject.getString("adcode"));
        String st=jsonObject.getString("rectangle");
        StringTokenizer stringTokenizer = new StringTokenizer(st,";");
        uRecangle.setText(stringTokenizer.nextToken()+" "+stringTokenizer.nextToken());
    }



}
