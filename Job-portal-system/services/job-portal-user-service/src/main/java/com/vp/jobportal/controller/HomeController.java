package com.vp.jobportal.controller;

import com.vp.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "Home of AI Job Portal User Service --" + UserRole.ROLE_JOB_SEEKER;
    }
}
