--liquibase formatted sql

--changeset clmarin:01
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--NTF_NO_REC_APO
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCorte}','0','Fecha Corte','Fecha de corte para evaluar periodo en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodos}','0','Periodos','Periodos para los cuales esta en mora','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
--NTF_NO_REC_APO_PER
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCorte}','0','Fecha Corte','Fecha de corte para evaluar periodo en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodos}','0','Periodos','Periodos para los cuales esta en mora','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
--SUS_NTF_NO_PAG
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCorte}','0','Fecha Corte','Fecha de corte para evaluar periodo en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodos}','0','Periodos','Periodos para los cuales esta en mora','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
--SUS_NTF_NO_PAG_PER
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCorte}','0','Fecha Corte','Fecha de corte para evaluar periodo en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodos}','0','Periodos','Periodos para los cuales esta en mora','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));

--changeset clmarin:02
--comment:Se actualizan registros en la tabla PlantillaComunicado - Variables
--NTF_NO_REC_APO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCorte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCorte}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodos}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
--NTF_NO_REC_APO_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCorte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCorte}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodos}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
--SUS_NTF_NO_PAG
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCorte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCorte}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodos}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
--SUS_NTF_NO_PAG_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCorte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCorte}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodos}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
