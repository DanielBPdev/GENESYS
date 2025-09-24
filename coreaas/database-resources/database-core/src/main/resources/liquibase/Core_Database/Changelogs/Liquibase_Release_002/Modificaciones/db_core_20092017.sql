--liquibase formatted sql

--changeset clmarin:01
--comment: Se eliminan registros de la tabla VariableComunicado y se actualizan registros en la tabla PlantillaComunicado
--RCHZ_AFL_EMP_PRE
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('RCHZ_AFL_EMP_PRE');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'RCHZ_AFL_EMP_PRE';