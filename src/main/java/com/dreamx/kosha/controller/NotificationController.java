package com.dreamx.kosha.controller;


import com.dreamx.kosha.model.ConsentNotificationRequestDTO;
import com.dreamx.kosha.model.DataRequestNotificationDTO;
import com.dreamx.kosha.model.NotificationResponseDTO;
import com.dreamx.kosha.service.ConsentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping("")
@Slf4j
public class NotificationController {

    @Autowired
    private ConsentService consentService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/events/v2/Consent/Notification", method = RequestMethod.POST)
    public ResponseEntity<NotificationResponseDTO> consentNotification(@RequestBody ConsentNotificationRequestDTO payload) throws JsonProcessingException {
        log.info("Got consent notification request from sahmati {} ", objectMapper.writeValueAsString(payload));
        NotificationResponseDTO notificationResponseDTO = consentService.consentNotification(payload);
        log.info("Successfully send notification response to sahamti  {} ", objectMapper.writeValueAsString(notificationResponseDTO));
        return new ResponseEntity<>(notificationResponseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/events/v2/FI/Notification", method = RequestMethod.POST)
    public ResponseEntity<NotificationResponseDTO> dataNotification(@RequestBody DataRequestNotificationDTO payload) throws JsonProcessingException {
        log.info("Got data request notification request from sahmati {} ", objectMapper.writeValueAsString(payload));
        NotificationResponseDTO notificationResponseDTO = consentService.dataNotification(payload);
        log.info("Successfully send data request notification response to sahamti  {} ", objectMapper.writeValueAsString(notificationResponseDTO));
        return new ResponseEntity<>(notificationResponseDTO, HttpStatus.CREATED);
    }
}
