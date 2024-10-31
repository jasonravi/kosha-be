package com.dreamx.kosha.model;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author deepika_rajani
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFITxnDTO {

    private FinancialInstrument name;
    private double value;
    private LocalDateTime txnTime;
    private String meta;

    private List<Transaction> transactions;

    public UserFITxnDTO(FinancialInstrument name, double value, LocalDateTime txnTime, List<Transaction> transactions) {
        this.name = name;
        this.value = value;
        this.txnTime = txnTime;
        this.transactions = transactions;
    }

}
