package ru.apmgor.candidateservice.router;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static ru.apmgor.candidateservice.router.CandidateRouter.ID;

public class ExceptionHandler {
    private ExceptionHandler() {}

    public static Mono<ServerResponse> illegalArgHandler(
            final Throwable ex,
            final ServerRequest request
    ) {
        return ServerResponse
                .badRequest()
                .bodyValue("Incorrect id: " + request.pathVariable(ID));
    }
}
