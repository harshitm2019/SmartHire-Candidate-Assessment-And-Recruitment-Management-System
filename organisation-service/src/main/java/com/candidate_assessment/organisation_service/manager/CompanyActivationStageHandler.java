package com.candidate_assessment.organisation_service.manager;

import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.enums.MembershipType;
import com.candidate_assessment.organisation_service.grpc.client.auth.AuthGrpcClient;
import com.candidate_assessment.organisation_service.service.CompanyService;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CompanyActivationStageHandler {

    private final CompanyUserService companyUserService;
    private final CompanyService companyService;

    @Transactional
    public void activateCompany(Company company) {

        CompanyUser companyUser = CompanyUser.builder()
                .company(company)
                .userId(company.getAdminUserId())
                .membershipType(MembershipType.COMPANY_ADMIN)
                .status(MembershipStatus.ACTIVE)
                .build();


        companyUserService.createCompanyUser(companyUser);

        company.setStatus(CompanyStatus.ACTIVE);

        companyService.updateCompany(company);
    }

}
