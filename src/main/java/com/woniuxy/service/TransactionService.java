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
        TransactionDAO tDao = new TransactionDAO();
        Transaction t = new Transaction();
        CardDAO cDao = new CardDAO();
        UserDAO uDao = new UserDAO();

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
}
