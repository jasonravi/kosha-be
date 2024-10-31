package com.dreamx.kosha.repository.impl;

import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.repository.TransactionCustomRepository;
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
public class TransactionRepositoryImpl implements TransactionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insertTransactions(Long userId, List<Transaction> transactions) {
        String deleteQuery = "delete from transactions where user_id = :userId";
        Object obj = entityManager.createNativeQuery(deleteQuery)
                .setParameter("userId", userId)
                .executeUpdate();
        log.info("deleted rows txn {}: ", obj);

        String queryString = "INSERT INTO transactions (txn_date, amount, description, type, mode, fi_name, user_id) VALUES (:txnDate, :amount, :description, :type, :mode, :financialInstrument, :user)";
        int batchSize = 50; // Adjust the batch size as needed

        for (int i = 0; i < transactions.size(); i++) {
            Transaction txn = transactions.get(i);
            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter("txnDate", txn.getTxnDate());
            query.setParameter("amount", txn.getAmount());
            query.setParameter("description", txn.getDescription());
            query.setParameter("type", txn.getType() == null ? null : txn.getType().name());
            query.setParameter("mode", txn.getMode());
            query.setParameter("financialInstrument", txn.getFinancialInstrument().name());
            query.setParameter("user", userId);
            query.executeUpdate();

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    public List<Transaction> findByUserId(Long userId) {
        String sql = "SELECT txn FROM Transaction txn WHERE txn.user.id = :userId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
