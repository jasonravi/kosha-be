package com.dreamx.kosha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author deepika_rajani
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyLineChartDTO {

    private List<String> months;
    private List<Double> assets;
    private List<Double> liabilities;
    private List<Double> spends;
}
