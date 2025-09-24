--liquibase formatted sql

--changeset atoro:01
--comment: Se elimina campo de la tabla RegistroEstadoAporte
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroEstadoAporte_reaEstadoInicialSolicitud')) ALTER TABLE RegistroEstadoAporte DROP CONSTRAINT CK_RegistroEstadoAporte_reaEstadoInicialSolicitud;
ALTER TABLE RegistroEstadoAporte DROP COLUMN reaEstadoInicialSolicitud;

--changeset clmarin:02
--comment: Se eliminan registros de la tabla VariableComunicado y se actualizan registros en la tabla PlantillaComunicado
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('NTF_PAG_PAG_SIM');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_PAG_PAG_SIM';