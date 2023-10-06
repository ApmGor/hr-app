package ru.apmgor.candidateservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.apmgor.candidateservice.repository.CandidateRepository;

@SpringBootTest
@EnableAutoConfiguration(exclude = MongoReactiveAutoConfiguration.class)
@MockBean({CandidateRepository.class})
class CandidateServiceTests {

    @Test
    void contextLoads() {
    }

}

