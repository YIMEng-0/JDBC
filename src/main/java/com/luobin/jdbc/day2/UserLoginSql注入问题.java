package com.luobin.jdbc.day2;

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

/**
 * 解决 sql 注入问题的关键是什么？
 *      即使用户在输入的过程中进行了输入，但是不起到相关的作用；
 */
public class UserLoginSql注入问题 {
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
        Statement stmt = null;
        ResultSet rs = null;

        // 进行配置文件的获取
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

            // 3、进行sql 运行环境的创建
            stmt = conn.createStatement();

            // 4、进行SQL 语句的书写
            // 下面的 sql 语句的正常书写中，'"+loginName+"' 只是判断其条件是否相等的一个条件，比如判断 loginName = loginName而已
            String sql = "select * from t_user where loginName = '" + loginName + "' and loginPwd = '" + loginPwd + "'";
            rs = stmt.executeQuery(sql);
            // 5、执行SQL 语句

            if (rs.next()) {
                String getScannerUserName = rs.getString("loginName");
                System.out.println(getScannerUserName);
                String getScannerUserPwd = rs.getString("loginPwd");
                System.out.println(getScannerUserPwd);

                System.out.println(loginName);
                System.out.println(loginPwd);
                if (getScannerUserName.equals(loginName) && getScannerUserPwd.equals(loginPwd)) {
                    loginSuccess = true;
                }
            }

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

            if (stmt != null) {
                try {
                    stmt.close();
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