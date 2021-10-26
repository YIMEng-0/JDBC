package com.luobin.jdbc.day01;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/26 3:34 下午
 */
public class JDBCTest处理查询结果集 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // 进行属性文件的读取，使用资源绑定
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        try {
            // 1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 2、获取连接
            conn = DriverManager.getConnection(url,username,password);

            // 3、获取数据库操作对象
            stmt = conn.createStatement();

            // 4、执行sql
            /**
             * executeUpdate(insert/delete/update)
             * executeQuery(select)
             */
            String sql = "select empno as a,ename,sal from emp";
            rs = stmt.executeQuery(sql); // execuQuery 专门执行查询语言的；返回单个的 result 对象
            /** rs 里面实际存储的数据
             * 7369	SMITH	800.00
             * 7499	ALLEN	1600.00
             * 7521	WARD	1250.00
             * 7566	JONES	2975.00
             * 7654	MARTIN	1250.00
             * 7698	BLAKE	2850.00
             * 7782	CLARK	2450.00
             * 7788	SCOTT	3000.00
             * 7839	KING	5000.00
             * 7844	TURNER	1500.00
             * 7876	ADAMS	1100.00
             * 7900	JAMES	950.00
             * 7902	FORD	3000.00
             * 7934	MILLER	1300.00
             */

            // 5、处理查询结果数据集
//            boolean flag = rs.next(); // true 存在数据在返回的数据集当中
//            System.out.println(flag);

            while (rs.next()) { // 当光标指向的行存在数据的时候，进行数据的读取
                // 取出来数据
                // getString() 方法的特点：
                // 不管数据库中存在的数据是什么形式，都是通过 String  进行取出来的；
                // 除了可以使用 String 进行数据的取出来之外，还可以使用 int double 进行数据的取出来
                int empno = rs.getInt("a");// JDBC 所有的下标从 1 开始，不是从 0 开始的；表示 第一列
                String ename = rs.getString("ename"); // 2 表示第二列
                double sal = rs.getDouble("sal"); // 3 表示第三列
                // 考虑到上面的程序健壮性，不使用列数，使用了列的标签进行下面的结果单额处理
                System.out.println("empno\t" + empno + "\tename\t" + ename + "\tsal\t" + sal);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // 6、进行资源释放以及关闭 conn
            if (rs != null) {
                try {
                    rs.close();
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