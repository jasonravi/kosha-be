package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author deepika_rajani
 */
@Repository
public interface FinancialInstrumentRepository extends JpaRepository<Consent, Long>, FinancialInstrumentCustomRepository {
}
