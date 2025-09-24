--liquibase formatted sql

--changeset abaquero:01
--comment: Se incluye el dato del número de planilla diligenciado en el formulario de aporte manual
ALTER TABLE dbo.AporteGeneral_aud ADD apgNumeroPlanillaManual varchar(10)