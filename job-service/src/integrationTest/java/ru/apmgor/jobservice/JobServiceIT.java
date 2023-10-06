package ru.apmgor.jobservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.apmgor.jobservice.dto.JobDto;
import ru.apmgor.jobservice.generic.BaseTest;

import java.util.Set;

@SpringBootTest
@AutoConfigureWebTestClient
class JobServiceIT extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllJobsTest() {
        webTestClient.get()
                .uri("/jobs")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }

    @Test
    public void getJobsBySkillsTest() {
        webTestClient.get()
                .uri("/jobs?skills=java")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    public void saveJobTest() {
        var dto = JobDto.builder()
                .id("5")
                .description("k8s eng")
                .company("google")
                .skills(Set.of("k8s"))
                .salary(200_000)
                .isRemote(true)
                .build();
        webTestClient.post()
                .uri("/jobs")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader()
                .location("/jobs/5");
    }
}
