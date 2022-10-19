package com.wang.database.service;



import com.wang.database.vo.Table;

import java.util.List;

/**
 * 连接数据源
 *
 * @param
 * @return  null
 * @since 1.0.0
 * @author wxf
 * @date 2021/12/20 11:08
 * @change 2021/12/20 11:08 by wxf for init
 */
public interface ConnectDataSource {

    /**
     * 连接库
     *
     * @param driver 驱动
     * @param url jdbc
     * @param username 用户名
     * @param password 密码
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/20 11:11
     * @change 2021/12/20 11:11 by wxf for init
     */
    List<Table> connectBase(String driver, String url, String username, String password);
}
