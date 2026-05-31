package com.vp.job.dto.response;

import com.vp.job.domain.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinkResponse {
    private SocialPlatform platform;
    private String url;
}
