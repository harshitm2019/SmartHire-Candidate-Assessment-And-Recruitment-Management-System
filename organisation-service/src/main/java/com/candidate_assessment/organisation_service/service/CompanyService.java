package com.candidate_assessment.organisation_service.service;

import com.candidate_assessment.organisation_service.entity.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {

    Company createCompany(Company company);

    Company getCompanyById(UUID companyId);

    Company getCompanyBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByEmail(String email);

    Optional<Company> findByEmail(String email);

    Company updateCompany(Company company);


}
