package com.dreamx.kosha.entity;

import com.dreamx.kosha.model.GoalRecommendationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author deepika_rajani
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendationDTO {

    private List<GoalRecommendationDTO> recommendations;
}
