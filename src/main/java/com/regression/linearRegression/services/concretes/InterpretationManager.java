package com.regression.linearRegression.services.concretes;

import com.regression.linearRegression.core.utilities.exceptions.CommentMessage;
import com.regression.linearRegression.core.utilities.exceptions.TemplateMessage;
import org.springframework.stereotype.Service;

@Service
public class InterpretationManager {

    public String generateRelationship(double m) {
        return (m > 0) ? CommentMessage.DIRECTLY_PROPORTIONAL.getMessage() : CommentMessage.INVERSELY_PROPORTIONAL.getMessage();
    }

    public String generateChangeEffect(double m) {
        return (m > 0) ? CommentMessage.INCREASE.getMessage() : CommentMessage.DECREASE.getMessage();
    }

    public String generateRSquaredInterpretation(double rSquared) {
        return String.format(TemplateMessage.RSQUARED_INTERPRETATION.getTemplate(), rSquared);
    }

    public String generateRelationshipInterpretation(String xVariableName, String yVariableName, String relationship) {
        return String.format(TemplateMessage.RELATIONSHIP_INTERPRETATION.getTemplate(), xVariableName, yVariableName, relationship);
    }

    public String generateChangeEffectInterpretation(String xVariableName, String yVariableName, double m, String percentageChange, String changeEffect) {
        return String.format(TemplateMessage.CHANGE_EFFECT_INTERPRETATION.getTemplate(), xVariableName, yVariableName, Math.abs(m), Double.parseDouble(percentageChange), changeEffect);
    }

    public String generateZeroEffectInterpretation(String xVariableName, String yVariableName, double b, double percentageB) {
        return String.format(TemplateMessage.ZERO_EFFECT_INTERPRETATION.getTemplate(), xVariableName, yVariableName, b, percentageB);
    }

    public String generateStandardErrorInterpretation(double standardError) {
        return String.format(TemplateMessage.STANDARD_ERROR_INTERPRETATION.getTemplate(), standardError);
    }

    public String generateResidualSquaresInterpretation(double sumOfSquaredResiduals) {
        String performanceComment = (sumOfSquaredResiduals < 0.05) ? CommentMessage.GOOD.getMessage() : CommentMessage.BAD.getMessage();
        String accuracyComment = (sumOfSquaredResiduals < 0.05) ? CommentMessage.HIGH.getMessage() : CommentMessage.LOW.getMessage();
        return String.format(TemplateMessage.RESIDUAL_SQUARES_INTERPRETATION.getTemplate(), sumOfSquaredResiduals, performanceComment, accuracyComment);
    }

    public String generateEconomicInterpretation(double m, double b, double rSquared, double standardError, double sumOfSquaredResiduals, String xVariableName, String yVariableName) {
        String relationship = generateRelationship(m);
        String changeEffect = generateChangeEffect(m);
        String percentageChange = String.format("%.2f", Math.abs(m * 100));
        double percentageB = b * 100;

        String rSquaredInterpretation = generateRSquaredInterpretation(rSquared);
        String relationshipInterpretation = generateRelationshipInterpretation(xVariableName, yVariableName, relationship);
        String changeEffectInterpretation = generateChangeEffectInterpretation(xVariableName, yVariableName, m, percentageChange, changeEffect);
        String zeroEffectInterpretation = generateZeroEffectInterpretation(xVariableName, yVariableName, b, percentageB);
        String standardErrorInterpretation = generateStandardErrorInterpretation(standardError);
        String residualSquaresInterpretation = generateResidualSquaresInterpretation(sumOfSquaredResiduals);

        return String.join(" ", rSquaredInterpretation, relationshipInterpretation, changeEffectInterpretation, zeroEffectInterpretation, standardErrorInterpretation, residualSquaresInterpretation);
    }
}
