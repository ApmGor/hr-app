package ru.apmgor.candidateservice.router;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.apmgor.candidateservice.dto.CandidateDto;
import ru.apmgor.candidateservice.service.CandidateService;

import java.net.InetAddress;
import java.net.URI;
import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static ru.apmgor.candidateservice.router.CandidateRouter.ID;

@Component
@RequiredArgsConstructor
public final class CandidateHandler {

    private final CandidateService service;

    public Mono<ServerResponse> handleAll(final ServerRequest request) {
        return response(service.allCandidates());
    }

    public Mono<ServerResponse> handleOne(final ServerRequest request) {
        return Mono.fromSupplier(() -> request.pathVariable(ID))
                .flatMap(service::oneCandidateBy)
                .onErrorResume(Mono::error)
                .flatMap(dto -> response(Mono.just(dto)));
    }

    public Mono<ServerResponse> handleSave(final ServerRequest request) {
        return service.saveCandidate(request.bodyToMono(CandidateDto.class))
                .map(id -> URI.create(request.path() + "/" + id))
                .flatMap(uri -> created(uri).build());
    }

    @SneakyThrows
    public Mono<ServerResponse> hostChangeLB(final ServerRequest request) {
        return ok()
                .bodyValue(InetAddress.getLocalHost().getHostName())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ServerResponse> response(final Publisher<CandidateDto> publisher) {
        return ok()
                .contentType(APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(publisher, CandidateDto.class);
    }
}
