package com.regression.linearRegression.controllers;

import com.regression.linearRegression.services.abstracts.LinearRegressionService;
import com.regression.linearRegression.services.dtos.LinearRegressionRequest;
import com.regression.linearRegression.services.dtos.LinearRegressionResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/linear-regression")
@AllArgsConstructor
@CrossOrigin
public class LinearRegressionController {
    @Autowired
    private final LinearRegressionService linearRegressionService;

    @PostMapping()
    public LinearRegressionResult calculateLinearRegression(@RequestBody LinearRegressionRequest[] data, @RequestParam String xVariableName, @RequestParam String yVariableName) {

        if (data == null || data.length == 0) {
            System.out.println("No data received.");
            return null;
        }

        double[] xValues = new double[data.length];
        double[] yValues = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            xValues[i] = data[i].getXValue();
            yValues[i] = data[i].getYValue();
        }

        return linearRegressionService.calculate(xValues, yValues, xVariableName, yVariableName);
    }
}
