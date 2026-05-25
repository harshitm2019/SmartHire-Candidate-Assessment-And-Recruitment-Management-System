package com.candidate_assessment.organisation_service.manager.recruiter;

import com.candidate_assessment.organisation_service.dto.RecruiterOnboardRequest;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.entity.RecruiterProfile;
import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.grpc.client.auth.AuthGrpcClient;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import com.candidate_assessment.organisation_service.service.RecruiterProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RecruiterActivationStageHandler {


    private CompanyUserService companyUserService;
    private RecruiterProfileService recruiterProfileService;

    @Transactional
    public CompanyUser activateRecruiter(RecruiterOnboardRequest request, CompanyUser companyUser) {

      companyUser.setStatus(MembershipStatus.ACTIVE);
      CompanyUser activeUser =  companyUserService.updateCompany(companyUser);

      RecruiterProfile recruiterProfile =   RecruiterProfile.builder()
              .companyUser(activeUser)
              .designation(request.designation())
              .department(request.department())
              .build();

       recruiterProfileService.createRecruiterProfile(recruiterProfile);

       return activeUser;

    }
}
