package com.candidate_assessment.organisation_service.dto;

import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
public record CompanyRegistrationResponse(

         UUID companyId,

         Long adminUserId,

         String companyName,

         String companySlug,

         CompanyStatus status

) {



}
