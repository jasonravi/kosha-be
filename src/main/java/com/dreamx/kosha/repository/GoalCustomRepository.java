package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.UserGoalDTO;

import java.util.List;

public interface GoalCustomRepository {

    List<UserGoalDTO> getGoal(Long userId);
    void saveGoal(Long id, UserGoalDTO userGoalDTO);
}
