package com.wang.database.utils;

import java.sql.*;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/20
 * @change 2021/12/20 by wangxiaofei for init
 **/
public class JdbcConnectUtil {

    /**
     * 连接Mysql
     *
     * @param
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/20 19:54
     * @change 2021/12/20 19:54 by wxf for init
     */
    public static Connection getConnect(String driver,String url,String username,String password) throws Exception{
        Class.forName(driver);
        //获取连接
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        //释放资源
        if (resultSet !=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement !=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
