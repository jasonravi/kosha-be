package com.dreamx.kosha.constants;

/**
 * @author deepika_rajani
 */
public enum FinancialWeightage {

    ALR(30),
    SIR(30),
    DSCR(30),
    AGE(4),
    GENDER(2),
    MARITAL_STATUS(4);

    private final int weightage;

    FinancialWeightage(int weightage) {
        this.weightage = weightage;
    }

    public int getWeightage() {
        return this.weightage;
    }

}
