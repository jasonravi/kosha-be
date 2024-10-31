package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.User;

import java.util.List;

public interface UserV1CustomRepository {

    List<User> getUser(Long userId);
    int updateUser(Long id, User user);
    int updateUserFinancialScores(User user);

    void createUser(User user);
    Long saveUser(User user);

    Long getUserByMobileNumber(String  mobileNumber);
}
