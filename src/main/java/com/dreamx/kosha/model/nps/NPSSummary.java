package com.dreamx.kosha.model.nps;

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
public class NPSSummary extends Summary {

    @JsonProperty("currentValue")
    private double currentValue;

    @JsonProperty("tier1NAVDate")
    private String tier1NAVDate;

    @JsonProperty("tier2NAVDate")
    private String tier2NAVDate;

    @JsonProperty("debtAssetValue")
    private double debtAssetValue;

    @JsonProperty("equityAssetValue")
    private double equityAssetValue;

    @JsonProperty("otherAssetValue")
    private double otherAssetValue;

    @JsonProperty("openingDate")
    private String openingDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tier1Status")
    private String tier1Status;

    @JsonProperty("tier2Status")
    private String tier2Status;

    public LocalDateTime getTier1NAVDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(tier1NAVDate, formatter).atStartOfDay();
    }

    public LocalDateTime getTier2NAVDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(tier2NAVDate, formatter).atStartOfDay();
    }

    public LocalDateTime getOpeningDateAsLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(openingDate, formatter).atStartOfDay();
    }
}
