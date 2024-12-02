package com.regression.linearRegression.services.concretes;

import com.regression.linearRegression.core.utilities.ExcelReader;
import com.regression.linearRegression.services.abstracts.LinearRegressionService;
import com.regression.linearRegression.services.dtos.LinearRegressionResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class LinearRegressionManager implements LinearRegressionService {

    //veri toplamlarını al
    private double[] calculateSums(double[] xValues, double[] yValues) {
        double sumOfx = 0.0, sumOfy = 0.0, sumOfxy = 0.0, sumOfSquaredx = 0.0;
        int n = xValues.length;

        for (int i = 0; i < n; i++) {
            sumOfx += xValues[i];
            sumOfy += yValues[i];
            sumOfxy += xValues[i] * yValues[i];
            sumOfSquaredx += xValues[i] * xValues[i];
        }

        return new double[]{sumOfx, sumOfy, sumOfxy, sumOfSquaredx};
    }

    //denklemi hesapla
    private double[] calculateEquation(double sumOfx, double sumOfy, double sumOfxy, double sumOfSquaredx, int n) {
        double denominator = (n * sumOfSquaredx) - (sumOfx * sumOfx);

        if (denominator == 0) {
            throw new IllegalArgumentException("Hesaplanamayan bir denklemin katsayısı.");
        }

        double m = ((n * sumOfxy) - (sumOfx * sumOfy)) / denominator;
        double b = (sumOfy - (m * sumOfx)) / n;
        return new double[]{m, b};
    }

    //hata terimlerini hesapla
    private double[] calculateErrors(double[] xValues, double[] yValues, double m, double b) {
        double ssTotal = 0.0;
        double ssResidual = 0.0;
        double yMean = Arrays.stream(yValues).average().orElse(Double.NaN);
        int n = xValues.length;

        for (int i = 0; i < n; i++) {
            double yHat = m * xValues[i] + b;
            double residual = yValues[i] - yHat;
            ssTotal += Math.pow(yValues[i] - yMean, 2);
            ssResidual += Math.pow(residual, 2);
        }

        double rSquared = 1 - (ssResidual / ssTotal);
        double standardError = Math.sqrt(ssResidual / (n - 2));

        return new double[]{rSquared, standardError, ssResidual};
    }

    //iktisadi yorumu yazdır
    private String generateEconomicInterpretation(double m, double b, double rSquared, double standardError, double sumOfSquaredResiduals, String xVariableName, String yVariableName) {
        String relationship = (m > 0) ? "doğru orantılı" : "ters orantılı";
        String changeEffect = (m > 0) ? "artmasına" : "azalmasına";
        String percentageChange = String.format("%.2f", Math.abs(m * 100));
        double percentageB = b * 100;

        return String.format("R-kare (R²) değeri %.6f olup, modelin bağımlı değişkendeki toplam varyasyonu ne kadar açıkladığını gösterir. %s ile %s arasında %s bir ilişki vardır. %s'deki 1 birimlik artış, %s'nin %.6f birim, yani %% %.6f %s neden olur. %s, 0 olduğunda %s, %.6f yani %% %.6f olur. Modelin standart hatası %.6f olup, bu değer modelin tahminlerinin gerçek değerlerden ne kadar saptığını gösterir. Bu düşük değer, modelin tahminlerinin gerçeğe oldukça yakın olduğunu gösterir. Modelin toplam hata terimleri karesi %.6f olup, modelin açıklayamadığı varyasyonu gösterir. Bu düşük değer, modelin iyi bir performans sergilediğini ve tahminlerin doğruluğunun yüksek olduğunu gösterir.", rSquared, xVariableName, yVariableName, relationship, xVariableName, yVariableName, Math.abs(m), Math.abs(m * 100), changeEffect, xVariableName, yVariableName, b, percentageB, standardError, sumOfSquaredResiduals);
    }


    private LinearRegressionResult calculateRegression(double[] xValues, double[] yValues, String xVariableName, String yVariableName) {
        int n = xValues.length;

        double[] sums = calculateSums(xValues, yValues);
        double sumOfx = sums[0];
        double sumOfy = sums[1];
        double sumOfxy = sums[2];
        double sumOfSquaredx = sums[3];

        double[] equation = calculateEquation(sumOfx, sumOfy, sumOfxy, sumOfSquaredx, n);
        double m = equation[0];
        double b = equation[1];
        String equationString = "y = " + m + "x + " + b;

        double[] errors = calculateErrors(xValues, yValues, m, b);
        double rSquared = errors[0];
        double standardError = errors[1];
        double sumOfSquaredResiduals = errors[2];

        String economicInterpretation = generateEconomicInterpretation(m, b, rSquared, standardError, sumOfSquaredResiduals, xVariableName, yVariableName);

        return new LinearRegressionResult(m, b, rSquared, standardError, sumOfx, sumOfy, sumOfxy, sumOfSquaredx, equationString, economicInterpretation);
    }


    //calculate via json
    @Override
    public LinearRegressionResult calculate(double[] xValues, double[] yValues, String xVariableName, String yVariableName) {
        return calculateRegression(xValues, yValues, xVariableName, yVariableName);
    }

    //calculate via xslx upload
    @Override
    public LinearRegressionResult calculateFromExcel(MultipartFile file, String xVariableName, String yVariableName) {
        try {
            double[][] values = ExcelReader.readExcelFile(file);
            double[] xValues = values[0];
            double[] yValues = values[1];
            return calculateRegression(xValues, yValues, xVariableName, yVariableName);
        } catch (IOException e) {
            throw new RuntimeException("Excel dosyası işlenirken bir hata oluştu.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
