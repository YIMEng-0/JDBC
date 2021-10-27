package com.luobin.jdbc.day02;

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

public class UserLogin解决sql注入问题精简版本04 {
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
            String sql = "select * from t_user where loginName = ? and loginPwd = ?"; // ? 叫做占位符，只能填充占位符
            ps = conn.prepareStatement(sql); // 将SQL语句进行传递，然后进行编译，编译成功之后，在编译的里面留下来了两个值空间，位置，在下面的语句中进行值得填充
            ps.setString(1,loginName);
            ps.setString(2,loginPwd);

            // 4、执行SQL
            rs = ps.executeQuery();

            // 5、处理结果数据集
            if (rs.next()) {
                loginSuccess = true;
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