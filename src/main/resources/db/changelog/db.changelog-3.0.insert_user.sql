--liquibase formatted sql

--changeset veta:1
INSERT INTO person (username, password, email, phone, birthday, created_at, is_active, role)
VALUES ('Mary', '48c8947f69c054a5caa934674ce8881d02bb18fb59d5a63eeaddff735b0e9801e87294783281ae49fc8287a0fd86779b27d7972d3e84f0fa0d826d7cb67dfefc', 'mary@mail.ru', 'go', '2002-04-17', '2023-04-12 04:05:06', true, 'ROLE_ADMIN');
