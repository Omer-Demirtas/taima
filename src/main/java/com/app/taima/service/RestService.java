package com.app.taima.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate;


    private static HttpHeaders createHeader () {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    public String get(String url) {
        try {
            HttpHeaders httpHeaders = createHeader();


            HttpEntity<?> httpEntity = new HttpEntity<Object>(httpHeaders);

            ResponseEntity<String> result = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );

            return result.getBody();
        } catch (Exception e) {
            log.error(e, e);
            throw new IllegalArgumentException("restApiRequest");
        }
    }
}
