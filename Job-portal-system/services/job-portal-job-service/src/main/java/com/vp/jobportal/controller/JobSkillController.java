package com.vp.jobportal.controller;

import com.vp.job.dto.response.ApiResponse;
import com.vp.job.dto.response.JobSkillResponse;
import com.vp.jobportal.payload.JobSkillRequest;
import com.vp.jobportal.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-skills")
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createSkill(@RequestBody @Valid JobSkillRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobSkillService.createSkill(req));
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllSkills() {
        return ResponseEntity.ok(jobSkillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobSkillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateSkill(@PathVariable("id") Long id,
                                                        @RequestBody @Valid JobSkillRequest req) {
        return ResponseEntity.ok(jobSkillService.updateSkill(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable("id") Long id) {
        jobSkillService.deleteSkill(id);
        return ResponseEntity.ok(new ApiResponse("Skill deleted successfully", true));
    }
}
