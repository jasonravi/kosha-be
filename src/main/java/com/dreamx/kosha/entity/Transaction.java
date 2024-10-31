package com.dreamx.kosha.entity;

/**
 * @author deepika_rajani
 */

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txn_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime txnDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "mode")
    private String mode;

    @Column(name = "fi_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private FinancialInstrument financialInstrument;

    // Many financial instruments belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    // Defines the foreign key column in the 'financial_instruments' table
    @JsonBackReference
    private User user;

    public Transaction(LocalDateTime txnDate, Double amount, FinancialInstrument financialInstrument) {
        this.txnDate = txnDate;
        this.amount = amount;
        this.financialInstrument = financialInstrument;
    }

    public Transaction(LocalDateTime txnDate, Double amount, String description, TransactionType type, String mode, FinancialInstrument financialInstrument) {
        this.txnDate = txnDate;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.mode = mode;
        this.financialInstrument = financialInstrument;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", txnDate=" + txnDate +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", mode='" + mode + '\'' +
                ", financialInstrument='" + financialInstrument + '\'' +
                '}';
    }
}
