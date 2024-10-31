package com.dreamx.kosha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author deepika_rajani
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private String category;
    private double score;
    private int weightage;
}
