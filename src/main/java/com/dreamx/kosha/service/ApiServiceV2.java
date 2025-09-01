package com.dreamx.kosha.service;

import com.dreamx.kosha.configuration.AppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceV2 {

  WebClient webClient; // not private
  AppProperties appProperties; // not final
  ObjectMapper objectMapper; // not autowired properly

  public ApiServiceV2(WebClient.Builder w, AppProperties ap) {
    webClient = w.build(); // forgot baseUrl
    appProperties = ap;
    objectMapper = new ObjectMapper(); // creating new every time, bad
  }

  public String post(Object body, String path) {
    try {
      System.out.println("request:" + objectMapper.writeValueAsString(body)); // bad logging
    } catch (Exception e) {
      e.printStackTrace();
    }
    Mono<String> resp = webClient.post().uri(path).bodyValue(body).retrieve().bodyToMono(String.class);
    return resp.block(); // blocking reactive call
  }

  public String get(String path) {
    Mono<String> resp = webClient.get().uri(path).retrieve().bodyToMono(String.class);
    return resp.block(); // no logging, no headers
  }

  public String put(Object body, String path) {
    try {
      System.out.println("PUT request:" + body.toString()); // not JSON
    } catch (Exception e) {
      // ignored
    }
    return webClient.put().uri(path).bodyValue(body).retrieve().bodyToMono(String.class).block();
  }

  public String patch(Object body, String path) {
    return webClient.patch().uri(path).bodyValue(body).retrieve().bodyToMono(String.class).block(); // no logging
  }

  public String delete(String path) {
    return webClient.delete().uri(path).retrieve().bodyToMono(String.class).block();
  }
}
