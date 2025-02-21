package com.hr.microservices;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.hr.microservices.config.ConfigProperties;
import com.hr.microservices.service.ExternalApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExternalApiTest {

    @Mock
    private ConfigProperties configProperties;

    /**
     * Don't mock external Api service as this will be initialized right before the test case execution.
     * This is done at a later point of time as we need to set/mock WireMock's base url in configuration properties (ConfigProperties)
     */
    private ExternalApiService externalApiService;

    /**
     * For RestTemplate ->>
     *
     * ✅ Use @Mock when you want to isolate the test from actual API calls. [Unit Testing]
     * ✅ Use @Autowired when you want to interact with WireMock and test real HTTP requests. [Integration Testing]
     *
     */
    @Autowired
    private RestTemplate restTemplate;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // ✅ Mock ConfigProperties to return WireMock's Base URL
        when(configProperties.getExternalApi()).thenReturn(wireMockExtension.baseUrl());
        externalApiService = new ExternalApiService(restTemplate, configProperties);
    }

    @Test
    public void testExternalApi() {
        final String userId = "himanshu001";
        // Define the mock api behavior
        wireMockExtension.stubFor(get(urlEqualTo("/users/"+userId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": \"123\", \"name\": \"John Doe\"}")
                        .withStatus(200))
            );

        // Check if the stub (behaviour) is properly registered
        List<StubMapping> mappings = wireMockExtension.getStubMappings();
        System.out.println("Registered Stubs: " + mappings);

        final String response = externalApiService.getUserDetails(userId);

        // Check if wiremock received the request -- no output if request is received else exception
        wireMockExtension.verify(getRequestedFor(urlEqualTo("/users/" + userId)));
        Assertions.assertEquals(response, "{\"id\": \"123\", \"name\": \"John Doe\"}");
    }
}
