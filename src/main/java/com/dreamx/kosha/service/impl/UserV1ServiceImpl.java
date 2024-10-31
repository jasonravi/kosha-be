package com.dreamx.kosha.service.impl;


import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.UserResponseDTO;
import com.dreamx.kosha.service.SahamatiService;
import com.dreamx.kosha.service.UserV1Service;
import com.dreamx.kosha.repository.UserV1Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserV1ServiceImpl implements UserV1Service {

    private UserV1Repository userV1Repository;

    private SahamatiService sahamatiService;

    @Override
    public List<User> getUser(Long userId) {
        return userV1Repository.getUser(userId);
    }

    @Override
    public ConsentResponseDTO createUser(User user) throws JsonProcessingException {
        userV1Repository.createUser(user);
        log.info("New user record created successfully.");
        return sahamatiService.startConsent(user.getMobileNumber());
    }

    @Override
    public ConsentResponseDTO saveUser(User user) throws JsonProcessingException {
        long userId = userV1Repository.saveUser(user);
        log.info("Successfully created user with userId {} ", userId);
        ConsentResponseDTO consentResponseDTO = sahamatiService.startConsent(user.getMobileNumber());
        consentResponseDTO.setUserId(userId); // todo return random user id
        return consentResponseDTO;
    }

    @Override
    public UserResponseDTO getOnboardingDetail(String mobileNumber) throws JsonProcessingException {
        Long userId = userV1Repository.getUserByMobileNumber(mobileNumber);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        if(userId != null) {
            userResponseDTO.setUserId(userId);
            userResponseDTO.setStatus(true);
        } else {
            userResponseDTO.setStatus(false);
        }
       return userResponseDTO;

    }

    @Override
    public int updateUserFinancialScores(User user) {
        return userV1Repository.updateUserFinancialScores(user);
    }
}
