package com.candidate_assessment.organisation_service.dto;

import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CompanyRegistrationResponse {

    private UUID companyId;

    private Long adminUserId;

    private String companyName;

    private String companySlug;

    private CompanyStatus status;

}
