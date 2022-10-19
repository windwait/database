package com.wang.database.dao;



import com.wang.database.vo.Table;
import com.wang.database.vo.TableNameNote;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/11/12
 * @change 2021/11/12 by wangxiaofei for init
 **/
@Mapper
@Repository
public interface WordTableMapper {

    /**
     * 获取数据库名称
     * @return
     */
    String getDateMateName();

    /**
     * 获取 表格名名称
     * @param mateName
     * @return
     */
    List<TableNameNote> getDateMateTableName(String mateName);

    /**
     * 获取表格属性
     * @param tableNames
     * @return
     */
    List<Table> selectTables(List<String> tableNames);
}
