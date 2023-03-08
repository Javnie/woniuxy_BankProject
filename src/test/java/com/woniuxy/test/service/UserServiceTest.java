package com.woniuxy.test.service;

import com.woniuxy.entity.User;
import com.woniuxy.service.UserService;

import java.time.LocalDate;

public class UserServiceTest {
    public static void main(String[] args) {
        UserService us=new UserService();
        us.register(new User("123","123","123", LocalDate.now(),"111111111111111111","11111111111"));
    }
}
