package com.vp.jobportal.service.impl;

import com.vp.job.dto.response.JobSkillResponse;
import com.vp.jobportal.mapper.JobSkillMapper;
import com.vp.jobportal.model.JobSkill;
import com.vp.jobportal.payload.JobSkillRequest;
import com.vp.jobportal.repository.JobSkillRepository;
import com.vp.jobportal.service.JobSkillService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {

    private final JobSkillRepository jobSkillRepository;


    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) {
        if (jobSkillRepository.existsByName(req.getName())) {
            throw new RuntimeException("Skill already exists");
        }

        String slug = generateUniqueSlug(req.getName());

        JobSkill skill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .build();

        JobSkill savedSkill = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(savedSkill);

    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9]", "")
                .trim().replaceAll("[\\s-]+", "-");
        if (jobSkillRepository.existsBySlug(base)) {
            return base;
        }
        int counter = 1;
        while (jobSkillRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue()
                .stream()
                .map(JobSkillMapper::toJobSkillResponse)
                .toList();
    }

    @Override
    public JobSkillResponse getSkillById(Long id) {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        return JobSkillMapper.toJobSkillResponse(skill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        if(!skill.getName().equals(req.getName())
            && jobSkillRepository.existsByName(skill.getName())
        ){
            throw new RuntimeException("Skill already exists");
        }
        skill.setName(req.getName());
        skill.setCategory(req.getCategory());
        JobSkill updatedSkill = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long id) {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setActive(false);
        jobSkillRepository.save(skill);
    }

    @Override
    public Set<JobSkill> getSkillByIds(Set<Long> ids) {
        Set<JobSkill> skills = new HashSet<>(jobSkillRepository.findAllById(ids));
        return skills;
    }
}
