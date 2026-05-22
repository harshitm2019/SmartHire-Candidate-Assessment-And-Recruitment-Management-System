package com.candidate_assessment.organisation_service.service.impl;

import com.candidate_assessment.organisation_service.constants.ErrorMessages;
import com.candidate_assessment.organisation_service.entity.RecruiterProfile;
import com.candidate_assessment.organisation_service.exception.ResourceNotFoundException;
import com.candidate_assessment.organisation_service.repository.RecruiterProfileRepository;
import com.candidate_assessment.organisation_service.service.RecruiterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    @Override
    public RecruiterProfile createRecruiterProfile(RecruiterProfile recruiterProfile) {

        return recruiterProfileRepository.save(recruiterProfile);

    }

    @Override
    public RecruiterProfile getByCompanyUserId(UUID companyUserId) {

        return recruiterProfileRepository.findByCompanyUser_Id(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException(

                        ErrorMessages.RECRUITER_PROFILE_NOT_FOUND

                ));

    }


}
