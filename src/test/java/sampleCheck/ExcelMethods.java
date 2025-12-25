package sampleCheck;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelMethods {
    public String[][] readDataFromExcel(String filename, String sheetTitle) {
        /* --------------Read Data Excluding the Header Row ------------------- */
        String dataSet[][] = null;
        try {
            File file = new File(System.getProperty("user.dir")
                    + "\\src\\test\\resources\\sampleResources\\" + filename);
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheetname = wb.getSheet(sheetTitle);
            int rowNum = sheetname.getLastRowNum();
            System.out.println("Total row in the sheet: " + rowNum);
            Row headerRow = sheetname.getRow(0);
            int colNum = headerRow.getLastCellNum();
            System.out.println("Total column in the sheet: " + colNum);
            dataSet = new String[rowNum][colNum];
            DataFormatter df = new DataFormatter();

            for (int rowcount = 1; rowcount <= rowNum; rowcount++) {
                for(int colcount = 0; colcount < colNum; colcount++)
                {
                    dataSet[rowcount-1][colcount] = df.formatCellValue(sheetname.getRow(rowcount).getCell(colcount));
                    System.out.print(dataSet[rowcount-1][colcount]);
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Exception in ExcelMethods.java/readFromExcel() method" + e);
        }
        return dataSet;
    }

    public String[][] readFilteredColumnData(String filename, String sheetName, String... header)
    {
        /* --------------Read Only Selected ColumnData ------------------- */
        String dataSet[][] = null;
        try {
            File file = new File(System.getProperty("user.dir")
                    + "\\src\\test\\resources\\sampleResources\\" + filename);
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheetname = wb.getSheet(sheetName);
            int rowNum = sheetname.getLastRowNum();
            System.out.println("Total row in the sheet: " + rowNum);
            Row headerRow = sheetname.getRow(0);
            int colNum = headerRow.getLastCellNum();
            System.out.println("Total column in the sheet: " + colNum);
            dataSet = new String[rowNum][colNum];
            DataFormatter df = new DataFormatter();
            List<Integer> colPosition = new ArrayList<>();

            for(String heading: header)
            {
                for(int cellValue = 0; cellValue< colNum; cellValue++)
                {
                    String columnName = df.formatCellValue(sheetname.getRow(0).getCell(cellValue));
                    if(columnName.equalsIgnoreCase(heading))
                    {
                        colPosition.add(cellValue);
                    }
                }
            }
            if(colPosition.isEmpty())
            {
                System.out.println("The Header in the method parameter does not match with the Header in the Excel file...");
                return null;
            }
            else
            {
                System.out.println("Total column filtered from the sheets: " + colPosition.size());
                for(int rowCount = 1; rowCount<=rowNum; rowCount++)
                {
                    int colCount = 0;
                    for(Integer columnValue : colPosition)
                    {
                        int colVal = columnValue.intValue();
                        dataSet[rowCount-1][colCount] = df.formatCellValue(sheetname.getRow(rowCount).getCell(colVal));
                        System.out.print(dataSet[rowCount-1][colCount]+"     ");
                        colCount++;
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in ExcelMethods.java/readFilteredColumnData() method" + e);
        }
        return dataSet;
    }

    public void writeDataToExcel(String filename, String sheetName, List<String> header, String... data) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        Workbook wb = null;
        try {
            File file = new File(System.getProperty("user.dir")
                    + "\\src\\test\\resources\\sampleResources\\" + filename);
            fis = new FileInputStream(file);
            wb = WorkbookFactory.create(fis);
            Sheet sheetname = wb.getSheet(sheetName);

            fos = new FileOutputStream(System.getProperty("user.dir")
                    + "\\src\\test\\resources\\sampleResources\\" + filename);
            if (sheetname == null) {
                Sheet newSheet = wb.createSheet(sheetName);
                wb.write(fos);
            } else {
                int existingNumberOfRecords = sheetname.getLastRowNum();
                System.out.println("No. of records in excel: " + existingNumberOfRecords);
                /* IF The sheet is completely Empty...No Header!!! */
                if(existingNumberOfRecords == -1 && !header.isEmpty())
                {
                    /* Adding Header to the Excel */
                    int i = 0;
                    for(String headerValue: header)
                    {
                        Row row = sheetname.getRow(1);
                        Cell cell = row.createCell(i);
                        cell.setCellValue(headerValue);
                        i++;
                    }
                }
                wb.write(fos);
                System.out.println("Check Point...");
            }
        } catch (Exception e) {
            System.out.println("Exception in ExcelMethods.java/writeDataToExcel() method" + e);
        } finally {
            try
            {
                if (fis!=null)
                {
                    fis.close();
                }
                if(fos!=null)
                {
                    fos.close();
                }
                if(wb!=null)
                {
                    wb.close();
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception in ExcelMethods.java/writeDataToExcel() method Finally Block" + e);
            }

        }
    }

}

