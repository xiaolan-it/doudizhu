/*
 * 文件名: DetailsHourCsvView.java
 * 版权: Copyright 2015 中星天下 Tech. Co. Ltd. All Rights Reserved.
 */
package com.jifeng.report.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jifeng.common.util.HttpRequestHelper;
import com.jifeng.security.entity.UserSetting;

/**
 * <成交率数据分析>
 * <p>
 * <功能详细描述> 数据导出csv
 * <p>
 * @author linzechun
 * @version [<版本号>, 2016年5月17日]
 * @see [<相关类>/<相关方法>]
 * @since [<产品>/<模块版本>]
 */

public class ConversionStatisticKPICsvView {
    //转换千位符的字段
    private String numberFormatStr = "-dq-tb-hb-enters-suspendNumber-entersSum-dqSum-entersSums-dqSums-";
    DecimalFormat df = new DecimalFormat("###,###.###") ;   // 千位符
    
    @SuppressWarnings("unchecked")
    public HSSFWorkbook buildExcelDocument(Map<String, Object> model, UserSetting userSetting, String type) throws Exception {
        Object chartType = model.get("chartType");
        HSSFWorkbook workbook = new HSSFWorkbook();
        String language = userSetting.getLanguage();
        String preStr = "";
        String afterStr = "";
        String sheelTitle = "";
        String userType = model.get("usertype").toString();
        String[] titles = null;
        String[] titleKeys = null;
        //当前，同步，环比（标题）
        String[] colTitles = new String[]{getMessage(language, "CrossMetricsByPeriod.Current"), getMessage(language, "label.prior.year"),
                getMessage(language, "label.prior.period") };
        //当前，同步，环比（数据）
        String[] colTitleKeys = new String[]{"dq", "tb", "hb" };
        String queryType = model.get("type").toString();
        boolean childFlag = "1031".equals(userType);
        //报表类型，店铺层级，报表日期，报表日历（标题）
        String[] sheetTitles = new String[]{getMessage(language, "statistic.export.title.type"), getMessage(language, "statistic.export.title.storeHierarchy"),
                getMessage(language, "statistic.export.title.date"), getMessage(language, "statistic.export.title.calendar") };
      //报表类型，店铺层级，报表日期，报表日历（数据字段）
        String[] sheetTitlekeys = new String[]{"title", "hierarchyName", "queryDate", "calendarType" };
        if (childFlag) {
            //店铺 楼层 品牌 品牌编号 业态（标题）
            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.hierarchy.level.floor"),
                    getMessage(language, "label.childStore.name"), getMessage(language, "label.childStore.code"),
                    getMessage(language, "label.store.category") };
            //店铺 楼层 品牌 品牌编号 业态（数据字段）
            titleKeys = new String[]{"storePname", "floor", "name", "code", "categoryName" };
        }
        String[] totalShowCols = null;
        // 根据类型设置标题 及数据符号（$ %)
        if ("exits".equals(type)) {// 客流量
          //报表类型，店铺层级，报表日期，报表日历 查看类型（标题）
            sheetTitles = new String[]{getMessage(language, "statistic.export.title.type"), getMessage(language, "statistic.export.title.storeHierarchy"),
                    getMessage(language, "statistic.export.title.date"), getMessage(language, "statistic.export.title.calendar"),
                    getMessage(language, "statistic.export.title.dataType") };
            //报表类型，店铺层级，报表日期，报表日历   查看类型（数据字段）
            sheetTitlekeys = new String[]{"title", "hierarchyName", "queryDate", "calendarType", "categoryType" };
            
            sheelTitle = getMessage(language, null!=chartType?"110217":"110201");
            Integer hType = Integer.parseInt(model.get("htype").toString());
            if (!queryType.equals("D")) {
                // "进","出","滞留时间"
                colTitles = new String[]{getMessage(language, "oper.view.enter"), getMessage(language, "oper.view.out"),
                        getMessage(language, "oper.view.residence.time") };
                colTitleKeys = new String[]{"enters", "exits", "suspendMinute" };
            } else {
                // "进","出","滞留时间","滞留人数"
                colTitles = new String[]{getMessage(language, "oper.view.enter"), getMessage(language, "oper.view.out"),
                        getMessage(language, "oper.view.residence.time"), getMessage(language, "oper.view.residence.number") };
                colTitleKeys = new String[]{"enters", "exits", "suspendMinute", "suspendNumber" };
            }
            //进出口，楼层不显示 "滞留时间","滞留人数"
            if(hType ==1041 || hType == 1050){
                colTitles = new String[]{getMessage(language, "oper.view.enter"), getMessage(language, "oper.view.out")};
                colTitleKeys = new String[]{"enters", "exits"};
            }
           
            if(null!=chartType){//客流量占比
                colTitles = new String[] { getMessage(language, "overview.current.traffic"),
                        getMessage(language, "overview.tb.traffic"), getMessage(language, "overview.hb.traffic"), getMessage(language, "overview.current.traffic.rate") };
                colTitleKeys = new String[] { "enters", "tb","hb", "dqStatisticRate" };
            }
            switch (hType)
                {
                    case 1030:
                        //店铺
                        titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger"), getMessage(language, "oper.view.average.residence.number")};
                        titleKeys = new String[]{"name","entersSum","dqSum","sumSuspendMinute" };
                        totalShowCols = new String[]{"entersSums","dqSums","sumSuspendMinutes"};
                        
                        if(null!=chartType){//客流量占比
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                            titleKeys = new String[]{"name","entersSum","tbSum","hbSum","dqStatisticRateSum" };
                            totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                        }
                        
                        model.put("categoryType", getMessage(language, "label.hierarchy.level.store"));
                        break;
                    case 1031:
                        if (childFlag) {
                          //店铺 品牌 楼层 品牌编码
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.childStore.name"),
                                    getMessage(language, "label.hierarchy.level.floor"), getMessage(language, "label.childStore.code"), getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger") , getMessage(language, "oper.view.average.residence.number")};
                            titleKeys = new String[]{"storePname", "name", "floor", "code","entersSum","dqSum" ,"sumSuspendMinute"};
                            totalShowCols = new String[]{"entersSums","dqSums","sumSuspendMinutes"};
                            if(null!=chartType){//客流量占比
                                titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.childStore.name"),
                                        getMessage(language, "label.hierarchy.level.floor"), getMessage(language, "label.childStore.code"), getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                                titleKeys = new String[]{"storePname", "name", "floor", "code","entersSum","tbSum","hbSum","dqStatisticRateSum" };
                                totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                            }
                        }
                        model.put("categoryType", getMessage(language, "label.hierarchy.level.store"));
                        break;
                    case 1041:
                        if (childFlag) {
                          //店铺 品牌 楼层
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.childStore.name"),
                                    getMessage(language, "label.hierarchy.level.floor")
                                    , getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger") };
                            titleKeys = new String[]{"storePname", "name", "floor","entersSum","dqSum" };
                            totalShowCols = new String[]{"entersSums","dqSums"};
                            if(null!=chartType){//客流量占比
                                titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.childStore.name"),
                                        getMessage(language, "label.hierarchy.level.floor"), getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                                titleKeys = new String[]{"storePname", "name", "floor","entersSum","tbSum","hbSum","dqStatisticRateSum" };
                                totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                            }
                        } else {
                          //店铺  楼层
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.hierarchy.level.floor")
                                    , getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger") };
                            titleKeys = new String[]{"name", "floor","entersSum","dqSum" };
                            totalShowCols = new String[]{"entersSums","dqSums"};
                            if(null!=chartType){//客流量占比
                                titles = new String[]{getMessage(language, "label.hierarchy.level.store"), 
                                        getMessage(language, "label.hierarchy.level.floor"), getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                                titleKeys = new String[]{"name", "floor","entersSum","tbSum","hbSum","dqStatisticRateSum" };
                                totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                            }
                        }
                        model.put("categoryType", getMessage(language, "label.hierarchy.level.floor"));
                        break;
                    case 1050:
                        if (childFlag) {
                          //店铺 品牌 楼层 出入口 出入口编号
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.childStore.name"),
                                    getMessage(language, "label.hierarchy.level.floor"), getMessage(language, "label.store.entrance.name"),
                                    getMessage(language, "label.store.entrance.code"), getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger")  };
                            titleKeys = new String[]{"storePname", "storeName", "floor", "name", "code","entersSum","dqSum" };
                            totalShowCols = new String[]{"entersSums","dqSums"};
                            if(null!=chartType){//客流量占比
                                titles = new String[]{getMessage(language, "label.hierarchy.level.store"),
                                        getMessage(language, "label.childStore.name"),
                                        getMessage(language, "label.hierarchy.level.floor"),
                                        getMessage(language, "label.store.entrance.name"),
                                        getMessage(language, "label.store.entrance.code") , getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                                titleKeys = new String[]{ "storePname", "storeName", "floor", "name", "code" ,"entersSum","tbSum","hbSum","dqStatisticRateSum" };
                                totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                            }
                            
                        } else {
                          //店铺  楼层 出入口 出入口编号
                            titles = new String[]{getMessage(language, "label.hierarchy.level.store"), getMessage(language, "label.hierarchy.level.floor"),
                                    getMessage(language, "label.store.entrance.name"), getMessage(language, "label.store.entrance.code")
                                    , getMessage(language, "oper.view.total.visitors"), getMessage(language, "oper.view.total.passenger") };
                            titleKeys = new String[]{"storePname", "floor", "name", "code","entersSum","dqSum" };
                            totalShowCols = new String[]{"entersSums","dqSums"};
                            if(null!=chartType){//客流量占比
                                titles = new String[]{getMessage(language, "label.hierarchy.level.store"),
                                        getMessage(language, "label.hierarchy.level.floor"),
                                        getMessage(language, "label.store.entrance.name"),
                                        getMessage(language, "label.store.entrance.code") , getMessage(language, "overview.current.total.traffic"), getMessage(language, "overview.tb.total.traffic"), getMessage(language, "overview.hb.total.traffic"),getMessage(language, "overview.current.total.traffic.rate")};
                                titleKeys = new String[]{ "storePname", "floor", "name", "code" ,"entersSum","tbSum","hbSum","dqStatisticRateSum" };
                                totalShowCols = new String[]{"entersSums","tbSums","hbSums","dqStatisticRateSums"};
                            }
                        }
                        model.put("categoryType", getMessage(language, "label.hierarchy.level.gateway"));
                        break;
                        
                    default:
                        break;
                }
            
        } else if ("sales".equals(type)) {// 销售额
            sheelTitle = getMessage(language, "110202");
            preStr = "$";
        } else if ("chengJiaoLv".equals(type)) {// 成交率
            sheelTitle = getMessage(language, "110203");
            afterStr = "%";
        } else if ("keDanJia".equals(type)) {// 客单价
            sheelTitle = getMessage(language, "110204");
            preStr = "$";
        } else if ("keLiuGongShi".equals(type)) {// 客流工时比
            sheelTitle = getMessage(language, "110205");
        } else if ("jiaoYiBiShu".equals(type)) {// 交易笔数
            sheelTitle = getMessage(language, "110206");
        } else if ("avgKeDanJia".equals(type)) {// 平均每件客单价
            sheelTitle = getMessage(language, "110207");
            preStr = "$";
        } else if ("avgEveryCount".equals(type)) {// 平均每单件数
            sheelTitle = getMessage(language, "110208");
        } else if ("renJunGongxd".equals(type)) {// 人均贡献度
            sheelTitle = getMessage(language, "110209");
        } else if ("sexScale".equals(type)) {// 性别比例
            sheelTitle = getMessage(language, "110213");
            afterStr = "%";
        } else if ("jinDianLv".equals(type)) {// 进店率
            sheelTitle = getMessage(language, "110211");
            afterStr = "%";
        } else if ("invalidKeliu".equals(type)) {// 无效客流
            sheelTitle = getMessage(language, "110215");
            afterStr = "%";
        } else if ("productGzd".equals(type)) {// 产品关注度
            sheelTitle = getMessage(language, "110216");
        }
        model.put("title", sheelTitle);
        HSSFSheet sheet = workbook.createSheet(sheelTitle);
        List<Map<String, Object>> lst = (List<Map<String, Object>>) model.get("list");
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) model.get("dataList");
        Integer storeData = Integer.parseInt(model.get("storeData").toString());
        
        int openHour = 0;
        int closeHour = 23;
        if (model.get("openTime") != null) {
            openHour = Integer.parseInt(model.get("openTime").toString());
        }
        if (model.get("closeTime") != null) {
            closeHour = Integer.parseInt(model.get("closeTime").toString());
        }
        
        HSSFCellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        defaultStyle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        //设置表头样式
        HSSFCellStyle sheetTitleStyle = workbook.createCellStyle();
        sheetTitleStyle.setAlignment(CellStyle.ALIGN_LEFT);
        HSSFFont fontT = workbook.createFont();
        fontT.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        sheetTitleStyle.setFont(fontT);
        sheetTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        sheetTitleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        sheetTitleStyle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        
        //设置标题样式
        HSSFCellStyle cellTitleStyle = workbook.createCellStyle();
        cellTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellTitleStyle.setFillForegroundColor(new HSSFColor.GREY_40_PERCENT().getIndex());
        cellTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = workbook.createFont();
        font.setColor(new HSSFColor.WHITE().getIndex());
        cellTitleStyle.setFont(font);
        cellTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellTitleStyle.setTopBorderColor(new HSSFColor.GREY_80_PERCENT().getIndex());
        cellTitleStyle.setBottomBorderColor(new HSSFColor.GREY_80_PERCENT().getIndex());
        cellTitleStyle.setRightBorderColor(new HSSFColor.GREY_80_PERCENT().getIndex());
        cellTitleStyle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());
        
