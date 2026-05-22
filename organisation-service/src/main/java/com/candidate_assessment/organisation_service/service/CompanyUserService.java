package com.candidate_assessment.organisation_service.service;

import com.candidate_assessment.organisation_service.entity.CompanyUser;

import java.util.UUID;

public interface CompanyUserService {

    CompanyUser createCompanyUser(CompanyUser companyUser);

    CompanyUser getByUserId(Long userId);

    CompanyUser getByCompanyIdAndUserId(UUID companyId, Long userId);

    boolean existsByCompanyIdAndUserId(UUID companyId, Long userId);

}
