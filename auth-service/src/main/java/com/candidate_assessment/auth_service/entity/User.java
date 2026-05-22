package com.candidate_assessment.auth_service.entity;

import com.candidate_assessment.auth_service.constants.DbSchema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = DbSchema.TABLE_USERS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DbSchema.COL_DISPLAY_NAME,nullable = false, length = DbSchema.LENGTH_USERNAME)
    private String displayName;

    @Column(name = DbSchema.COL_USER_EMAIL,nullable = false, unique = true, length = DbSchema.LENGTH_EMAIL)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = DbSchema.COL_USER_LOCKED, nullable = false)
    private boolean accountNonLocked = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbSchema.JOIN_TABLE_USER_ROLES,
            joinColumns = @JoinColumn(name = DbSchema.JOIN_COL_USER),
            inverseJoinColumns = @JoinColumn(name = DbSchema.JOIN_COL_ROLE)
    )

    private Set<Role> roles = new HashSet<>();

}
