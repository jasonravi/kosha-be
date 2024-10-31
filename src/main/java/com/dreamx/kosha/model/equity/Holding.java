package com.dreamx.kosha.model.equity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holding {
    @JsonProperty("description")
    private String description;

    @JsonProperty("investmentDateTime")
    private String investmentDateTime;

    @JsonProperty("isin")
    private String isin;

    @JsonProperty("issuerName")
    private String issuerName;

    @JsonProperty("lastTradedPrice")
    private String lastTradedPrice;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("units")
    private String units;

    public LocalDateTime getInvestmentDateTimeAsLocalDateTime() {
        return LocalDateTime.parse(investmentDateTime);
    }
}