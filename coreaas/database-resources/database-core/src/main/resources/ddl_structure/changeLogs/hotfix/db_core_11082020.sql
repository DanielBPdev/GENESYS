--liquibase formatted sql

--changeset oocampo:01
--comment:
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria ADD bbcNombrePersona varchar(200) NULL;
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria ADD bbcNumLinea int NULL;