package com.dreamx.kosha.constants;

/**
 * @author deepika_rajani
 */
public enum FinancialRecommendation {

    HEALTH_INSURANCE("Recommendation for health insurance coverage to protect against medical expenses.", 1000000),
    TERM_INSURANCE("Recommendation for term insurance to provide life cover for a specific term.", 5000000),
    EMERGENCY_FUND("Recommendation to maintain an emergency fund for unexpected financial needs.", 500000),
    TAX_SAVING("Recommendation for tax-saving investment options.", 1500000),
    USER_DEFINED_GOALS("Recommendation based on user-defined financial goals.", 1000000),
    PENSION("Recommendation to invest in pension schemes for post-retirement income.", 2000000),
    INVESTMENTS("Recommendation to make investments for wealth creation.", 2000000),
    SPENDS("Recommendation related to spending and managing expenses.", 500000),
    NONE("No specific financial recommendation at the moment.", 0);

    private final String description;
    private final double fund;

    // Constructor to initialize the description
    FinancialRecommendation(String description, double fund) {

        this.description = description;
        this.fund = fund;
    }

    // Getter to access the description
    public String getDescription() {
        return description;
    }

    public Double getFund() {
        return fund;
    }
}
