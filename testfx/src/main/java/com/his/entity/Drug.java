package com.his.entity;

import com.his.fileio.Tools;

public class Drug {
    /***
     * 药品名称
     */
    private String name;
    /**
     * 药品编号
     */
    private int number;
    /**
     * 药品价格
     */
    private double price;
    /**
     * 药品数量
     */
    private int num;
    /**
     *
     * @param name
     * @param number
     * @param price
     * @param num
     */
    public Drug(String name, int number, double price,int num) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.num=num;
    }

    /**
     *
     */
    public Drug() {
    }

    /**
     *
     * @return
     */
    public int getNum() {
        return num;
    }

    /**
     *
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 药品数量+x
     * @param x
     */
    public void upnum(int x){

            this.num=this.num+x;


    }

    /**
     * 药品数量-x
     * @param x
     */
    public void downnum(int x){
        this.num=this.num-x;
    }
}
