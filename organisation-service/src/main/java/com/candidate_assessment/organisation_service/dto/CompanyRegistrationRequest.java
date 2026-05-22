package com.candidate_assessment.organisation_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRegistrationRequest {

    private String companyName;

    private String companyEmail;

    private String adminEmail;

    private String adminPassword;

    private String adminDisplayName;

}
