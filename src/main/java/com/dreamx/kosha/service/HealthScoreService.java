package com.dreamx.kosha.service;

import com.dreamx.kosha.entity.HealthScoreDTO;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.entity.UserFinancialInstrument;

import java.util.List;

/**
 * @author deepika_rajani
 */
public interface HealthScoreService {

    HealthScoreDTO getUserHealthScore(User user, List<UserFinancialInstrument> financialInstruments, List<Transaction> transactions);
}
