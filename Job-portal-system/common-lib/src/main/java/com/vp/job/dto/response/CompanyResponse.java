package com.vp.job.dto.response;

import com.vp.job.domain.CompanySize;
import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;

    private CompanySize companySize;
    private CompanyType companyType;
    private IndustryType industryType;
    private CompanyStatus status;
    private Boolean verified;
    private Boolean active;

    private Long ownerId;

    private List<SocialLinkResponse> socialLinks;
    private List<CompanyLocationResponse> locations;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime verifiedAt;

}
