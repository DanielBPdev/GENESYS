--liquibase formatted sql

--changeset clmarin:01
--comment: Se elimina y adiciona campo en la tabla SolicitudAporte
ALTER TABLE SolicitudAporte DROP COLUMN soaObservacionesSupervisor;
ALTER TABLE SolicitudAporte ADD soaTipoSolicitante varchar(13) NULL;

--changeset clmarin:02
--comment: Se eliminan registros de la tabla VariableComunicado y se actualizan registros en la tabla PlantillaComunicado
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('NTF_GST_INF_PAG_APT');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_GST_INF_PAG_APT';