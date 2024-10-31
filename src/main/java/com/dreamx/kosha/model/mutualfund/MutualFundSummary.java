package com.dreamx.kosha.model.mutualfund;

import com.dreamx.kosha.model.fimodels.Summary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class MutualFundSummary extends Summary {


    @JsonProperty("investmentValue")
    private double investmentValue;

    @JsonProperty("currentValue")
    private double currentValue;

    @JsonProperty("Investment")
    private Investment investment;

}
