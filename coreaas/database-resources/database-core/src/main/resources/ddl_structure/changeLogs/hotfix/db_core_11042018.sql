--liquibase formatted sql

--changeset jvelandia:01
--comment: Insert en tabla PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 1);

--changeset borozco:02
--comment: Se agrega un nuevo campo con relacion a la tabla 
ALTER TABLE NotificacionPersonal ADD ntpCartera BIGINT;
ALTER TABLE NotificacionPersonal ADD CONSTRAINT FK_NotificacionPersonal_ntpCartera FOREIGN KEY (ntpCartera) REFERENCES Cartera(carId);