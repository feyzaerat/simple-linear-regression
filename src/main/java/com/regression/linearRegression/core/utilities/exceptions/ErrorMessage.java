package com.regression.linearRegression.core.utilities.exceptions;

public enum ErrorMessage {
    INSUFFICIENT_DATA("Excel dosyası yeterli veri içermiyor."),
    MISSING_DATA("Excel dosyasında eksik veri bulundu."),
    PROCESSING_ERROR("Excel dosyası işlenirken bir hata oluştu."),
    DENOMINATOR_ERROR("Hesaplanamayan bir denklemin katsayısı.");
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
