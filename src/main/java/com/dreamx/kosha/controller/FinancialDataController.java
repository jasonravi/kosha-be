package com.dreamx.kosha.controller;

import com.dreamx.kosha.entity.HealthScoreDTO;
import com.dreamx.kosha.entity.InvestmentPortfolioDTO;
import com.dreamx.kosha.entity.MonthlyLineChartDTO;
import com.dreamx.kosha.entity.WealthDTO;
import com.dreamx.kosha.service.FinancialDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author deepika_rajani
 */
@RestController
@AllArgsConstructor
@RequestMapping("v1/kosha/financial-data")
public class FinancialDataController {

    private final FinancialDataService financialDataService;

    @GetMapping("{id}/monthly-data")
    public ResponseEntity<MonthlyLineChartDTO> getUserMonthlyData(@PathVariable("id") Long userId) {
        MonthlyLineChartDTO monthlyData = financialDataService.getUserMonthlyData(userId);
        return new ResponseEntity<>(monthlyData, HttpStatus.OK);
    }

    @GetMapping("{id}/health-score")
    public ResponseEntity<HealthScoreDTO> healthScore(@PathVariable("id") Long userId) {
        HealthScoreDTO healthScore = financialDataService.getHealthScore(userId);
        return new ResponseEntity<>(healthScore, HttpStatus.OK);
    }

    @GetMapping("{id}/total-wealth")
    public ResponseEntity<WealthDTO> totalWealth(@PathVariable("id") Long userId) {
        WealthDTO wealth = financialDataService.getTotalWealth(userId);
        return new ResponseEntity<>(wealth, HttpStatus.OK);
    }

    @GetMapping("{id}/investment-portfolio")
    public ResponseEntity<List<InvestmentPortfolioDTO>> getInvestmentPortfolio(@PathVariable("id") Long userId) {
        final List<InvestmentPortfolioDTO> investmentPortfolio = financialDataService.getInvestmentPortfolio(userId);
        return new ResponseEntity<>(investmentPortfolio, HttpStatus.OK);
    }

}
