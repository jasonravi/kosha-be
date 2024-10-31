package com.dreamx.kosha.service;

import com.dreamx.kosha.model.ConsentNotificationRequestDTO;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.DataFetchResponseDTO;
import com.dreamx.kosha.model.DataRequestNotificationDTO;
import com.dreamx.kosha.model.DataRequestResponseDTO;
import com.dreamx.kosha.model.NotificationResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConsentService {
    ConsentResponseDTO fetchConsent(String handle) throws JsonProcessingException;
    DataRequestResponseDTO getDataRequest(String handle, String mobileNumber, String from, String to) throws JsonProcessingException;
    DataFetchResponseDTO fetchDataRequest(String sessionId) throws JsonProcessingException;
    NotificationResponseDTO consentNotification(ConsentNotificationRequestDTO payload);
    NotificationResponseDTO dataNotification(DataRequestNotificationDTO payload);
}
