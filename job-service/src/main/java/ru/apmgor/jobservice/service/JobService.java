package ru.apmgor.jobservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.jobservice.dto.JobDto;
import ru.apmgor.jobservice.entity.Job;
import ru.apmgor.jobservice.repository.JobRepository;
import ru.apmgor.jobservice.mapper.JobMapper;

import java.util.Set;

@Service
@RequiredArgsConstructor
public final class JobService {

    private final JobRepository repository;

    public Flux<JobDto> allJobs() {
        return repository.findAll()
                .map(JobMapper::toDto);
    }

    public Flux<JobDto> jobsBySkills(final Set<String> skills) {
        return repository.findBySkills(skills)
                .map(JobMapper::toDto);
    }

    public Mono<String> save(final Mono<JobDto> mono) {
        return mono
                .map(JobMapper::toEntity)
                .flatMap(repository::save)
                .map(Job::getId);
    }

}
