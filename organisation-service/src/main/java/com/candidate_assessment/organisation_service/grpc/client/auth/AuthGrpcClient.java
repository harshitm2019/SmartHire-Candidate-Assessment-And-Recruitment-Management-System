package com.candidate_assessment.organisation_service.grpc.client.auth;

import com.proto.auth.IdentityServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import com.proto.auth.CreateIdentityRequest;
import com.proto.auth.CreateIdentityResponse;

@Component
public class AuthGrpcClient {

    @GrpcClient("auth-service")
    private IdentityServiceGrpc.IdentityServiceBlockingStub
            identityServiceBlockingStub;

    public CreateIdentityResponse createIdentity(CreateIdentityRequest request) {

        return identityServiceBlockingStub
                .createIdentity(request);

    }


}
