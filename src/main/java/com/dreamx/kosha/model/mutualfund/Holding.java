package com.dreamx.kosha.model.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holding {

    @JsonProperty("amc")
    private String amc;

    @JsonProperty("registrar")
    private String registrar;

    @JsonProperty("schemeCode")
    private String schemeCode;

    @JsonProperty("isin")
    private String isin;

    @JsonProperty("ucc")
    private String ucc;

    @JsonProperty("amfiCode")
    private String amfiCode;

    @JsonProperty("folioNo")
    private String folioNo;

    @JsonProperty("dividendType")
    private String dividendType;

    @JsonProperty("FatcaStatus")
    private String fatcaStatus;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("units")
    private String units;

    @JsonProperty("closingUnits")
    private String closingUnits;

    @JsonProperty("lienUnits")
    private String lienUnits;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("nav")
    private String nav;

    @JsonProperty("lockingUnits")
    private String lockingUnits;
}
