package com.candidate_assessment.organisation_service.manager.company;

import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.enums.MembershipType;
import com.candidate_assessment.organisation_service.service.CompanyService;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyActivationStageHandler {

    private final CompanyUserService companyUserService;
    private final CompanyService companyService;

    @Transactional
    public void activateCompany(Company company) {

        company.setStatus(CompanyStatus.ACTIVE);
        Company activeCompany = companyService.updateCompany(company);

        CompanyUser companyUser = CompanyUser.builder()
                .company(activeCompany) // Points cleanly to the active instance
                .userId(company.getAdminUserId())
                .membershipType(MembershipType.COMPANY_ADMIN)
                .status(MembershipStatus.ACTIVE)
                .build();

         companyUserService.createCompanyUser(companyUser);
    }

}
