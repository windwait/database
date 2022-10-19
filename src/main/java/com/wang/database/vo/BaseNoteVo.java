package com.wang.database.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/29
 * @change 2021/12/29 by wangxiaofei for init
 **/
@Data
public abstract class BaseNoteVo {

    private String pKid;

    private String sjPkid;

    private List<BaseNoteVo> children;

}
