package com.dreamx.kosha.model.deposit;

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
public class DepositTransaction {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("currentBalance")
    private double currentBalance;

    @JsonProperty("transactionTimestamp")
    private String transactionTimestamp;

    @JsonProperty("narration")
    private String narration;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("type")
    private String type;

    @JsonProperty("mode")
    private String mode;

    public LocalDateTime convertTransactionTimestampToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(this.transactionTimestamp, formatter);
    }
}