--liquibase formatted sql

--changeset dsuesca:01
--comment: Actualizacion PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_RCHZ_DVL_APT';

--changeset clmarin:02
--comment: Actualizacion PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_GST_INF_FLT_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_PAG_APT_TRC';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';

--changeset fvasquez:03
--comment: Insercion PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado WHERE dcoProceso='GESTION_COBRO_MANUAL' AND dcoEtiquetaPlantilla='LIQ_APO_MAN'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre='AFILIADO_PRINCIPAL'),1);