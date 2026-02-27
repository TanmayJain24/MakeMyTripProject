package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtil {
    public static void writeDynamicDataToExcel(String filePath, String sheetName, String[] headers, List<String[]> dataRows) {
        Workbook workbook;
        File file = new File(filePath);

        try {
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = WorkbookFactory.create(fis);
                }
            } else {
                workbook = new XSSFWorkbook();
            }

            if (workbook.getSheetIndex(sheetName) != -1) {
                workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
            }
            Sheet sheet = workbook.createSheet(sheetName);

            // Header loop
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            for (int i = 0; i < dataRows.size(); i++) {
                Row row = sheet.createRow(i + 1);
                String[] rowData = dataRows.get(i);
                for (int j = 0; j < rowData.length; j++) {
                    row.createCell(j).setCellValue(rowData[j]);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}