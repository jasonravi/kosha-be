package com.dreamx.kosha.repository.impl;

import com.dreamx.kosha.entity.Consent;
import com.dreamx.kosha.repository.ConsentCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

public class ConsentCustomRepositoryImpl implements ConsentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Consent> getUserConsent(Long userId) {
        String sql = "SELECT id, userId, status FROM Consent c WHERE c.userId = :userId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int updateConsentStatus(Long id, int status) {
        String queryString = "UPDATE Consent c SET c.status = :status WHERE c.id = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        query.setParameter("status", status);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public void createConsent(Long userId, int status) {
        String queryString = "INSERT INTO consent (userId, status) VALUES (:userId, :status)";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("userId", userId);
        query.setParameter("status", status);
        query.executeUpdate();
    }
}
