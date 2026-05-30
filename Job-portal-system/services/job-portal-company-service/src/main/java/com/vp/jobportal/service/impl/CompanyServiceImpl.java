package com.vp.jobportal.service.impl;

import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.job.dto.request.CompanyRequest;
import com.vp.job.dto.response.CompanyResponse;
import com.vp.jobportal.model.Company;
import com.vp.jobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;


    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) {
        return null;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return null;
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        return null;
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus status) {
        return List.of();
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest companyRequest) {
        return null;
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        return null;
    }

    @Override
    public void deleteCompany(Long companyId) {

    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        return null;
    }

    @Override
    public Company getCompanyEntityById(Long id) {
        return null;
    }
}
