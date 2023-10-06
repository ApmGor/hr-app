package ru.apmgor.candidateservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.apmgor.candidateservice.dto.JobDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public final class JobClient {

    private final WebClient client;

    public Mono<List<JobDto>> getRecommendedJobs(final Set<String> skills) {
        return client.get()
                .uri(uri -> uri
                        .path("/jobs")
                        .queryParam("skills", skills)
                        .build())
                .retrieve()
                .bodyToFlux(JobDto.class)
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }
}
