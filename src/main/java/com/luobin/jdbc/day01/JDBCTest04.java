package com.luobin.jdbc.day01;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/25 9:15 下午
 */

public class JDBCTest04 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        // 使用资源绑定器，对于属性配置文件进行绑定
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("username");
        String password = bundle.getString("password");

        try {
            // 1、注册驱动
            Class.forName(driver);

            // 2、获取链接
            conn = DriverManager.getConnection(url,user,password);

            // 3、获取数据库操作对象
            stmt = conn.createStatement();

            // 4、执行 SQL 语句
            String sql = "delete from dept where deptno = 40";
            String sql1 = "update dept set dname = '销售部',loc = '天津' where deptno = 20";
            int count = stmt.executeUpdate(sql);
            int count1 = stmt.executeUpdate(sql1);
            System.out.println(count == 1 ? "删除成功" : "删除失败");
            System.out.println(count1 == 1 ? "更新成功" : "更新失败");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null){
                try {
                    stmt.close();
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