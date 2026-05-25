package com.candidate_assessment.organisation_service.manager.recruiter;


import com.candidate_assessment.organisation_service.dto.RecruiterOnboardRequest;
import com.candidate_assessment.organisation_service.entity.CompanyUser;
import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.exception.BusinessException;
import com.candidate_assessment.organisation_service.grpc.client.auth.AuthGrpcClient;
import com.candidate_assessment.organisation_service.grpc.client.auth.mapper.AuthGrpcMapper;
import com.candidate_assessment.organisation_service.service.CompanyService;
import com.candidate_assessment.organisation_service.service.CompanyUserService;
import com.proto.auth.CreateIdentityRequest;
import com.proto.auth.CreateIdentityResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruiterIdentityStageHandler {

    private final AuthGrpcClient authGrpcClient;
    private final CompanyUserService companyUserService;

    @Transactional
    public CompanyUser createIdentity(RecruiterOnboardRequest request, CompanyUser companyUser) {

        CreateIdentityResponse grpcResponse;

        try {

            CreateIdentityRequest grpcRequest = AuthGrpcMapper.toRecruiterIdentityRequest(request);

            grpcResponse = authGrpcClient.createIdentity(grpcRequest);


        } catch (Exception e) {

            throw new BusinessException(e.getMessage());

        }

        companyUser.setUserId(grpcResponse.getUserId());
        companyUser.setStatus(MembershipStatus.IDENTITY_CREATED);
       return   companyUserService.updateCompany(companyUser);

    }

}
