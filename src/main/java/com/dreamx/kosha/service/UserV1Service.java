package com.dreamx.kosha.service;

import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.UserResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserV1Service {

    List<User> getUser(Long userId);
    ConsentResponseDTO createUser(User user) throws JsonProcessingException;
    ConsentResponseDTO saveUser(User user) throws JsonProcessingException;
    UserResponseDTO getOnboardingDetail(String mobileNumber) throws JsonProcessingException;

    int updateUserFinancialScores(User user);
}
