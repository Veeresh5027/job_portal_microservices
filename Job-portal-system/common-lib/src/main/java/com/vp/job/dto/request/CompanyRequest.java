package com.vp.job.dto.request;

import com.vp.job.domain.CompanySize;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.job.dto.response.SocialLinkResponse;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CompanyRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String tagline;

    private String description;

    private String logoUrl;
    private String coverImageUrl;

    @Pattern(regexp = "(^https?://).*", message = "Invalid website URL")
    private String website;

    @Email(message = "Company email must be valid")
    private String email;

    private String phone;

    @Min(value = 1800, message = "Founded year seems too old")
    @Max(value = 2100, message = "Founded year seems too new")
    private Integer foundedYear;

    @NotNull(message = "Company size is required")
    private CompanySize companySize;

    @NotNull(message = "Company type is required")
    private CompanyType companyType;

    @NotNull(message = "Industry type is required")
    private IndustryType industryType;

    private String registrationNumber;

    private List<SocialLinkResponse> socialLinks;

}
