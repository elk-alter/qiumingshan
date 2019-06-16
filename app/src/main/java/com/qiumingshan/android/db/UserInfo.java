package com.qiumingshan.android.db;

import org.litepal.crud.LitePalSupport;

public class UserInfo extends LitePalSupport {

    private int id;//主键

    private String userName;

    private String password;

    private Double credit;

    private int testtimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public int getTesttimes() {
        return testtimes;
    }

    public void setTesttimes(int testtimes) {
        this.testtimes = testtimes;
    }
}
