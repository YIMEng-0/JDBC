package com.luobin.jdbc.day01;

/**
 * @author Doraemon
 * @version 1.0
 * @date 2021/10/26 3:16 下午
 */

/**
 * 使用反射机制进行驱动的注册
 */
public class JDBCTest03 {
    public static void main(String[] args) {
        // 1、注册驱动
        try {
            // 为什么使用起来比较常用？
            // 因为参数是一个字符串，字符串可以写到配置文件中 .properties 中
            // 下面的代码不需要使用返回值，因为知识需要一个类加载的动作而已，也就是进行了驱动的注册就行
            Class.forName("com.mysql.jdbc.Driver"); // 使用了类加载的动作
            System.out.println("数据库驱动加载正常！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
