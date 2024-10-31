package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.constants.FinancialCategory;
import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.FinancialProfile;
import com.dreamx.kosha.constants.FinancialWeightage;
import com.dreamx.kosha.constants.Gender;
import com.dreamx.kosha.constants.HealthScoreCategory;
import com.dreamx.kosha.constants.MaritalStatus;
import com.dreamx.kosha.constants.TransactionType;
import com.dreamx.kosha.entity.HealthScoreDTO;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.entity.UserFinancialInstrument;
import com.dreamx.kosha.model.Category;
import com.dreamx.kosha.model.HealthScoreDetails;
import com.dreamx.kosha.service.HealthScoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author deepika_rajani
 */
@Service
@AllArgsConstructor
@Slf4j
public class HealthScoreServiceImpl implements HealthScoreService {

    @Override
    public HealthScoreDTO getUserHealthScore(User user, List<UserFinancialInstrument> financialInstruments, List<Transaction> transactions) {
        // 1. Financial Ratios
        // Fetch the necessary data for the user

        double totalAssets = 0d;
        double totalLiabilities = 0d;
        double monthlySpends = 0d;
        double monthlyIncome = 0d;
        double monthlyDebtPayments = 0d;

        for (UserFinancialInstrument instrument : financialInstruments) {

            final FinancialCategory category = instrument.getFinancialInstrument().getCategory();

            if (category == FinancialCategory.ASSET) {
                totalAssets += instrument.getValue();
            }

            if (category == FinancialCategory.LIABILITY) {
                totalLiabilities += instrument.getValue();
            }

            if (category == FinancialCategory.BANK_ACCOUNT) {
                totalAssets += instrument.getValue();
            }

        }

        YearMonth latestMonth = transactions.stream()
                .map(transaction -> YearMonth.from(transaction.getTxnDate()))
                .max(Comparator.naturalOrder())
                .orElse(YearMonth.now());

        // Filter transactions for the latest month
        List<Transaction> latestMonthTransactions = transactions.stream()
                .filter(transaction -> YearMonth.from(transaction.getTxnDate()).equals(latestMonth))
                .filter(transaction -> transaction.getFinancialInstrument() == FinancialInstrument.DEPOSIT)
                .collect(Collectors.toList());

        for (Transaction transaction : latestMonthTransactions) {

            final TransactionType type = transaction.getType();
            final String narration = transaction.getDescription();
            final Double amount = transaction.getAmount();
            final String mode = transaction.getMode();

            // calculating monthly spends
            if (type == TransactionType.DEBIT) {
                monthlySpends += amount;

                if (debtPatternMatch(narration, mode)) {
                    monthlyDebtPayments += amount;
                }
            }

            if (type == TransactionType.CREDIT) {
                if (salaryCreditPatternMatch(narration)) {
                    monthlyIncome = amount; // Retrieve the latest income
                }
            }

        }

        // Calculate the ratios
        double ALR; // = totalAssets / totalLiabilities;
        double SIR; // = monthlySpends / monthlyIncome;
        double DSCR; // = monthlyIncome / monthlyDebtPayments;

        // Handle zero liabilities
        if (totalLiabilities == 0) {
            ALR = (totalAssets > 0) ? Double.POSITIVE_INFINITY : 0; // Asset-heavy if assets exist, otherwise neutral
        } else {
            ALR = totalAssets / totalLiabilities;
        }

        // Handle zero income
        if (monthlyIncome == 0) {
            SIR = (monthlySpends > 0) ? Double.POSITIVE_INFINITY : 0; // Spend-heavy if spends exist, otherwise neutral
            DSCR = (monthlyDebtPayments > 0) ? 0 : Double.POSITIVE_INFINITY; // No income means no debt coverage
        } else {
            SIR = monthlySpends / monthlyIncome;
            DSCR = (monthlyDebtPayments > 0) ? monthlyIncome / monthlyDebtPayments : Double.POSITIVE_INFINITY; // Infinite if no debt payments
        }

        // 2. Financial Profile Analysis
        final FinancialProfile financialProfile = getFinancialProfile(ALR, SIR, DSCR);
        user.setFinancialProfile(financialProfile);

        return calculateHealthScore(user, ALR, SIR, DSCR);
    }

