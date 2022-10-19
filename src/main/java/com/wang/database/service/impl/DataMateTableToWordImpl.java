package com.wang.database.service.impl;


import com.wang.database.config.CustomXWPFDocument;
import com.wang.database.service.DataMateTableToWord;
import com.wang.database.utils.WordTableUtil;
import com.wang.database.vo.Table;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2021/12/19
 * @change 2021/12/19 by wangxiaofei for init
 **/
@Service
public class DataMateTableToWordImpl implements DataMateTableToWord {
    @Override
    public void tableToWord(List<Table> tables, CustomXWPFDocument document) {

        Map<String, List<Table>> map = tables.stream().collect(Collectors.groupingBy(Table::getTableName));
        map = sortByKey(map);
        Set<Map.Entry<String, List<Table>>> entries = map.entrySet();
        double num = 5.00;
        double add = 0.01;
        for (Map.Entry<String, List<Table>> entry : entries) {
            XWPFParagraph paragraph1 = document.createParagraph();
            XWPFRun run1 = paragraph1.createRun();

            double result = num + add;
            add = add + 0.01;
            String formatResult = String.format("%.2f", result);
            run1.setText(formatResult+" " + "表名:" + entry.getValue().get(0).getTableNameNote());
            XWPFTable table = WordTableUtil.getTable(document,3+entry.getValue().size(), 5);
            WordTableUtil.fillNullData(table,0,0,3+entry.getValue().size(),5,null);
            WordTableUtil.fillReallyData(table,2,0,entry.getValue().size()+2,5,entry.getValue());
            XWPFTableCell cell = table.getRow(0).getCell(1);
            cell.setText(entry.getKey());


            XWPFParagraph paragraph2 = document.createParagraph();
            XWPFRun run2 = paragraph2.createRun();
            run2.setText("");
        }
    }

    public <K extends Comparable<? super K>, V > Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();

        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;

    }
}
