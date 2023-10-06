package ru.apmgor.candidateservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CandidateDetailsDto extends CandidateDto {
    private List<JobDto> recommendedJobs;
}
