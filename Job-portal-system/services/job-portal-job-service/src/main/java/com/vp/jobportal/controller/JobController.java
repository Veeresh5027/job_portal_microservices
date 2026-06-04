package com.vp.jobportal.controller;

import com.vp.job.dto.request.JobRequest;
import com.vp.job.dto.response.ApiResponse;
import com.vp.job.dto.response.JobResponse;
import com.vp.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader ("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(employerId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(
            @PathVariable("companyId") Long companyId
    ){
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsAdmin(){
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable("id") Long id,
            @RequestHeader ("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req
    ) throws RuntimeException {
        return ResponseEntity.ok(jobService.updateJob(id, employerId, req));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<JobResponse> publishJob(
            @PathVariable("id") Long id,
            @RequestHeader ("X-User-Id") Long employerId
    ) throws RuntimeException {
        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable("id") Long id,
            @RequestHeader ("X-User-Id") Long employerId
    ) throws RuntimeException {
        return ResponseEntity.ok(jobService.closeJob(id, employerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long employerId
    ) throws RuntimeException {
        jobService.deleteJob(id, employerId);
        return ResponseEntity.ok(new ApiResponse("Job deleted successfully", true));
    }

}
