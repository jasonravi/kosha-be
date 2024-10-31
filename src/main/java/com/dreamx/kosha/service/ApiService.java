package com.dreamx.kosha.service;

import com.dreamx.kosha.configuration.AppProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ApiService {

    private final WebClient webClient;
    private final AppProperties appProperties;

    @Autowired
    private ObjectMapper objectMapper;

    public ApiService(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = webClientBuilder.baseUrl(appProperties.getSahamatiUrl()).build();
    }

    public  <T> String makePostRequest(T requestBody, String path, String apiName) throws JsonProcessingException {

        log.info("{} Request is {} ",apiName,  objectMapper.writeValueAsString(requestBody));
        Mono<String> response = this.webClient
                .post()
                .uri(path)
                .bodyValue(requestBody)
                .headers(httpHeaders -> {
                    httpHeaders.add("tenantCode", "kosha");
                })
                .retrieve()
                .bodyToMono(String.class);

        String result = response.block();
        log.info(" {} Response is {} ", apiName, objectMapper.writeValueAsString(result));
        return result;
    }
}
