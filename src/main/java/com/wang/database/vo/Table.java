package com.wang.database.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/11/12
 * @change 2021/11/12 by wangxiaofei for init
 **/

@Data
@ApiModel(value = "Table对象", description = "表信息")
public class Table implements Serializable {

    /**
     * 列名
     */
    @ApiModelProperty(value = "列名")
    private String colName;

    /**
     * 列名 备注
     */
    @ApiModelProperty(value = "列名 备注")
    private String note;

    /**
     * 列类型
     */
    @ApiModelProperty(value = "列类型")
    private String type;

    /**
     * 列是否为空
     */
    @ApiModelProperty(value = "列是否为空")
    private String ifNull;

    /**
     * 是否主键
     */
    @ApiModelProperty(value = "是否主键")
    private String rule;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

    /**
     * 表名 备注
     */
    @ApiModelProperty(value = "表名备注")
    private String tableNameNote;

    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度")
    private Integer colLength;

    public Table() {
    }

    public Table(String colName, String note, String type, String ifNull,  String tableName, Integer colLength) {
        this.colName = colName;
        this.note = note;
        this.type = type;
        this.ifNull = ifNull;
        this.tableName = tableName;
        this.colLength = colLength;
    }

    public Table(String colName, String note, String type, String ifNull, String rule, String tableName) {
        this.colName = colName;
        this.note = note;
        this.type = type;
        this.ifNull = ifNull;
        this.rule = rule;
        this.tableName = tableName;
    }

    public Table(String colName, String note, String type, String ifNull, String rule, String tableName, String tableNameNote) {
        this.colName = colName;
        this.note = note;
        this.type = type;
        this.ifNull = ifNull;
        this.rule = rule;
        this.tableName = tableName;
        this.tableNameNote = tableNameNote;
    }
}
