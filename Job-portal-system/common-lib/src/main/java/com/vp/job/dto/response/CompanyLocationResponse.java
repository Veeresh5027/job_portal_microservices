package com.vp.job.dto.response;

import com.vp.job.domain.SocialPlatform;
import lombok.Data;

@Data
public class CompanyLocationResponse {

    private SocialPlatform platform;
    private String url;

}
