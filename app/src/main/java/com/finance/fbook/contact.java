package com.finance.fbook;

import java.io.Serializable;

public class contact implements Serializable {

    String name,number;

    public contact(String name,String number)
    {
        this.name = name;
        this.number = number;
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
}
