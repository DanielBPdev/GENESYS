--liquibase formatted sql

--changeset flopez:01
--comment: Se eliminan registros de la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_TARJETA_ACTIVA' AND vapBloque IN ('NOVEDAD_REPORTE_FALLECIMIENTO_TRABAJADOR_DEPENDIENTE_DEPWEB','NOVEDAD_REPORTE_FALLECIMIENTO_TRABAJADOR_DEPENDIENTE_PRESENCIAL','NOVEDAD_REPORTE_FALLECIMIENTO_TRABAJADOR_DEPENDIENTE_WEB');

--changeset clmarin:02
--comment: Se adiciona campo en la tabla AporteGeneral
ALTER TABLE AporteGeneral ADD apgTipoSolicitante varchar(13) NULL;