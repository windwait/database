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
 * @Date 2021/12/21
 * @change 2021/12/21 by wangxiaofei for init
 **/
@ApiModel(value = "DataParam对象", description = "数据参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataParam implements Serializable {

    /**
     * 驱动
     */
    @ApiModelProperty(value = "驱动")
    private String driver;
    /**
     * jdbc url
     */
    @ApiModelProperty(value = "jdbc url")
    private String url;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    private String dataType;
}
