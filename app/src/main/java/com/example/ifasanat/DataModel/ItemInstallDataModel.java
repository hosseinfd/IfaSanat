package com.example.ifasanat.DataModel;

import java.io.Serializable;

public class ItemInstallDataModel implements Serializable {


    private int id;

    private String installNumber;

    private String  konturNumber;

    private String phoneNumber;


    private String state;

    private String installTime;

    private String costumerName;

    private String address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstallNumber() {
        return installNumber;
    }

    public void setInstallNumber(String installNumber) {
        this.installNumber = installNumber;
    }

    public String getKonturNumber() {
        return konturNumber;
    }

    public void setKonturNumber(String konturNumber) {
        this.konturNumber = konturNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }
}
