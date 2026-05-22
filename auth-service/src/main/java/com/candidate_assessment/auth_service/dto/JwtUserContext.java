package com.candidate_assessment.auth_service.dto;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserContext {


    private long userId;

    private String email;

    private Set<String> roles;

    private Set<String> permissions;

    private Long companyId;


}
