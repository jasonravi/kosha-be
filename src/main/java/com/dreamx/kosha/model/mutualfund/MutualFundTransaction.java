package com.dreamx.kosha.model.mutualfund;

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
public class MutualFundTransaction {

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("amc")
    private String amc;

    @JsonProperty("registrar")
    private String registrar;

    @JsonProperty("schemeCode")
    private String schemeCode;

    @JsonProperty("schemePlan")
    private String schemePlan;

    @JsonProperty("isin")
    private String isin;

    @JsonProperty("amfiCode")
    private String amfiCode;

    @JsonProperty("fundType")
    private String fundType;

    @JsonProperty("schemeOption")
    private String schemeOption;

    @JsonProperty("schemeTypes")
    private String schemeTypes;

    @JsonProperty("schemeCategory")
    private String schemeCategory;

    @JsonProperty("ucc")
    private String ucc;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("closingUnits")
    private String closingUnits;

    @JsonProperty("lienUnits")
    private String lienUnits;

    @JsonProperty("nav")
    private String nav;

    @JsonProperty("navDate")
    private String navDate;

    @JsonProperty("type")
    private String type;

    @JsonProperty("orderDate")
    private String orderDate;

    @JsonProperty("executionDate")
    private String executionDate;

    @JsonProperty("lock-inFlag")
    private String lockInFlag;

    @JsonProperty("lock-inDays")
    private String lockInDays;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("narration")
    private String narration;

    public LocalDateTime convertExecutionDateToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.executionDate, formatter).atStartOfDay();
    }
}