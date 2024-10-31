package com.dreamx.kosha.repository;

import com.dreamx.kosha.entity.Consent;

import java.util.List;

public interface ConsentCustomRepository {
    List<Consent> getUserConsent(Long userId);
    int updateConsentStatus(Long id, int status);
    void createConsent(Long userId, int status);
}
