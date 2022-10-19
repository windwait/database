package com.wang.database.config;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import java.io.FileInputStream;

/**
 * @Description 自定义document 主要是处理图片的插入
 * @Author wxf
 * @Date 2021/11/12
 * @change 2021/11/12 by wangxiaofei for init
 **/
public class CustomXWPFDocument extends XWPFDocument {

    /**
     * 设置单元格图片
     *
     * @param document 文档
     * @param table 表格
     * @param row 行
     * @param col 列
     * @param filePath 图片路径
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/11/12 14:47
     * @change 2021/11/12 14:47 by wxf for init
     */
    public void setPicture(CustomXWPFDocument document, XWPFTable table,int row,int col,String filePath,int width,int height){
        String id = null;
        try {
            id = document.addPictureData(new FileInputStream(filePath), XWPFDocument.PICTURE_TYPE_PNG);
        } catch (Exception e) {
            System.out.println("获取替换的图片出现了异常");
            e.printStackTrace();
        }
        int id2 = document.getAllPackagePictures().size()+1;
        XWPFTableCell cell = table.getRow(row).getCell(col);
        XWPFRun run = cell.getParagraphs().get(0).createRun();
        CTInline ctInline = run.getCTR().addNewDrawing().addNewInline();
        document.createPic(id,id2,width,height,ctInline);

    }
    /**
     * 创建图片
     *
     * @param blipId
     * @param id
     * @param width 图片宽度
     * @param height 图片高度
     * @param inline
     * @return  null
     * @since 1.0.0
     * @author wxf
     * @date 2021/11/12 14:39
     * @change 2021/11/12 14:39 by wxf for init
     */
    public void createPic(String blipId, int id, int width, int height, CTInline inline) {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;


        String picXml = "" +
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                " <pic:nvPicPr>" +
                "    <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "    <pic:cNvPicPr/>" +
                " </pic:nvPicPr>" +
                " <pic:blipFill>" +
                "    <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "    <a:stretch>" +
                "       <a:fillRect/>" +
                "    </a:stretch>" +
                " </pic:blipFill>" +
                " <pic:spPr>" +
                "    <a:xfrm>" +
                "       <a:off x=\"0\" y=\"0\"/>" +
                "       <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "    </a:xfrm>" +
                "    <a:prstGeom prst=\"rect\">" +
                "       <a:avLst/>" +
                "    </a:prstGeom>" +
                " </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";



        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);


        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

    public static void main(String[] args) {
        String property = System.getProperty("os.name");
        System.out.printf(property);
    }
}

