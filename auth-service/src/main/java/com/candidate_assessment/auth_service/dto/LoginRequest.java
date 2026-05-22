package com.candidate_assessment.auth_service.dto;

import com.candidate_assessment.auth_service.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {


    @Email
    @NotBlank(message = AppConstants.MSG_EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = AppConstants.MSG_PASSWORD_REQUIRED)
    private String password;


}
