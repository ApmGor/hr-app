package ru.apmgor.candidateservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.apmgor.candidateservice.dto.CandidateDto;
import ru.apmgor.candidateservice.generic.BaseTest;

import java.util.Set;

@SpringBootTest
@AutoConfigureWebTestClient
class CandidateServiceIT extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllCandidatesTest() {
        webTestClient.get()
                .uri("/candidates")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }

    @Test
    void getOneCandidateTest() {
        webTestClient.get()
                .uri("/candidates/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("John Mayer")
                .jsonPath("$.skills.size()").isEqualTo(2)
                .jsonPath("$.recommendedJobs.size()").isEqualTo(2);
    }

    @Test
    void getOneCandidateErrorTest() {
        webTestClient.get()
                .uri("/candidates/10")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void saveCandidateTest() {
        var dto = CandidateDto.builder()
                .id("5")
                .name("Ridley Scott")
                .skills(Set.of("producer", "director"))
                .build();
        webTestClient.post()
                .uri("/candidates")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader()
                .location("/candidates/5");
    }

    @Test
    void jobServiceReturns4xxTest() {
        webTestClient.get()
                .uri("/candidates/2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Oliver Blane")
                .jsonPath("$.skills.size()").isEqualTo(1)
                .jsonPath("$.recommendedJobs").isEmpty();
    }

    @Test
    void jobServiceReturns5xxTest() {
        webTestClient.get()
                .uri("/candidates/3")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Diego Garcia")
                .jsonPath("$.skills.size()").isEqualTo(2)
                .jsonPath("$.recommendedJobs").isEmpty();
    }
}

