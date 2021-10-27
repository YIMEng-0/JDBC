package com.luobin.jdbc.day03;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 5:13 下午
 */

/**
 * JDBC 的事务：
 *      JDBC 中的事务是自动提交的
 *          只要执行任意的 DML 语句，就会进行自动的提交,这个是 JDBC 默认的事务行为，但是在实际的开发中，这显然是不合适的
 *          因为在开发的过程当中，是需要将一些语句记性捆绑执行的，要么同时成功，要么同时失败，所以需要使用事务；
 *          实际的开发当中，是需要DML 语句的同时执行的，才能完成，保证 DML 在同一个事务中，同时成功或者同时失败；
 *
 * 2、对于JDBC 是不是自动提交的验证
 */

/**
 * 下面的代码测试结果：
 *      只要 JDBC 进行执行了 DML 语句，执行一次，就会提交一次，在实际的开发中这种逻辑是不合适的；
 */

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/26 4:59 下午
 */

public class 事务的自动提交演示 {
    public static void main(String[] args) {
        // 初始化界面
        Map<String, String> userLoginInfo = initUI();

        // 验证用户名以及密码
        boolean loginSuccess = login(userLoginInfo);

        // 输出用户登录的结果
        System.out.println(loginSuccess ? "success" : "fail");
    }

    /**
     * 用户登录
     *
     * @param userLoginInfo 用户登录信息
     * @return true表示登陆成功； false 表示登陆失败
     */
    private static boolean login(Map<String, String> userLoginInfo) {
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

        String loginName = userLoginInfo.get("loginName");
        String loginPwd = userLoginInfo.get("loginPwd");

        try {
            // 1、加载驱动
            Class.forName(driver);

            // 2、进行数据库连接
            conn = DriverManager.getConnection(url, username, password);

            // 3、获取预编译的数据库操作对象
            String sql = "update dept set dname = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            // 第一次给占位符进行赋值操作
            ps.setString(1,"z部门");
            ps.setInt(2,30);
            int count = ps.executeUpdate(); // 执行SQL 语句
            System.out.println(count);

            // 第二次给占位符进行赋值操作
            ps = conn.prepareStatement(sql);
            ps.setString(1,"h部门");
            ps.setInt(2,20);
            int count1 = ps.executeUpdate(); // 执行SQL语句
            System.out.println(count1);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
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
        return loginSuccess;
    }


    /**
     * 初始化用户界面
     *
     * @return 返回用户输入的用户名以及密码等信息
     */
    private static Map<String, String> initUI() {
        Scanner s = new Scanner(System.in);

        System.out.println("用户名称：");
        String loginName = s.nextLine();

        System.out.println("输入密码：");
        String loginPwd = s.nextLine();

        Map<String, String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("loginName", loginName);
        userLoginInfo.put("loginPwd", loginPwd);

        return userLoginInfo;
    }
}