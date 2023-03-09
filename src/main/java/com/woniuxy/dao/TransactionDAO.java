package com.woniuxy.dao;

import com.woniuxy.entity.Transaction;
import com.woniuxy.utility.DbHelper;

import java.util.List;

public class TransactionDAO {
    public void add(Transaction transaction) {
        DbHelper.executeSQL("INSERT INTO transaction(output,money,input,time) VALUES(?,?,?,?)"
                , transaction.getOutput(), transaction.getMoney(), transaction.getInput(), transaction.getTime());
    }

    public Transaction getById(int id) {
        return DbHelper.executeSQL(Transaction.class, "SELECT * FROM transaction WHERE id=?", id);
    }

    public List<Transaction> getAll() {
        return DbHelper.executeSQL(Transaction.class, "SELECT * FROM transaction");
    }
}
