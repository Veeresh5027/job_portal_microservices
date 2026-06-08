package com.vp.jobportal.service;

import com.vp.jobportal.model.JobCategory;
import com.vp.jobportal.payload.JobCategoryRequest;
import com.vp.job.dto.response.JobCategoryResponse;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createJobCategory(JobCategoryRequest req);

    List<JobCategoryResponse> getAllCategories();

    JobCategoryResponse getCategoryById(Long id);

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req);

    void deleteCategory(Long id);

    JobCategory getJobCategoryEntityById(Long id);

}
