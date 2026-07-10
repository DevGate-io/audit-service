-- Seed script for audit-service
-- Populates audit_events with mock data matching the frontend structure.
-- actor_id uses UUIDs (matching devgate-user-service), all other IDs are strings.
--
-- Actor UUID mapping:
--   usr-anna-ivanova     → 00000000-0000-0000-0000-000000000001
--   usr-alexey-zaytsev   → 00000000-0000-0000-0000-000000000002
--   usr-roman-volkov     → 00000000-0000-0000-0000-000000000003
--   usr-denis-kravtsov   → 00000000-0000-0000-0000-000000000004
--   usr-irina-fedorova   → 00000000-0000-0000-0000-000000000005
--   usr-ekaterina-belova → 00000000-0000-0000-0000-000000000006
--   usr-mikhail-orlov    → 00000000-0000-0000-0000-000000000007
--   usr-pavel-sidorov    → 00000000-0000-0000-0000-000000000008
--   usr-olga-petrova     → 00000000-0000-0000-0000-000000000009
--   usr-natalia-popova   → 00000000-0000-0000-0000-00000000000a
--
-- Usage:
--   docker compose exec -T -e PGPASSWORD=devgate-audit db psql -U postgres -d audit < seed.sql
--   make seed

BEGIN;

CREATE SEQUENCE IF NOT EXISTS audit_events_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS audit_events (
    id          BIGINT PRIMARY KEY,
    action      VARCHAR(255),
    actor_id    UUID,
    target_type VARCHAR(255),
    target_id   VARCHAR(255),
    target_label VARCHAR(255),
    payload     TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT now()
);

TRUNCATE audit_events;

