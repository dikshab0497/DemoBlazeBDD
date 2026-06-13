package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private static final Logger logger = LogManager.getLogger(ExcelUtility.class);

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    public ExcelUtility(String path) {
        this.path = path;
        logger.info("[EXCEL] ExcelUtility initialised with path: " + path);
    }

    public int getRowCount(String sheetName) throws IOException {
        logger.info("[EXCEL] Getting row count for sheet: " + sheetName);
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        logger.info("[EXCEL] Row count for sheet '" + sheetName + "': " + rowCount);
        return rowCount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        logger.info("[EXCEL] Getting cell count for sheet: " + sheetName + " | Row: " + rownum);
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fi.close();
        logger.info("[EXCEL] Cell count for sheet '" + sheetName + "' row " + rownum + ": " + cellCount);
        return cellCount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            logger.warn("[EXCEL] Could not read cell [" + rownum + "," + colnum + "] in sheet '" + sheetName + "' — defaulting to empty string");
            data = "";
        }
        workbook.close();
        fi.close();
        return data;
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        logger.info("[EXCEL] Setting cell data | Sheet: " + sheetName + " | Row: " + rownum + " | Col: " + colnum + " | Value: " + data);
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            logger.info("[EXCEL] File not found — creating new workbook at: " + path);
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        if (workbook.getSheetIndex(sheetName) == -1) {
            logger.info("[EXCEL] Sheet not found — creating new sheet: " + sheetName);
            workbook.createSheet(sheetName);
        }
        sheet = workbook.getSheet(sheetName);

        if (sheet.getRow(rownum) == null) {
            sheet.createRow(rownum);
        }
        row = sheet.getRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
        logger.info("[EXCEL] Cell data set successfully");
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        logger.info("[EXCEL] Filling GREEN color | Sheet: " + sheetName + " | Row: " + rownum + " | Col: " + colnum);
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
        logger.info("[EXCEL] GREEN color applied successfully");
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        logger.info("[EXCEL] Filling RED color | Sheet: " + sheetName + " | Row: " + rownum + " | Col: " + colnum);
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
        logger.info("[EXCEL] RED color applied successfully");
    }
}