--liquibase formatted sql

--changeset  mgiraldo:01
--comment: Creación de UK 
ALTER TABLE RequisitoTipoSolicitante DROP CONSTRAINT UK_RequisitoTipoSolicitante;
ALTER TABLE RequisitoTipoSolicitante ADD CONSTRAINT UK_RequisitoTipoSolicitante UNIQUE (rtsRequisito,rtsClasificacion,rtsTipoTransaccion);

--changeset  jusanchez:02
--comment: 27/10/2016-jusanchez-HU-HU-121-109

ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora ADD soaIdentificadorCartaResultadoGestion VARCHAR(255);

--changeset  mgiraldo:03
--comment: 27/10/2016-mgiraldo-HU-Creación IDX
SET ANSI_PADDING ON
CREATE UNIQUE INDEX IDX_Empresa_empNombreComercial ON Empresa (empNombreComercial) WHERE empNombreComercial IS NOT NULL;
SET ANSI_PADDING OFF