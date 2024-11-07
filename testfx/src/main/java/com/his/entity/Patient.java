package com.his.entity;
//since 2024/6/13 by neu.CZ

public class Patient {
    private String realName;    //患者姓名
    private String gender;      //患者性别
    private String cardNumber;  //身份证号
    private String birthDate;     //出生日期
    private Integer age;           //年龄
    private String homeAddress;     //家庭住址
    private String deptName;        //挂号科室
    private String doctorName;      //挂号医生
    private int registLevel;     //挂号级别
    private double registFee; //挂号费
    private String registDate;      //挂号日期
    private String diagiosis;       //诊断结果（一个描述，看诊医生自己写）
    private String prescription;    //药品信息
    private Double drugPrice;       //药品总价
    private boolean iskz;         //看诊状态（false:未看诊,true:已看诊）
    private boolean isyf; //药房状态（false:未开药,true:已开药）
    private double balance; //患者余额
    private boolean isjf; //是否缴费
    private String id; //病历号
    private String drugInformation; //药品信息
    private boolean isHoi;

    public Patient() {
    }

    public Patient(String realname, String gender, String cardnumber,
                   String birthdate, Integer age, String homeaddress, String deptname,
                   String doctorname, int registlevel, Double registfee, String registdate,
                   String diagiosis, String prescription, Double drugprice, boolean iskz,
                   boolean isyf, double balance, boolean isjf, String id,boolean isHoi ,String drugInformation) {

        this.realName = realname;
        this.gender = gender;
        this.cardNumber = cardnumber;
        this.birthDate = birthdate;
        this.age = age;
        this.homeAddress = homeaddress;
        this.deptName = deptname;
        this.doctorName = doctorname;
        this.registLevel = registlevel;
        this.registFee = registfee;
        this.registDate = registdate;
        this.diagiosis = diagiosis;
        this.prescription = prescription;
        this.drugPrice = drugprice;
        this.iskz = iskz;
        this.isyf = isyf;
        this.balance = balance;
        this.isjf = isjf;
        this.id = id;
        this.isHoi = isHoi;
        this.drugInformation = drugInformation;
    }

    public boolean isHoi() {
        return isHoi;
    }

    public void setHoi(boolean hoi) {
        isHoi = hoi;
    }

    public String getDrugInformation() {
        return drugInformation;
    }

    public void setDrugInformation(String drugInformation) {
        if(getDrugInformation()==null){
            this.drugInformation=" "+drugInformation;
        }
        else{
            this.drugInformation=""+this.drugInformation+" "+drugInformation;
        }

    }

    public String getRealname() {
        return realName;
    }

    public String getGender() {
        return gender;
    }

    public String getCardnumber() {
        return cardNumber;
    }

    public String getBirthdate() {
        return birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public String getHomeaddress() {
        return homeAddress;
    }

    public String getDeptname() {
        return deptName;
    }

    public String getDoctorname() {
        return doctorName;
    }

    public int getRegistlevel() {
        return registLevel;
    }

    public String getRegistdate() {
        return registDate;
    }

    public String getDiagiosis() {
        return diagiosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public Double getDrugprice() {
        return drugPrice;
    }

    public boolean isIskz() {
        return iskz;
    }

    public boolean isIsyf() {
        return isyf;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isIsjf() {
        return this.isjf;
    }

    public String getId() {
        return this.id;
    }

    public double getRegistFee() { return this.registFee; }


    public void setRealname(String realname) {
        this.realName = realname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCardnumber(String cardnumber) {
        this.cardNumber = cardnumber;
    }

    public void setBirthdate(String birthdate) {
        this.birthDate = birthdate;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeAddress = homeaddress;
    }

    public void setDeptname(String deptname) {
        this.deptName = deptname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorName = doctorname;
    }

    public void setRegistlevel(int registlevel) {
        this.registLevel = registlevel;
    }

    public void setRegistdate(String registdate) {
        this.registDate = registdate;
    }

    public void setDiagiosis(String diagiosis) {
        this.diagiosis = diagiosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setDrugprice(Double drugprice) {
        this.drugPrice = drugprice;
    }

    public void setIskz(boolean iskz) {
        this.iskz = iskz;
    }

    public void setIsyf(boolean isyf) {
        this.isyf = isyf;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setIsjf(boolean isjf) {
        this.isjf = isjf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegistFee(double fee) {this.registFee = fee; }

    public Double getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(Double drugPrice) {
        this.drugPrice = drugPrice;
    }

//    public String getDurgInformation() {
//        return this.drugInformation;
//    }
//
//    public void setDurgInformation(String drugInformation) {
//
//            this.drugInformation=""+this.drugInformation+" "+drugInformation;
//
////        if (getDurgInformatoion() == null) {
////            this.durgInformatoion = " " + durgInformatoion;
////        } else {
////            this.durgInformatoion = "" + this.durgInformatoion + "" + durgInformatoion;
////        }
//    }

}
