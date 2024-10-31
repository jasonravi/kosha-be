package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.Transaction;

import java.util.List;

/**
 * @author deepika_rajani
 */
public interface TransactionCustomRepository {

    void insertTransactions(Long userId, List<Transaction> transactions);

    List<Transaction> findByUserId(Long userId);

}
