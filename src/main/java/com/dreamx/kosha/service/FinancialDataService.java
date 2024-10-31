package com.dreamx.kosha.service;

import com.dreamx.kosha.entity.HealthScoreDTO;
import com.dreamx.kosha.entity.InvestmentPortfolioDTO;
import com.dreamx.kosha.entity.MonthlyLineChartDTO;
import com.dreamx.kosha.entity.WealthDTO;

import java.util.List;

/**
 * @author deepika_rajani
 */
public interface FinancialDataService {
    MonthlyLineChartDTO getUserMonthlyData(Long userId);

    HealthScoreDTO getHealthScore(Long userId);

    WealthDTO getTotalWealth(Long userId);

    List<InvestmentPortfolioDTO> getInvestmentPortfolio(Long userId);

    void processUserFinancialData(Long userId);
}
