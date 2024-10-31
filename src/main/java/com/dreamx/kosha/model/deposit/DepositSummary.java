package com.dreamx.kosha.model.deposit;

import com.dreamx.kosha.model.fimodels.Summary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class DepositSummary extends Summary {

    @JsonProperty("currentBalance")
    private String currentBalance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("ifscCode")
    private String ifscCode;

    @JsonProperty("micrCode")
    private String micrCode;

    @JsonProperty("openingDate")
    private String openingDate;

    @JsonProperty("status")
    private String status;

    public LocalDateTime getOpeningDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(openingDate, formatter).atStartOfDay();
    }
}
