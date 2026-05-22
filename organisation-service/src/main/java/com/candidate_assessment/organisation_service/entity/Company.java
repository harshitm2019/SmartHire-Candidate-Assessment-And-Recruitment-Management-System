package com.candidate_assessment.organisation_service.entity;


import com.candidate_assessment.organisation_service.enums.CompanyStatus;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "companies",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_company_slug",
                        columnNames = "slug"
                ),
                @UniqueConstraint(
                        name = "uk_company_email",
                        columnNames = "email"
                )
        }
)
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private Long adminUserId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String email;

    @Column(length = 30)
    private String phone;

    private String website;

    @Column(length = 100)
    private String industry;

    @Column(length = 50)
    private String size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CompanyStatus status;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
