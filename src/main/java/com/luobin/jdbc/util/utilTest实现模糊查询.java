package com.luobin.jdbc.util;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/27 8:12 下午
 */

import java.sql.*;

/**
 * 1、测试 DBUtil 的使用
 * 2、进行模糊查询的书写
 */
public class utilTest实现模糊查询 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();

            // 获取预编译的SQL语句
            String sql = "select ename from emp where ename like ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"_A%");
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }
}
