package com.dreamx.kosha.service;

import com.dreamx.kosha.constants.EmploymentType;
import com.dreamx.kosha.constants.FinancialRecommendation;
import com.dreamx.kosha.constants.Priority;
import com.dreamx.kosha.entity.RecommendationDTO;
import com.dreamx.kosha.entity.User;

import java.util.List;

/**
 * @author deepika_rajani
 */
public interface RecommendationService {

    List<FinancialRecommendation> getRecommendations(int financialHealthScore, int age, int dependents, EmploymentType employment, Priority userGoalPriority, User user);

    RecommendationDTO getUsersRecommendations(Long userId, Boolean user);
}
