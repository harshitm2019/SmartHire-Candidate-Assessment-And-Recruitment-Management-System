package com.candidate_assessment.auth_service.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {


    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private String displayName;



}
