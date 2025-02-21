package com.hr.microservices.service;

import com.hr.microservices.config.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final ConfigProperties configProperties;

    public String getUserDetails(final String userId) {
        final String apiUrl = configProperties.getExternalApi() + "/users/" + userId;
        System.out.println("Calling External API: " + apiUrl);
        return restTemplate.getForObject(apiUrl, String.class);
    }

}
