package com.dreamx.kosha.repository.impl;

import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.repository.UserV1CustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UserV1CustomRepositoryImpl implements UserV1CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUser(Long userId) {
        String sql = "SELECT id, firstName, lastName, gender, maritalStatus, age, numberOfDependents, mobileNumber, employmentType, status FROM User c WHERE c.id = :userId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int updateUser(Long id, User user) {
        String queryString = "UPDATE Consent c SET c.status = :status WHERE c.id = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int updateUserFinancialScores(User user) {
        String queryString = "UPDATE User u SET u.financialProfile = :financialProfile, "
                + "u.financialHealthScore = :financialHealthScore, "
                + "u.demographicHealthScore = :demographicHealthScore WHERE u.id = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("financialProfile", user.getFinancialProfile());
        query.setParameter("financialHealthScore", user.getFinancialHealthScore());
        query.setParameter("demographicHealthScore", user.getDemographicHealthScore());
        query.setParameter("id", user.getId());
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        String queryString = "INSERT INTO users (first_name, last_name, gender, marital_status, age, number_of_dependents, mobile_number, employment_type, status) VALUES (:firstName, :lastName, :gender, :maritalStatus, :age, :numberOfDependents, :mobileNumber, :employmentType, :status)";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());

        query.setParameter("gender", user.getGender().toString());
        query.setParameter("maritalStatus", user.getMaritalStatus().toString());

        query.setParameter("age", user.getAge());
        query.setParameter("numberOfDependents", user.getNumberOfDependents());

        query.setParameter("mobileNumber", user.getMobileNumber());
        query.setParameter("employmentType", user.getEmploymentType());

        query.setParameter("status", user.getStatus());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public Long saveUser(User user) {
        // Persist the user entity to insert it into the database
        try {
            entityManager.persist(user);
            entityManager.flush();  // Explicitly flush to catch any exceptions here
            return user.getId();
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                // Clear the session to avoid flush issues
                entityManager.clear();
                return getUserByMobileNumber(user.getMobileNumber());
            } else {
                // Clear session to avoid any unwanted flush issues
                entityManager.clear();
                throw e;  // Rethrow to handle it properly or return null
            }
        }
    }

    @Override
    public  Long getUserByMobileNumber(String  mobileNumber) {
        String sql = "SELECT id FROM User c WHERE c.mobileNumber = :mobileNumber";
        Query query = entityManager.createQuery(sql);
        query.setParameter("mobileNumber", mobileNumber);
        if(CollectionUtils.isEmpty(query.getResultList())) {
            return null;
        }
        return (Long) query.getResultList().get(0);
    }
}
