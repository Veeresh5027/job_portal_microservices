package com.vp.jobportal.service;

import com.vp.job.dto.request.JobTagRequest;
import com.vp.job.dto.response.JobTagResponse;
import com.vp.jobportal.model.JobTag;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createJobTag(JobTagRequest req);

    List<JobTagResponse> getAllTags();

    JobTagResponse getById(Long id);

    JobTagResponse updateJobTag(Long id, JobTagRequest req);

    void deleteJobTag(Long id);

    JobTag getTabEntityById(Long id);

    Set<JobTag> getTagsByIds(Set<Long> ids);
}
