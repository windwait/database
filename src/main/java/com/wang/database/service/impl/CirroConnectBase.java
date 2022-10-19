package com.wang.database.service.impl;

import com.wang.database.service.ConnectDataSource;
import com.wang.database.vo.Table;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/21
 * @change 2021/12/21 by wangxiaofei for init
 **/
@Service
public class CirroConnectBase implements ConnectDataSource {
    @Override
    public List<Table> connectBase(String driver, String url, String username, String password) {
        return null;
    }
}
