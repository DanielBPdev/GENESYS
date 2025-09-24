--liquibase formatted sql

--changeset atoro:01
--comment: Se adiciona campo en la tabla AporteGeneral_aud
ALTER TABLE AporteGeneral_aud ADD apgOrigenAporte varchar(26) NULL;
ALTER TABLE AporteGeneral_aud ADD apgCajaCompensacion int NULL;

