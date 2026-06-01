package com.vp.jobportal.payload;

import com.vp.job.domain.ExperienceLevel;
import com.vp.job.domain.JobStatus;
import com.vp.job.domain.JobType;
import com.vp.job.domain.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSearchRequest {

    private String keyword;

    private Long categoryId;

    private List<Long> skillIds;

    private List<Long> tagIds;

    private Long companyId;

    //matches city, state, country
    private String location;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;

    private JobStatus status;

    private Integer minOpenings;
    private Integer maxOpenings;
}
