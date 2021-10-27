package com.luobin.jdbc.util;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 7:52 下午
 */

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.sun.tools.javac.code.Attribute;

import java.io.PrintStream;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * JDBC 工具类，用来简化 JDBC 编程
 */
public class DBUtil {
    /**
     * 构造类中的构造方法都是私有的
     * 因为工具类中的方法都是静态的，不需要直接的new 对象，直接使用类名字进行调用即可；
     */

    //私有变量
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    private DBUtil() {

    }

    static { // 在类加载的时候，只需要执行一次即可
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        driver = bundle.getString("driver");
        url = bundle.getString("url");
        username = bundle.getString("username");
        password = bundle.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭资源
     * @param conn 连接对象
     * @param ps 数据库操作对象
     * @param rs 结果集
     */
    public static void close(Connection conn, Statement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}