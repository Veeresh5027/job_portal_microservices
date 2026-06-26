package com.vp.jobportal.service;

import com.vp.job.dto.response.JobSkillResponse;
import com.vp.jobportal.model.JobSkill;
import com.vp.jobportal.payload.JobSkillRequest;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req);

    List<JobSkillResponse> getAllSkills();

    JobSkillResponse getSkillById(Long id);

    JobSkillResponse updateSkill(Long id, JobSkillRequest req);

    void deleteSkill(Long id);

    Set<JobSkill> getSkillByIds(Set<Long> ids);
}
