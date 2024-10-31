package com.dreamx.kosha.model.termdeposit;

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
public class TermDepositSummary extends Summary {

    @JsonProperty("openingDate")
    private String openingDate;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("branch")
    private String branch;

    @JsonProperty("ifsc")
    private String ifsc;

    @JsonProperty("maturityAmount")
    private double maturityAmount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("interestPayout")
    private String interestPayout;

    @JsonProperty("interestRate")
    private String interestRate;

    @JsonProperty("maturityDate")
    private String maturityDate;

    @JsonProperty("principalAmount")
    private double principalAmount;

    @JsonProperty("tenureDays")
    private int tenureDays;

    @JsonProperty("tenureMonths")
    private int tenureMonths;

    @JsonProperty("tenureYears")
    private int tenureYears;

    @JsonProperty("interestComputation")
    private String interestComputation;

    @JsonProperty("compoundingFrequency")
    private String compoundingFrequency;

    @JsonProperty("interestPeriodicPayoutAmount")
    private double interestPeriodicPayoutAmount;

    @JsonProperty("interestOnMaturity")
    private double interestOnMaturity;

    @JsonProperty("currentValue")
    private double currentValue;

    public LocalDateTime getOpeningDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(openingDate).atStartOfDay();
    }

    public LocalDateTime getMaturityDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(maturityDate, formatter).atStartOfDay();
    }
}