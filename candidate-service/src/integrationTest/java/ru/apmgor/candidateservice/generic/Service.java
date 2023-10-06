package ru.apmgor.candidateservice.generic;

public record Service(
        String name,
        int port,
        String resourcePath,
        String containerPath,
        String uri
) {}
