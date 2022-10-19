package com.wang.database.service;



import com.wang.database.config.CustomXWPFDocument;
import com.wang.database.vo.Table;

import java.util.List;

/**
 * 获取数据库 信息转word
 *
 * @param
 * @return  null
 * @since 1.0.0
 * @author wxf
 * @date 2021/12/19 16:20
 * @change 2021/12/19 16:20 by wxf for init
 */
public interface DataMateTableToWord {

    /**
     * 转成word
     *
     * @param tables 数据库表数据
     * @param document word
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/19 17:28
     * @change 2021/12/19 17:28 by wxf for init
     */
    void tableToWord(List<Table> tables, CustomXWPFDocument document);
}
