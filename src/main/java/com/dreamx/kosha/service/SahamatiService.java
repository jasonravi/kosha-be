package com.dreamx.kosha.service;

import com.dreamx.kosha.model.ConsentDTO;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.DataFetchDTO;
import com.dreamx.kosha.model.DataFetchResponseDTO;
import com.dreamx.kosha.model.DataRequestDTO;
import com.dreamx.kosha.model.FetchConsentDTO;
import com.dreamx.kosha.model.ConsentRequestDTO;
import com.dreamx.kosha.model.DataRequestResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SahamatiService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ObjectMapper objectMapper;


    public ConsentResponseDTO startConsent(String mobileNumber) throws JsonProcessingException {
        try {
            ConsentRequestDTO consentRequestDTO = new ConsentRequestDTO();
            consentRequestDTO.setMobileNumber(mobileNumber);
            String consentResponse = apiService.makePostRequest(consentRequestDTO, "proxy/v1/consent/request", "Consent Request ");
            return objectMapper.readValue(consentResponse, ConsentResponseDTO.class);
        } catch (Exception exception) {
            return getConsentResponseDTO();
        }
    }

    private ConsentResponseDTO getConsentResponseDTO() {
        ConsentResponseDTO consentResponseDTO = new ConsentResponseDTO();
        consentResponseDTO.setSuccess(true);
        consentResponseDTO.setRedirect_url("https://test.saafe.in/webview?ecreq=ftjpVZL6ZGUMD_ZhxTI7fE6VbNhLw6fJu1gDxbfU1Dc3y-AjeePVKx68BvYrPClTVLHL3qazVAIxhUTlcgdKP3fPmSD9rVy1Yl39vBMiOk6Reww8-aEHZC1-xhXvqe7pkwgfSiNCaFF7Do1W5bXDEPlUeJHB6rQvqHdm7Xx19ei8yHmoZjUbM3NH4A08E-EIdHT4yDTKIN3fbPr-Ku6BJcH2Brq6yFfjZPS_UODNdLxlwj5D_9FW6FL_mgosm4xKclpOfMSkx2gzJfNzEWsDKp0C0PvFswzdLM4GGjw0-ut2BBl70IkL9i-D4q5QFeoD&fi=WllCWFM%3D&reqdate=161020241118000");
        ConsentDTO consentDTO = new ConsentDTO();
        consentDTO.setHandle("56559b05-dec7-4e70-b230-04bd708a8aa4");
        consentDTO.setStatus("PENDING");
        consentResponseDTO.setConsents(List.of(consentDTO));
        return consentResponseDTO;
    }

    public ConsentResponseDTO getConsent(String handle) throws JsonProcessingException {
        FetchConsentDTO fetchConsentDTO = new FetchConsentDTO();
        fetchConsentDTO.setHandle(handle);
        String consentResponse = apiService.makePostRequest(fetchConsentDTO, "proxy/v1/consent/fetch", "Get Consent ");
        return objectMapper.readValue(consentResponse, ConsentResponseDTO.class);
    }

    public DataRequestResponseDTO requestData(DataRequestDTO dataRequestDTO) throws JsonProcessingException {
        String consentResponse = apiService.makePostRequest(dataRequestDTO, "proxy/v1/data/request", "Request Data ");
        return objectMapper.readValue(consentResponse, DataRequestResponseDTO.class);
    }

    public DataFetchResponseDTO fetchData(DataFetchDTO dataFetchDTO) throws JsonProcessingException {
        String consentResponse = apiService.makePostRequest(dataFetchDTO, "proxy/v1/data/fetch",  "Fetch Data ");
        return objectMapper.readValue(consentResponse, DataFetchResponseDTO.class);
    }
}
