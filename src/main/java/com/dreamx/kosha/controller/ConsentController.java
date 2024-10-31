package com.dreamx.kosha.controller;


import com.dreamx.kosha.exception.ConsentException;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.DataFetchResponseDTO;
import com.dreamx.kosha.model.DataRequestResponseDTO;
import com.dreamx.kosha.service.ConsentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping("v1/kosha/user/consent")
public class ConsentController {

    private ConsentService consentService;


    @GetMapping
    public ResponseEntity<ConsentResponseDTO> fetchConsent(@RequestParam String handle) throws JsonProcessingException {
        ConsentResponseDTO consents = consentService.fetchConsent(handle);
        return new ResponseEntity<>(consents, HttpStatus.OK);
    }


    @RequestMapping(value = "/get/data", method = RequestMethod.POST)
    public ResponseEntity<DataRequestResponseDTO> requestData(@RequestBody String  handle) throws JsonProcessingException {
        DataRequestResponseDTO dataRequestResponseDTO = consentService.getDataRequest("987b7475-ba1c-4fba-96c9-a616593fee90", "9876994433", "", "");
        return new ResponseEntity<>(dataRequestResponseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/fetch/data", method = RequestMethod.POST)
    public ResponseEntity<DataFetchResponseDTO> fetchData(@RequestBody String handle ) throws JsonProcessingException {
        DataFetchResponseDTO dataFetchResponseDTO = consentService.fetchDataRequest(handle);
        return new ResponseEntity<>(dataFetchResponseDTO, HttpStatus.CREATED);
    }

    @ExceptionHandler(ConsentException.class)
    public ResponseEntity<String> handleConsentNotFound(ConsentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
