package com.wang.database.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/23
 * @change 2021/12/23 by wangxiaofei for init
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "TablePriCol对象", description = "表名和主键名字")
public class TablePriCol implements Serializable {

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

    /**
     * 表名 备注
     */
    @ApiModelProperty(value = "字段名")
    private String tableCol;

}
