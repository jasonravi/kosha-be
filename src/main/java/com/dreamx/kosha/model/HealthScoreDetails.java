package com.dreamx.kosha.model;

import lombok.Data;

import java.util.List;

/**
 * @author deepika_rajani
 */
@Data
public class HealthScoreDetails {

    private List<Category> financialScore;
    private List<Category> demographicScore;
    private long finalScore;
}
