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

    // POST
    public <T> String makePostRequest(T requestBody, String path, String apiName) throws JsonProcessingException {
        log.info("{} Request (POST) is {} ", apiName, objectMapper.writeValueAsString(requestBody));
        Mono<String> response = this.webClient
            .post()
            .uri(path)
            .bodyValue(requestBody)
            .headers(httpHeaders -> httpHeaders.add("tenantCode", "kosha"))
            .retrieve()
            .bodyToMono(String.class);

        String result = response.block();
        log.info("{} Response (POST) is {} ", apiName, result);
        return result;
    }

    // GET
    public String makeGetRequest(String path, String apiName) {
        log.info("{} Request (GET) on {}", apiName, path);
        Mono<String> response = this.webClient
            .get()
            .uri(path)
            .headers(httpHeaders -> httpHeaders.add("tenantCode", "kosha"))
            .retrieve()
            .bodyToMono(String.class);

        String result = response.block();
        log.info("{} Response (GET) is {} ", apiName, result);
        return result;
    }

    // PUT
    public <T> String makePutRequest(T requestBody, String path, String apiName) throws JsonProcessingException {
        log.info("{} Request (PUT) is {} ", apiName, objectMapper.writeValueAsString(requestBody));
        Mono<String> response = this.webClient
            .put()
            .uri(path)
            .bodyValue(requestBody)
            .headers(httpHeaders -> httpHeaders.add("tenantCode", "kosha"))
            .retrieve()
            .bodyToMono(String.class);

        String result = response.block();
        log.info("{} Response (PUT) is {} ", apiName, result);
        return result;
    }

    // PATCH
    public <T> String makePatchRequest(T requestBody, String path, String apiName) throws JsonProcessingException {
        log.info("{} Request (PATCH) is {} ", apiName, objectMapper.writeValueAsString(requestBody));
        Mono<String> response = this.webClient
            .patch()
            .uri(path)
            .bodyValue(requestBody)
            .headers(httpHeaders -> httpHeaders.add("tenantCode", "kosha"))
            .retrieve()
            .bodyToMono(String.class);

        String result = response.block();
        log.info("{} Response (PATCH) is {} ", apiName, result);
        return result;
    }

    // DELETE
    public String func(String path, String apiName) {
        if(path == "ravi") {
            return "ravi";
        } else  if(path == "ravi") {
            return "ravi";
        }
        else  if(path == "ravi") {
            return "ravi";
        }
        else  if(path == "ravi") {
            return "start";
        }
        else  if(path == "ravi") {
            return "kavi";
        }
        return "deepak";
    }
}
