package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.UserFinancialInstrument;
import jakarta.transaction.Transactional;

import java.util.List;

public interface FinancialInstrumentCustomRepository {
    List<UserFinancialInstrument> findByUserId(Long userId);
    Double getAchieveAmount(Long userId, List<String> instruments);

    void insertUserFinancialInstrument(List<UserFinancialInstrument> ufiList);
}
