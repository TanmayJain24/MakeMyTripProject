package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtility {

    public static String writeGiftCardTitles(List<String> titles, String directory, String baseFileName) {
        // Ensure directory exists
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory: " + directory, e);
        }

        // Build timestamped filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = Paths.get(directory, baseFileName + "_" + timestamp + ".xlsx").toString();

        // Create workbook & sheet
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("GiftCards");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Header row
            Row header = sheet.createRow(0);
            Cell h0 = header.createCell(0);
            h0.setCellValue("S.No");
            h0.setCellStyle(headerStyle);

            Cell h1 = header.createCell(1);
            h1.setCellValue("Gift Card Title");
            h1.setCellStyle(headerStyle);

            // Data rows
            int rowIdx = 1;
            for (int i = 0; i < titles.size(); i++) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(titles.get(i));
            }

            // Autosize columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            workbook.write(out);
            return filePath;

        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel file: " + filePath, e);
        }
    }
}