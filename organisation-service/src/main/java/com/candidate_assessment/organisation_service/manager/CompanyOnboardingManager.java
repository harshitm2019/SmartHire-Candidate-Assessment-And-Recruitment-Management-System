package com.candidate_assessment.organisation_service.manager;

import com.candidate_assessment.organisation_service.constants.ErrorMessages;
import com.candidate_assessment.organisation_service.dto.CompanyRegistrationRequest;
import com.candidate_assessment.organisation_service.dto.CompanyRegistrationResponse;
import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import com.candidate_assessment.organisation_service.exception.DuplicateResourceException;
import com.candidate_assessment.organisation_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyOnboardingManager {


    private final CompanyService companyService;
    private final CompanyIdentityStageHandler companyIdentityStageHandler;
    private final CompanyActivationStageHandler companyActivationStageHandler;

    public CompanyRegistrationResponse registerCompany(CompanyRegistrationRequest request) {

        String slug = generateSlug(request.getCompanyName());

        Optional<Company> existingCompany = companyService.findByEmail(request.getCompanyEmail());

        Company company = existingCompany.orElseGet(() -> {

            validateSlug(slug);
            return createCompany(request, slug);

        });

        if (company.getStatus() == CompanyStatus.ACTIVE) {

            throw new DuplicateResourceException(ErrorMessages.COMPANY_EMAIL_ALREADY_EXISTS);

        }

        if (company.getStatus() == CompanyStatus.PENDING) {

            companyIdentityStageHandler.createIdentity(request,company);

        }

        if (company.getStatus() == CompanyStatus.IDENTITY_CREATED) {

            companyActivationStageHandler.activateCompany(company);

        }

       return buildResponse(company);
    }

    private Company createCompany(CompanyRegistrationRequest request, String slug) {


        Company company = Company.builder()
                .name(request.getCompanyName())
                .slug(slug)
                .email(request.getCompanyEmail())
                .status(CompanyStatus.PENDING)
                .build();

        return companyService.createCompany(company);


    }

    private void validateSlug(String  slug) {


        if (companyService.existsBySlug(slug)) {
            throw new DuplicateResourceException(ErrorMessages.COMPANY_SLUG_ALREADY_EXISTS);
        }
    }

    private String generateSlug(String companyName) {

        return  companyName
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "-");
    }

    private CompanyRegistrationResponse buildResponse(Company company) {

        return CompanyRegistrationResponse.builder()
                .companyId(company.getId())
                .companyName(company.getName())
                .companySlug(company.getSlug())
                .adminUserId(company.getAdminUserId())
                .status(company.getStatus())
                .build();
    }

}
