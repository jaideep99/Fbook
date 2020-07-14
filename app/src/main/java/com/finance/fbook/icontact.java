package com.finance.fbook;

import java.io.Serializable;

public class icontact implements Serializable {

    String name,number,uid;

    public icontact(String name, String number, String id)
    {
        this.name = name;
        this.number = number;
        this.uid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
