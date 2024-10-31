package com.dreamx.kosha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author deepika_rajani
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentPortfolioDTO {

    private String category;
    private String totalValue;
    private List<ItemDTO> items;
}
