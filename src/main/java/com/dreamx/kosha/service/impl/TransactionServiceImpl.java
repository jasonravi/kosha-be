package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.repository.TransactionRepository;
import com.dreamx.kosha.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author deepika_rajani
 */
@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Override
    public void insertTransaction(Long userId, List<Transaction> transactions) {
        transactionRepository.insertTransactions(userId, transactions);
    }

    @Override
    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
