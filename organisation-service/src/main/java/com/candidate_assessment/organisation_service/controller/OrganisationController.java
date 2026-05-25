package com.candidate_assessment.organisation_service.controller;

import com.candidate_assessment.organisation_service.dto.CompanyRegistrationRequest;
import com.candidate_assessment.organisation_service.dto.CompanyRegistrationResponse;
import com.candidate_assessment.organisation_service.manager.company.CompanyOnboardingManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organisations")
@RequiredArgsConstructor
public class OrganisationController {

    private final CompanyOnboardingManager companyOnboardingManager;

    @PostMapping("/register")
    public ResponseEntity<CompanyRegistrationResponse> registerCompany(
            @Valid @RequestBody CompanyRegistrationRequest request
    ) {

        CompanyRegistrationResponse response = companyOnboardingManager.onboardCompany(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
