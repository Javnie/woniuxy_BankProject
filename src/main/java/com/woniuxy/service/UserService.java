package com.woniuxy.service;

import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.User;
import com.woniuxy.exception.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    UserDAO uDao = new UserDAO();

    public List<String> getUserShowingInfoById(int id) {
        User user = uDao.getById(id);
        List<String> list = new ArrayList<>();
        list.add("账户名：" + user.getAccount());
        list.add("姓名：" + user.getName());
        list.add("生日：" + user.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        list.add("身份证：" + user.getIdNum());
        list.add("手机号码：" + user.getPhone());
        list.add("账户余额：" + user.getMoney());
        String status = user.getStatus() == 1 ? "正常" : "冻结中";
        list.add("账户状态：" + status);
        return list;
    }

    public void register(User user) {
        if (uDao.getByAccount(user.getAccount()) != null) {
            throw new UserAccountAlreadyExistException();
        }
        if (uDao.getByIdNum(user.getIdNum()) != null) {
            throw new IdNumAlreadyExistException();
        }
        uDao.add(user);
    }
}
