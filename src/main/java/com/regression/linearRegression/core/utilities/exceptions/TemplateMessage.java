package com.regression.linearRegression.core.utilities.exceptions;

public enum TemplateMessage {
    RSQUARED_INTERPRETATION("R-kare (R²) değeri %.6f olup, modelin bağımlı değişkendeki toplam varyasyonu ne kadar açıkladığını gösterir."),
    RELATIONSHIP_INTERPRETATION("%s ile %s arasında %s bir ilişki vardır."),
    CHANGE_EFFECT_INTERPRETATION("%s'deki 1 birimlik artış, %s'nin %.2f birim, yani %% %.2f %s neden olur."),
    ZERO_EFFECT_INTERPRETATION("%s, 0 olduğunda %s, %.2f yani %% %.2f olur."),
    STANDARD_ERROR_INTERPRETATION("Modelin standart hatası %.6f olup, bu değer modelin tahminlerinin gerçek değerlerden ne kadar saptığını gösterir."),
    RESIDUAL_SQUARES_INTERPRETATION("Modelin toplam hata terimleri karesi %.6f olup, modelin açıklayamadığı varyasyonu gösterir. Bu değer, modelin %s performans sergilediğini ve tahminlerin doğruluğunun %s olduğunu gösterir.");

    private final String template;

    TemplateMessage(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
