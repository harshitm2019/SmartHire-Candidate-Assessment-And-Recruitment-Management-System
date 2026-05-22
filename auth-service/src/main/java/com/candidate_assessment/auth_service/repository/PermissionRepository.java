package com.candidate_assessment.auth_service.repository;

import com.candidate_assessment.auth_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {


    Optional<Permission> findByPermissionName(String permissionName);


}
