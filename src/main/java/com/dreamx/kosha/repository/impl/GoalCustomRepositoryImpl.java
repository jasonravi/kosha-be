package com.dreamx.kosha.repository.impl;

import com.dreamx.kosha.entity.UserGoalDTO;
import com.dreamx.kosha.repository.GoalCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class GoalCustomRepositoryImpl implements GoalCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String IMAGE_BASE_URL = "https://buildathon-kosha-web-client.s3.ap-south-1.amazonaws.com/goal/";

    private String getUrl(String goal) {
        List<String> goals = Arrays.asList(goal.split(" "));
        goals = goals.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        if(goals.contains("renovation") || goals.contains("home renovation") || goals.contains("redecoration") || goals.contains("reconstruction") || goals.contains("refurbishment")) {
            return IMAGE_BASE_URL + "kosh_home-renovation_logo.png";
        }
        if(goals.contains("house") || goals.contains("makan") || goals.contains("home") || goals.contains("ghar") || goals.contains("flat") || goals.contains("villa")) {
            return IMAGE_BASE_URL + "kosh_house_logo.png";
        }
        if(goals.contains("car") || goals.contains("bus") || goals.contains("van") || goals.contains("bmw") || goals.contains("fourwheeler") || goals.contains("four wheeler")) {
            return IMAGE_BASE_URL + "kosh_car_logo.png";
        }
        if(goals.contains("gadget") || goals.contains("accessory")) {
            return IMAGE_BASE_URL + "kosh_gadget_logo.png";
        }

        if(goals.contains("vacation") || goals.contains("travel") || goals.contains("holiday") || goals.contains("cinema") || goals.contains("honeymoon") || goals.contains("trip") || goals.contains("tour")) {
            return IMAGE_BASE_URL + "kosh_vacation_logo.png";
        }

        if(goals.contains("wedding") || goals.contains("shadi") || goals.contains("engagement")) {
            return IMAGE_BASE_URL + "kosh_wedding_logo.png";
        }
        if(goals.contains("business") || goals.contains("dhandha") || goals.contains("vyapar")) {
            return IMAGE_BASE_URL + "kosh_start-business_logo.png";
        }
        return IMAGE_BASE_URL + "kosh_default_logo.png";
    }


    @Override
    public List<UserGoalDTO> getGoal(Long userId) {

        String jpql = "SELECT new com.dreamx.kosha.entity.UserGoalDTO(c.id, c.name, c.targetAmount, c.achiveAmount, c.duration, c.logo, c.priority, c.description) " +
                "FROM UserGoalDTO c WHERE c.userId = :userId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("userId", userId);
        return query.getResultList();


        /*String sql = "SELECT id, name, targetAmount, achiveAmount, duration, logo, priority, description FROM UserGoalDTO c WHERE c.userId = :userId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("userId", userId);
        return query.getResultList();*/
    }


    @Override
    @Transactional
    public void saveGoal(Long id, UserGoalDTO userGoalDTO) {

        String queryString = "INSERT INTO goal (user_id, name, target_amount, achive_amount, duration, logo, priority, description) VALUES (:userId, :name, :targetAmount, :achiveAmount, :duration, :logo, :priority, :description)";
        log.info("Save goal query string for userID {} is {} ", id, queryString);
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("userId", id);
        query.setParameter("name", userGoalDTO.getName());
        query.setParameter("targetAmount", userGoalDTO.getTargetAmount());

        query.setParameter("achiveAmount", userGoalDTO.getAchiveAmount());
        query.setParameter("duration", userGoalDTO.getDuration());

        query.setParameter("logo", getUrl(userGoalDTO.getName()));
        query.setParameter("priority",userGoalDTO.getPriority().name());

        query.setParameter("description", userGoalDTO.getDescription());
        log.info("Executing query to save goal for userId {} ", id);
        query.executeUpdate();
        log.info("User goal creation finished for userID {} ", id);
    }
}
