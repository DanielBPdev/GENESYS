--liquibase formatted sql

--changeset clmarin:01
--comment: 
ALTER TABLE RolAfiliado_aud ADD roaMunicipioDesempenioLabores smallint NULL;