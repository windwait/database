package com.wang.database.service.impl;


import com.wang.database.config.CustomXWPFDocument;
import com.wang.database.config.McpException;
import com.wang.database.constant.DataMateNameConstant;
import com.wang.database.dao.WordTableMapper;
import com.wang.database.service.ConnectDataSource;
import com.wang.database.service.DataMateTableToWord;
import com.wang.database.service.WordTableService;
import com.wang.database.vo.DataParam;
import com.wang.database.vo.Table;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/11/12
 * @change 2021/11/12 by wangxiaofei for init
 **/
@Service
public class WordTableServiceImpl implements WordTableService {

    @Resource
    private WordTableMapper mapper;

    @Resource
    private DataMateTableToWord dataMateTableToWord;

    @Resource
    private MysqlConnectBase mysqlConnectBase;

    @Resource
    private OracleConnectBase oracleConnectBase;

    @Resource
    private CirroConnectBase cirroConnectBase;

    @Override
    public void tableToWord(CustomXWPFDocument document, DataParam dataParam) {

        //获取数据源信息
        ConnectDataSource connectDataSource = getDataMateTable(dataParam.getDataType(),dataParam);

        //获取数据表信息
        List<Table> tableList = connectDataSource.connectBase(dataParam.getDriver(),dataParam.getUrl(),dataParam.getUsername(),dataParam.getPassword());

        //转word
        dataMateTableToWord.tableToWord(tableList,document);
    }

    /**
     * 获取数据源
     * @param dataMateName
     * @return
     */
    private ConnectDataSource getDataMateTable(String dataMateName,DataParam dataParam){

        ConnectDataSource connectDataSource = null;

        if (dataMateName.equalsIgnoreCase(DataMateNameConstant.MYSQL)){
            String url = "jdbc:mysql://" + dataParam.getUrl() + "?serverTimezone=GMT%2B8&allowMultiQueries=true";
            dataParam.setUrl(url);
            connectDataSource = mysqlConnectBase;
        }else if (dataMateName.equalsIgnoreCase(DataMateNameConstant.CIRRO)){
            connectDataSource = cirroConnectBase;
        }else if (dataMateName.equalsIgnoreCase(DataMateNameConstant.ORACLE)){
            String url = "jdbc:oracle:thin:@" + dataParam.getUrl();
            dataParam.setUrl(url);
            connectDataSource = oracleConnectBase;
        }else {
            throw new McpException("您输入的数据库类型暂不支持");
        }
        return connectDataSource;
    }
}
