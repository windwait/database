package com.wang.database.service.impl;



import com.wang.database.service.DataMateTableService;
import com.wang.database.vo.Table;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/19
 * @change 2021/12/19 by wangxiaofei for init
 **/
@Service
public class OracleDataMateTableServiceImpl implements DataMateTableService {

    @Override
    public List<Table> getTableList() {
        return null;
    }

    @Override
    public String getDateMateName() {
        //查询库名
        String sql = "select name from v$database";
        return sql;
    }

    @Override
    public String getDateMateTableName() {
        String sql = "select TABLE_NAME as tableName,COMMENTS as tableComment from user_tab_comments";
        return sql;
    }

    @Override
    public String selectTables(List<String> size) {
        String param = null;
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
        param = String.valueOf(builder);
        //查询出表名 字段名 数据类型 字段长度 可空
        String sql = "  SELECT\n" +
                "      b.TABLE_NAME tableNameF,b.COLUMN_NAME as colName,a.COMMENTS note,b.DATA_TYPE type, b.data_length  colLength,\n" +
                "\t\t\tCASE b.nullable WHEN 'N' THEN '' ELSE 'Y' END ifNull\n" +
                "  FROM\n" +
                "      USER_TAB_COLUMNS b,USER_COL_COMMENTS a\n" +
                "  WHERE\n" +
                "      b.TABLE_NAME in " + param + " AND b.TABLE_NAME = a.TABLE_NAME AND b.COLUMN_NAME = a.COLUMN_NAME";
        return sql;
    }

    public String selectTablePri(List<String> size){
        String param = null;
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
        param = String.valueOf(builder);
        //查询表名和主键名字
        String sql = "select\n" +
                "\t\t\t col.table_name as tableName,col.column_name as tableCol \n" +
                "\t\t\tfrom\n" +
                "\t\t\t user_constraints con,user_cons_columns col\n" +
                "\t\t\twhere\n" +
                "\t\t\t con.constraint_name=col.constraint_name and con.constraint_type='P'\n" +
                "\t\t\t and col.table_name in " + param;
        return sql;
    }
}
