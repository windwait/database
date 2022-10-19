package com.wang.database.utils;



import com.wang.database.vo.Table;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;

/**
 * @Description word 表格工具雷
 * @Author wxf
 * @Date 2021/11/5
 * @change 2021/11/5 by wangxiaofei for init
 **/
public class WordTableUtil {



    /**
     * 创建 几行 几列的表格
     * document docx
     * rows  行数
     * cols  列数
     * @return
     */
    public static XWPFTable getTable(XWPFDocument document, int rows,int cols){
        //创建表格
        XWPFTable table = document.createTable(rows, cols);
        //设置表格居中
        //table.setTableAlignment(TableRowAlign.CENTER);
        //表格属性
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblPr = ctTbl.getTblPr() == null ? ctTbl.addNewTblPr() : ctTbl.getTblPr();

        //表格宽度
        CTTblWidth ctTblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        ctTblWidth.setW(new BigInteger("8500"));
        ctTblWidth.setType(STTblWidth.DXA);
        return table;
    }



    /**
     *
     * @param table 表格
     * @param rows   多少行
     * @param cols   多少列
     */
    public static void fillData(XWPFTable table, int beginRow,int endCol,int rows,int cols,List<Table> list) throws Exception{

        CTTblBorders ctTblBorders = table.getCTTbl().getTblPr().addNewTblBorders();

        int temp = 0;
        table.setCellMargins(10,80,10,80);
        for (int i = beginRow; i < rows; i++) {
            XWPFTableRow row = table.getRow(i);
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(360));
            int flag = 0;
            for (int j = endCol; j < cols; j++) {

                XWPFTableCell cell = row.getCell(j);
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                if (CollectionUtils.isEmpty(list)){
                    CTTc cttc = cell.getCTTc();
                    CTP ctp = cttc.getPList().get(0);
                    CTPPr ctppr = ctp.getPPr();
                    if (ctppr == null) {
                        ctppr = ctp.addNewPPr();
                    }
                    CTJc ctjc = ctppr.getJc();
                    if (ctjc == null) {
                        ctjc = ctppr.addNewJc();
                    }
                    //水平居中
                    ctjc.setVal(STJc.LEFT);

                    cell.setText("");
                    //垂直居中
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    CTTcPr tcpr = cell.getCTTc().addNewTcPr();

                    CTTblWidth cellw = tcpr.addNewTcW();

                    cellw.setType(STTblWidth.DXA);

                    cellw.setW(BigInteger.valueOf(360*5));

                }else {

                    Table Table = list.get(temp);
                    Class cls = Table.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    Field f = fields[flag];
                    f.setAccessible(true);
                    String text = (String) f.get(Table);

                    flag++;
                    cell.setText(text);
                }
            }
            temp++;
        }
    }

    /**
     *
     * @param table 表格
     * @param rows   多少行
     * @param cols   多少列
     */
    public static void fillNullData(XWPFTable table, int beginRow,int endCol,int rows,int cols,List<Table> list){

        try {
            fillData(table,beginRow,endCol,rows,cols,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillHeadData(table,rows,cols);
    }

    /**
     *
     * @param table 表格
     * @param rows   多少行
     * @param cols   多少列
     */
    public static void fillReallyData(XWPFTable table, int beginRow,int endCol,int rows,int cols,List<Table> list){
        try {
            fillData(table,beginRow,endCol,rows,cols,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 横向合并
     * table     合并的表格   0,1这种表格
     * row       合并的行号   0开始
     * beginCell 开始的单元格 0开始
     * endCell   结束的单元格
     */
    public static void transverseMerge(XWPFTable table,int row,int beginCell,int endCell){
        if (endCell - beginCell == 1){
            table.getRow(row).getCell(beginCell).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            table.getRow(row).getCell(endCell).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        }else {
            for (int i = beginCell; i <= endCell; i++) {
                if (i == beginCell){
                    table.getRow(row).getCell(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                }else {
                    table.getRow(row).getCell(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                }
            }
        }

    }

    public static void transverseAiZheMerge(XWPFTable table,int row,int beginCell,int endCell){
        table.getRow(row).getCell(beginCell).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        table.getRow(row).getCell(endCell).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
    }

    /**
     * 纵向合并
     * table     合并的表格
     * row       合并的行号   0开始
     * beginCell 开始的单元格 0开始
     * endCell   结束的单元格
     */
    public static void portraitMerge(XWPFTable table,int col,int beginRow,int endRow,int noMerge){

        for (int i = beginRow; i <= endRow; i++) {
            if (i == beginRow){
                table.getRow(i).getCell(col).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            }else {
                //如果不是最后一列那么设置 被合并的单元格值为空  在word中显示正常  内容不然会出现重复在PDF中
                if (col!=noMerge){
                    table.getRow(i).getCell(col).removeParagraph(0);
                }
                table.getRow(i).getCell(col).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    /**
     * 纵向合并
     * table     合并的表格
     * row       合并的行号   0开始
     * beginCell 开始的单元格 0开始
     * endCell   结束的单元格
     */
    public static void portraitMerge(XWPFTable table,int col,int beginRow,int endRow){

        for (int i = beginRow; i <= endRow; i++) {
            if (i == beginRow){
                table.getRow(i).getCell(col).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            }else {
                table.getRow(i).getCell(col).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 纵向合并
     * table 需要合并的表格
     * col   需要合并的列
     * beginRow 开始合并的行
     * endRow   结束合并的行
     */
    public static void portraitMerge2(XWPFTable table,int col,int beginRow,int endRow){
        //获取 列合并是否继续对象
        CTVMerge ctvMergeRestart = CTVMerge.Factory.newInstance();
        CTVMerge ctvMergeContinue = CTVMerge.Factory.newInstance();
        ctvMergeRestart.setVal(STMerge.RESTART);
        ctvMergeContinue.setVal(STMerge.CONTINUE);

        for (int i = beginRow; i <= endRow; i++) {
            if (i == beginRow){

                table.getRow(i).getCell(col).getCTTc().getTcPr().setVMerge(ctvMergeRestart);
            }else {
                table.getRow(i).getCell(col).getCTTc().getTcPr().setVMerge(ctvMergeContinue);
            }
        }
    }

    /**
     * 设置单元格颜色
     *
     *
     * @param cell 单元格
     * @param colorCode 颜色编码
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/11/15 11:48
     * @change 2021/11/15 11:48 by wxf for init
     */
    public static void setCellColor(XWPFTableCell cell,String colorCode){
        if (StringUtils.isEmpty(colorCode)){
            cell.setColor("E7E3E3");
        }else {
            cell.setColor(colorCode);
        }
    }

    /**
     * 设置表格线条样式 解决PDF表格线条丢失
     *
     * @param table
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/11/12 14:28
     * @change 2021/11/12 14:28 by wxf for init
     */
    public static void setTableBorder(XWPFTable table){
        //获取表格  边框线条
        CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder=borders.addNewInsideH();
        //表格边框类型
        hBorder.setVal(STBorder.Enum.forString("single"));
        //设置表格线条4  解决转PDF 后表格线条丢失
        hBorder.setSz(new BigInteger("4"));


        CTBorder vBorder=borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("single"));
        vBorder.setSz(new BigInteger("4"));


        CTBorder lBorder=borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("single"));
        lBorder.setSz(new BigInteger("4"));


        CTBorder rBorder=borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("4"));


        CTBorder tBorder=borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("single"));
        tBorder.setSz(new BigInteger("4"));


        CTBorder bBorder=borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("single"));
        bBorder.setSz(new BigInteger("4"));

        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblLayoutType t = tblPr.isSetTblLayout()?tblPr.getTblLayout():tblPr.addNewTblLayout();
        t.setType(STTblLayoutType.FIXED);//使布局固定，不随内容改变宽度

        //table.getRow(0).setHeight(500);
    }

    /**
     *
     * @param table 表格
     * @param rows  行
     * @param cols   列
     */
    public static void fillHeadData(XWPFTable table,int rows,int cols){
        String colName = "列名";
        String note = "中文说明";
        String type = "数据类型（精度范围）";
        String ifNull = "空/非空";
        String rule = "约束条件";
        String tableName = "表名";
        String tableNote = "备注";
        //0 0 设置为表名
        XWPFTableCell cell = table.getRow(0).getCell(0);
        cell.setText(tableName);
        setCellColor(cell,null);

        XWPFTableRow row = table.getRow(0);
        CTTrPr ctTrPr = row.getCtRow().addNewTrPr();
        CTHeight height = ctTrPr.addNewTrHeight();
        height.setVal(BigInteger.valueOf(360));

        XWPFTableRow rowEnd = table.getRow(rows-1);
        CTTrPr ctTrPrEnd = row.getCtRow().addNewTrPr();
        CTHeight heightEnd = ctTrPr.addNewTrHeight();
        height.setVal(BigInteger.valueOf(360));
        //横向合并
        transverseMerge(table,0,1,4);

        XWPFTableCell cellEnd = table.getRow(rows-1).getCell(0);
        cellEnd.setText(tableNote);

        setCellColor(cellEnd,null);

        //横向合并
        transverseMerge(table,rows-1,1,4);


        XWPFTableCell cell1 = table.getRow(1).getCell(0);
        cell1.setText(colName);
        setCellColor(cell1,null);
        XWPFTableCell cell2 = table.getRow(1).getCell(1);
        cell2.setText(note);
        setCellColor(cell2,null);
        XWPFTableCell cell3 = table.getRow(1).getCell(2);
        cell3.setText(type);
        setCellColor(cell3,null);
        XWPFTableCell cell4 = table.getRow(1).getCell(3);
        cell4.setText(ifNull);
        setCellColor(cell4,null);
        XWPFTableCell cell5 = table.getRow(1).getCell(4);
        cell5.setText(rule);
        setCellColor(cell5,null);

    }
}


