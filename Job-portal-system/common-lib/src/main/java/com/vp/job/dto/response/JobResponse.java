package com.vp.job.dto.response;

import com.vp.job.domain.ExperienceLevel;
import com.vp.job.domain.JobStatus;
import com.vp.job.domain.JobType;
import com.vp.job.domain.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String responsibilities;
    private String benefits;

    private CompanyResponse company;
    private Long employerId;

//    private JobCategoryResponse category;
//    private Set<JobSkillResponse> skills;
//    private Set<JobTagResponse> tags;

    //location
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    //Salary
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private Boolean salaryNegotiable;
    private Boolean salaryDisclosed;

    //classification
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus status;

    //posting details
    private Integer openings;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
    private Boolean active;

    //Analytics
    private Long views;
    private Long applicationCount;

    //Dates
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;

}
