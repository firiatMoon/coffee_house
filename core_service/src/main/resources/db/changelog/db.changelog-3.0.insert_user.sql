--liquibase formatted sql

--changeset veta:1
INSERT INTO person (username, password, email, phone, birthday, created_at, is_active, role)
VALUES ('Mary', '$2a$10$vTXRVLS.uOcRTolLNGgN1.1cRaKW0yWBrYDAPrNp1biruTiargPc6', 'mary@mail.ru', '9123456545', '2002-04-17', '2023-04-12 04:05:06', true, 'ROLE_ADMIN');
