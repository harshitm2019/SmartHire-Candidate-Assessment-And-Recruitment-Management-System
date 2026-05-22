package com.candidate_assessment.organisation_service.repository;

import com.candidate_assessment.organisation_service.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, UUID> {

    Optional<RecruiterProfile> findByCompanyUser_Id(UUID companyUserId);

    boolean existsByEmployeeCode(String employeeCode);

}
