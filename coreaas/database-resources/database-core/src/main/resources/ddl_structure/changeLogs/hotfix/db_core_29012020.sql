--liquibase formatted sql

--changeset mamonroy:01
--comment: 
INSERT INTO VariableComunicado(vcoClave ,vcoDescripcion ,vcoNombre ,vcoPlantillaComunicado ,vcoNombreConstante ,vcoTipoVariableComunicado ,vcoOrden) 
VALUES ('${porcentajeAportes}','Porcentaje de aportes','Porcentaje aportes',(SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'CRT_ENT_PAG'), NULL,'VARIABLE',0);