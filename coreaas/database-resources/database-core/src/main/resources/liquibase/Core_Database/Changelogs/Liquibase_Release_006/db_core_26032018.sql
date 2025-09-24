--liquibase formatted sql

--changeset jocorrea:01
--comment:Insercion de registros en las tablas DestinatarioComunicado y PrioridadDestinatario
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('NOVEDADES_FOVIS_REGULAR','NTF_RAD_SOL_NVD_FOVIS');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('NOVEDADES_FOVIS_REGULAR','NTF_SOL_NVD_FOVIS');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('NOVEDADES_FOVIS_REGULAR','RCHZ_SOL_NVD_FOVIS_ESCA');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('NOVEDADES_FOVIS_ESPECIAL','RCHZ_SOL_NVD_FOVIS_PROD_NSUB');
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='NOVEDADES_FOVIS_REGULAR' AND des.dcoEtiquetaPlantilla='NTF_RAD_SOL_NVD_FOVIS'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_JEFE_HOGAR'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='NOVEDADES_FOVIS_REGULAR' AND des.dcoEtiquetaPlantilla='NTF_SOL_NVD_FOVIS'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_JEFE_HOGAR'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='NOVEDADES_FOVIS_REGULAR' AND des.dcoEtiquetaPlantilla='RCHZ_SOL_NVD_FOVIS_ESCA'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_JEFE_HOGAR'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='NOVEDADES_FOVIS_ESPECIAL' AND des.dcoEtiquetaPlantilla='RCHZ_SOL_NVD_FOVIS_PROD_NSUB'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_JEFE_HOGAR'),1);

--changeset jzambrano:02
--comment:Se adiciona campo en la tabla Solicitud
ALTER TABLE Solicitud ADD solAnulada BIT NULL;

--changeset borozco:03
--comment:Se adicionan campos en la tabla ActividadCartera
ALTER TABLE ActividadCartera ADD acrFechaCompromiso DATETIME;

--changeset borozco:04
--comment:Se modifica tipo de dato de columna acrFechaCompromiso
ALTER TABLE ActividadCartera ALTER COLUMN acrFechaCompromiso DATE;