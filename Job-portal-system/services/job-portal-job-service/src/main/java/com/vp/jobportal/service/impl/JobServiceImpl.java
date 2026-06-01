package com.vp.jobportal.service.impl;


import com.vp.job.dto.request.JobRequest;
import com.vp.job.dto.response.CompanyResponse;
import com.vp.job.dto.response.JobResponse;
import com.vp.jobportal.mapper.JobMapper;
import com.vp.jobportal.model.Job;
import com.vp.jobportal.model.JobLocation;
import com.vp.jobportal.model.SalaryRange;
import com.vp.jobportal.payload.JobSearchRequest;
import com.vp.jobportal.repository.JobRepository;
import com.vp.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse createJob(Long employerId, JobRequest req) {

        Long companyId = 1L;

        Job job = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .companyId(companyId)
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null ? req.getOpenings() : 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .build();

        Job savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }

    private JobResponse convertToResponse(Job savedJob) {
        //TODO : fetch company response
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(savedJob.getCompanyId())
                .build();

        return JobMapper.toResponse(savedJob, companyResponse);
    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();
    }

    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .build();
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return convertToResponse(job);
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        return List.of();
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        return List.of();
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) {
        return null;
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) {
        return null;
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) {
        return null;
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) {

    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return List.of();
    }
}
