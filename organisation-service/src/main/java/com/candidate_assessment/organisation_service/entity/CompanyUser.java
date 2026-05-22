package com.candidate_assessment.organisation_service.entity;

import com.candidate_assessment.organisation_service.enums.MembershipStatus;
import com.candidate_assessment.organisation_service.enums.MembershipType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "company_users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_company_user",
                        columnNames = {
                                "company_id",
                                "user_id"
                        }
                )
        }
)
public class CompanyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_company_user_company"
            )
    )
    private Company company;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "membership_type",
            nullable = false,
            length = 50
    )
    private MembershipType membershipType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MembershipStatus status;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

}
