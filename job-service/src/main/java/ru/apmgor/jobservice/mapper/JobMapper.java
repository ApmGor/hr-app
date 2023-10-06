package ru.apmgor.jobservice.mapper;

import lombok.val;
import org.springframework.beans.BeanUtils;
import ru.apmgor.jobservice.dto.JobDto;
import ru.apmgor.jobservice.entity.Job;

public class JobMapper {
    private JobMapper() {}

    public static JobDto toDto(final Job job) {
        val dto = new JobDto();
        BeanUtils.copyProperties(job, dto);
        return dto;
    }

    public static Job toEntity(final JobDto dto) {
        val job = new Job();
        BeanUtils.copyProperties(dto, job);
        return job;
    }
}
