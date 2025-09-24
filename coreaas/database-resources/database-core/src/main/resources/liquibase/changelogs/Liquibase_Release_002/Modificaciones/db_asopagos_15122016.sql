--liquibase formatted sql

--changeset  abaquero:01 
--comment: Actualización PilaIndicePlanilla pipVersionDocumento pioVersionDocumento a varchar

alter table PilaIndicePlanilla alter column pipVersionDocumento varchar(10);
alter table PilaIndicePlanillaOF alter column pioVersionDocumento varchar(10);

--changeset  mgiraldo:02 
--comment: cambio tamaño 15 ProaIdentificadorAnteEntidadPagadora de RolAfiliado
ALTER TABLE RolAfiliado ALTER COLUMN roaIdentificadorAnteEntidadPagadora VARCHAR(15);

--changeset  mgiraldo:03 
--comment: Eliminación Index único de la Razon Social
DROP INDEX IDX_Persona_RazonSocial ON Persona;
CREATE INDEX IDX_Persona_RazonSocial ON Persona (perRazonSocial);

--changeset  mgiraldo:04 
--comment: Creación de FK_RolAfiliado_roaPagadorAportes
ALTER TABLE RolAfiliado ADD CONSTRAINT FK_RolAfiliado_roaPagadorAportes FOREIGN KEY (roaPagadorAportes) REFERENCES EntidadPagadora;

--changeset  atoro:07
--comment: Creación campo solResultadoProceso Solicitud
ALTER TABLE Solicitud ADD solResultadoProceso varchar (10); 
