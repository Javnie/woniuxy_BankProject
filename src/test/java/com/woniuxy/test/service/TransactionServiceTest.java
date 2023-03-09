package com.woniuxy.test.service;

import com.woniuxy.service.TransactionService;
import com.woniuxy.utility.ProxyUtil;

public class TransactionServiceTest {
    public static void main(String[] args) {
//        ProxyUtil.getProxy(TransactionService.class).doTransaction("0000000002","0000000001",new BigDecimal("500"));
        ProxyUtil.getProxy(TransactionService.class).getAllShowingInfo("0000000000").values().stream()
                .forEach(System.out::println);
    }
}
