--liquibase formatted sql

--changeset  mgiraldo:01
--comment: 01/11/2016-mgirald-Cambio de nombres en formato estándar, y creación de FK
EXEC sp_rename 'Persona.perfallecido', 'perFallecido', 'COLUMN';
ALTER TABLE Persona DROP COLUMN habitaCasaPropia;
ALTER TABLE empresa ADD CONSTRAINT FK_empresa_empUltimaCajaCompensacion FOREIGN KEY (empUltimaCajaCompensacion) REFERENCES CajaCompensacion;

