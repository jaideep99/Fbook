package com.finance.fbook;

import java.io.Serializable;

public class bill implements Serializable {

    private int time,amount,interest_amnt;
    private boolean interest,gave;
    private float rate;
    private String desc,uid,date;

    public bill()
    {

    }

    public bill(String uid, int amount, int interest_amnt, String desc, String date, int time, float rate, boolean interest, boolean gave)
    {
        this.uid = uid;
        this.amount = amount;
        this.interest_amnt = interest_amnt;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.rate = rate;
        this.interest = interest;
        this.gave = gave;
    }

    public int getInterest_amnt() {
        return interest_amnt;
    }

    public void setInterest_amnt(int interest_amnt) {
        this.interest_amnt = interest_amnt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getGave()
    {
        return  this.gave;
    }

    public void setGave(boolean gave) {
        this.gave = gave;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public boolean getInterest()
    {
        return  this.interest;
    }
}

