--liquibase formatted sql

--changeset abaquero:01
--comment: se inactiva el Operador de Información "Coomeva Financiera"
UPDATE dbo.OperadorInformacion SET oinOperadorActivo = 0 WHERE oinCodigo = '61'