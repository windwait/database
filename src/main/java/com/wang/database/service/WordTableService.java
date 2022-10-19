package com.wang.database.service;


import com.wang.database.config.CustomXWPFDocument;
import com.wang.database.vo.DataParam;

public interface WordTableService {

    /**
     * tableToWord  数据转表格
     *
     * @param document 文档
     * @param dataParam 参数
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/12/23 16:55
     * @change 2021/12/23 16:55 by wxf for init
     */
    void tableToWord(CustomXWPFDocument document, DataParam dataParam);
}
