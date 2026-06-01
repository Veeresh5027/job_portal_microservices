package com.vp.jobportal.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobLocation {

    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
