package com.woniuxy.service;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.TransactionDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.Card;
import com.woniuxy.entity.Transaction;
import com.woniuxy.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionService {
    public void doTransaction(String output, String input, BigDecimal money) {
        //需要使用动态代理
        TransactionDAO tDao = new TransactionDAO();
        CardDAO cDao = new CardDAO();
        UserDAO uDao = new UserDAO();
        Transaction t = new Transaction();

        t.setOutput(output);
        t.setMoney(money);
        t.setInput(input);
        t.setTime(LocalDateTime.now());
        tDao.add(t);

        Card c = cDao.getByNum(input);
        c.setBalance(c.getBalance().add(money));
        cDao.update(c);

        User userInput = uDao.getById(c.getOwner());
        userInput.setMoney(userInput.getMoney().add(money));
        uDao.update(userInput);

        Card cardOutput = cDao.getByNum(output);
        cardOutput.setBalance(cardOutput.getBalance().subtract(money));
        cDao.update(cardOutput);

        User userOutput = uDao.getById(cardOutput.getOwner());
        userOutput.setMoney(userOutput.getMoney().subtract(money));
        uDao.update(userOutput);
    }

    public void verifyBeforeTransaction() {
        //判断状态是否正常 rollback 原子操作 （密码输错三次冻结 24小时解冻）
        //动态代理

        //1.获取card信息

    }
}
