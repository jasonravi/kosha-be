package com.dreamx.kosha.model.nps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tier2SchemeTransactions {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("txnDate")
    private String txnDate;

    @JsonProperty("type")
    private String type;

    @JsonProperty("schemeId")
    private String schemeId;

    @JsonProperty("schemeName")
    private String schemeName;

    @JsonProperty("narration")
    private String narration;

    @JsonProperty("allocationPercent")
    private int allocationPercent;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("nav")
    private String nav;

    @JsonProperty("units")
    private double units;

    @JsonProperty("cumulativeUnits")
    private double cumulativeUnits;

    public LocalDateTime convertTxnDateToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.txnDate, formatter).atStartOfDay();
    }
}
