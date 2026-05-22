package com.candidate_assessment.organisation_service.service;

import com.candidate_assessment.organisation_service.entity.RecruiterProfile;

import java.util.UUID;

public interface RecruiterProfileService {

    RecruiterProfile createRecruiterProfile(RecruiterProfile recruiterProfile);

    RecruiterProfile getByCompanyUserId(UUID companyUserId);

}
