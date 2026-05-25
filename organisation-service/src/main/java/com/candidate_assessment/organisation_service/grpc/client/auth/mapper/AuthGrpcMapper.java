package com.candidate_assessment.organisation_service.grpc.client.auth.mapper;

import com.candidate_assessment.organisation_service.dto.CompanyRegistrationRequest;
import com.candidate_assessment.organisation_service.dto.RecruiterOnboardRequest;
import com.candidate_assessment.organisation_service.enums.MembershipType;
import com.proto.auth.CreateIdentityRequest;
import com.proto.auth.CreateIdentityResponse;

import java.util.Arrays;
import java.util.List;

public class AuthGrpcMapper {

    private AuthGrpcMapper() {}

    public static CreateIdentityRequest
    toCompanyAdminIdentityRequest(
            CompanyRegistrationRequest request
    ) {

        return CreateIdentityRequest.newBuilder()

                .setEmail(request.adminEmail())

                .setPassword(request.adminPassword())

                .setDisplayName(request.adminDisplayName())

                .addAllRole(

                        List.of(MembershipType.COMPANY_ADMIN.toString())

                )

                .build();
    }

    public static CreateIdentityRequest toRecruiterIdentityRequest(
            RecruiterOnboardRequest request
    ) {

        return CreateIdentityRequest.newBuilder()

                .setEmail(request.email())

                .setPassword(request.password())

                .setDisplayName(request.displayName())

                .addAllRole(

                        request.roles()
                )
                .build();

    }


}
