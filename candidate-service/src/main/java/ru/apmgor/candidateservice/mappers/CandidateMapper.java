package ru.apmgor.candidateservice.mappers;

import lombok.val;
import org.springframework.beans.BeanUtils;
import ru.apmgor.candidateservice.dto.CandidateDetailsDto;
import ru.apmgor.candidateservice.dto.CandidateDto;
import ru.apmgor.candidateservice.entity.Candidate;

public final class CandidateMapper {
    private CandidateMapper() {}

    public static CandidateDto toDto(final Candidate candidate) {
        val dto = new CandidateDto();
        BeanUtils.copyProperties(candidate, dto);
        return dto;
    }

    public static CandidateDetailsDto toDetailsDto(final Candidate candidate) {
        val detailsDto = new CandidateDetailsDto();
        BeanUtils.copyProperties(candidate, detailsDto);
        return detailsDto;
    }

    public static Candidate toCandidate(final CandidateDto dto) {
        val candidate = new Candidate();
        BeanUtils.copyProperties(dto, candidate);
        return candidate;
    }
}
