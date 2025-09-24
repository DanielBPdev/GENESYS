--liquibase formatted sql

--changeset clmarin:01
--comment: Se eliminan registros de la tabla VariableComunicado y se actualizan registros en la tabla PlantillaComunicado
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('NTF_PAG_DVL_APT');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_PAG_DVL_APT';