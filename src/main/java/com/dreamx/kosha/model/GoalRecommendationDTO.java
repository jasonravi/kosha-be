package com.dreamx.kosha.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoalRecommendationDTO {

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double achievedAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double targetAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String priority;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    List<InstrumentDTO> recommendations;
}
