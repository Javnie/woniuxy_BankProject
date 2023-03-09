package com.woniuxy;

import com.woniuxy.dao.CardDAO;
import com.woniuxy.dao.UserDAO;
import com.woniuxy.data.Global;
import com.woniuxy.entity.Card;
import com.woniuxy.entity.User;
import com.woniuxy.exception.CardNumAlreadyExistException;
import com.woniuxy.exception.IdNumAlreadyExistException;
import com.woniuxy.exception.UserAccountAlreadyExistException;
import com.woniuxy.service.CardService;
import com.woniuxy.service.TransactionService;
import com.woniuxy.service.UserService;
import com.woniuxy.utility.ProxyUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner scanner = new Scanner(System.in);

    UserDAO uDAO = new UserDAO();
    CardDAO cDAO = new CardDAO();

    public static void main(String[] args) {
        Bank bank = new Bank();
        boolean isRunning = true;

        System.out.println("  欢迎使用蜗牛银行管理系统  ");
        while (isRunning) {
            System.out.println("------请选择功能菜单------");
            System.out.println("--按【1】键  进行登录操作--");
            System.out.println("--按【2】键  进行开户操作--");
            System.out.println("--按【3】键  进行开卡操作--");
            System.out.println("--按【4】键  进行转账操作--");
            System.out.println("--按【5】键  查询户头信息--");
            System.out.println("--按【6】键  查询卡片信息--");
            System.out.println("--按【7】键  查询转账信息--");
            System.out.println("--按【0】键  退出银行系统--");
            System.out.println("------------------------");

            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    //登录操作
                    try {
                        bank.login();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //开户操作
                    try {
                        bank.createUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    //开卡操作
                    try {
                        bank.createCard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    //转账操作
                    try {
                        bank.transact();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    //查询户头
                    try {
                        bank.getUserInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    //查询卡片
                    try {
                        bank.getCardInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    //查询转账信息
                    try {
                        bank.getTransaction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("感谢您的使用，期待与您的下次相见！");
                    isRunning = false;
            }
        }
    }

    private void getUserInfo() {
        while (Global.user == null) {
            System.out.println("请登录您的账户信息");
            login();
        }
        ProxyUtil.getProxy(UserService.class).getUserShowingInfoById(Global.user.getId()).forEach(System.out::println);

        boolean running = true;
        System.out.println("如需继续查询卡片信息，输入任意键继续，或输入「N」退出：");
        if (scanner.next().equals("N")) running = false;
        else showCards();
    }

    private void getCardInfo() {
        if (Global.user != null) {
            showCards();
        } else {
            boolean running = true;
            while (running) {
                System.out.println("请输入您要查询的卡号：");
                String cardNum = null;
                Card card = null;

                while (true) {
                    cardNum = scanner.next();
                    card = cDAO.getByNum(cardNum);
                    if (card != null) break;
                    else System.out.println("输入的卡号有误，请重新输入");
                }

                System.out.println("请输入您的卡片密码：");
                String pw = null;
                while (true) {
                    pw = scanner.next();
                    if (card.getPw().equals(pw)) break;
                    else System.out.println("输入的密码有误，请重新输入");
                }

                System.out.println("您的卡片信息如下：");
                ProxyUtil.getProxy(CardService.class).getCardShowingInfoById(card).stream().forEach(System.out::println);
                System.out.println("如需继续查询其他卡片信息，输入任意键继续，或输入「N」退出：");
                if (scanner.next().equals("N")) {
                    running = false;
                }
            }
        }
    }

    //查询交易信息
    private void getTransaction() {
        while (Global.user == null) {
            System.out.println("请登录您的账户信息");
            login();
        }

        List<Card> cardsInfo = ProxyUtil.getProxy(CardService.class).getCardListByOwner(Global.user.getId());
        if (cardsInfo.size() != 0) {
            boolean outRunning = true;
            while (outRunning) {
                System.out.println("您的卡片列表如下：");
                for (int i = 0; i < cardsInfo.size(); i++) {
                    System.out.println(i + 1 + ": " + cardsInfo.get(i).getNum());
                }

                System.out.println("请输入您想查询的卡片编号：");
                int no = 0;
                boolean innerRunning = true;
                while (innerRunning) {
                    no = scanner.nextInt();
                    for (int i = 1; i <= cardsInfo.size(); i++) {
                        if (no == i) {
                            innerRunning = false;
                            break;
                        }
                    }
                    if (innerRunning) System.out.println("您的输入有误，请重新输入卡片编号：");
                }

                System.out.println("您所查询的卡片转账信息如下：");
                String output = cardsInfo.get(no - 1).getNum();
                Collection<List> information = ProxyUtil.getProxy(TransactionService.class).getAllShowingInfo(output).values();
                if (information.isEmpty()) System.out.println("抱歉，您当前卡片没有转账记录");
                else information.stream().forEach(System.out::println);
                System.out.println("如需继续查询其他卡片信息，输入任意键继续，或输入「N」退出：");
                if (scanner.next().equals("N")) {
                    outRunning = false;
                }
            }
        } else {
            System.out.println("您当前户下没有卡片");
        }
    }

    private void showCards() {
        List<Card> cardsInfo = ProxyUtil.getProxy(CardService.class).getCardListByOwner(Global.user.getId());

        if (cardsInfo.size() != 0) {
            boolean outRunning = true;
            while (outRunning) {
                System.out.println("您的卡片列表如下：");
                for (int i = 0; i < cardsInfo.size(); i++) {
                    System.out.println(i + 1 + ": " + cardsInfo.get(i).getNum());
                }

                System.out.println("请输入您想查询的卡片编号：");
                int no = 0;
                boolean innerRunning = true;
                while (innerRunning) {
                    no = scanner.nextInt();
                    for (int i = 1; i <= cardsInfo.size(); i++) {
                        if (no == i) {
                            innerRunning = false;
                            break;
                        }
                    }
                    if (innerRunning) System.out.println("您的输入有误，请重新输入卡片编号：");
                }

                System.out.println("您所查询的卡片信息如下：");
                ProxyUtil.getProxy(CardService.class).getCardShowingInfoById(cardsInfo.get(no - 1)).stream().forEach(System.out::println);
                System.out.println("如需继续查询其他卡片信息，输入任意键继续，或输入「N」退出：");
                if (scanner.next().equals("N")) {
                    outRunning = false;
                }
            }
        } else {
            System.out.println("您当前户下没有卡片");
        }
    }

    private void transact() {
        System.out.println("请输入对方卡号：");
        String input = null;
        Card inputCard = null;
        while (true) {
            input = scanner.next();
            inputCard = cDAO.getByNum(input);
            if (cDAO.getByNum(input) != null) {
                if (inputCard.getStatus() != 1)
                    System.out.println("抱歉，对方卡片状态异常，无法完成交易，请重新输入对方卡号");
                else break;
            } else System.out.println("输入的卡号有误，请重新输入");
        }

        String output = null;
        Card outputCard = null;
        if (Global.user == null) {
            System.out.println("请输入您的卡号：");
            while (true) {
                output = scanner.next();
                outputCard = cDAO.getByNum(output);
                if (outputCard != null) {
                    if (outputCard.getStatus() != 1)
                        System.out.println("抱歉，您的卡片状态异常，无法完成交易，请更换您的卡片");
                    else break;
                } else System.out.println("输入的卡号有误，请重新输入");
            }

            System.out.println("请输入您的密码：");
            String pw = null;
            while (true) {
                pw = scanner.next();
                if (outputCard.getPw().equals(pw)) break;
                else System.out.println("输入的密码有误，请重新输入");
            }
        } else {
            System.out.println("您的卡片列表如下，请输入您要进行转账操作的卡片编号：");
            List<Card> list = ProxyUtil.getProxy(CardService.class).getCardListByOwner(Global.user.getId());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + ": " + list.get(i).getNum());
            }

            int no = 0;
            boolean running = true;
            while (running) {
                no = scanner.nextInt();
                for (int i = 1; i <= list.size(); i++) {
                    if (no == i) {
                        running = false;
                        break;
                    }
                }
                if (running) System.out.println("您的输入有误，请重新输入卡片编号");
            }
            outputCard = cDAO.getByNum(list.get(no - 1).getNum());
            output = outputCard.getNum();
        }

        System.out.println("请输入转账金额：");
        BigDecimal money = scanner.nextBigDecimal();

        if (outputCard.getBalance().compareTo(money) == -1)
            System.out.println("抱歉，您的卡片余额不足，无法完成本次交易");
        else {
            ProxyUtil.getProxy(TransactionService.class).doTransaction(output, input, money);
            System.out.println("转账成功");
        }
    }

    private void login() {
        System.out.println("请输入账户名：");
        String account = scanner.next();
        System.out.println("请输入密码：");
        String pw = scanner.next();

        User user = uDAO.getByAccount(account);
        if (user == null) System.out.println("账户不存在");
        else if (!user.getPw().equals(pw)) System.out.println("密码错误");
        else {
            Global.user = user;
            System.out.println("登录成功");
        }
    }

    private void createCard() {
        while (Global.user == null) {
            System.out.println("请登录您的账户信息");
            login();
        }
        //流程1：直接输入卡号，支付密码，持卡人来自开户的信息，状态默认
        System.out.println("请输入新卡号：");
        String num = null;
        while (true) {
            num = scanner.next();
            if (num.length() != 10)
                System.out.println("新卡号长度不符合规范，请重新输入10位数的卡号：");
            else if (stringMatch(num) != 2) System.out.println("新卡号必须为纯数字，请重新输入符合规范的卡号：");
            else break;
        }

        System.out.println("请输入新卡片密码：");
        String pw = null;
        while (true) {
            pw = scanner.next();
            if (pw.length() != 6)
                System.out.println("新密码长度不符合规范，请重新输入6位数的密码：");
            else if (stringMatch(pw) != 2) System.out.println("新密码必须为纯数字，请重新输入符合规范的密码：");
            else break;
        }

        System.out.println("您是否需要存入现金: Y/N");
        BigDecimal money = new BigDecimal(0);
        if (scanner.next().equals("Y")) {
            System.out.println("您存入的现金为：");
            money = new BigDecimal(scanner.next());
        }

        int owner = Global.user.getId();
        Card card = new Card(num, pw, money, owner, 1);

        try {
            ProxyUtil.getProxy(CardService.class).newCard(card);
            if (money.doubleValue() != 0) ProxyUtil.getProxy(CardService.class).importMoney(card, money);
            System.out.println("开卡成功");
        } catch (Exception e) {
            if (e instanceof CardNumAlreadyExistException) System.out.println("很抱歉，您输入的卡号已存在，请重新开卡");
            else System.out.println("很抱歉，目前系统维护中，请稍候再试");
        }
    }

    private void createUser() {
        //选择开户：直接输入相关信息，状态，总金额不需要直接输入，提供默认值，账号和身份证号
        //账号，密码，姓名，出生年月，身份证号码，手机号
        System.out.println("请输入新账户名：");
        String account = null;
        while (true) {
            account = scanner.next();
            if (account.length() <= 6) break;
            else System.out.println("新帐户名长度过长，请重新输入6位数以内的账户名：");
        }

        System.out.println("请输入新账户密码：");
        String pw = null;
        while (true) {
            pw = scanner.next();
            if (pw.length() > 10 || pw.length() < 6)
                System.out.println("新密码长度不符合规范，请重新输入6~10位数的密码：");
            else if (stringMatch(pw) != 3) System.out.println("新密码必须为数字和密码组合，请重新输入符合规范的密码：");
            else break;
        }

        System.out.println("请输入姓名：");
        String name = null;
        while (true) {
            name = scanner.next();
            if (name.length() <= 6) break;
            else System.out.println("姓名长度不符合规范，请重新输入姓名：");
        }

        System.out.println("请输入身份证号码：");
        String idNum = null;
        while (true) {
            idNum = scanner.next();
            if (idNum.length() == 18) break;
            else System.out.println("身份证长度不符合规范，请重新输入身份证号码：");
        }

        System.out.println("请输入手机号码：");
        String phone = null;
        while (true) {
            phone = scanner.next();
            if (phone.length() == 11) break;
            else System.out.println("手机号码长度不符合规范，请重新输入手机号码：");
        }

        System.out.println("请输入出生年份：");
        int year = scanner.nextInt();
        System.out.println("请输入出生月份：");
        int month = scanner.nextInt();
        System.out.println("请输入出生日期：");
        int date = scanner.nextInt();
        LocalDate birthdate = LocalDate.of(year, month, date);

        User user = new User(account, pw, name, birthdate, idNum, phone);

        try {
            ProxyUtil.getProxy(UserService.class).register(user);
            Global.user = uDAO.getByAccount(account);
            System.out.println("开户成功");
        } catch (Exception e) {
            if (e instanceof UserAccountAlreadyExistException)
                System.out.println("很抱歉，您输入的账户名已存在，请重新开户");
            else if (e instanceof IdNumAlreadyExistException)
                System.out.println("很抱歉，您输入的身份证信息已存在，请重新开户");
            else System.out.println("很抱歉，目前系统维护中，请稍候再试");
        }
    }

    private int stringMatch(String msg) {
        int letter = 0, number = 0, other = 0;
        int result = 0;

        //根据ASCII码判断
        for (int temp : msg.toCharArray()) {
            if (temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122) {
                letter++;
            } else if (temp >= 48 && temp <= 57) {
                number++;
            } else {
                other++;
            }
        }

        if (letter != 0) result += 1;
        if (number != 0) result += 2;
        if (other != 0) result += 4;
        return result;
    }
}