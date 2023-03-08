package com.woniuxy.dao;

import com.woniuxy.entity.Card;
import com.woniuxy.utility.DbHelper;

import java.util.List;

public class CardDAO {
    public void add(Card card){
        DbHelper.executeSQL("INSERT INTO card(num,pw,balance,owner,status) VALUES(?,?,?,?,?)"
        ,card.getNum(),card.getPw(),card.getBalance(),card.getOwner(),card.getStatus());
    }

    public void update(Card card){
        DbHelper.executeSQL("UPDATE card SET balance=? WHERE id=?",card.getBalance(),card.getId());
    }

    public Card getById(int id){
        return DbHelper.executeSQL(Card.class,"SELECT * FROM card WHERE id=?",id);
    }

    public Card getByNum(String num){
        return DbHelper.executeSQL(Card.class,"SELECT * FROM card WHERE num=?",num);
    }

    public List<Card> getAll(){
        return DbHelper.executeSQL(Card.class,"SELECT * FROM card");
    }

}
