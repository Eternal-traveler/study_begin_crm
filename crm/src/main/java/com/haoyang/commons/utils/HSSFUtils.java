package com.haoyang.commons.utils;

import com.haoyang.workbench.pojo.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-06-21:11
 */
public class HSSFUtils {
    /**
     * 创建市场活动Excel文件
     * @param activityList 市场活动集合
     * @param fileName 导出文件名
     * @param response 响应
     * @throws Exception 输出流异常
     */
    public static void createExcelByActivityList(List<Activity> activityList, String fileName, HttpServletResponse response) throws Exception{
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        //设置每一列字段
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        // 遍历activityList，创建HSSFRow对象，生成所有的数据行
        if(activityList != null && activityList.size() > 0){
            Activity activity = null;
            for(int i = 0 ; i < activityList.size() ; i++){
                // 获取每一个市场活动
                activity = activityList.get(i);
                // 每遍历出一个activity，生成一行
                row = sheet.createRow(i+1);
                // 每一行创建11列，每一列的数据从activity中获取
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        // 将wb对象内存中的所有市场活动数据直接转入输出流内存中
        wb.write(out);
        wb.close(); // 关闭资源
        out.flush(); // 刷新资源
    }

    /**
     * 从指定的HSSFCell对象中获取列的值
     * @return 列中的数据
     * 错误
     */
//    public static String getCellValueForStr(CellType cellType) {
//        String ret = "";
//        if(cellType == CellType.STRING) {
//            ret = CellType.STRING.name();
//        } else if(cellType == CellType.NUMERIC) {
//            ret = CellType.NUMERIC.name() + "";
//        } else if(cellType == CellType.BOOLEAN) {
//            ret = CellType.BOOLEAN.name() + "";
//        } else if(cellType == CellType.FORMULA) {
//            ret = CellType.FORMULA.name();
//        } else {
//            ret = "";
//        }
//        return ret;
//    }

}
