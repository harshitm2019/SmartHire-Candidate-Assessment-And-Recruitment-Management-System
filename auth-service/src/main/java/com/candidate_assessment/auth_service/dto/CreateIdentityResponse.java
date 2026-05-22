package com.candidate_assessment.auth_service.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateIdentityResponse {

    private Long userId;

}
