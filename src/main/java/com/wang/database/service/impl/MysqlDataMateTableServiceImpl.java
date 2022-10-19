package com.wang.database.service.impl;


import com.wang.database.dao.WordTableMapper;
import com.wang.database.service.DataMateTableService;
import com.wang.database.vo.Table;
import com.wang.database.vo.TableNameNote;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/19
 * @change 2021/12/19 by wangxiaofei for init
 **/
@Service
public class MysqlDataMateTableServiceImpl implements DataMateTableService {

    @Resource
    private WordTableMapper mapper;

    @Override
    public List<Table> getTableList() {

        //获取数据库名称
        String dateMateName = mapper.getDateMateName();
        if (StringUtils.isEmpty(dateMateName)){
            throw new RuntimeException("数据库名称获取失败");
        }
        //获取表格名称
        List<TableNameNote> tableNameNotes = mapper.getDateMateTableName(dateMateName);
        if (CollectionUtils.isEmpty(tableNameNotes)){
            throw new RuntimeException("数据库表获取失败");
        }

        //tableNameNote  获取表名
        List<String> tableNames = tableNameNotes.stream().map(TableNameNote::getTableName).collect(Collectors.toList());

        //设置对象表的备注
        List<Table> tables = mapper.selectTables(tableNames);
        for (Table table : tables) {
            for (TableNameNote tableNameNote : tableNameNotes) {
                if (tableNameNote.getTableName().equals(table.getTableName())){
                    table.setTableNameNote(tableNameNote.getTableComment());
                    break;
                }
            }
        }

        //获取表格属性
        if (CollectionUtils.isEmpty(tables)){
            throw new RuntimeException("数据库表字段获取失败");
        }

        return tables;
    }

    @Override
    public String getDateMateName() {
        String sql = "select database()";
        return sql;
    }

    @Override
    public String getDateMateTableName() {
        String sql = "SELECT TABLE_NAME as tableName,TABLE_COMMENT as tableComment FROM INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = ?";
        return sql;
    }

    @Override
    public String selectTables(List<String> size) {
        String sql = "SELECT table_name as tableName, COLUMN_NAME as colName, COLUMN_COMMENT as note, COLUMN_TYPE as type, CASE WHEN IS_NULLABLE = 'YES' THEN 'Y' ELSE '' END AS ifNull, IF (column_key = 'pri', 'PK', '') as rule FROM INFORMATION_SCHEMA. COLUMNS WHERE table_name in ";
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < size.size(); i++) {
            if (i == size.size()-1){
                builder.append("?");
            }else {
                builder.append("?,");
            }
        }
        builder.append(")");
        sql = sql + builder;
        return sql;
    }
}
