package com.vp.jobportal.repository;

import com.vp.jobportal.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);

    List<JobCategory> findByActiveTrue();

}
