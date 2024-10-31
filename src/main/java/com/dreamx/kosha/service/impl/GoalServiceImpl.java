package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.entity.UserGoalDTO;
import com.dreamx.kosha.model.DataRequestResponseDTO;
import com.dreamx.kosha.model.UserGoalResponseDTO;
import com.dreamx.kosha.repository.GoalRepository;
import com.dreamx.kosha.service.ConsentService;
import com.dreamx.kosha.service.FinancialDataService;
import com.dreamx.kosha.service.GoalService;
import com.dreamx.kosha.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class GoalServiceImpl implements GoalService {


    private GoalRepository goalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConsentService consentService;

    private FinancialDataService financialDataService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    @Override
    public List<UserGoalDTO> getGoal(Long userId) {
//        financialDataService.processUserFinancialData(userId);
        return goalRepository.getGoal(userId);
    }

    @Override
    public UserGoalResponseDTO saveGoal(Long userId, String handle, UserGoalDTO userGoalDTO) throws JsonProcessingException {
        User user = userService.getUserById(userId);
        List<UserGoalDTO> goals = getGoal(userId);
        UserGoalResponseDTO userGoalResponseDTO;
        try {

            if(!StringUtils.isEmpty(handle) || goals.isEmpty()) {
               goalRepository.saveGoal(userId, userGoalDTO);

                log.info("User consent flow started for userId {} and handle {} ", userId, handle);
                consentService.fetchConsent(handle);

                // consent notification will be received post this

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime sixMonthsAgo = now.minus(6, ChronoUnit.MONTHS);
                String from = sixMonthsAgo.atZone(ZoneOffset.UTC).format(formatter);
                String to = now.atZone(ZoneOffset.UTC).format(formatter);
                DataRequestResponseDTO dataRequestResponseDTO = consentService.getDataRequest(handle, user.getMobileNumber(), from, to);

                log.info("Session Id Of handle {} is {} ", dataRequestResponseDTO.getSession_id(), handle);
                consentService.fetchDataRequest(dataRequestResponseDTO.getSession_id());

                // fetch notification will be received post this

                // read data, update assets & calculate score
                financialDataService.processUserFinancialData(userId);
            } else {
                goalRepository.saveGoal(userId, userGoalDTO);
                log.info("Already compute data for user {} , consent {} ", userId, handle);
            }

            userGoalResponseDTO = new UserGoalResponseDTO();
            userGoalResponseDTO.setMessage("Successfully saved user goal");
            userGoalResponseDTO.setStatus(true);
        } catch (Exception e) {
            userGoalResponseDTO = new UserGoalResponseDTO();
            userGoalResponseDTO.setMessage("Failed to save user");
            userGoalResponseDTO.setStatus(false);
        }
        return userGoalResponseDTO;
    }


}
