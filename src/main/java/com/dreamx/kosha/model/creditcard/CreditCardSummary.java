package com.dreamx.kosha.model.creditcard;

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
public class CreditCardSummary extends Summary {
    @JsonProperty("totalDueAmount")
    private String totalDueAmount;

    @JsonProperty("dueDate")
    private String dueDate;

    public LocalDateTime getDueDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dueDate, formatter).atStartOfDay();
    }
}