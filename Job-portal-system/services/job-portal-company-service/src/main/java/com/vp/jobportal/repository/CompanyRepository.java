package com.vp.jobportal.repository;

import com.vp.job.domain.CompanyStatus;
import com.vp.job.domain.CompanyType;
import com.vp.job.domain.IndustryType;
import com.vp.jobportal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByOwnerId(Long ownerId);

    boolean existsByOwnerId(Long ownerId);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByEmail(String email);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByWebsite(String website);

    @Query("SELECT c FROM Company c WHERE " +
            "(:companyType IS NULL OR c.companyType = :companyType) AND " +
            "(:industryType IS NULL OR c.industryType = :industryType) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<Company> findByFilters(
            @Param("companyType") CompanyType companyType,
            @Param("industryType") IndustryType industryType,
            @Param("status") CompanyStatus status
    );

}
