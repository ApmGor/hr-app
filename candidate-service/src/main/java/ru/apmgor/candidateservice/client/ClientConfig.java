package ru.apmgor.candidateservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Value("${job.service.url}")
    private String baseUrl;

    @Bean
    public WebClient client() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
