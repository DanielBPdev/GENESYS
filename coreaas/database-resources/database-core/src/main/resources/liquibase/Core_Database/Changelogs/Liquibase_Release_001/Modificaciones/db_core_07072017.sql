--liquibase formatted sql

--changeset jzambrano:01
--comment: Actualizacion de campo empEstadoEmpleador a NO_FORMALIZADO_CON_INFORMACION
UPDATE Empleador SET empEstadoEmpleador = 'NO_FORMALIZADO_CON_INFORMACION' WHERE empEstadoEmpleador IN('PREAFILIADO', 'NO_FORMALIZADO');

--changeset jzambrano:02
--comment: Se adicionan columnas dtsTipoIdentificacion y dtsNumeroIdentificacion
ALTER TABLE dbo.DatoTemporalSolicitud ADD dtsTipoIdentificacion varchar (20) NULL;
ALTER TABLE dbo.DatoTemporalSolicitud ADD dtsNumeroIdentificacion varchar (16) NULL;
ALTER TABLE dbo.DatoTemporalSolicitud ADD CONSTRAINT FK_DatoTemporalSolicitud_dtsSolicitud FOREIGN KEY (dtsSolicitud) REFERENCES dbo.Solicitud (solId);

