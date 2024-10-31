package com.dreamx.kosha.entity;

import com.dreamx.kosha.model.HealthScoreDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthScoreDTO {

    private long score;
    private String description;
    private HealthScoreDetails details;
}
