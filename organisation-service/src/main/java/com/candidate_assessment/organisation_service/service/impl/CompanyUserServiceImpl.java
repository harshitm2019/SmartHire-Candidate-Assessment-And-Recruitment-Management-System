package com.candidate_assessment.organisation_service.service.impl;

import com.candidate_assessment.organisation_service.constants.ErrorMessages;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.exception.ResourceNotFoundException;
import com.candidate_assessment.organisation_service.repository.CompanyUserRepository;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyUserServiceImpl implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;

    @Override
    public CompanyUser createCompanyUser(CompanyUser companyUser) {

        return companyUserRepository.save(companyUser);

    }

    @Override
    public CompanyUser getByUserId(Long userId){

        return companyUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(

                        ErrorMessages.COMPANY_USER_NOT_FOUND_WITH_ID

                ));

    }

    @Override
    public CompanyUser getByCompanyIdAndUserId(UUID companyId, Long userId) {

        return companyUserRepository.findByCompany_IdAndUserId(companyId,userId)
                .orElseThrow(() -> new ResourceNotFoundException(

                    ErrorMessages.COMPANY_USER_NOT_FOUND

                ));

    }

    @Override
    public boolean existsByCompanyIdAndUserId(UUID companyId, Long userId) {

        return  companyUserRepository.existsByCompany_IdAndUserId(companyId,userId);

    }

    @Override
    public Optional<CompanyUser>  findByCompanyIdAndEmail(UUID companyId, String email) {

           return companyUserRepository.findByCompany_IdAndEmail(companyId,email);

    }

    @Override
    public CompanyUser updateCompany(CompanyUser companyUser) {

        return companyUserRepository.save(companyUser);

    }
}
