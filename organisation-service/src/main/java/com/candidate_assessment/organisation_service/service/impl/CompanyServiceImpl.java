package com.candidate_assessment.organisation_service.service.impl;

import com.candidate_assessment.organisation_service.constants.ErrorMessages;
import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.exception.ResourceNotFoundException;
import com.candidate_assessment.organisation_service.repository.CompanyRepository;
import com.candidate_assessment.organisation_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {

       return companyRepository.save(company);

    }

    @Override
    public Company getCompanyById(UUID companyId) {

        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(

                        ErrorMessages.COMPANY_NOT_FOUND_WITH_ID + companyId

                ));
    }

    @Override
    public Company getCompanyBySlug(String slug) {

        return companyRepository.findBySlug(slug)
                .orElseThrow(() -> new  ResourceNotFoundException(

                        ErrorMessages.COMPANY_NOT_FOUND_WITH_ID + slug

                ));

    }

    @Override
    public boolean existsBySlug(String slug) {

        return companyRepository.existsBySlug(slug);

    }

    @Override
    public boolean existsByEmail(String email) {

        return companyRepository.existsByEmail(email);

    }

    @Override
    public Optional<Company> findByEmail(String email) {

         return  companyRepository.findByEmail(email);

    }

    @Override
    public Company updateCompany(Company company) {

       return companyRepository.save(company);

    }


}
