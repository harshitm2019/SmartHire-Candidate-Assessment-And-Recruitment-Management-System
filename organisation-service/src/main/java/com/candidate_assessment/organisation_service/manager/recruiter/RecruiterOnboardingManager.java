package com.candidate_assessment.organisation_service.manager.recruiter;


import com.candidate_assessment.organisation_service.constants.ErrorMessages;
import com.candidate_assessment.organisation_service.dto.RecruiterOnboardRequest;
import com.candidate_assessment.organisation_service.dto.RecruiterOnboardResponse;
import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.exception.DuplicateResourceException;
import com.candidate_assessment.organisation_service.service.CompanyService;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RecruiterOnboardingManager {

    private final CompanyUserService companyUserService;
    private final CompanyService companyService;
    private final RecruiterIdentityStageHandler  recruiterIdentityStageHandler;
    private final RecruiterActivationStageHandler recruiterActivationStageHandler;


    public RecruiterOnboardResponse onboardRecruiter(RecruiterOnboardRequest request) {


        Optional<CompanyUser> existingRecruiter =
                companyUserService.findByCompanyIdAndEmail(request.companyId(),request.email());

        if(existingRecruiter.isPresent() && existingRecruiter.get().getStatus() == MembershipStatus.ACTIVE){

             throw new DuplicateResourceException(ErrorMessages.RECRUITER_EMAIL_ALREADY_EXISTS);

        }

        CompanyUser companyUser;

        if (existingRecruiter.isPresent()) {

            companyUser = existingRecruiter.get();

        }
        else {
            Company company = companyService.getCompanyById(request.companyId());
            companyUser = createCompanyUser(request, company);
        }

        if(companyUser.getStatus() == MembershipStatus.PENDING){

           companyUser =  recruiterIdentityStageHandler.createIdentity(request,companyUser);

        }

        if(companyUser.getStatus() == MembershipStatus.IDENTITY_CREATED){

            companyUser = recruiterActivationStageHandler.activateRecruiter(request,companyUser);

        }

        return buildResponse(companyUser);

    }

    private RecruiterOnboardResponse buildResponse(CompanyUser companyUser) {

        return RecruiterOnboardResponse.builder()
                .email(companyUser.getEmail())
                .userId(companyUser.getUserId())
                .profileId(companyUser.getId())
                .status(companyUser.getStatus().name())
                .companyId(companyUser.getCompany().getId())
                .build();

    }

    private CompanyUser createCompanyUser(RecruiterOnboardRequest request, Company company) {

          CompanyUser companyUser = CompanyUser.builder()
                  .email(request.email())
                  .status(MembershipStatus.PENDING)
                  .company(company)
                  .membershipType(request.membershipType())
                  .build();

         return companyUserService.createCompanyUser(companyUser);

    }

    private Company getCompanyById(UUID companyId) {

        return companyService.getCompanyById(companyId);

    }


}
