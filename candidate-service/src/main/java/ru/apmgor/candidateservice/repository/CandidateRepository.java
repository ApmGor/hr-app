package ru.apmgor.candidateservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.apmgor.candidateservice.entity.Candidate;

public interface CandidateRepository extends ReactiveMongoRepository<Candidate, String> {}
