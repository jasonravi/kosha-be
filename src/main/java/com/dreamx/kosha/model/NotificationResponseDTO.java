package com.dreamx.kosha.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponseDTO {

    private String ver;
    private String timestamp;
    private String txnid;
    private String response;
}
