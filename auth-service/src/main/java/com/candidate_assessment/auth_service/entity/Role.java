package com.candidate_assessment.auth_service.entity;

import com.candidate_assessment.auth_service.constants.DbSchema;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = DbSchema.TABLE_ROLES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DbSchema.COL_ROLE_NAME, nullable = false, unique = true)
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbSchema.JOIN_TABLE_ROLE_PERM,
            joinColumns = @JoinColumn(name = DbSchema.JOIN_COL_ROLE),
            inverseJoinColumns = @JoinColumn(name = DbSchema.JOIN_COL_PERM)
    )
    private Set<Permission> permissions = new HashSet<>();

}
