package com.vp.jobportal.controller;

import com.vp.job.dto.request.JobTagRequest;
import com.vp.job.dto.response.ApiResponse;
import com.vp.job.dto.response.JobTagResponse;
import com.vp.jobportal.service.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-tags")
public class JobTagController {

    private final JobTagService jobTagService;

    @PostMapping
    public ResponseEntity<JobTagResponse> createJobTag(
            @RequestBody @Valid JobTagRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobTagService.createJobTag(req));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllTags() {
        return ResponseEntity.ok(jobTagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getTagById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobTagService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateJobTag(@PathVariable("id") Long id,
                                                      @RequestBody @Valid JobTagRequest req) {
        return ResponseEntity.ok(jobTagService.updateJobTag(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJobTag(@PathVariable("id") Long id) {
        jobTagService.deleteJobTag(id);
        return ResponseEntity.ok(new ApiResponse("Tag deleted successfully", true));
    }
}
