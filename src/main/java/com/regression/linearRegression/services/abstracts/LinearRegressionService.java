package com.regression.linearRegression.services.abstracts;

import com.regression.linearRegression.services.dtos.LinearRegressionResult;
import org.springframework.web.multipart.MultipartFile;

public interface LinearRegressionService {
    LinearRegressionResult calculate(double[] xValues, double[] yValues, String xVariableName, String yVariableName);
    LinearRegressionResult calculateFromExcel(MultipartFile file, String xVariableName, String yVariableName);
}
