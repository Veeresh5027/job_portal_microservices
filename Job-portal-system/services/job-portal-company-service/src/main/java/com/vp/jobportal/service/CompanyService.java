package com.vp.jobportal.service;

import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.job.dto.request.CompanyRequest;
import com.vp.job.dto.response.CompanyResponse;
import com.vp.jobportal.model.Company;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req);

    CompanyResponse getCompanyById(Long id);

    CompanyResponse getMyCompany(Long ownerId);

    List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType,
                                          CompanyStatus companyStatus);

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest companyRequest);

    CompanyResponse verifyCompany(Long companyId);

    void deleteCompany(Long companyId, Long ownerId);

    CompanyResponse deactivateCompany(Long companyId);

    Company getCompanyEntityById(Long id);

}
