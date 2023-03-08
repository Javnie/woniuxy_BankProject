package com.woniuxy.entity;

import java.math.BigDecimal;

public class Card {
    private int id;
    private String num;
    private String pw;
    private BigDecimal balance;
    private int owner;
    private int status;

    public Card() {
    }

    public Card(String num, String pw, BigDecimal balance, int owner, int status) {
        this.num = num;
        this.pw = pw;
        this.balance = balance;
        this.owner = owner;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", pw='" + pw + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                ", status=" + status +
                '}';
    }
}
