package com.candidate_assessment.organisation_service.grpc.client.auth.mapper;

import com.candidate_assessment.organisation_service.dto.CompanyRegistrationRequest;
import com.candidate_assessment.organisation_service.enums.MembershipType;
import com.proto.auth.CreateIdentityRequest;

import java.util.List;

public class AuthGrpcMapper {

    private AuthGrpcMapper() {}

    public static CreateIdentityRequest
    toCompanyAdminIdentityRequest(
            CompanyRegistrationRequest request
    ) {

        return CreateIdentityRequest.newBuilder()

                .setEmail(
                        request.getAdminEmail()
                )

                .setPassword(
                        request.getAdminPassword()
                )

                .setDisplayName(
                        request.getAdminDisplayName()
                )

                .addAllRole(

                        List.of(MembershipType.COMPANY_ADMIN.toString())

                )

                .build();
    }


}
