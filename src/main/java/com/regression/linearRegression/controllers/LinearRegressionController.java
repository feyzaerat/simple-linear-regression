package com.regression.linearRegression.controllers;

import com.regression.linearRegression.services.abstracts.LinearRegressionService;
import com.regression.linearRegression.services.dtos.LinearRegressionRequest;
import com.regression.linearRegression.services.dtos.LinearRegressionResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/linear-regression")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Linear Regression API", description = "API for calculating simple linear regression")
public class LinearRegressionController {
    @Autowired
    private final LinearRegressionService linearRegressionService;

    @PostMapping()
    @Operation(summary = "Calculate Linear Regression from JSON data")
    public LinearRegressionResult calculateLinearRegression(
            @RequestBody LinearRegressionRequest[] data,
            @RequestParam String xVariableName,
            @RequestParam String yVariableName) {

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

    @PostMapping(value= "/excel", consumes = "multipart/form-data")
    @Operation(summary = "Calculate Linear Regression from Excel file",
            description = "Calculates a simple linear regression line using data provided in an Excel file.")
    public ResponseEntity<LinearRegressionResult> calculateLinearRegressionFromExcel(
            @RequestParam("file") @Parameter(description = "Excel file to upload", required = true) MultipartFile file,
            @RequestParam @Parameter(description = "Name of the x variable", required = true) String xVariableName,
            @RequestParam @Parameter(description = "Name of the y variable", required = true) String yVariableName) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        LinearRegressionResult result = linearRegressionService.calculateFromExcel(file, xVariableName, yVariableName);
        return ResponseEntity.ok(result);
    }

}
