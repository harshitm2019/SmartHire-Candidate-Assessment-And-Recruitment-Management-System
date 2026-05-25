package com.candidate_assessment.organisation_service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public record CompanyRegistrationRequest (

         String companyName,
         String companyEmail,
         String adminEmail,
         String adminPassword,
         String adminDisplayName

){



}
