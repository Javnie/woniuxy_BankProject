package com.woniuxy.test.dao;

import com.woniuxy.dao.TransactionDAO;
import com.woniuxy.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDAOTest {
    public static void main(String[] args) {
        TransactionDAO td=new TransactionDAO();

//        Transaction t1=new Transaction();
//        t1.setOutput("0000000002");
//        t1.setMoney(new BigDecimal(500));
//        t1.setInput("0000000001");
//        t1.setTime(LocalDateTime.now());
//        td.add(t1);
//
//        System.out.println(td.getById(1));
//
//        td.getAll().forEach(System.out::println);
    }
}
