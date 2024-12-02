package com.regression.linearRegression.core.utilities;

import com.regression.linearRegression.core.utilities.exceptions.ErrorMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReader {

    public static double[][] readExcelFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            if (rowCount <= 1) {
                throw new IllegalArgumentException(ErrorMessage.INSUFFICIENT_DATA.getMessage());
            }

            double[] xValues = new double[rowCount - 1];
            double[] yValues = new double[rowCount - 1];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null || row.getCell(1) == null) {
                    throw new IllegalArgumentException(ErrorMessage.MISSING_DATA.getMessage());
                }

                xValues[i - 1] = row.getCell(0).getNumericCellValue();
                yValues[i - 1] = row.getCell(1).getNumericCellValue();
            }

            return new double[][]{xValues, yValues};
        } catch (IOException e) {
            throw new IOException(ErrorMessage.PROCESSING_ERROR.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
