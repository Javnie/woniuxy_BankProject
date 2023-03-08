package com.woniuxy.utility;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.entity.User;

public class AccountManager {
    public static <T> boolean isAccountExist(Class<T> c, String account) {
        if (c == User.class) return new UserDAO().getByAccount(account) != null;
        else return new CardDAO().getByNum(account) != null;
    }

    public static <T> boolean isPwCorrect(Class<T> c, String account, String pw) {
        if (c == User.class) return new UserDAO().getByAccount(account).getPw().equals(pw);
        else return new CardDAO().getByNum(account).getPw().equals(pw);
    }
}
