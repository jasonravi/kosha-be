package com.dreamx.kosha.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsentResponseDTO {

    private Boolean success;
    private List<ConsentDTO> consents;
    private String redirect_url;
    private long userId;
}
