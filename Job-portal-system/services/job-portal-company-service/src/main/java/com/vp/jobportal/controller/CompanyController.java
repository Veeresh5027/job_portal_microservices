package com.vp.jobportal.controller;

import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.job.dto.request.CompanyRequest;
import com.vp.job.dto.response.ApiResponse;
import com.vp.job.dto.response.CompanyResponse;
import com.vp.jobportal.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader ("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.createCompany(ownerId, companyRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(@RequestHeader ("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus status
            ){
        return ResponseEntity.ok(companyService.getAllCompanies(companyType, industryType, status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable("id") Long id,
                                                         @RequestHeader ("X-User-Id") Long ownerId,
                                                         @RequestBody @Valid CompanyRequest req) {
        return ResponseEntity.ok(companyService.updateCompany(ownerId, id, req));
    }

    @PatchMapping("/verify/{id}")
    public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable("id") Long id,
                                                     @RequestHeader ("X-User-Id") Long ownerId) {
        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }

 

}
