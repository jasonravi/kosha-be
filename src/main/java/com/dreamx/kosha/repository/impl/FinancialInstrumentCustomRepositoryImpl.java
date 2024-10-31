package com.dreamx.kosha.repository.impl;

import com.dreamx.kosha.entity.UserFinancialInstrument;
import com.dreamx.kosha.repository.FinancialInstrumentCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author deepika_rajani
 */
@Slf4j
public class FinancialInstrumentCustomRepositoryImpl implements FinancialInstrumentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserFinancialInstrument> findByUserId(Long userId) {
        String sql = "SELECT ufi FROM UserFinancialInstrument ufi WHERE ufi.user.id = :userId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Double getAchieveAmount(Long userId, List<String> instruments) {
        String sql = "SELECT sum(ufi.value) as amount FROM UserFinancialInstrument ufi WHERE ufi.user.id = :userId AND ufi.financialInstrument IN :instruments";

        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("instruments", instruments);

        // Fetch the result
        Double amount = (Double) query.getSingleResult();

        // Handle case where the result is null (no matching records)
        return amount != null ? amount : 0.0;
    }

    @Override
    @Transactional
    public void insertUserFinancialInstrument(List<UserFinancialInstrument> ufiList) {
        String deleteQuery = "delete from user_financial_instrument where user_id = :userId";
        Object obj = entityManager.createNativeQuery(deleteQuery)
                .setParameter("userId", ufiList.get(0).getUser().getId())
                .executeUpdate();
        log.info("deleted rows fin ins {}: ", obj);

        String queryString = "INSERT INTO user_financial_instrument (value, name, user_id, meta, date_range_from, date_range_to, txn_time) VALUES (:value, :financialInstrument, :user, :meta, :dateRangeFrom, :dateRangeTo, :transactionTime)";

        int batchSize = 50; // Adjust the batch size as needed

        for (int i = 0; i < ufiList.size(); i++) {
            UserFinancialInstrument ufi = ufiList.get(i);
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter("value", ufi.getValue());
            query.setParameter("financialInstrument", ufi.getFinancialInstrument().name());

            query.setParameter("user", ufi.getUser().getId());
            query.setParameter("meta", ufi.getMeta());

            query.setParameter("dateRangeFrom", ufi.getDateRangeFrom());
            query.setParameter("dateRangeTo", ufi.getDateRangeTo());

            query.setParameter("transactionTime", ufi.getTransactionTime());
            query.executeUpdate();

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

}
