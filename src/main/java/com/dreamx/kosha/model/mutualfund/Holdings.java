package com.dreamx.kosha.model.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holdings {

    @JsonProperty("Holding")
    private Holding holding;
}
