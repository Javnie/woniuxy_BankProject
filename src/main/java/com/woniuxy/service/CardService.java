package com.woniuxy.service;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.Card;
import com.woniuxy.entity.User;
import com.woniuxy.exception.CardNumAlreadyExistException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardService {
    CardDAO cDao = new CardDAO();

    public List<String> getCardShowingInfoById(Card card) {
        List<String> list = new ArrayList<>();
        list.add("卡号：" + card.getNum());
        list.add("余额：" + card.getBalance());
        String status = card.getStatus() == 1 ? "正常" : "冻结中";
        list.add("账户状态：" + status);
        return list;
    }

    public List<Card> getCardListByOwner(int owner) {
        return cDao.getAll().stream()
                .filter(card -> card.getOwner() == owner)
                .collect(Collectors.toList());
    }

    public void newCard(Card card) {
        //卡号是否重复
        if (cDao.getByNum(card.getNum()) != null) {
            throw new CardNumAlreadyExistException();
        }
        cDao.add(card);
    }

    public void importMoney(Card card, BigDecimal money) {
        UserDAO uDao = new UserDAO();
        User userInput = uDao.getById(card.getOwner());
        userInput.setMoney(userInput.getMoney().add(money));
        uDao.update(userInput);
    }
}
