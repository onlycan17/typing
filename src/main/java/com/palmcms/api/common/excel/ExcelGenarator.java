package com.palmcms.api.common.excel;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class ExcelGenarator {

  public Workbook userListToExcel(String[][] dataArray, String strTitle,
      List<String> itemList, HttpServletResponse response) throws IOException {

    try (
        Workbook workbook = new SXSSFWorkbook(100);
    ) {
      CreationHelper createHelper = workbook.getCreationHelper();
      SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(strTitle);
      sheet.trackAllColumnsForAutoSizing();
      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLUE.getIndex());

      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);

      // Row for Header
      SXSSFRow headerRow = sheet.createRow(0);

      // Header
      for (int col = 0; col < itemList.size(); col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(itemList.get(col));
        cell.setCellStyle(headerCellStyle);
      }

      // CellStyle for Age
      CellStyle ageCellStyle = workbook.createCellStyle();
      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

      for (int i = 0; i < dataArray.length; i++) {
        SXSSFRow row = sheet.createRow(i + 1);
        for (int j = 0; j < dataArray[i].length; j++) {
          row.createCell(j).setCellValue(dataArray[i][j]);
        }
      }

      for (int i = 0; i < dataArray[0].length; i++) {
        sheet.autoSizeColumn(i);
        sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
      }

      response.setContentType("application/octet-stream;charset=utf-8");
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Content-disposition", "attachment;filename=" + strTitle + ".xlsx");
      BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
      workbook.write(out);
      out.close();
      return workbook;
    }
  }
}
