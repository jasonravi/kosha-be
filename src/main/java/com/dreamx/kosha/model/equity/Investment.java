package com.dreamx.kosha.model.equity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Investment {
    @JsonProperty("Holdings")
    private Holdings holdings;
}