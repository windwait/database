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
 * @Date 2021/11/15
 * @change 2021/11/15 by wangxiaofei for init
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "TableNameNote对象", description = "table名称和备注")
public class TableNameNote implements Serializable {

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

    /**
     * 表名 备注
     */
    @ApiModelProperty(value = "表名 备注")
    private String tableComment;
}
