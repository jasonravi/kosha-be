package com.dreamx.kosha.entity;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_financial_instrument") // Specify the table name
public class UserFinancialInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private FinancialInstrument financialInstrument;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "meta")
    @Transient
    private String meta;

    // Many financial instruments belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    // Defines the foreign key column in the 'financial_instruments' table
    @JsonBackReference
    private User user;

    @Column(name = "date_range_from", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateRangeFrom;

    @Column(name = "date_range_to", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateRangeTo;

    @Column(name = "txn_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime transactionTime;

}
