--liquibase formatted sql

--changeset mamonroy:01
--comment: 
INSERT INTO VariableComunicado (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
VALUES ('${ciudadCcf}','Ciudad de Ubicación de la Caja de Compensación','Ciudad CCF',(SELECT pcoId FROM Plantillacomunicado WHERE pcoEtiqueta = 'COM_GEN_CER_AFI'),'CIUDAD_CCF','CONSTANTE',NULL);