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
 * 对于 SQL 注入问题的合理解决：
 *      用户对于提供的信息是不参与 SQL 语句编译的过程，问题就会解决；
 *      为了保证用户输入的东西不参与编译，在这里，使用了 java.sql.PreparedStatement 继承了 java.sql.statement ；
 *
 *      PreparedStatement 属于预编译的数据库对象，原理是什么？
 *          预先对于 SQL 语句框架进行编译，然后对于 SQL 语句进行值得传递；
 */
public class UserLogin解决sql注入问题 {
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

            // 3、获取预编译的数据库操作对象
            // 建立了 SQL 语句的框架
            // ？ 一个  ？ 表示一个占位符，将来接受一个值，占位符不能使用 '' 进行括起来的
            String sql = "select * from t_user where loginName = ? and loginPwd = ?"; // ? 叫做占位符，只能填充占位符
            // 注意是 英语 ? 不是 中文 ？
            ps = conn.prepareStatement(sql);
            /**
             * 给占位符进行值得传递
             *      第一个 ？ 的下标是 1 第二个 ？ 下标是 2
             */
            // 在前面SQL 语句已经完成了编译工作，在这里随意传入，已经不起到作用了；破解不了了
            ps.setString(1,loginName);
            ps.setString(2,loginPwd);

            // 4、执行SQL
            // 下面的 sql 语句的正常书写中，'"+loginName+"' 只是判断其条件是否相等的一个条件，比如判断 loginName = loginName而已
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