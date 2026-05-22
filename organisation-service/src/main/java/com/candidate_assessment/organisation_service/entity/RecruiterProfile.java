package com.candidate_assessment.organisation_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "recruiter_profiles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_recruiter_company_user",
                        columnNames = "company_user_id"
                )
        }
)
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "company_user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_recruiter_profile_company_user"
            )
    )
    private CompanyUser companyUser;

    private String designation;

    private String department;

    @Column(name = "employee_code", length = 100)
    private String employeeCode;

}
