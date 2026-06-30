package com.vp.jobportal.service.impl;

import com.vp.job.dto.request.JobTagRequest;
import com.vp.job.dto.response.JobTagResponse;
import com.vp.jobportal.mapper.JobTagMapper;
import com.vp.jobportal.model.JobTag;
import com.vp.jobportal.repository.JobTagRepository;
import com.vp.jobportal.service.JobTagService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {

    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createJobTag(JobTagRequest req) {
        if (jobTagRepository.existsByName(req.getName())) {
            throw new RuntimeException("Tag already exists");
        }
        String slug = generateUniqueSlug(req.getName());

        JobTag jobTag = JobTag.builder()
                .name(req.getName())
                .slug(slug)
                .build();

        JobTag savedJobTag = jobTagRepository.save(jobTag);

        return JobTagMapper.jobTagResponse(savedJobTag);
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9]", "")
                .trim().replaceAll("[\\s-]+", "-");
        if (jobTagRepository.existsBySlug(base)) {
            return base;
        }
        int counter = 1;
        while (jobTagRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }

    @Override
    public List<JobTagResponse> getAllTags() {
        return jobTagRepository.findAll().stream()
                .map(JobTagMapper::jobTagResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobTagResponse getById(Long id) throws RuntimeException {
        JobTag jobTag = getTabEntityById(id);
        return JobTagMapper.jobTagResponse(jobTag);
    }

    @Override
    public JobTagResponse updateJobTag(Long id, JobTagRequest req) {
        JobTag jobTag = getTabEntityById(id);

        if(!jobTag.getName().equals(req.getName())
                && jobTagRepository.existsByName(req.getName())){
            throw new RuntimeException("Tag already exists");
        }
        jobTag.setName(req.getName());

        return JobTagMapper.jobTagResponse(jobTagRepository.save(jobTag));
    }

    @Override
    public void deleteJobTag(Long id) {
        JobTag jobTag = getTabEntityById(id);
        jobTagRepository.delete(jobTag);
    }

    @Override
    public JobTag getTabEntityById(Long id) {
        return jobTagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public Set<JobTag> getTagsByIds(Set<Long> ids) {
        List<JobTag> jobTags = jobTagRepository.findAllById(ids);
        return new HashSet<>(jobTags);
    }


}
