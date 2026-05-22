package com.candidate_assessment.auth_service.service;

import com.candidate_assessment.auth_service.dto.CreateIdentityRequest;
import com.candidate_assessment.auth_service.dto.CreateIdentityResponse;

public interface InternalIdentityService {

    CreateIdentityResponse createIdentity(CreateIdentityRequest request);

}
