package com.regression.linearRegression.core.utilities.exceptions;

public enum CommentMessage {
    DIRECTLY_PROPORTIONAL("DOĞRU ORANTILI"),
    INVERSELY_PROPORTIONAL("TERS ORANTILI"),
    INCREASE("ARTMASINA"),
    DECREASE("AZALMASINA"),
    GOOD("İYİ"),
    BAD("BAD"),
    HIGH("YÜKSEK"),
    LOW("DÜŞÜK"),

    MISSING_DATA("Excel dosyasında eksik veri bulundu."),
    PROCESSING_ERROR("Excel dosyası işlenirken bir hata oluştu."),
    DENOMINATOR_ERROR("Hesaplanamayan bir denklemin katsayısı.");
    private final String message;

    CommentMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
