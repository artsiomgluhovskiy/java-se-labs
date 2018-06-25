package org.art.java_core.xlsx;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Helper class which is used to export data into .xlsx format.
 */
public class XLSXExporter {

    private static final Logger LOGGER = LogManager.getLogger(XLSXExporter.class);

    private static final String DATE_FORMAT = "dd.MM.yy HH:mm";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    private CellStyle hLinkStyle;
    private CellStyle hLinkStyleOdd;
    private CellStyle dateStyle;
    private CellStyle dateStyleOdd;
    private CellStyle stringStyle;
    private CellStyle stringStyleOdd;

    /**
     * Exports data into .xlsx format.
     *
     * @param out     - any output stream
     * @param title   - sheet title
     * @param headers - table headers
     *                Convention:
     *                1. if the header contains ", URL" - the URL style will be applied and the cell will have the "Address" type;
     *                2. if the header contains ", Date" - the Date style will be applied and the cell will have the "Date" type.
     * @param data    - table data, the list of Key(header)/Value - the Keys should be the equal the headers in the "headers" list parameter
     */
    public void export(final OutputStream out, final String title, final Map<String, String> headers, final List<Map<String, String>> data) {
        if (headers == null || headers.isEmpty() || data == null || data.isEmpty()) {
            return;
        }
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(title));
            CreationHelper createHelper = wb.getCreationHelper();
            //Coordinates of the initial cell
            int rowNumber = 0;
            int columnNumber = 0;
            Row row = sheet.createRow(rowNumber);
            XSSFCellStyle headerStyle = (XSSFCellStyle) wb.createCellStyle();
            headerStyle.setFillBackgroundColor(new XSSFColor(new Color(80, 100, 40)));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = wb.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            for (String header : headers.keySet()) {
                Cell cell = row.createCell(columnNumber++);
                cell.setCellValue(header);
                cell.setCellStyle(headerStyle);
            }
            for (Map<String, String> dataRow : data) {
                row = sheet.createRow(++rowNumber);
                columnNumber = 0;
                for (String header : headers.keySet()) {
                    Cell cell = row.createCell(columnNumber++);
                    String value = dataRow.get(headers.get(header));
                    if (header.endsWith("URL")) {
                        putURLCell(createHelper, wb, cell, value, rowNumber % 2 != 0);
                    } else if (header.endsWith("Date")) {
                        Date parsedDate = StringUtils.isBlank(value) ? null : simpleDateFormat.parse(value);
                        putDateCell(createHelper, wb, cell, parsedDate, rowNumber % 2 != 0);
                    } else {
                        putStringCell(createHelper, wb, cell, value, rowNumber % 2 != 0);
                    }
                }
            }
            for (int i = 0; i < headers.keySet().size(); i++) {
                sheet.autoSizeColumn(i);
            }
            sheet.setAutoFilter(new CellRangeAddress(0, rowNumber, 0, columnNumber - 1));
            wb.write(out);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private CellStyle getHLinkStyle(Workbook wb, boolean odd) {
        if (odd) {
            if (hLinkStyleOdd == null) {
                hLinkStyleOdd = wb.createCellStyle();
                Font hLinkFont = wb.createFont();
                hLinkFont.setUnderline(Font.U_SINGLE);
                hLinkFont.setColor(IndexedColors.BLUE.getIndex());
                hLinkStyleOdd.setFont(hLinkFont);
                hLinkStyleOdd.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                ((XSSFCellStyle) hLinkStyleOdd).setFillBackgroundColor(new XSSFColor(new Color(217, 217, 217)));
                ((XSSFCellStyle) hLinkStyleOdd).setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
            }
            return hLinkStyleOdd;
        } else {
            if (hLinkStyle == null) {
                hLinkStyle = wb.createCellStyle();
                Font hLinkFont = wb.createFont();
                hLinkFont.setUnderline(Font.U_SINGLE);
                hLinkFont.setColor(IndexedColors.BLUE.getIndex());
                hLinkStyle.setFont(hLinkFont);
            }
            return hLinkStyle;
        }
    }

    private CellStyle getDateStyle(Workbook wb, CreationHelper createHelper, boolean odd) {
        if (odd) {
            if (dateStyleOdd == null) {
                dateStyleOdd = wb.createCellStyle();
                dateStyleOdd.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm:ss"));
                dateStyleOdd.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                ((XSSFCellStyle) dateStyleOdd).setFillBackgroundColor(new XSSFColor(new Color(217, 217, 217)));
                ((XSSFCellStyle) dateStyleOdd).setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
            }
            return dateStyleOdd;
        } else {
            if (dateStyle == null) {
                dateStyle = wb.createCellStyle();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm:ss"));
            }
            return dateStyle;
        }
    }

    private CellStyle getStyle(Workbook wb, boolean odd) {
        if (odd) {
            if (stringStyleOdd == null) {
                stringStyleOdd = wb.createCellStyle();
                stringStyleOdd.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                ((XSSFCellStyle) stringStyleOdd).setFillBackgroundColor(new XSSFColor(new Color(217, 217, 217)));
                ((XSSFCellStyle) stringStyleOdd).setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
            }
            return stringStyleOdd;
        } else {
            if (stringStyle == null) {
                stringStyle = wb.createCellStyle();
            }
            return stringStyle;
        }
    }

    private void putURLCell(CreationHelper createHelper, Workbook wb, Cell cell, String value, boolean odd) {
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
        cell.setCellValue(value);
        link.setAddress(value);
        cell.setHyperlink(link);
        cell.setCellStyle(getHLinkStyle(wb, odd));
    }

    private void putDateCell(CreationHelper createHelper, Workbook wb, Cell cell, Date date, boolean odd) {
        cell.setCellValue(date);
        cell.setCellStyle(getDateStyle(wb, createHelper, odd));
    }

    private void putStringCell(CreationHelper createHelper, Workbook wb, Cell cell, String value, boolean odd) {
        cell.setCellValue(value);
        cell.setCellStyle(getStyle(wb, odd));
    }
}
