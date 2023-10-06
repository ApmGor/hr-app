package ru.apmgor.candidateservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.candidateservice.client.JobClient;
import ru.apmgor.candidateservice.dto.CandidateDetailsDto;
import ru.apmgor.candidateservice.dto.CandidateDto;
import ru.apmgor.candidateservice.entity.Candidate;
import ru.apmgor.candidateservice.mappers.CandidateMapper;
import ru.apmgor.candidateservice.repository.CandidateRepository;

@Service
@RequiredArgsConstructor
public final class CandidateService {

    private final CandidateRepository repository;
    private final JobClient client;

    public Flux<CandidateDto> allCandidates() {
        return repository.findAll()
                .map(CandidateMapper::toDto);
    }

    public Mono<CandidateDetailsDto> oneCandidateBy(final String id) {
        return repository.findById(id)
                .map(CandidateMapper::toDetailsDto)
                .flatMap(this::addRecommendedJobs)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new));
    }

    private Mono<CandidateDetailsDto> addRecommendedJobs(final CandidateDetailsDto dto) {
        return client.getRecommendedJobs(dto.getSkills())
                .doOnNext(dto::setRecommendedJobs)
                .thenReturn(dto);
    }

    public Mono<String> saveCandidate(final Mono<CandidateDto> dtoMono) {
        return dtoMono
                .map(CandidateMapper::toCandidate)
                .flatMap(repository::save)
                .map(Candidate::getId);
    }
}
