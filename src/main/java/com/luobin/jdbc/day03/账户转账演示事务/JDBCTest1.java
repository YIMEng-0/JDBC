package com.luobin.jdbc.day03.账户转账演示事务;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 7:08 下午
 */

import java.sql.*;
import java.util.ResourceBundle;

/**
 * drop table if exists t_act
 * create table t_act(
 * actno int,
 * balance double(7,2)
 * );
 * insert into t_act(actno,balance) values(111,20000);
 * insert into t_act(actno,balance) values(222,0);
 * commit;
 * select * from t_act;
 */

/**
 * 重点的三行代码
 *      conn.setAutoCommit();
 *      conn.commit();
 *      conn.rollback();
 */
public class JDBCTest1 {
    public static void main(String[] args) {
        boolean loginSuccess = false;

        // JDBC 代码的实现
        Connection conn = null;
        PreparedStatement ps = null; //Prepared 预编译的  这里使用了预编译的数据库操作对象
        ResultSet rs = null;

        // 进行配置文件的获取，获取到进入数据库的权限，进行数据库的操作权限
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        try {
            // 1、加载驱动
            Class.forName(driver);

            // 2、进行数据库连接
            conn = DriverManager.getConnection(url, username, password);
            // 管子自动提交代码的机制，手动提交即可
            // 开启事务
            conn.setAutoCommit(false);

            // 3、获取预编译的数据库操作对象
            // 实现账户之间的转账操作
            String sql = "update t_act set balance = ? where actno = ?";
            ps = conn.prepareStatement(sql); // 进行了SQL 代码的编译操作
            ps.setDouble(1, 10000);
            ps.setInt(2, 111);  // 将 111 账户的钱变成 10000
            int count = ps.executeUpdate();

            // 设置空指针异常

            ps = conn.prepareStatement(sql); // 进行了SQL 代码的编译操作
            ps.setDouble(1, 10000);
            ps.setInt(2, 222);  // 将 111 账户的钱变成 10000
            count += ps.executeUpdate();
            System.out.println(count == 2 ? "转账成功" : "转账失败");

            // 程序可以运行到这里，说明是没有异常的，可以进行事务的提交
            // 提交事务
            conn.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            // 回滚事务
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
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

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}