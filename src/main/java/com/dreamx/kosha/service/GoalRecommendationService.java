package com.dreamx.kosha.service;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.GoalRecommendation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GoalRecommendationService {

    // A mapping of user goals to financial recommendations

    private static final Map<GoalRecommendation, List<FinancialInstrument>> goalFinanceRecommendationMap = new HashMap<>();


    static {

        goalFinanceRecommendationMap.put(GoalRecommendation.EDUCATION, Arrays.asList(FinancialInstrument.FD, FinancialInstrument.RD, FinancialInstrument.MUTUAL_FUND_UNITS));

        goalFinanceRecommendationMap.put(GoalRecommendation.BUYING_HOME, Arrays.asList(FinancialInstrument.HOME_LOAN, FinancialInstrument.SIP));

        goalFinanceRecommendationMap.put(GoalRecommendation.BUILD_ASSETS, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD, FinancialInstrument.EPF, FinancialInstrument.PPF));

        goalFinanceRecommendationMap.put(GoalRecommendation.TRAVEL, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD));

        goalFinanceRecommendationMap.put(GoalRecommendation.WANT_TO_TRAVEL, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD));

        goalFinanceRecommendationMap.put(GoalRecommendation.STARTING_BUSINESS, Arrays.asList(FinancialInstrument.PERSONAL_LOAN, FinancialInstrument.GOLD_LOAN, FinancialInstrument.BUSINESS_INSURANCE));

        goalFinanceRecommendationMap.put(GoalRecommendation.BUYING_CAR, Arrays.asList(FinancialInstrument.VEHICLE_LOAN, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.BUY_GADGET, Arrays.asList(FinancialInstrument.PERSONAL_LOAN));

        goalFinanceRecommendationMap.put(GoalRecommendation.BUYING_MOTORCYCLE, Arrays.asList(FinancialInstrument.VEHICLE_LOAN, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.SAVING_FOR_WEDDING, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD, FinancialInstrument.FD, FinancialInstrument.CREDIT_CARD));

        goalFinanceRecommendationMap.put(GoalRecommendation.SAVING_FOR_RETIREMENT, Arrays.asList(FinancialInstrument.NPS, FinancialInstrument.PPF, FinancialInstrument.EPF, FinancialInstrument.MUTUAL_FUND_UNITS));

        goalFinanceRecommendationMap.put(GoalRecommendation.CHARITY_DONATIONS, Arrays.asList(FinancialInstrument.MUTUAL_FUND_UNITS, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.REAL_ESTATE_INVESTMENT, Arrays.asList(FinancialInstrument.HOME_LOAN, FinancialInstrument.MUTUAL_FUND_UNITS));

        goalFinanceRecommendationMap.put(GoalRecommendation.LUXURY_PURCHASE, Arrays.asList(FinancialInstrument.CREDIT_CARD, FinancialInstrument.RD));

        goalFinanceRecommendationMap.put(GoalRecommendation.VENTURE_CAPITAL, Arrays.asList(FinancialInstrument.MUTUAL_FUND_UNITS, FinancialInstrument.AIF));

        goalFinanceRecommendationMap.put(GoalRecommendation.HOLIDAY_FUND, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD));

        goalFinanceRecommendationMap.put(GoalRecommendation.RENOVATE_HOME, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.PERSONAL_LOAN, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.EMERGENCY_FUND, Arrays.asList(FinancialInstrument.RD, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.PERSONAL_SAVING, Arrays.asList(FinancialInstrument.SIP, FinancialInstrument.RD, FinancialInstrument.FD));

        goalFinanceRecommendationMap.put(GoalRecommendation.DEFAULT, Arrays.asList(FinancialInstrument.FD, FinancialInstrument.HEALTH_INSURANCE, FinancialInstrument.TERM_INSURANCE));

        goalFinanceRecommendationMap.put(GoalRecommendation.INSURANCE, Arrays.asList(FinancialInstrument.HEALTH_INSURANCE, FinancialInstrument.TERM_INSURANCE));

    }

    public List<FinancialInstrument> getRecommendationForGoal(GoalRecommendation goalRecommendation) {
        return goalFinanceRecommendationMap.getOrDefault(goalRecommendation, new ArrayList<>());
    }
}
