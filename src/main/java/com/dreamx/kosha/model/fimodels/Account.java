package com.dreamx.kosha.model.fimodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author deepika_rajani
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account<S extends Summary> {

    @JsonProperty("Summary")
    private S summary;

    @JsonProperty("type")
    private String type;

}
