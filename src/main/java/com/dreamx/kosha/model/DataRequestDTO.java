package com.dreamx.kosha.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataRequestDTO {

    private String consent_handle;
    private String from;
    private String to;
    private String curve;
    private Boolean proxy;
    private String mobileNumber;
}