INSERT INTO audit_events (id, action, actor_id, target_type, target_id, target_label, payload, created_at) VALUES
    (1,  'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000001', 'service',     'svc-orders-worker',    'orders-worker',        NULL,                                                '2026-05-03T10:42:00Z'::timestamp),
    (2,  'TEMPLATE_USED',        '00000000-0000-0000-0000-000000000001', 'template',    'tpl-go-worker',        'Go Worker',            '{"newServiceId":"svc-orders-worker"}',            '2026-05-03T10:39:00Z'::timestamp),
    (3,  'ROLE_CHANGED',         '00000000-0000-0000-0000-000000000002', 'user',        'usr-mikhail-orlov',    'Михаил Орлов',         '{"from":"member","to":"devops"}',                 '2026-05-03T09:55:00Z'::timestamp),
    (4,  'SERVICE_UPDATED',      '00000000-0000-0000-0000-000000000004', 'service',     'svc-payments-api',     'payments-api',         '{"fields":"pipelineUrl, docsUrl"}',               '2026-05-03T09:14:00Z'::timestamp),
    (5,  'TEAM_MEMBER_ADDED',    '00000000-0000-0000-0000-000000000001', 'team',        'team-platform',        'Platform',             '{"userId":"usr-pavel-sidorov"}',                  '2026-05-02T16:30:00Z'::timestamp),
    (6,  'INTEGRATION_CONNECTED','00000000-0000-0000-0000-000000000002', 'integration', 'int-datadog',          'Datadog',              NULL,                                                '2026-05-02T14:20:00Z'::timestamp),
    (7,  'SERVICE_DELETED',      '00000000-0000-0000-0000-000000000003', 'service',     'svc-legacy-checkout',  'legacy-checkout',      NULL,                                                '2026-05-02T11:05:00Z'::timestamp),
    (8,  'TEMPLATE_USED',        '00000000-0000-0000-0000-000000000006', 'template',    'tpl-nodejs-service',   'Node.js Service',      '{"newServiceId":"svc-promo-api"}',                '2026-05-02T09:48:00Z'::timestamp),
    (9,  'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000006', 'service',     'svc-promo-api',        'promo-api',            NULL,                                                '2026-05-02T09:50:00Z'::timestamp),
    (10, 'ROLE_CHANGED',         '00000000-0000-0000-0000-000000000002', 'user',        'usr-irina-fedorova',   'Ирина Фёдорова',       '{"from":"member","to":"qa"}',                     '2026-05-01T18:15:00Z'::timestamp),
    (11, 'TEAM_MEMBER_REMOVED',  '00000000-0000-0000-0000-000000000003', 'team',        'team-commerce',        'Commerce',             '{"userId":"usr-natalia-popova"}',                 '2026-05-01T15:42:00Z'::timestamp),
    (12, 'SERVICE_UPDATED',      '00000000-0000-0000-0000-000000000001', 'service',     'svc-auth-service',     'auth-service',         '{"fields":"tags"}',                               '2026-05-01T13:08:00Z'::timestamp),
    (13, 'INTEGRATION_DISCONNECTED','00000000-0000-0000-0000-000000000004', 'integration','int-sonarqube',        'SonarQube',            NULL,                                                '2026-05-01T11:55:00Z'::timestamp),
    (14, 'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000003', 'service',     'svc-analytics-pipeline','analytics-pipeline',  NULL,                                                '2026-04-30T14:25:00Z'::timestamp),
    (15, 'TEMPLATE_USED',        '00000000-0000-0000-0000-000000000003', 'template',    'tpl-rust-grpc',        'Rust gRPC Service',    '{"newServiceId":"svc-rates-grpc"}',               '2026-04-30T10:32:00Z'::timestamp),
    (16, 'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000003', 'service',     'svc-rates-grpc',       'rates-grpc',           NULL,                                                '2026-04-30T10:35:00Z'::timestamp),
    (17, 'ROLE_CHANGED',         '00000000-0000-0000-0000-000000000002', 'user',        'usr-pavel-sidorov',    'Павел Сидоров',        '{"from":"qa","to":"member"}',                     '2026-04-29T17:00:00Z'::timestamp),
    (18, 'SERVICE_UPDATED',      '00000000-0000-0000-0000-000000000009', 'service',     'svc-billing-gateway',  'billing-gateway',      '{"fields":"docsUrl"}',                            '2026-04-29T12:18:00Z'::timestamp),
    (19, 'TEAM_MEMBER_ADDED',    '00000000-0000-0000-0000-000000000005', 'team',        'team-growth',          'Growth',               '{"userId":"usr-natalia-popova"}',                 '2026-04-29T09:40:00Z'::timestamp),
    (20, 'SERVICE_DELETED',      '00000000-0000-0000-0000-000000000001', 'service',     'svc-trial-api',        'trial-api',            NULL,                                                '2026-04-28T16:05:00Z'::timestamp),
    (21, 'INTEGRATION_CONNECTED','00000000-0000-0000-0000-000000000004', 'integration', 'int-slack',            'Slack',                NULL,                                                '2026-04-28T11:32:00Z'::timestamp),
    (22, 'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000007', 'service',     'svc-search-api',       'search-api',           NULL,                                                '2026-04-27T14:55:00Z'::timestamp),
    (23, 'TEMPLATE_USED',        '00000000-0000-0000-0000-000000000007', 'template',    'tpl-go-worker',        'Go Worker',            '{"newServiceId":"svc-search-api"}',               '2026-04-27T14:50:00Z'::timestamp),
    (24, 'SERVICE_UPDATED',      '00000000-0000-0000-0000-000000000001', 'service',     'svc-orders-worker',    'orders-worker',        '{"fields":"pipelineUrl"}',                        '2026-04-26T09:18:00Z'::timestamp),
    (25, 'ROLE_CHANGED',         '00000000-0000-0000-0000-000000000002', 'user',        'usr-ekaterina-belova', 'Екатерина Белова',     '{"from":"member","to":"manager"}',                '2026-04-25T16:42:00Z'::timestamp),
    (26, 'TEAM_MEMBER_ADDED',    '00000000-0000-0000-0000-000000000003', 'team',        'team-commerce',        'Commerce',             '{"userId":"usr-ekaterina-belova"}',               '2026-04-25T11:08:00Z'::timestamp),
    (27, 'SERVICE_CREATED',      '00000000-0000-0000-0000-000000000009', 'service',     'svc-billing-gateway',  'billing-gateway',      NULL,                                                '2026-04-24T13:30:00Z'::timestamp),
    (28, 'SERVICE_UPDATED',      '00000000-0000-0000-0000-000000000003', 'service',     'svc-promo-api',        'promo-api',            '{"fields":"tags, docsUrl"}',                      '2026-04-23T15:14:00Z'::timestamp),
    (29, 'SERVICE_DELETED',      '00000000-0000-0000-0000-000000000002', 'service',     'svc-deprecated-feed',  'deprecated-feed',      NULL,                                                '2026-04-23T10:00:00Z'::timestamp),
    (30, 'INTEGRATION_CONNECTED','00000000-0000-0000-0000-000000000004', 'integration', 'int-github',           'GitHub',               NULL,                                                '2026-04-22T08:48:00Z'::timestamp);

SELECT setval('audit_events_seq', 50, true);

COMMIT;
