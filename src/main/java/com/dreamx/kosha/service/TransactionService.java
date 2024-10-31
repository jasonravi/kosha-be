package com.dreamx.kosha.service;

import com.dreamx.kosha.entity.Transaction;

import java.util.List;

/**
 * @author deepika_rajani
 */
public interface TransactionService {

    void insertTransaction(Long userId, List<Transaction> transactions);

    List<Transaction> findByUserId(Long userId);
}
