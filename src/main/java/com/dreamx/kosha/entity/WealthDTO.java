package com.dreamx.kosha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WealthDTO {

    private String totalWealth;
    private String assets;
    private String liabilities;
    private String spends;
}
