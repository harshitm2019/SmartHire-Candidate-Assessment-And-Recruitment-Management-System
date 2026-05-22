INSERT INTO permissions(permission_name)
VALUES
('ASSESSMENT_CREATE'),
('ASSESSMENT_VIEW'),
('ASSESSMENT_UPDATE'),
('ASSESSMENT_DELETE'),
('ASSESSMENT_ATTEMPT'),
('ASSESSMENT_RESULT_VIEW'),

('RECRUITER_CREATE'),
('RECRUITER_UPDATE'),
('RECRUITER_DELETE'),
('RECRUITER_VIEW')

ON CONFLICT DO NOTHING;



INSERT INTO roles(role_name)
VALUES
('COMPANY_ADMIN'),
('RECRUITER_MANAGER'),
('RECRUITER_VIEWER'),
('CANDIDATE')

ON CONFLICT DO NOTHING;



-- COMPANY_ADMIN

INSERT INTO role_permissions(role_id, permission_id)

SELECT r.id, p.id

FROM roles r,
     permissions p

WHERE r.role_name = 'COMPANY_ADMIN'

AND p.permission_name IN (

    'ASSESSMENT_CREATE',
    'ASSESSMENT_VIEW',
    'ASSESSMENT_UPDATE',
    'ASSESSMENT_DELETE',

    'ASSESSMENT_ATTEMPT',
    'ASSESSMENT_RESULT_VIEW',

    'RECRUITER_CREATE',
    'RECRUITER_UPDATE',
    'RECRUITER_DELETE',
    'RECRUITER_VIEW'
)

ON CONFLICT DO NOTHING;



-- RECRUITER_MANAGER

INSERT INTO role_permissions(role_id, permission_id)

SELECT r.id, p.id

FROM roles r,
     permissions p

WHERE r.role_name = 'RECRUITER_MANAGER'

AND p.permission_name IN (

    'ASSESSMENT_CREATE',
    'ASSESSMENT_VIEW',
    'ASSESSMENT_UPDATE',
    'ASSESSMENT_DELETE'
)

ON CONFLICT DO NOTHING;



-- RECRUITER_VIEWER

INSERT INTO role_permissions(role_id, permission_id)

SELECT r.id, p.id

FROM roles r,
     permissions p

WHERE r.role_name = 'RECRUITER_VIEWER'

AND p.permission_name IN (

    'ASSESSMENT_VIEW'
)

ON CONFLICT DO NOTHING;



-- CANDIDATE

INSERT INTO role_permissions(role_id, permission_id)

SELECT r.id, p.id

FROM roles r,
     permissions p

WHERE r.role_name = 'CANDIDATE'

AND p.permission_name IN (

    'ASSESSMENT_ATTEMPT',
    'ASSESSMENT_RESULT_VIEW'
)

ON CONFLICT DO NOTHING;