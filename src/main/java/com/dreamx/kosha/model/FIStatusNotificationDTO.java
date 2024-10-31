package com.dreamx.kosha.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FIStatusNotificationDTO {

    private String sessionId;
    private String sessionStatus;

    @JsonProperty("FIStatusResponse")
    private List<FIStatusResponseDTO> fiStatusResponse;
}
