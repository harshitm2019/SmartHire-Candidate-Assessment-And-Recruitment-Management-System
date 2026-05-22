package com.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.rate-limit")
public class RateLimitProperties {

    private Limit ip;
    private Limit user;

    @Getter
    @Setter
    public static class Limit {

        private long capacity;
        private long refillTokens;
        private long refillDuration;
    }


}
