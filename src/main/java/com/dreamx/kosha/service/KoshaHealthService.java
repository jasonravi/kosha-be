package com.dreamx.kosha.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class KoshaHealthService {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean isDatabaseUp() {
        try {
            // Perform a simple query to check connection
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return true;  // Connection is OK
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Connection failed
        }
    }
}
