--liquibase formatted sql

--changeset jvelandia:01
--comment: 	
ALTER TABLE Comunicado_aud ADD comCertificado BIGINT;