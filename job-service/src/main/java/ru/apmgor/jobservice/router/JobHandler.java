package ru.apmgor.jobservice.router;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.apmgor.jobservice.dto.JobDto;
import ru.apmgor.jobservice.service.JobService;

import java.net.InetAddress;
import java.net.URI;
import java.time.Duration;
import java.util.HashSet;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public final class JobHandler {

    private final JobService service;

    public Mono<ServerResponse> getJobs(final ServerRequest request) {
        return Mono.justOrEmpty(request.queryParams().get("skills"))
                .map(HashSet::new)
                .map(service::jobsBySkills)
                .flatMap(this::response)
                .switchIfEmpty(response(service.allJobs()));
    }

    public Mono<ServerResponse> saveJob(final ServerRequest request) {
        return service.save(request.bodyToMono(JobDto.class))
                .map(id -> URI.create(request.path() + "/" + id))
                .flatMap(uri -> created(uri).build());
    }

    @SneakyThrows
    public Mono<ServerResponse> hostChangeLB(final ServerRequest request) {
        return ok()
                .bodyValue(InetAddress.getLocalHost().getHostName())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ServerResponse> response(final Publisher<JobDto> publisher) {
        return ok()
                .contentType(APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(publisher, JobDto.class);
    }
}
