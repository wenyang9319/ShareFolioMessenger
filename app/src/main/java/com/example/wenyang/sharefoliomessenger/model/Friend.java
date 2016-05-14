package com.example.wenyang.sharefoliomessenger.model;

/**
 * Created by WenYang on 12/05/2016.
 */
public class Friend {
    String personName, regId;

    public Friend(String personName, String regId) {
        this.personName = personName;
        this.regId = regId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "personName='" + personName + '\'' +
                ", regId='" + regId + '\'' +
                '}';
    }
}
