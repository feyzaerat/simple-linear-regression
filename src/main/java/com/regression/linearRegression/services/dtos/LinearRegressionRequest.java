package com.regression.linearRegression.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinearRegressionRequest {
    private double xValue;
    private double yValue;

}
