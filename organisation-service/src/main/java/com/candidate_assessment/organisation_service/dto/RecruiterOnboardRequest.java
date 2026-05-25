package com.candidate_assessment.organisation_service.dto;

import com.candidate_assessment.organisation_service.enums.MembershipType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;


public record RecruiterOnboardRequest(

        @NotBlank(message = "Company Id is required")
        UUID companyId,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Display name is required")
        String displayName,

        @NotBlank(message = "Designation is required")
        String designation,

        @NotBlank(message = "Department is required")
        String department,

        @NotBlank(message = "MembershipType is required")
        MembershipType membershipType,

        @NotEmpty(message = "At least one role must be assigned")
        Set<String> roles
) {}
