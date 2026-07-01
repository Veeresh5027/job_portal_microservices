package com.vp.jobportal.model;

import com.vp.job.domain.ExperienceLevel;
import com.vp.job.domain.JobStatus;
import com.vp.job.domain.JobType;
import com.vp.job.domain.WorkMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "requirements", nullable = false)
    private String requirements;

    private String responsibilities;

    private String benefits;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "employer_id", nullable = false)
    private Long employerId;

    @ManyToOne
    private JobCategory jobCategory;

    @ManyToMany
    private Set<JobSkill> skills;

    @ManyToMany
    private Set<JobTag> tags;

    @Embedded
    private JobLocation location;

    private SalaryRange salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.DRAFT;

    private Integer openings = 1;

    private LocalDate applicationDeadline;

    private LocalDate expiresAt;

    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime publishedAt;

    private LocalDateTime closedAt;
}
