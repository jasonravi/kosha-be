package com.dreamx.kosha.service;

import com.dreamx.kosha.model.UserGoalResponseDTO;
import com.dreamx.kosha.entity.UserGoalDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GoalService {

    List<UserGoalDTO> getGoal(Long userId);
    UserGoalResponseDTO saveGoal(Long userId, String handle, UserGoalDTO userGoalDTO) throws JsonProcessingException;
}
