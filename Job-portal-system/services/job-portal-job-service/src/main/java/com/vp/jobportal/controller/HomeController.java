package com.vp.jobportal.controller;

import com.vp.job.domain.UserRole;
import com.vp.job.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse> home() {
        ApiResponse response = new ApiResponse("Welcome to Job Portal Job Service" + UserRole.ROLE_EMPLOYER, true);
        return ResponseEntity.ok(response);
    }
}