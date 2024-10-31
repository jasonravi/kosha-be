package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author deepika_rajani
 */
@Repository
public interface TransactionRepository extends JpaRepository<Consent, Long>, TransactionCustomRepository {
}
