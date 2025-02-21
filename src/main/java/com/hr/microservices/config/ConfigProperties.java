package com.hr.microservices.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ConfigProperties {
    private String externalApi;
}
