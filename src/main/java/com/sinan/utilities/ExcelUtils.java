package com.sinan.utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelUtils {

    private Sheet sheet;

    public ExcelUtils(String path, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet(sheetName);

            // --- EKLEMEN GEREKEN KISIM ---
            if (sheet == null) {
                throw new RuntimeException("HATA: Excel dosyasında '" + sheetName + "' isminde bir sayfa bulunamadı! Lütfen dosyadaki sekme adını kontrol et (Genelde 'Sheet1' veya 'Sayfa1' olur).");
            }
            // -----------------------------

        } catch (Exception e) {
            throw new RuntimeException("Excel dosyası okunurken hata: " + e.getMessage());
        }
    }

    public Object[][] getDataArrayWithoutHeader() {
        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                String cellData = "";
                try {
                    Cell cell = sheet.getRow(i).getCell(j);
                    // Boş hücre kontrolü
                    cellData = (cell == null) ? "" : cell.toString();
                } catch (Exception e) {
                    cellData = "";
                }
                data[i - 1][j] = cellData;
            }
        }
        return data;
    }
}