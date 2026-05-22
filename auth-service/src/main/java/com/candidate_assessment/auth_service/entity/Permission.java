package com.candidate_assessment.auth_service.entity;

import com.candidate_assessment.auth_service.constants.DbSchema;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = DbSchema.TABLE_PERMISSIONS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DbSchema.COL_PERM_NAME, nullable = false, unique = true)
    private String permissionName;

}
