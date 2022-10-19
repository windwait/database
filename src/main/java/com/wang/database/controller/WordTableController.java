package com.wang.database.controller;



import com.wang.database.config.CustomXWPFDocument;
import com.wang.database.service.WordTableService;
import com.wang.database.vo.DataParam;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/11/12
 * @change 2021/11/12 by wangxiaofei for init
 **/
@RestController
@RequestMapping("/database")
@Api(tags = "数据库表生成")
@Validated
public class WordTableController {

    @Resource
    private WordTableService wordTableService;

    @PostMapping("/to")
    @ApiOperationSupport(order = 1, author = "wxf")
    @ApiOperation(value = "数据表", notes = "数据表生成", hidden = false)
    public void tableToWord(HttpServletResponse response, HttpServletRequest request,
                             DataParam vo) throws Exception{
        //设置生成word 相应头信息
        response.setContentType("application/msword");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据库表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".docx");

        //获取word  对象
        CustomXWPFDocument document = new CustomXWPFDocument();
        wordTableService.tableToWord(document,vo);
        document.write(response.getOutputStream());
    }
}
