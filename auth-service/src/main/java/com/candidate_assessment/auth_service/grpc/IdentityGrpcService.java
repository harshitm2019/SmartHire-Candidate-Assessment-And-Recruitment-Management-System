package com.candidate_assessment.auth_service.grpc;

import com.candidate_assessment.auth_service.service.InternalIdentityService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashSet;

@GrpcService
@RequiredArgsConstructor
public class IdentityGrpcService
        extends IdentityServiceGrpc.IdentityServiceImplBase {

    private final InternalIdentityService internalIdentityService;


    @Override
    public void createIdentity(CreateIdentityRequest request, StreamObserver<CreateIdentityResponse> responseObserver) {


            com.candidate_assessment.auth_service.dto.CreateIdentityResponse response =

                    internalIdentityService.createIdentity(

                            com.candidate_assessment.auth_service.dto.CreateIdentityRequest.builder()
                                    .email(request.getEmail())
                                    .password(request.getPassword())
                                    .displayName(request.getDisplayName())
                                    .roles(new HashSet<>(request.getRolesList()))
                                    .build()

                    );


            responseObserver.onNext(

                    CreateIdentityResponse.newBuilder().setUserId(response.getUserId()).build()

            );

            responseObserver.onCompleted();

    }

    @Override
    public void createIdentity() {



    }
}