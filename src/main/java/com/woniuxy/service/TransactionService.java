package com.woniuxy.service;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.TransactionDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.Card;
import com.woniuxy.entity.Transaction;
import com.woniuxy.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {
    TransactionDAO tDao = new TransactionDAO();
    UserDAO uDao = new UserDAO();
    CardDAO cDao = new CardDAO();

    public void doTransaction(String output, String input, BigDecimal money) {
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

    //根据转出卡号查询到符合条件的Transaction的集合
    public List<Transaction> getTransListByOutput(String output) {
        return tDao.getAll().stream()
                .filter(transaction -> transaction.getOutput().equals(output))
                .collect(Collectors.toList());
    }

    //单次信息包括：转出及转入的卡号，收款人的姓名，转账金额及转账时间
    public List<String> getSingleShowingInfo(Transaction transaction) {
        List<String> list = new ArrayList<>();
        list.add("转出卡号：" + transaction.getOutput());
        list.add("转入卡号：" + transaction.getInput());
        //收款人的姓名
        String name = uDao.getById(cDao.getByNum(transaction.getInput()).getOwner()).getName();
        list.add("收款人姓名：" + name);
        list.add("转账金额：" + transaction.getMoney());
        list.add("转账时间：" + transaction.getTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        return list;
    }

    //最后展示给用户的符合所有条件的信息
    public Map<Integer, List> getAllShowingInfo(String output) {
        Map<Integer, List> result = new HashMap<>();
        //根据转出卡号查询到的Transaction
        List<Transaction> trList = getTransListByOutput(output);
        for (int i = 0; i < trList.size(); i++) {
            result.put(i, getSingleShowingInfo(trList.get(i)));
        }
        return result;
    }
}
