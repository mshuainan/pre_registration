package com.elementspeed.tool.excel.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class CopyExcelSheetToAnotherExcelSheet {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fromPath = "E:\\srm\\excel";// excel存放路径
        String toPath = "E:\\srm\\excel2";// 保存新EXCEL路径
        // 新的excel 文件名
        String excelName = "节目访问量";
        // 创建新的excel
        HSSFWorkbook wbCreat = new HSSFWorkbook();
        File file = new File(fromPath);
        for (File excel : file.listFiles()) {
            // 打开已有的excel
            String strExcelPath = fromPath + "\\" + excel.getName();
            InputStream in = new FileInputStream(strExcelPath);
            HSSFWorkbook wb = new HSSFWorkbook(in);
            for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
                HSSFSheet sheet = wb.getSheetAt(ii);
                HSSFSheet sheetCreat = wbCreat.createSheet(sheet.getSheetName());
                // 复制源表中的合并单元格
                MergerRegion(sheetCreat, sheet);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int i = firstRow; i <= lastRow; i++) {
                    // 创建新建excel Sheet的行
                    HSSFRow rowCreat = sheetCreat.createRow(i);
                    // 取得源有excel Sheet的行
                    HSSFRow row = sheet.getRow(i);
                    // 单元格式样
                    int firstCell = row.getFirstCellNum();
                    int lastCell = row.getLastCellNum();
                    for (int j = firstCell; j < lastCell; j++) {
                        // 自动适应列宽 貌似不起作用
                        //sheetCreat.autoSizeColumn(j);
                        System.out.println(row.getCell(j));
                        rowCreat.createCell(j);
                        String strVal ="";
                        if (row.getCell(j)==null) {
                            
                        }else{
                             strVal = removeInternalBlank(row.getCell(j).getStringCellValue());
                        }
                        rowCreat.getCell(j).setCellValue(strVal);
                    }
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream(toPath + excelName + ".xls");
        wbCreat.write(fileOut);
        fileOut.close();
    }
 
    /**
     * 复制原有sheet的合并单元格到新创建的sheet
     * 
     * @param sheetCreat
     *            新创建sheet
     * @param sheet
     *            原有的sheet
     */
    private static void MergerRegion(HSSFSheet sheetCreat, HSSFSheet sheet) {
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            Region mergedRegionAt = sheet.getMergedRegionAt(i);
            sheetCreat.addMergedRegion(mergedRegionAt);
        }

    }

    /**
     * 去除字符串内部空格
     */
    public static String removeInternalBlank(String s) {
        // System.out.println("bb:" + s);
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        char str[] = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == ' ') {
                sb.append(' ');
            } else {
                break;
            }
        }
        String after = m.replaceAll("");
        return sb.toString() + after;
    }
}