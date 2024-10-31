package com.dreamx.kosha.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class KoshaResponseDTO {

    private boolean status;
    private String message;
}
