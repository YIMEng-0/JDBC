package com.luobin.jdbc.day01;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest01 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        // 1、注册驱动
        try {
            java.sql.Driver driver = new Driver(); // 使用了多态机制，父类行的引用指向了子类型的对象
            DriverManager.registerDriver(driver);
            System.out.println("驱动连接成功！");

            // 2、获取连接
            String url = "jdbc:mysql://localhost:3306/data";
            String user = "root";
            String password = "9842213764";
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("数据库连接对象：" + conn);

            // 3、获取数据库操作对象
            // 进行专门的执行 SQL 语句
            stmt = conn.createStatement();

            // 4、执行SQL语句
            String sql = "insert into dept(deptno,dname,loc) values (60,'人事部','北京')";
            int count = stmt.executeUpdate(sql);
            System.out.println(count == 1 ? "保存成功" : "保存失败");

            // 5、处理查询结果集
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 6、释放资源
            // 为了保证资源一定被释放掉，在 finally 语句块中关闭资源，并且遵循从小到大的依次关闭
            //
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt!=null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/**
 * jdbc:mysql:（通信协议）//localhost（ip地址）:3306（端口号）/data（具体的数据库的名字）
 *
 * 什么是协议?有什么作用？
 *      通信协议：通信协议就是提前定义好的数据传送格式；
 *      数据包怎么传递数据，格式是提前制定好的；
 *      遵守同样的协议，双方就可以互相理解对象说的是什么；
 */