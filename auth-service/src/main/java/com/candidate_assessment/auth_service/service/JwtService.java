package com.candidate_assessment.auth_service.service;


import com.candidate_assessment.auth_service.dto.JwtUserContext;
import com.candidate_assessment.auth_service.entity.User;

public interface JwtService {


    String generateAccessToken(JwtUserContext context);


}
