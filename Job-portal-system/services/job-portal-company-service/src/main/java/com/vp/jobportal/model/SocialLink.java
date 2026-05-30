package com.vp.jobportal.model;

import com.vp.job.domain.SocialPlatform;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {

    private SocialPlatform platform;
    private String url;
}
