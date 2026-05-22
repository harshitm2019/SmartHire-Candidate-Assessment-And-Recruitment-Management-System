package com.candidate_assessment.auth_service.service;


import com.candidate_assessment.auth_service.dto.LoginRequest;
import com.candidate_assessment.auth_service.dto.LoginResponse;

public interface AuthService {



    LoginResponse login(LoginRequest request);

}
