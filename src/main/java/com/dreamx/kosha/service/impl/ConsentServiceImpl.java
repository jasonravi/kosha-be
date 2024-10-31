package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.model.ConsentNotificationRequestDTO;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.DataFetchDTO;
import com.dreamx.kosha.model.DataFetchResponseDTO;
import com.dreamx.kosha.model.DataRequestDTO;
import com.dreamx.kosha.model.DataRequestNotificationDTO;
import com.dreamx.kosha.model.DataRequestResponseDTO;
import com.dreamx.kosha.model.NotificationResponseDTO;
import com.dreamx.kosha.service.ConsentService;
import com.dreamx.kosha.service.SahamatiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class ConsentServiceImpl implements ConsentService {


    @Autowired
    private SahamatiService sahamatiService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public ConsentResponseDTO fetchConsent(String  handle) throws JsonProcessingException {
        try {
            return sahamatiService.getConsent(handle);
        } catch (Exception e) {
            log.info("Fetch consent request has failed {} ", handle);
            return new ConsentResponseDTO();
        }
    }

    @Override
    public DataRequestResponseDTO getDataRequest(String handle, String mobileNumber, String from, String to) throws JsonProcessingException {
        try {
            DataRequestDTO dataRequestDTO = new DataRequestDTO();
            dataRequestDTO.setFrom(from);
            dataRequestDTO.setTo(to);
            dataRequestDTO.setConsent_handle(handle);
            dataRequestDTO.setCurve("X25519");
            dataRequestDTO.setProxy(true);
            dataRequestDTO.setMobileNumber(mobileNumber);
            return sahamatiService.requestData(dataRequestDTO);
        } catch (Exception e) {
            log.info("Data request has failed for handle {} {} ", handle, e);
            DataRequestResponseDTO dataRequestResponseDTO = new DataRequestResponseDTO();
            return dataRequestResponseDTO;
        }

    }

    @Override
    public DataFetchResponseDTO fetchDataRequest(String sessionId) throws JsonProcessingException {
        try {
            DataFetchDTO dataFetchDTO = new DataFetchDTO();
            dataFetchDTO.setSession_id(sessionId);
            return sahamatiService.fetchData(dataFetchDTO);
        } catch (Exception e) {
            log.info("Data fetch request has failed for handle {} ", sessionId);
            DataFetchResponseDTO dataFetchResponseDTO = new DataFetchResponseDTO();
            return dataFetchResponseDTO;
        }
    }

    @Override
    public NotificationResponseDTO consentNotification(ConsentNotificationRequestDTO payload) {
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
        notificationResponseDTO.setVer("2.0.0");
        notificationResponseDTO.setResponse("OK");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.atZone(ZoneOffset.UTC).format(formatter);
        notificationResponseDTO.setTimestamp(timestamp);
        notificationResponseDTO.setTxnid(payload.getTxnid());
        return notificationResponseDTO;
    }


    @Override
    public NotificationResponseDTO dataNotification(DataRequestNotificationDTO payload) {
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
        notificationResponseDTO.setVer("2.0.0");
        notificationResponseDTO.setResponse("OK");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.atZone(ZoneOffset.UTC).format(formatter);
        notificationResponseDTO.setTimestamp(timestamp);
        notificationResponseDTO.setTxnid(payload.getTxnid());
        return notificationResponseDTO;
    }

}
