package com.regression.linearRegression.services.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "m", "b", "rSquared", "standardError", "sumOfx", "sumOfy", "sumOfxy", "sumOfSquaredx", "equation", "economicInterpretation" })
public class LinearRegressionResult {
    private double m;
    private double b;
    private double rSquared;
    private double standardError;
    private double sumOfx;
    private double sumOfy;
    private double sumOfxy;
    private double sumOfSquaredx;
    private String equation;
    private String economicInterpretation;
}
