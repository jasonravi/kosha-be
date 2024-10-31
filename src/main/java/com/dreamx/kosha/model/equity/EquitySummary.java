package com.dreamx.kosha.model.equity;

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
public class EquitySummary extends Summary {

    @JsonProperty("Investment")
    private Investment investment;

    @JsonProperty("currentValue")
    private double currentValue;
}