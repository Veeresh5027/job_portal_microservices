package com.vp.jobportal.mapper;

import com.vp.job.dto.response.CompanyResponse;
import com.vp.job.dto.response.JobResponse;
import com.vp.job.dto.response.JobSkillResponse;
import com.vp.job.dto.response.JobTagResponse;
import com.vp.jobportal.model.Job;
import com.vp.jobportal.model.JobLocation;
import com.vp.jobportal.model.SalaryRange;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {

    public static JobResponse toResponse(Job job, CompanyResponse companyResponse){

        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange();
        Set<JobSkillResponse> skills = job.getSkills() == null?
                Collections.emptySet()
                : job.getSkills().stream().map(JobSkillMapper :: toJobSkillResponse)
                .collect(Collectors.toSet());
        Set<JobTagResponse> tags = job.getTags() == null?
                Collections.emptySet()
                : job.getTags().stream().map(JobTagMapper :: jobTagResponse)
                .collect(Collectors.toSet());

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .company(companyResponse)
                .category(JobCategoryMapper.toJobCategoryResponse(job.getJobCategory(), false))
                .skills(skills)
                .tags(tags)

                //location
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .state(loc != null ? loc.getState() : null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)

                //salary
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)

                //classification
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())

                //postings
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.isActive())

                //timestamps
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();

    }

}
