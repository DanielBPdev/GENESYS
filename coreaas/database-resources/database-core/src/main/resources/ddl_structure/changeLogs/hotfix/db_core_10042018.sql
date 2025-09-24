--liquibase formatted sql

--changeset atoro:01
--comment: Se agregan campos a tabla RolAfiliado
ALTER TABLE RolAfiliado ADD roaEnviadoAFiscalizacion BIT;
ALTER TABLE RolAfiliado ADD roaMotivoFiscalizacion VARCHAR(30);
ALTER TABLE RolAfiliado ADD roaFechaFiscalizacion DATE;

--changeset alquintero:02
--comment: Actualizacion tabla ParametrizacionMetodoAsignacion
UPDATE ParametrizacionMetodoAsignacion SET pmaMetodoAsignacion = 'NUMERO_SOLICITUDES', pmaUsuario = NULL WHERE pmaProceso IN('POSTULACION_FOVIS_PRESENCIAL','POSTULACION_FOVIS_WEB');

--changeset jvelandia:03
--comment: Inserts tabla PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_SUBSIDIOS'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL_SUPLENTE'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='OFICINA_PRINCIPAL'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENVIO_DE_CORRESPONDENCIA'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='NOTIFICACION_JUDICIAL'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_SUBSIDIOS'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL_SUPLENTE'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='OFICINA_PRINCIPAL'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENVIO_DE_CORRESPONDENCIA'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_PREVENTIVA_CARTERA' AND des.dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='NOTIFICACION_JUDICIAL'), 2);