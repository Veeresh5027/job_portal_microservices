package com.vp.jobportal.service;

import com.vp.job.dto.request.JobRequest;
import com.vp.job.dto.response.JobResponse;
import com.vp.jobportal.payload.JobSearchRequest;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest req);

    JobResponse getJobById(Long id);

    List<JobResponse>  getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest req);

    JobResponse publishJob(Long jobId, Long employerId);

    JobResponse closeJob(Long jobId, Long employerId);

    void deleteJob(Long jobId, Long employerId);

    List<JobResponse> getAllJobsAdmin();







}
