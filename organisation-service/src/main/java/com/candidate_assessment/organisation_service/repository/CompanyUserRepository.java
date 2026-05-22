package com.candidate_assessment.organisation_service.repository;

import com.candidate_assessment.organisation_service.entity.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, UUID> {

    boolean existsByCompany_IdAndUserId(
            UUID companyId,
            Long userId
    );

    Optional<CompanyUser> findByCompany_IdAndUserId(
            UUID companyId,
            Long userId
    );

    Optional<CompanyUser> findByUserId(Long userId);

}
