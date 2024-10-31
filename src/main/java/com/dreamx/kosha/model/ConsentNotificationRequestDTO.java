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
public class ConsentNotificationRequestDTO {

    private String ver;
    private String timestamp;
    private String txnid;
    private String consentHandle;
    @JsonProperty("Notifier")
    private NotifierDTO notifier;

    @JsonProperty("ConsentStatusNotification")
    private ConsentStatusNotificationDTO consentStatusNotification;

}
