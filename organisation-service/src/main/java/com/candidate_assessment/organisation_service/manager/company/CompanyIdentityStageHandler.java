package com.candidate_assessment.organisation_service.manager.company;

import com.candidate_assessment.organisation_service.dto.CompanyRegistrationRequest;
import com.candidate_assessment.organisation_service.entity.Company;
import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import com.candidate_assessment.organisation_service.exception.BusinessException;
import com.candidate_assessment.organisation_service.grpc.client.auth.AuthGrpcClient;
import com.candidate_assessment.organisation_service.grpc.client.auth.mapper.AuthGrpcMapper;
import com.candidate_assessment.organisation_service.service.CompanyService;
import com.proto.auth.CreateIdentityRequest;
import com.proto.auth.CreateIdentityResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyIdentityStageHandler {


    private final AuthGrpcClient authGrpcClient;
    private final CompanyService  companyService;


    public void createIdentity(CompanyRegistrationRequest request, Company company) {

        CreateIdentityResponse grpcResponse;

        try {

            CreateIdentityRequest grpcRequest = AuthGrpcMapper.toCompanyAdminIdentityRequest(request);

            grpcResponse = authGrpcClient.createIdentity(grpcRequest);


        } catch (Exception e) {

            throw new BusinessException(e.getMessage());

        }

        saveIdentityState(company, grpcResponse.getUserId());

    }

    @Transactional
    protected void saveIdentityState(Company company, Long userId) {

        company.setAdminUserId(userId);
        company.setStatus(CompanyStatus.IDENTITY_CREATED);
        companyService.updateCompany(company);

    }

}
