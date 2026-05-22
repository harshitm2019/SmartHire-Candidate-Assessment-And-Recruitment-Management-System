package com.candidate_assessment.organisation_service.repository;

import com.candidate_assessment.organisation_service.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsBySlug(String slug);

    boolean existsByEmail(String email);

    Optional<Company> findBySlug(String slug);

    Optional<Company> findByEmail(String email);
}
