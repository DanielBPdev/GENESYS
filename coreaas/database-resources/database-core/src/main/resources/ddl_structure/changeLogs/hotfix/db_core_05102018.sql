--liquibase formatted sql

--changeSET fvasquez:01
--comment: 
ALTER TABLE ActividadCartera ALTER column acrCicloAportante BIGINT NULL;
ALTER TABLE ActividadCartera DROP FK_ActividadCartera_acrCicloAportante;
ALTER TABLE AgendaCartera ALTER column agrCicloAportante BIGINT NULL;
ALTER TABLE AgendaCartera DROP FK_AgendaCartera_agrCicloAportante;
ALTER TABLE ActividadCartera add acrCartera BIGINT NULL;
ALTER TABLE AgendaCartera add agrCartera BIGINT NULL;

--changeSET clmarin:02
--comment: 
EXEC sp_rename 'SolicitudAporte.soaAporteGeneral', 'soaRegistroGeneral', 'COLUMN';  

--changeSET mosorio:03
--comment: 
UPDATE PlantillaComunicado SET pcoAsunto = 'Notificación de dispersión de pagos subsidio fallecimiento  programados al admin subsidio'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PRO_ADM_SUB';

--changeSET mosorio:04
--comment: 
UPDATE PlantillaComunicado SET pcoAsunto = 'Notificación de dispersión de pagos subsidio fallecimiento programados al trabajador'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PRO_TRA';
UPDATE PlantillaComunicado SET pcoAsunto = 'Notificación de dispersión de pagos subsidio fallecimiento al trabajador'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_TRA';
UPDATE PlantillaComunicado SET pcoAsunto = 'Notificación de dispersión de pagos subsidio fallecimiento  al admin subsidio'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_ADM_SUB';