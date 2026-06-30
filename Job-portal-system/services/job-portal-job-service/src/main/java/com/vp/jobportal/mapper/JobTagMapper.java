package com.vp.jobportal.mapper;

import com.vp.job.dto.response.JobTagResponse;
import com.vp.jobportal.model.JobTag;

public class JobTagMapper {

    public static JobTagResponse jobTagResponse(JobTag jobTag){
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }
}
