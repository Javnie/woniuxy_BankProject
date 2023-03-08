package com.woniuxy.dao;

import com.woniuxy.entity.Card;
import com.woniuxy.entity.User;
import com.woniuxy.utility.DbHelper;

import java.util.List;

public class UserDAO {
    public void add(User user){
        DbHelper.executeSQL("INSERT INTO user(account,pw,name,birthdate,id_num,phone,status,money) VALUES(?,?,?,?,?,?,?,?)"
        ,user.getAccount(),user.getPw(),user.getName(),user.getBirthdate(),user.getIdNum(),user.getPhone(),user.getStatus(),user.getMoney());
    }

    public void update(User user){
        DbHelper.executeSQL("UPDATE user SET money=? WHERE id=?",user.getMoney(),user.getId());
    }

    public void delete(User user){

    }

    public User getById(int id){
        return DbHelper.executeSQL(User.class,"SELECT id,account,pw,name,birthdate,id_num AS idNum,phone,status,money FROM user WHERE id=?",id);
    }

    public User getByAccount(String account){
        return DbHelper.executeSQL(User.class,"SELECT id,account,pw,name,birthdate,id_num AS idNum,phone,status,money FROM user WHERE account=?",account);
    }

    public User getByIdNum(String idNum){
        return DbHelper.executeSQL(User.class,"SELECT id,account,pw,name,birthdate,id_num AS idNum,phone,status,money FROM user WHERE id_num=?",idNum);
    }

    public List<User> getAll(){
        return DbHelper.executeSQL(User.class,"SELECT id,account,pw,name,birthdate,id_num AS idNum,phone,status,money FROM user");
    }

}
