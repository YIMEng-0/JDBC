package com.luobin.jdbc.day02;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 4:54 下午
 */

import java.sql.*;
import java.util.ResourceBundle;

/**
 * PrepareStatement 完成 insert delete update
 */
public class PrepareStatement实现增删改07 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");

        try {
            // 获取驱动
            Class.forName(driver);

            // 连接数据库
            conn = DriverManager.getConnection(url,username,password);

            // 获取预编译数据库操作对象
            String sql = "insert into dept(deptno,dname,loc) values(?,?,?) ";
            ps = conn.prepareStatement(sql);
            // 进行SQL 语句的传值操作
            ps.setInt(1, 100); // 键值是不能冲突的，冲突会报错，需要注意
            ps.setString(2,"Gou东");
            ps.setString(3,"上海");

            // 执行 SQL
            int count = ps.executeUpdate();
            System.out.println(count);
            // SQL 执行结果的处理

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
