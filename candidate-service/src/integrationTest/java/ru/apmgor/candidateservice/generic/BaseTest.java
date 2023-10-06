package ru.apmgor.candidateservice.generic;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class BaseTest {

    private static final Service MONGO = new Service(
            "mongo",
            27017,
            "data/candidate-init.js",
            "/docker-entrypoint-initdb.d/candidate-init.js",
            "mongodb://candidate_user:candidate_password@%s:%s/candidate"
    );

    private static final Service JOB_MOCK = new Service(
            "mockserver/mockserver",
            1080,
            "data/job-init.json",
            "/config/init.json",
            "http://%s:%s"
    );

    @Container
    public static GenericContainer<?> mongo = new GenericContainer<>(DockerImageName.parse(MONGO.name()))
            .withExposedPorts(MONGO.port())
            .withClasspathResourceMapping(MONGO.resourcePath(), MONGO.containerPath(), BindMode.READ_ONLY)
            .waitingFor(Wait.forListeningPort());

    @Container
    public static GenericContainer<?> jobMock = new GenericContainer<>(DockerImageName.parse(JOB_MOCK.name()))
            .withExposedPorts(JOB_MOCK.port())
            .withEnv("MOCKSERVER_INITIALIZATION_JSON_PATH", "/config/init.json")
            .withClasspathResourceMapping(JOB_MOCK.resourcePath(), JOB_MOCK.containerPath(), BindMode.READ_ONLY)
            .waitingFor(Wait.forHttp("/health").forStatusCode(200));

    @DynamicPropertySource
    public static void mongoProperties(final DynamicPropertyRegistry registry) {
        var mongoUri = String.format(MONGO.uri(), mongo.getHost(), mongo.getMappedPort(MONGO.port()));
        var jobUri = String.format(JOB_MOCK.uri(), jobMock.getHost(), jobMock.getMappedPort(JOB_MOCK.port()));
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
        registry.add("job.service.url", () -> jobUri);
    }
}
