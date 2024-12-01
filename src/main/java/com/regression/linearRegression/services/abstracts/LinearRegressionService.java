package com.regression.linearRegression.services.abstracts;

import com.regression.linearRegression.services.dtos.LinearRegressionResult;

public interface LinearRegressionService {
    LinearRegressionResult calculate(double[] xValues, double[] yValues, String xVariableName, String yVariableName);
}
