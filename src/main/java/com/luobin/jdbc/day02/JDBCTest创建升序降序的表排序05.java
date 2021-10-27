package com.luobin.jdbc.day02;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 4:33 下午
 */
public class JDBCTest创建升序降序的表排序05 {
    public static void main(String[] args) {
        /**
         * 演示用户在控制台输入 descr 为降序；
         * 输入 asc 表示的是升序；
         */

        Scanner s = new Scanner(System.in);
        System.out.println("请输入 desc / asc 进行降序 / 升序的排列");
        System.out.println("请输入：");
        String keyWords = s.nextLine();

        // 执行 SQL
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 获取连接
            conn = DriverManager.getConnection(url,username,password);
            System.out.println("数据库连接成功！");

            // 获取预编译的数据库操作对象
            String sql = "select ename from emp order by ename ?";
            ps = conn.prepareStatement(sql);
            // 编译之后的占位符赋值操作
            ps.setString(1,keyWords);

            // 执行SQL
            rs = ps.executeQuery();

            // 结果集合的处理
            while (rs.next()) {
                System.out.println(rs.getString("ename"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
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
}
