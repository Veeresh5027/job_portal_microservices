package com.vp.jobportal.service.impl;

import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.job.dto.request.CompanyRequest;
import com.vp.job.dto.response.CompanyResponse;
import com.vp.job.dto.response.SocialLinkResponse;
import com.vp.jobportal.mapper.CompanyMapper;
import com.vp.jobportal.model.Company;
import com.vp.jobportal.model.SocialLink;
import com.vp.jobportal.repository.CompanyRepository;
import com.vp.jobportal.service.CompanyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CodePointBuffer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;


    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest req) {
        if(companyRepository.existsByOwnerId(ownerId)) {
            throw new RuntimeException("Company already exists");
        }
        if(companyRepository.existsByName(req.getName())) {
            throw new RuntimeException("Company name already exists");
        }
        if(req.getRegistrationNumber() != null &&
            companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())){
            throw new RuntimeException("Company registration number already exists");
        }

        String slug = generateUniqueSlug(req.getName());

        Company company = Company.builder()
                .name(req.getName())
                .slug(slug)
                .tagline(req.getTagline())
                .description(req.getDescription())
                .logoUrl(req.getLogoUrl())
                .coverImageUrl(req.getCoverImageUrl())
                .website(req.getWebsite())
                .email(req.getEmail())
                .phone(req.getPhone())
                .foundedYear(req.getFoundedYear())
                .companySize(req.getCompanySize())
                .companyType(req.getCompanyType())
                .industryType(req.getIndustryType())
                .registrationNumber(req.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(req.getSocialLinks()))
                .build();

        // Save the entity to the MySQL database
        Company savedCompany = companyRepository.save(company);
        // Map and return the saved object as a response
        return CompanyMapper.toResponse(savedCompany);
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null || socialLinks.isEmpty()){
            return new ArrayList<>();
        }
        return socialLinks.stream().map(e -> SocialLink.builder()
                .platform(e.getPlatform())
                .url(e.getUrl())
                .build())
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug(@NotBlank(message = "Name is required") String name) {

        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().
                replaceAll("\\s+", "-");

        if(!companyRepository.existsBySlug(base)) {
            return base;
        }
        // If the base slug already exists, append a number to make it unique. ex: "my-company-1"
        int count = 1;
        while(companyRepository.existsBySlug(base + "-" + count)) {
            count++;
        }
        return base + "-" + count;

    }

    @Override
    public CompanyResponse getCompanyById(Long id) throws RuntimeException {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Company not found"));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws RuntimeException {
        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new RuntimeException("Company not found"));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType,
                                                 IndustryType industryType,
                                                 CompanyStatus companyStatus) {
        return companyRepository.findByFilters(
                companyType,
                industryType,
                companyStatus
        ).stream().map(CompanyMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest companyRequest) {
        // 1. Fetch the existing company entity
        Company company = getCompanyEntityById(companyId);

        // 2. Authorization Check: Ensure the person updating owns this company record
        if (!company.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: You do not own this company profile");
        }

        // 3. Validation: Unique Name Check (Skip if the name hasn't changed)
        if (!company.getName().equalsIgnoreCase(companyRequest.getName()) &&
                companyRepository.existsByName(companyRequest.getName())) {
            throw new RuntimeException("Company name already exists");
        }

        // 4. Validation: Unique Registration Check (Skip if it's null or hasn't changed)
        if (companyRequest.getRegistrationNumber() != null &&
                !companyRequest.getRegistrationNumber().equals(company.getRegistrationNumber())) {

            if (companyRepository.existsByRegistrationNumber(companyRequest.getRegistrationNumber())) {
                throw new RuntimeException("Company registration number already exists");
            }
        }

        // 5. If the name changed, regenerate a fresh unique slug for SEO routing
        if (!company.getName().equalsIgnoreCase(companyRequest.getName())) {
            String newSlug = generateUniqueSlug(companyRequest.getName());
            company.setSlug(newSlug);
        }

        // 6. Update all target fields from the Request DTO
        company.setName(companyRequest.getName());
        company.setTagline(companyRequest.getTagline()); // Fixed your bug here
        company.setDescription(companyRequest.getDescription());
        company.setLogoUrl(companyRequest.getLogoUrl());
        company.setCoverImageUrl(companyRequest.getCoverImageUrl());
        company.setWebsite(companyRequest.getWebsite());
        company.setEmail(companyRequest.getEmail());
        company.setPhone(companyRequest.getPhone());
        company.setFoundedYear(companyRequest.getFoundedYear());
        company.setCompanySize(companyRequest.getCompanySize());
        company.setCompanyType(companyRequest.getCompanyType());
        company.setIndustryType(companyRequest.getIndustryType());
        company.setRegistrationNumber(companyRequest.getRegistrationNumber());

        // 7. Map and update child relationships (Social Links)
        if (companyRequest.getSocialLinks() != null) {
            company.setSocialLinks(mapSocialLinks(companyRequest.getSocialLinks()));
        }

        // 8. Commit changes to the MySQL Database and return the freshly updated DTO response
        Company updatedCompany = companyRepository.save(company);
        return CompanyMapper.toResponse(updatedCompany);
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long companyId, Long ownerId) {

        Company company = getCompanyEntityById(companyId);
        assertOwner(company, ownerId);
        companyRepository.delete(company);

    }

    private void assertOwner(Company company, Long ownerId) {
        if (!company.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: You do not own this company profile");
        }
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.SUSPENDED);
        company.setActive(false);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Company not found"));
    }
}
