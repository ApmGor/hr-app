package ru.apmgor.candidateservice.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class CandidateRouter {

    public static final String ID = "id";

    private final CandidateHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/candidates", builder -> builder
                        .GET("/{" + ID + "}", handler::handleOne)
                        .GET(handler::handleAll)
                        .POST(handler::handleSave)
                        .onError(IllegalArgumentException.class, ExceptionHandler::illegalArgHandler))
                .build();
    }
}
