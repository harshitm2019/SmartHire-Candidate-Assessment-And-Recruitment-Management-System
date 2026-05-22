package com.candidate_assessment.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Builder
public class CreateIdentityRequest {


    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String displayName;

    @NotBlank
    private String password;

    @NotEmpty
    private Set<String> roles;


}
