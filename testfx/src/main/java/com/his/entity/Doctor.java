package com.his.entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Doctor implements Iterable<Patient> {
    private String conut;//医生账号
    private String password;//医生密码
    private String room;//医生所在科室
    private String id;//医生编号
    private String realName;//医生姓名
    private String grade;//医生级别
    private ArrayList<Patient> patients;//所看诊的病人

    public Doctor() {
        patients = new ArrayList<>();
    }

    public Doctor(String conut, String password, String id,String room, String realName, String grade) {
        this.conut = conut;
        this.password = password;
        this.id = id;
        this.room = room;
        this.realName = realName;
        this.grade = grade;
        patients = new ArrayList<>();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public void setConut(String conut) {
        this.conut = conut;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getConut() {
        return conut;
    }

    public String getPassword() {
        return password;
    }

    public String getRoom() {
        return room;
    }

    public String getRealName() {
        return realName;
    }

    public String getGrade() {
        return grade;
    }

    public int getTotalPatient(){
        return patients.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void add(Patient patient){
        this.patients.add(patient);
    }

    @Override
    public Iterator<Patient> iterator() {
        return patients.iterator();
    }
}
