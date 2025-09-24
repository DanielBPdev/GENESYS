--liquibase formatted sql

--changeset dsuesca:01
--comment: 
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria_aud DROP COLUMN bbcCargueBloqueoCM;
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria_aud ADD bbcCargueBloqueoCuotaMonetaria BIT;

--changeset dsuesca:02
--comment: 
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria_aud DROP COLUMN bbcCargueBloqueoCuotaMonetaria;
ALTER TABLE BloqueoBeneficiarioCuotaMonetaria_aud ADD bbcCargueBloqueoCuotaMonetaria BIGINT;