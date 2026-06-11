package com.vp.jobportal.service.impl;


import com.vp.job.dto.response.JobCategoryResponse;
import com.vp.jobportal.mapper.JobCategoryMapper;
import com.vp.jobportal.model.JobCategory;
import com.vp.jobportal.payload.JobCategoryRequest;
import com.vp.jobportal.repository.JobCategoryRepository;
import com.vp.jobportal.service.JobCategoryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;


    @Override
    public JobCategoryResponse createJobCategory(JobCategoryRequest req) {
        if (jobCategoryRepository.existsByName(req.getName())) {
            throw new RuntimeException("Category name already exists");
        }
        JobCategory parent = null;
        if (req.getParentId() != null) {
            parent = getJobCategoryEntityById(req.getParentId());
        }

        String slug = generateUniqueSlug(req.getName());
        JobCategory category = JobCategory.builder()
                .name(req.getName())
                .slug(slug)
                .description(req.getDescription())
                .iconUrl(req.getIconUrl())
                .parent(parent)
                .build();

        JobCategory savedCategory = jobCategoryRepository.save(category);
        return JobCategoryMapper.toJobCategoryResponse(savedCategory, true);

    }

    private String generateUniqueSlug(@NotBlank(message = "Category name is required") String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
        if (jobCategoryRepository.existsBySlug(base)) {
            return base;
        }

        int counter = 1;
        while (jobCategoryRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }

    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return jobCategoryRepository.findByActiveTrue()
                .stream()
                .map(category -> JobCategoryMapper.toJobCategoryResponse(category, false))
                .collect(Collectors.toList());
    }

    @Override
    public JobCategoryResponse getCategoryById(Long id) {
        JobCategory category = getJobCategoryEntityById(id);
        return JobCategoryMapper.toJobCategoryResponse(category, true);
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) {
        JobCategory category = getJobCategoryEntityById(id);

        if (!category.getName().equals(req.getName()) &&
        jobCategoryRepository.existsByName(req.getName())
        ){
           throw new RuntimeException("Category name already exists, please use a different name");
        }

        JobCategory parent = null;
        if (req.getParentId() != null) {
            if (req.getParentId().equals(id)){
                throw new RuntimeException("Category cannot be its own parent");
            }

            parent = getJobCategoryEntityById(req.getParentId());
        }

        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIconUrl(req.getIconUrl());
        category.setParent(parent);

        JobCategory updatedCategory = jobCategoryRepository.save(category);
        return JobCategoryMapper.toJobCategoryResponse(updatedCategory, true);
    }

    @Override
    public void deleteCategory(Long id) {

        JobCategory category = getJobCategoryEntityById(id);
        category.setActive(false);
        jobCategoryRepository.save(category);

    }

    @Override
    public JobCategory getJobCategoryEntityById(Long id) {
        return jobCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
