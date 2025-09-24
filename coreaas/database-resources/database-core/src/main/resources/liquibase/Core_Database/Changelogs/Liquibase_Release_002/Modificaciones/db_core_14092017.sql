--liquibase formatted sql

--changeset atoro:01
--comment: Se adiciona campo en la tabla AporteGeneral
ALTER TABLE AporteGeneral ADD apgOrigenAporte varchar(26) NULL;
ALTER TABLE AporteGeneral ADD apgCajaCompensacion int NULL;
ALTER TABLE AporteGeneral ADD CONSTRAINT FK_AporteGeneral_apgCajaCompensacion FOREIGN KEY (apgCajaCompensacion) REFERENCES CajaCompensacion (ccfId);