package com.vp.jobportal.mapper;

import com.vp.job.dto.response.CompanyResponse;
import com.vp.job.dto.response.SocialLinkResponse;
import com.vp.jobportal.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    /**
     * Maps a single Company Entity to a CompanyResponse DTO using the Builder pattern
     */
    public static CompanyResponse toResponse(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getStatus())
                .active(company.getActive())     // Matches your Boolean 'active' field
                .ownerId(company.getOwnerId())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                // Safely streams and maps child social links if present
                .socialLinks(company.getSocialLinks() != null ?
                        company.getSocialLinks().stream()
                                .map(link -> SocialLinkResponse.builder()
                                        .platform(link.getPlatform())
                                        .url(link.getUrl())
                                        .build())
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                // Add locations mapping here if your Company model contains a location relationship
                .build();
    }

    /**
     * Maps a List of Company Entities to a List of CompanyResponse DTOs
     */
    public static List<CompanyResponse> toResponseList(List<Company> companies) {
        if (companies == null || companies.isEmpty()) {
            return new ArrayList<>();
        }
        return companies.stream()
                .map(CompanyMapper::toResponse)
                .collect(Collectors.toList());
    }
}