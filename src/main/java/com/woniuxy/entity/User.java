package com.woniuxy.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class User {
    private int id;
    private String account;
    private String pw;
    private String name;
    private LocalDate birthdate;
    private String idNum;
    private String phone;
    private int status;
    private BigDecimal money;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String account, String pw, String name, LocalDate birthdate, String idNum, String phone) {
        this.account = account;
        this.pw = pw;
        this.name = name;
        this.birthdate = birthdate;
        this.idNum = idNum;
        this.phone = phone;
        this.status = 1;
        this.money = new BigDecimal(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", idNum='" + idNum + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", money=" + money +
                '}';
    }
}
