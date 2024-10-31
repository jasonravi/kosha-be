package com.dreamx.kosha.model.creditcard;

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
public class CreditCardTransaction {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("txnType")
    private String txnType;

    @JsonProperty("txnDate")
    private String txnDate;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("valueDate")
    private String valueDate;

    @JsonProperty("narration")
    private String narration;

    @JsonProperty("statementDate")
    private String statementDate;

    @JsonProperty("mcc")
    private String mcc;

    @JsonProperty("maskedCardNumber")
    private String maskedCardNumber;

    public LocalDateTime convertTxnDateToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.txnDate, formatter).atStartOfDay();
    }
}
