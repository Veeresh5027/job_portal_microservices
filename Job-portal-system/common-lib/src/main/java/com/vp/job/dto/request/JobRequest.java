package com.vp.job.dto.request;

import com.vp.job.domain.ExperienceLevel;
import com.vp.job.domain.JobType;
import com.vp.job.domain.WorkMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;


    private String requirements;
    private String responsibilities;
    private String benefits;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    //ids from job_skill table
    private Set<Long> skillIds;

    //ids from job_tag table
    private Set<Long> tagIds;

    //locations - flattened for simple API surface
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    //salary - flattened for simple API surface

    @DecimalMin(value = "0.0", inclusive = true, message = "Salary must be greater than or equal to 0")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", inclusive = true, message = "Salary must be greater than or equal to 0")
    private BigDecimal maxSalary;

    //classification
    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    //posting details
    @Min(value = 1, message = "Openings must be greater than or equal to 1")
    @Builder.Default
    private Integer openings = 1;

    private LocalDate applicationDeadline;
    private LocalDate expiresAt;

}
