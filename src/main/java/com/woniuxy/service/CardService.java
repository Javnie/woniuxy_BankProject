package com.woniuxy.service;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.Card;
import com.woniuxy.entity.User;
import com.woniuxy.exception.CardNumAlreadyExistException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CardService {
    CardDAO cDao=new CardDAO();

    public List getCardsOfUser(int owner){
        return cDao.getAll().stream()
                .filter(card -> card.getOwner()==owner)
                .map(card -> card.getNum())
                .collect(Collectors.toList());
    }

    public void newCard(Card card){
        //卡号是否重复
        if (cDao.getByNum(card.getNum()) != null) {
            throw new CardNumAlreadyExistException();
        }

        cDao.add(card);
    }

    public void importMoney(Card card,BigDecimal money){
        UserDAO uDao=new UserDAO();

        User userInput = uDao.getById(card.getOwner());
        userInput.setMoney(userInput.getMoney().add(money));
        uDao.update(userInput);
    }
}
