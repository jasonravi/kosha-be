package com.dreamx.kosha.constants;

/**
 * @author deepika_rajani
 */
public enum FinancialCategory {

    ASSET("Assets"),
    LIABILITY("Liabilities"),
    BANK_ACCOUNT("Bank Account"),
    INSURANCE("Insurance"),
    EXPENSE("Spends"),
    NONE("None");

    private final String displayName;

    FinancialCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
