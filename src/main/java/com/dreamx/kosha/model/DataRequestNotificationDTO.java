package com.dreamx.kosha.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataRequestNotificationDTO {

    private String ver;
    private String timestamp;
    private String txnid;

    @JsonProperty("Notifier")
    private NotifierDTO notifier;

    @JsonProperty("FIStatusNotification")
    private FIStatusNotificationDTO fiStatusNotification;


}
