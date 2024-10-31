package com.dreamx.kosha.model.equity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquityTransaction {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("tradeId")
    private String tradeId;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("transactionDateTime")
    private String transactionDateTime;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("isin")
    private String isin;

    @JsonProperty("equityCategory")
    private String equityCategory;

    @JsonProperty("instrumentType")
    private String instrumentType;

    @JsonProperty("narration")
    private String narration;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("totalCharge")
    private String totalCharge;

    @JsonProperty("tradeValue")
    private String tradeValue;

    @JsonProperty("type")
    private String type;

    @JsonProperty("shareHolderEquityType")
    private String shareHolderEquityType;

    @JsonProperty("units")
    private String units;

    @JsonProperty("otherCharges")
    private String otherCharges;

    public LocalDateTime convertTransactionDateTimeToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(this.transactionDateTime, formatter);
    }
}
