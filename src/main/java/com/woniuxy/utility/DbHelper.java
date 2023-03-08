package com.woniuxy.utility;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbHelper {
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();
    private static Properties properties = null;

    static {
        properties = new Properties();
        InputStream is = DbHelper.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection = conns.get();
        if (connection == null) {
            DataSource dataSource = null;
            try {
                dataSource = DruidDataSourceFactory.createDataSource(properties);
                connection = dataSource.getConnection();
                conns.set(connection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void executeSQL(String sql, Object... params) {
        PreparedStatement pstmt = null;

        try {
            pstmt = getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (!getConnection().isClosed()) {
                getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public static <T> T executeSQL(Class<T> c, String sql, int id) {
//        ResultSet resultSet = null;
//        T t = null;
//        Field[] f = c.getDeclaredFields();
//        try {
//            PreparedStatement pstmt = getConnection().prepareStatement(sql);
//            pstmt.setObject(1, id);
//            resultSet = pstmt.executeQuery();
//            if (resultSet.next()) {
//                t = c.newInstance();
//                for (Field field : f) {
//                    field.setAccessible(true);
//                    if (field.getType().equals(LocalDate.class)) {
//                        String msg = resultSet.getString(field.getName());
//                        LocalDate date = LocalDate.parse(msg, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                        field.set(t, date);
//                    } else {
//                        field.set(t, resultSet.getObject(field.getName()));
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//        return t;
//    }

    public static <T> T executeSQL(Class<T> c, String sql, Object o) {
        ResultSet resultSet = null;
        T t = null;
        Field[] f = c.getDeclaredFields();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setObject(1, o);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                t = c.newInstance();
                for (Field field : f) {
                    field.setAccessible(true);
                    if (field.getType().equals(LocalDate.class)) {
                        String msg = resultSet.getString(field.getName());
                        LocalDate date = LocalDate.parse(msg, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        field.set(t, date);
                    } else {
                        field.set(t, resultSet.getObject(field.getName()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    public static <T> List<T> executeSQL(Class<T> c, String sql, Object... params) {
        ResultSet resultSet = null;
        List<T> resultList = null;
        Field[] f = c.getDeclaredFields();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            resultSet = pstmt.executeQuery();
            resultList = new ArrayList<>();
            while (resultSet.next()) {
                T t = c.newInstance();
                for (Field field : f) {
                    field.setAccessible(true);
                    if (field.getType() == LocalDate.class) {
                        String msg = resultSet.getString(field.getName());
                        LocalDate date = LocalDate.parse(msg, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        field.set(t, date);
                    } else {
                        field.set(t, resultSet.getObject(field.getName()));
                    }
                }
                resultList.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public static Object getLinkedInfo(String columnLabel, Object t1, Object t2, Object d1, Object d2) {
        ResultSet resultSet = null;
        Object result = null;
        String sql = "SELECT * FROM ?,? WHERE ?=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setObject(1, t1);
            pstmt.setObject(2, t2);
            pstmt.setObject(3, d1);
            pstmt.setObject(4, d2);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) result = resultSet.getObject(columnLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
