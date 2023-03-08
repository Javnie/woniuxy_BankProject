package com.woniuxy.test.service;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.entity.Card;
import com.woniuxy.service.CardService;

import java.math.BigDecimal;

public class CardServiceTest {
    public static void main(String[] args) {
        CardService cs=new CardService();
        Card card=new Card();
        card.setNum("123");
        card.setPw("123");
        card.setBalance(new BigDecimal("100"));
        card.setOwner(1);
        card.setStatus(1);
        cs.newCard(card);
    }
}