    private boolean debtPatternMatch(String narration, String mode) {
         /*
         -- For Loan Payments --
        Loan Payment
        Personal Loan
        Auto Loan
        Mortgage
        Home Loan
        Student Loan
        Cash Advance
        -- For Credit Card Payments --
        Credit Card Payment
        CC Payment
        Credit Balance
        Minimum Payment
        Credit Charge
        Credit Account Payment
        -- For Other Debt Payments --
        Line of Credit
        Debt Consolidation
        Payoff
        Settled Account
        Interest Payment
        Finance Charge
         */
        String[] keywords = {"Loan Payment", "Personal Loan", "Auto Loan", "Mortgage", "Home Loan", "Student Loan", "Cash Advance",
                "Line of Credit", "Debt Consolidation", "Payoff", "Settled Account", "Interest Payment", "Finance Charge",
                "Credit Card Payment", "CC Payment", "Credit Balance", "Minimum Payment", "Credit Charge", "Credit Account Payment"};

        String[] modeKeywords = {"UPI", "FT"};

        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(narration);
            if (matcher.find()) {
                for (String modeKeyword : modeKeywords) {
                    Pattern modePattern = Pattern.compile(modeKeyword, Pattern.CASE_INSENSITIVE);
                    Matcher modeMatcher = modePattern.matcher(mode);
                    if (modeMatcher.find()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean salaryCreditPatternMatch(String narration) {
        /*
        Salary
        Wages
        Pay
        Income
        Compensation
        Earnings
        Direct Deposit
        Payroll
        Payment
        Annual Bonus
        Overtime Pay
        Performance Bonus
        Bonus
        Commission
        Reimbursement
        Stipend
        Honorarium
        Monthly Salary
        Biweekly Pay
        Fortnightly Salary
        Gross Pay
        Net Pay
         */

        String[] keywords = {"Salary", "Wages", "Pay", "Income", "Compensation", "Earnings", "Direct Deposit", "Payroll", "Payment",
                "Annual Bonus", "Overtime Pay", "Performance Bonus", "Bonus", "Commission", "Reimbursement", "Stipend",
                "Honorarium", "Monthly Salary", "Biweekly Pay", "Fortnightly Salary", "Gross Pay", "Net Pay"};

        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(narration);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

    private FinancialProfile getFinancialProfile(double ALR, double SIR, double DSCR) {
        // Determine financial profile based on calculated ratios
        if (ALR == Double.POSITIVE_INFINITY) {
            return FinancialProfile.ASSET_HEAVY; // No liabilities, strong asset position
        } else if (ALR <= 1) {
            return FinancialProfile.LIABILITY_HEAVY;
        } else if (SIR == Double.POSITIVE_INFINITY) {
            return FinancialProfile.SPEND_HEAVY; // No income but has spending
        } // else if (DSCR == Double.POSITIVE_INFINITY) {
            // return FinancialProfile.FINANCIALLY_STABLE; // No debt payments means good coverage
//        }
        else if (DSCR > 1.5) {
            return FinancialProfile.FINANCIALLY_STABLE;
        } else {
            return FinancialProfile.DEFAULT;
        }

//        if (ALR > 1) {
//            return FinancialProfile.ASSET_HEAVY;
//        } else if (ALR <= 1) {
//            return FinancialProfile.LIABILITY_HEAVY;
//        } else if (SIR > 1) {
//            return FinancialProfile.SPEND_HEAVY;
//        } else if (DSCR > 4) {
//            return FinancialProfile.FINANCIALLY_STABLE;
//        } else {
//            return FinancialProfile.DEFAULT;
//        }
    }

    public HealthScoreDTO calculateHealthScore(User user, double ALR, double SIR, double DSCR) {
        // Fetch the necessary data for the user
        int age = user.getAge();
        Gender gender = user.getGender();
        MaritalStatus maritalStatus = user.getMaritalStatus();

        // Calculate the scores for the ratios
        double ALRScore = (ALR == Double.POSITIVE_INFINITY) ? 100 :
                ALR < 1 ? 0 :
                ALR == 1 ? 5 :
                        5 + (ALR - 1) * 2.5;

        double SIRScore = (SIR == Double.POSITIVE_INFINITY) ? 100 :
                SIR > 1 ? 0 :
                SIR == 1 ? 5 :
                        5 + (1 - SIR) * 2.5;

        double DSCRScore = (DSCR == Double.POSITIVE_INFINITY) ? 100 :
                DSCR < 1 ? 0 :
                DSCR == 1 ? 5 :
                        5 + (DSCR - 1) * 2.5;

        // Calculate the scores for the demographic factors
        double ageScore = age < 25 ? 2 :
                age <= 35 ? 4 :
                        age <= 45 ? 6 :
                                age <= 55 ? 8 : 10;

        double genderScore = gender == Gender.FEMALE ? 6 : 5;

        double maritalStatusScore = maritalStatus == MaritalStatus.MARRIED ? 7 : 5;

        // Calculate the total scores for the ratios and demographic factors
        double totalFinancialScore = ALRScore * FinancialWeightage.ALR.getWeightage() / 100
                + SIRScore * FinancialWeightage.SIR.getWeightage() / 100
                + DSCRScore * FinancialWeightage.DSCR.getWeightage() / 100;
        totalFinancialScore /= 10; // Normalize the score
        user.setFinancialHealthScore(Math.round(totalFinancialScore * 100.0) / 100.0);

        double totalDemographicScore = ageScore * FinancialWeightage.AGE.getWeightage() / 100
                + genderScore * FinancialWeightage.GENDER.getWeightage() / 100
                + maritalStatusScore * FinancialWeightage.MARITAL_STATUS.getWeightage() / 100;
        user.setDemographicHealthScore(Math.round(totalDemographicScore * 100.0) / 100.0);

        // Calculate the final score
        double finalScore = totalFinancialScore + totalDemographicScore;
//        finalScore = Math.round(finalScore);

        final HealthScoreDetails healthScoreDetails = healthScoreDetails(ALR, SIR, DSCR, ageScore, genderScore, maritalStatusScore, finalScore);

        HealthScoreCategory category = HealthScoreCategory.fromScore(finalScore);

        return new HealthScoreDTO(Math.round(finalScore), category.getValue(), healthScoreDetails);
    }

    private HealthScoreDetails healthScoreDetails(double ALR, double SIR, double DSCR, double ageScore, double genderScore, double maritalStatusScore, double finalScore) {
        // Set details
        HealthScoreDetails details = new HealthScoreDetails();

        List<Category> fin = new ArrayList<>();
        fin.add(new Category("Asset-to-Liability Ratio",
                ALR == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Math.round(ALR),
                FinancialWeightage.ALR.getWeightage()));

        fin.add(new Category("Spends-to-Income Ratio",
                SIR == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Math.round(SIR),
                FinancialWeightage.SIR.getWeightage()));

        fin.add(new Category("Debt Service Coverage Ratio",
                DSCR == Double.POSITIVE_INFINITY ? 150 // Double.POSITIVE_INFINITY
                         : Math.round(DSCR),
                FinancialWeightage.DSCR.getWeightage()));

        details.setFinancialScore(fin);

        List<Category> demographics = new ArrayList<>();
        demographics.add(new Category("Age",
                ageScore == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Math.round(ageScore),
                FinancialWeightage.AGE.getWeightage()));

        demographics.add(new Category("Gender",
                genderScore == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Math.round(genderScore),
                FinancialWeightage.GENDER.getWeightage()));

        demographics.add(new Category("Marital",
                maritalStatusScore == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Math.round(maritalStatusScore),
                FinancialWeightage.MARITAL_STATUS.getWeightage()));

        details.setDemographicScore(demographics);

        details.setFinalScore(Math.round(finalScore));
        return details;
    }
}
