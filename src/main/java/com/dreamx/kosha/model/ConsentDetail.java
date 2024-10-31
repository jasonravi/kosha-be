package com.dreamx.kosha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConsentDetail {

    private String consentStart;
    private String consentExpiry;
    private ConsentMode consentMode;
    private FetchType fetchType;
    private List<ConsentType> consentTypes;
    private List<FiType> fiTypes;
    private DataConsumer DataConsumer;
    private Customer customer;
    private Purpose Purpose;
    private FIDataRange FIDataRange;
    private UnitDTO DataLife;
    private UnitDTO Frequency;


}
