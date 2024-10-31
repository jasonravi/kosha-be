package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long>, ConsentCustomRepository {
}
