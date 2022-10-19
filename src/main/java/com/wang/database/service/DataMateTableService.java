package com.wang.database.service;



import com.wang.database.vo.Table;

import java.util.List;

/**
 * 数据源
 *
 * @param
 * @return  null
 * @since 1.0.0
 * @author wxf
 * @date 2021/12/19 17:41
 * @change 2021/12/19 17:41 by wxf for init
 */
public interface DataMateTableService {

    /**
     * 获取当前连接的数据库信息 数据转word
     *
     * @param
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/19 17:41
     * @change 2021/12/19 17:41 by wxf for init
     */
    List<Table> getTableList();

    /**
     * 获取数据库名
     *
     * @param
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/20 20:54
     * @change 2021/12/20 20:54 by wxf for init
     */
    String getDateMateName();

    /**
     * 获取数据库表名
     *
     * @param
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/20 20:55
     * @change 2021/12/20 20:55 by wxf for init
     */
    String getDateMateTableName();

    /**
     * 获取表的详细信息
     *
     * @param size 数据库表名数量
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/20 20:56
     * @change 2021/12/20 20:56 by wxf for init
     */
    String selectTables(List<String> size);
}
