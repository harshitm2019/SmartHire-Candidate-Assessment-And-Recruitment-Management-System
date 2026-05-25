package com.candidate_assessment.organisation_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RecruiterOnboardResponse(
        Long userId,
        UUID companyId,
        UUID profileId,
        String email,
        String status
) {}
