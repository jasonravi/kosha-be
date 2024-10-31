package com.dreamx.kosha.model.termdeposit;

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
public class TermDepositTransaction {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("narration")
    private String narration;

    @JsonProperty("type")
    private String type;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("balance")
    private double balance;

    @JsonProperty("transactionDateTime")
    private String transactionDateTime;

    @JsonProperty("valueDate")
    private String valueDate;

    @JsonProperty("reference")
    private String reference;

    public LocalDateTime convertTransactionDateTimeToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(this.transactionDateTime, formatter);
    }
}
