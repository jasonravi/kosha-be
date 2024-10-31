package com.dreamx.kosha.constants;

/**
 * @author deepika_rajani
 */
public enum HealthScoreCategory {

    FINANCIALLY_SECURE("Financially Secure"),
    NEEDS_IMPROVEMENT("Needs Improvement"),
    AT_RISK("At Risk");

    private String value;

    HealthScoreCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HealthScoreCategory fromScore(double score) {
        if (score >= 67 && score <= 100) {
            return FINANCIALLY_SECURE;
        } else if (score >= 34 && score <= 66) {
            return NEEDS_IMPROVEMENT;
        } else if (score >= 0 && score <= 33) {
            return AT_RISK;
        } else {
            throw new IllegalArgumentException("Invalid score: " + score);
        }
    }
}
