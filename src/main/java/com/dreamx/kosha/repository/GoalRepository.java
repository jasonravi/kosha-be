package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.UserGoalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<UserGoalDTO, Long>, GoalCustomRepository {
}