        //设置样式
        HSSFCellStyle cellContentStyle = workbook.createCellStyle();
        cellContentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellContentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellContentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellContentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellContentStyle.setBottomBorderColor(new HSSFColor.GREY_80_PERCENT().getIndex());
        cellContentStyle.setRightBorderColor(new HSSFColor.GREY_80_PERCENT().getIndex());
        cellContentStyle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());
        
        
        /*
         * cellStyle.setAlignment(CellStyle.ALIGN_LEFT); HSSFFont font = workbook.createFont();
         * font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); font.setFontHeight((short) 256);
         * cellStyle.setFont(font);
         */
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short)300);
        for(int i=0;i<100;i++){
            sheet.setDefaultColumnStyle(i, defaultStyle);
            sheet.setColumnWidth(i, 10*256);
        }
        
        int sheetRow = 0;
        // 添加标题
        for (sheetRow = 0; sheetRow < sheetTitles.length; sheetRow++) {
            HSSFRow sheetTitleRow = sheet.createRow(sheetRow);
           /* if (titles != null) {
                sheet.addMergedRegion(new CellRangeAddress(sheetRow, (short) sheetRow, 0,(short)(titles.length-1)));// 指定合并区域
            }*/
            //Ssheet.addMergedRegion(new CellRangeAddress(sheetRow, (short) sheetRow, 0, 1));// 指定合并区域
            HSSFCell sheetTitleCell = sheetTitleRow.createCell(0);
            sheetTitleCell.setCellValue(sheetTitles[sheetRow] + model.get(sheetTitlekeys[sheetRow]));
            sheetTitleCell.setCellStyle(sheetTitleStyle);
        }
        sheetRow++;
        
        HSSFRow titleRow = sheet.createRow(sheetRow);
        HSSFRow titleRow1 = sheet.createRow(sheetRow + 1);
        
        // 表格标题
        
        if (titles != null) {
            // CellRangeAddress 起始行 结束行 起始列 结束列
            sheet.addMergedRegion(new CellRangeAddress(sheetRow, (short) sheetRow, 0, (short) (titles.length - 1)));// 指定合并区域
            HSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(getMessage(language, "oper.view.basic.information"));
            titleCell.setCellStyle(cellTitleStyle);
            for(int i=1;i<titles.length;i++){
                HSSFCell titleCell1 = titleRow.createCell(i);
                titleCell1.setCellStyle(cellTitleStyle);
            }
        } else {
            // CellRangeAddress 起始行 结束行 起始列 结束列
            sheet.addMergedRegion(new CellRangeAddress(sheetRow, (short) sheetRow + 1, 0, (short) 0));// 指定合并区域
            HSSFCell titleCell = titleRow.createCell(0);
            HSSFRow titleRow2 = sheet.createRow(sheetRow + 1);
            HSSFCell titleCell2 = titleRow2.createCell(0);
            titleCell2.setCellStyle(cellContentStyle);
            titleCell.setCellValue(getMessage(language, "label.hierarchy.level.store"));
            titleCell.setCellStyle(cellTitleStyle);
        }
        //titleCell.setCellStyle(cellTitleStyle);
        /*
         * HSSFCell curCell = null; HSSFCell tbCell = null; HSSFCell hbCell = null;
         */
        
        HSSFCell timeCell = null;
        int j = 0;
        if (lst == null || lst.size() == 0) {
            return workbook;
        }
        for (int i = 0; i < lst.size(); i++) {
            if (i == 0) {
                j = 1;
                if (titles != null) {
                    
                    j = createTitle(titles, titleRow1, i, cellTitleStyle, null, null, null);
                    /*
                     * HSSFCell pStoreCell = titleRow1.createCell(i); HSSFCell floorCell =
                     * titleRow1.createCell(i+1); HSSFCell storeCell = titleRow1.createCell(i+2);
                     * HSSFCell storeCodeCell = titleRow1.createCell(i+3); HSSFCell categoryCell =
                     * titleRow1.createCell(i+4); pStoreCell.setCellStyle(cellStyle);
                     * pStoreCell.setCellValue(getMessage(language, "label.hierarchy.level.store"));
                     * floorCell.setCellStyle(cellStyle);
                     * floorCell.setCellValue(getMessage(language, "label.hierarchy.level.floor"));
                     * storeCell.setCellStyle(cellStyle); storeCell.setCellValue("品牌名称");
                     * storeCodeCell.setCellStyle(cellStyle);
                     * storeCodeCell.setCellValue(getMessage(language, "label.childStore.code"));
                     * categoryCell.setCellStyle(cellStyle);
                     * categoryCell.setCellValue(getMessage(language, "label.store.category"));
                     */
                    // j = 5;
                }
                
            }
            if (queryType.equals("D")) {
                int curHour = calTime(lst.get(i).get("time").toString());
                if (1 == storeData.intValue() && (curHour < openHour || curHour > closeHour)) {
                    continue;
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(sheetRow, sheetRow, (short) j, (short) (j + colTitles.length - 1)));// 指定合并区域
            timeCell = titleRow.createCell(j);
            timeCell.setCellValue(parseTitleTime(lst.get(i),queryType,language));
            timeCell.setCellStyle(cellTitleStyle);
            for(int k=j+1;k<j + colTitles.length;k++){
                HSSFCell titleCell1 = titleRow.createCell(k);
                titleCell1.setCellStyle(cellTitleStyle);
            }
            j += createTitle(colTitles, titleRow1, j, cellTitleStyle, null, null, null);
            /*
             * curCell = titleRow1.createCell(j); tbCell = titleRow1.createCell(j + 1); hbCell =
             * titleRow1.createCell(j + 2); curCell.setCellStyle(cellStyle);
             * curCell.setCellValue(getMessage(language, "CrossMetricsByPeriod.Current"));
             * tbCell.setCellStyle(cellStyle); tbCell.setCellValue(getMessage(language,
             * "label.prior.year")); hbCell.setCellStyle(cellStyle);
             * hbCell.setCellValue(getMessage(language, "label.prior.period")); j += 3;
             */
        }
        sheetRow++;
        int rowNum = sheetRow;
        for (Map<String, Object> map : dataList) {
            rowNum++;
            int colNum = 0;
            HSSFRow dataRow = sheet.createRow(rowNum);
            // 店铺：storePname 楼层：floor 品牌名称：name 品牌编号：code 业态：categoryName
            if (titleKeys != null) {
                int rowCount = createTitle(titleKeys, dataRow, colNum, cellContentStyle, map, null, null);
                colNum = colNum + rowCount - 1;
                
                /*
                 * dataRow.createCell(colNum).setCellValue(parseStringValue(map.get("storePname")));
                 * colNum++;
                 * dataRow.createCell(colNum).setCellValue(parseStringValue(map.get("floor")));
                 * colNum++;
                 * dataRow.createCell(colNum).setCellValue(parseStringValue(map.get("name")));
                 * colNum++;
                 * dataRow.createCell(colNum).setCellValue(parseStringValue(map.get("code")));
                 * colNum++;
                 * dataRow.createCell(colNum).setCellValue(parseStringValue(map.get("categoryName"))
                 * );
                 */
            } else {
                HSSFCell dataCell = dataRow.createCell(colNum);
                dataCell.setCellValue(map.get("name") + "");
                dataCell.setCellStyle(cellContentStyle);
            }
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) map.get("list");
            for (Map<String, Object> map2 : tempList) {
                if (queryType.equals("D")) {
                    int curHour = calTime(map2.get("time").toString());
                    if (1 == storeData.intValue() && (curHour < openHour || curHour > closeHour)) {
                        continue;
                    }
                }
                colNum += createTitle(colTitleKeys, dataRow, (colNum + 1), cellContentStyle, map2, preStr, afterStr);
                /*
                 * dataRow.createCell(colNum + 1).setCellValue(preStr + map2.get("dq") + "" +
                 * afterStr); dataRow.createCell(colNum + 2).setCellValue(preStr + map2.get("tb") +
                 * "" + afterStr); dataRow.createCell(colNum + 3).setCellValue(preStr +
                 * map2.get("hb") + "" + afterStr); colNum = colNum + 3;
                 */
            }
        }
        // 合计
        rowNum++;
        HSSFRow countRow = sheet.createRow(rowNum);
        int k = 0;
        HSSFCell countTitleCell = countRow.createCell(0);
        countTitleCell.setCellValue(getMessage(language, "oper.view.total"));
        int totalClosNum = 0;
        if (titles != null) {
            if(totalShowCols != null){
                totalClosNum = totalShowCols.length;
            }
            
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, titles.length - 1-totalClosNum));// 指定合并区域
        }
        countTitleCell.setCellStyle(cellContentStyle);
        /*
         * HSSFCell countCurCell = null; HSSFCell countTbCell = null; HSSFCell countHbCell = null;
         */
        if(totalShowCols != null){
            createTitle(totalShowCols, countRow, titles.length -totalClosNum, cellContentStyle, model, preStr, afterStr);
        }
        for (int i = 0; i < lst.size(); i++) {
            if (i == 0) {
                k = totalClosNum+1;
                if (titleKeys != null) {
                    k = titleKeys.length;
                }
            }
            if (queryType.equals("D")) {
                int curHour = calTime(lst.get(i).get("time").toString());
                if (1 == storeData.intValue() && (curHour < openHour || curHour > closeHour)) {
                    continue;
                }
            }
            k += createTitle(colTitleKeys, countRow, k, cellContentStyle, lst.get(i), preStr, afterStr);
            /*
             * countCurCell = countRow.createCell(k); countTbCell = countRow.createCell(k + 1);
             * countHbCell = countRow.createCell(k + 2); countCurCell.setCellStyle(cellStyle);
             * countCurCell.setCellType(Cell.CELL_TYPE_STRING); String dqCount =
             * lst.get(i).get("dq") + ""; String tbCount = lst.get(i).get("tb") + ""; String hbCount
             * = lst.get(i).get("hb") + ""; countCurCell.setCellValue(preStr + dqCount + afterStr);
             * countTbCell.setCellStyle(cellStyle); countTbCell.setCellType(Cell.CELL_TYPE_STRING);
             * countTbCell.setCellValue(preStr + tbCount + afterStr);
             * countHbCell.setCellStyle(cellStyle); countHbCell.setCellType(Cell.CELL_TYPE_STRING);
             * countHbCell.setCellValue(preStr + hbCount + afterStr); k += 3;
             */
        }
        //countRow.setRowStyle(cellContentStyle);
        return workbook;
    }
    
    
    private String parseTitleTime(Map<String,Object> dataMap,String queryType,String language){
        String str = "";
        String timeTile = dataMap.get("time").toString();
        if(!StringUtils.isBlank(queryType)){
            if(dataMap.get("dateNo") == null){
                return timeTile;
            }
            String dateNo = dataMap.get("dateNo").toString();
            /*String weekDay = 
            time.week.sun=星期天
                    time.week.mon=星期一
                    time.week.tues=星期二
                    time.week.wed=星期三
                    time.week.thur=星期四
                    time.week.fri=星期五
                    time.week.sat=星期六*/
            if (queryType.equals("W")) {
                str = getMessage(language, "time.week."+dateNo) +"(";
                str +=timeTile+")";
            }else if (queryType.equals("M")) {
                str = dateNo+getMessage(language, "details.time.week") +"(";
                str +=timeTile+")";
            }else if (queryType.equals("Q")) {
                str = dateNo+getMessage(language, "details.time.month") +"(";
                str +=timeTile+")";
            }else{
                str = timeTile;
            }
        }
        return str;
    }

    /*@SuppressWarnings("unchecked")
    private int calc(Map<String, Object> model, List<Map<String, Object>> dataList, List<Map<String, Object>> lst,String queryRow,String type) {
        int dataCount = dataList.size();
        int hour = 24;
        for (Map<String, Object> map : dataList) {
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) map.get("list");
            if(map.get("workTime") != null){
                hour = Integer.parseInt(map.get("workTime").toString());
            }
            for (Map<String, Object> map2 : tempList) {
                int days = calcDays(map2.get("time").toString(),type, hour);
                map2.put(queryRow, parDouble2(map2.get(queryRow).toString(),days));
            }
        }
        for(Map<String, Object> map : lst){
            int days = calcDays(map.get("time").toString(),type, hour);
            map.put(queryRow, parDouble2(map.get(queryRow).toString(),(days*dataCount)));
        }
        return 1;
    }
    
    private Double parDouble2(String value,int day){
        BigDecimal bd = new BigDecimal(value);
        BigDecimal result = new BigDecimal(bd.doubleValue()/day);
        result = result.setScale(2,BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();  
    }

    private int calcDays(String key,String type,int hours){
        if("D".equals(type)){
            return 1;
        }else if("W".equals(type)){
            return hours;
        }else if("M".equals(type)){
            return 7*hours;
        }else if("Q".equals(type)){
            String[] dates = key.split("-");
            if(dates != null && dates.length >1){
                String startDate = DateFormatHelper.formatToYmd(trimStr(dates[0]));
                String endDate = DateFormatHelper.formatToYmd(trimStr(dates[1]));
                int day = DateFormatHelper.daysBetween(startDate,endDate);
                return day*hours;
            }
            return 30*hours;
        }else{
            return hours;
        }
    }
    
    private String trimStr(String str){
        return str.replaceAll(" ", "").trim();
    }*/
    

    private int createTitle(String[] titles, HSSFRow titleRow, int startIdx, HSSFCellStyle cellStyle, Map<String, Object> dataMap, String preStr,
            String afterStr) {
        if (titles == null || titles.length == 0) {
            return startIdx;
        }
        
        for (int i = 0; i < titles.length; i++) {
            HSSFCell TitleCell = titleRow.createCell(startIdx + i);
            if (cellStyle != null) {
                TitleCell.setCellStyle(cellStyle);
            }
            if (dataMap != null) {
                
                StringBuffer sb = new StringBuffer();
                if (!StringUtils.isBlank(preStr)) {
                    sb.append(preStr);
                }
                String str = parseStringValue(dataMap.get(titles[i])+(-1!=titles[i].indexOf("StatisticRate")?"%":""));
                if(numberFormatStr.indexOf("-"+titles[i]+"-")>-1){
                    sb.append(pareNumStr(str));
                }else{
                    sb.append(str);
                }
                if (!StringUtils.isBlank(afterStr)) {
                    sb.append(afterStr);
                }
                TitleCell.setCellValue(sb.toString());
            } else {
                TitleCell.setCellValue(titles[i]);
            }
        }
        return titles.length;
    }
    
    private String pareNumStr(String str){
        Double db = Double.parseDouble(str);
        return df.format(db);
        //Format
    }
    
    
    private String parseStringValue(Object obj) {
        if (obj == null) {
            return "";
        }
        String value = obj + "";
        if(value.endsWith(".0")){
            value = value.substring(0, value.lastIndexOf("."));
        }
        return value;
    }
    
    private int calTime(String time) {
        String[] times = time.split(":");
        if (times != null) {
            int hour = Integer.parseInt(times[0].trim());
            return hour;
        }
        return -1;
    }
    
    private String getMessage(String language, String key) {
        return HttpRequestHelper.getMessage(key, language);
    }
    
}
