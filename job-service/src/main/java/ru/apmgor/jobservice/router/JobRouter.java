package ru.apmgor.jobservice.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class JobRouter {

    private final JobHandler handler;

    @Bean
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions.route()
                .path("/jobs", builder -> builder
                        .GET(handler::getJobs)
                        .POST(handler::saveJob)
                        .build())
                .build();
    }
}
