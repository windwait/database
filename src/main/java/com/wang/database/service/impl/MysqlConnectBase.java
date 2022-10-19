package com.wang.database.service.impl;

import com.wang.database.config.McpException;
import com.wang.database.service.ConnectDataSource;
import com.wang.database.utils.JdbcConnectUtil;
import com.wang.database.vo.Table;
import com.wang.database.vo.TableNameNote;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/20
 * @change 2021/12/20 by wangxiaofei for init
 **/
@Service
public class MysqlConnectBase implements ConnectDataSource {

    @Resource
    private MysqlDataMateTableServiceImpl mysqlDataMateTable;

    @Override
    public List<Table> connectBase(String driver, String url, String username, String password) {

        List<Table> tables = null;
        Connection connection = null;
        try {
            //注册驱动
            connection = JdbcConnectUtil.getConnect(driver,url,username,password);

            //获取数据库名称
            String dateMateName = getDateMateName(connection);

            //获取数据库表名和备注
            List<TableNameNote> list = getDateMateTableName(connection, dateMateName);

            //tableNameNote  获取表名
            List<String> tableNames = list.stream().map(TableNameNote::getTableName).collect(Collectors.toList());

            //获取表详细信息
            tables = selectTables(connection, tableNames);

            //设置对象表的备注
            for (Table table : tables) {
                for (TableNameNote tableNameNote : list) {
                    if (tableNameNote.getTableName().equals(table.getTableName())){
                        table.setTableNameNote(tableNameNote.getTableComment());
                        break;
                    }
                }
            }

        }catch (Exception e){
            System.out.println("数据库连接异常");
            e.printStackTrace();
        }

        if (CollectionUtils.isEmpty(tables)){
            throw new McpException("数据库表信息为空");
        }

        return tables;

    }

    /**
     * getDateMateName 获取数据库名称
     *
     * @param connection 连接
     * @return 数据库名
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/21 11:58
     * @change 2021/12/21 11:58 by wxf for init
     */
    public String getDateMateName(Connection connection){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String dateMateName = null;
        try {
            //执行sql
            String dateMateNameSql = mysqlDataMateTable.getDateMateName();
            //获取数据库操作对象
            statement = connection.prepareStatement(dateMateNameSql);

            resultSet = statement.executeQuery(dateMateNameSql);

            //处理查询结果集
            while (resultSet.next()){
                //jdbc 中从1 开始
                dateMateName = resultSet.getString(1);
            }

        }catch (Exception e){
            System.out.println("数据库异常--获取数据库名称");
            e.printStackTrace();
        }

        return dateMateName;
    }

    /**
     * getDateMateTableName 获取数据库所有的表
     *
     * @param connection 连接
     * @param dateMateName 数据库名称
     *
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/21 11:58
     * @change 2021/12/21 11:58 by wxf for init
     */
    public List<TableNameNote> getDateMateTableName(Connection connection, String dateMateName){
        List<TableNameNote> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            String dateMateTableNameSql = mysqlDataMateTable.getDateMateTableName();

            statement = connection.prepareStatement(dateMateTableNameSql);

            statement.setString(1,dateMateName);

            resultSet = statement.executeQuery();

            //处理查询结果集
            String tableName = null;
            String tableComment = null;
            TableNameNote tableNameNote = null;

            while (resultSet.next()){
                //jdbc 中从1 开始
                tableName = resultSet.getString(1);
                tableComment = resultSet.getString(2);
                tableNameNote = new TableNameNote(tableName,tableComment);
                list.add(tableNameNote);
            }
        }catch (Exception e){
            System.out.println("数据库异常--获取数据库表名");
            e.printStackTrace();
        }

        return list;
    }

    /**
     * selectTables 获取表信息
     *
     * @param connection 连接
     * @param tableNames 表名
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/21 11:59
     * @change 2021/12/21 11:59 by wxf for init
     */
    public List<Table> selectTables(Connection connection, List<String> tableNames){
        List<Table> tables = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = mysqlDataMateTable.selectTables(tableNames);

            statement = connection.prepareStatement(sql);


            for (int i = 0; i < tableNames.size(); i++) {
                int j = i + 1;
                statement.setString(j,tableNames.get(i));
            }

            //colName note type ifNull rule tableName tableNameNote
            String colName = null;
            String note = null;
            String type = null;
            String ifNull = null;
            String rule = null;
            String tableNameF = null;
            String tableNameNoteF = null;
            Table table = null;

            resultSet = statement.executeQuery();
            while (resultSet.next()){
                tableNameF =  resultSet.getString(1);
                colName = resultSet.getString(2);
                note = resultSet.getString(3);
                type = resultSet.getString(4);
                ifNull = resultSet.getString(5);
                rule = resultSet.getString(6);

                table = new Table(colName, note, type, ifNull, rule, tableNameF);
                tables.add(table);
            }
        }catch (Exception e){
            System.out.println("数据库异常--获取表详细信息");
            e.printStackTrace();
        }finally {
            JdbcConnectUtil.closeResources(connection,statement,resultSet);
        }
        return tables;
    }
}
