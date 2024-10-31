package com.dreamx.kosha.constants;


/**
 * @author deepika_rajani
 */
public enum FinancialInstrument {

    RD("Recurring Deposit", FinancialCategory.ASSET, FinancialRecommendation.EMERGENCY_FUND),
    FD("Fix Deposit", FinancialCategory.ASSET, FinancialRecommendation.EMERGENCY_FUND),
    HOME_LOAN("Home Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),
    TD("Term Deposit", FinancialCategory.ASSET, FinancialRecommendation.EMERGENCY_FUND),
    PERSONAL_LOAN("Personal Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),
    BUSINESS_INSURANCE("Business insurance", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    CREDIT_CARD("Credit Card", FinancialCategory.LIABILITY, FinancialRecommendation.SPENDS),
    AIF("National Agriculture Infra Financing Facility", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    CP("Commercial Paper", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    CD("Certificates of Deposit", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    G_SEC("Government Securities", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    EQUITY_SHARES("Equity Shares", FinancialCategory.ASSET, FinancialRecommendation.INVESTMENTS),
    MUTUAL_FUND_UNITS("Mutual Fund Units", FinancialCategory.ASSET, FinancialRecommendation.INVESTMENTS),
    ETF("Exchange Traded Funds", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    IDR("Indian Depository Receipts", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    AIF_UNITS("Alternative Investment Funds Units", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    INVIT_UNITS("Units of Infrastructure Investment Trusts", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    REIT_UNITS("Units of Real Estate Investment Trusts", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    BONDS("Bonds", FinancialCategory.ASSET, FinancialRecommendation.INVESTMENTS),
    DEBENTURES("Debentures", FinancialCategory.ASSET, FinancialRecommendation.NONE),
    ULIP("Unit Linked Insurance Plan", FinancialCategory.ASSET, FinancialRecommendation.INVESTMENTS),   // todo check if this is correct
    NPS("Balances under the National Pension System", FinancialCategory.ASSET, FinancialRecommendation.PENSION),
    EPF("Employee Provident Fund", FinancialCategory.ASSET, FinancialRecommendation.PENSION),
    PPF("Public Provident Fund", FinancialCategory.ASSET, FinancialRecommendation.PENSION),
    SIP("Systematic Investment Plan", FinancialCategory.ASSET, FinancialRecommendation.INVESTMENTS),
    CIS("Collective Investment Schemes", FinancialCategory.ASSET, FinancialRecommendation.NONE),

    CC("Credit Card", FinancialCategory.LIABILITY, FinancialRecommendation.SPENDS),
    PL("Personal Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE), // todo check if this is correct
    HL("Home Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE), // todo check if this is correct
    EL("Education Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),    // todo check if this is correct
    VEHICLE_LOAN("Vehicle Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),    // todo check if this is correct
    GOLD_LOAN("Gold Loan", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),  // todo check if this is correct
    MORTGAGE("Mortgage", FinancialCategory.LIABILITY, FinancialRecommendation.NONE),

    DEPOSIT("Deposit", FinancialCategory.BANK_ACCOUNT, FinancialRecommendation.EMERGENCY_FUND),

    GENERAL_INSURANCE("General Insurance", FinancialCategory.INSURANCE, FinancialRecommendation.NONE),  // todo check if this is correct
    LIFE_INSURANCE("Life Insurance", FinancialCategory.INSURANCE, FinancialRecommendation.NONE),    // todo check if this is correct
    HEALTH_INSURANCE("Health Insurance", FinancialCategory.INSURANCE, FinancialRecommendation.HEALTH_INSURANCE),
    TERM_INSURANCE("Term Insurance", FinancialCategory.INSURANCE, FinancialRecommendation.TERM_INSURANCE),
    INSURANCE_POLICIES("Insurance Policies", FinancialCategory.INSURANCE, FinancialRecommendation.NONE),    // todo check if this is correct

    GSTR("Goods and Services Tax Return", FinancialCategory.NONE, FinancialRecommendation.NONE);

    // todo add EXPENSES case

    private String name;
    private FinancialCategory category;
    private FinancialRecommendation recommendation;

    FinancialInstrument(String name, FinancialCategory category, FinancialRecommendation recommendation) {
        this.name = name;
        this.category = category;
        this.recommendation = recommendation;
    }

    public String getName() {
        return name;
    }

    public FinancialCategory getCategory() {
        return this.category;
    }

    public FinancialRecommendation getRecommendation() {
        return this.recommendation;
    }

}
