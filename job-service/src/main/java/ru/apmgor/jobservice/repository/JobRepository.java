package ru.apmgor.jobservice.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.apmgor.jobservice.entity.Job;

import java.util.Set;


public interface JobRepository extends ReactiveMongoRepository<Job, String> {

    @Query("{'skills' :  { $in : ?0 }}")
    Flux<Job> findBySkills(final Set<String> skills);
}
