--liquibase formatted sql

--changeset dsuesca:01
--comment: Cambio tipo de datos diferentes de core a core_aud
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacTipoParametrizacion VARCHAR(74);
ALTER TABLE aud.RolAfiliado_aud ALTER COLUMN roaPorcentajePagoAportes NUMERIC(5,5);
ALTER TABLE aud.SolicitudLegalizacionDesembolso_aud ALTER COLUMN sldEstadoSolicitud VARCHAR(60);
ALTER TABLE ParametrizacionGestionCobro ALTER COLUMN pgcTipoParametrizacion VARCHAR(55);
ALTER TABLE aud.CargueArchivoActualizacion_aud ALTER COLUMN caaNombreArchivo VARCHAR(255);
ALTER TABLE aud.CargueArchivoCruceFovis_aud ALTER COLUMN cacNombreArchivo VARCHAR(255);
ALTER TABLE aud.MedioDePago_aud ALTER COLUMN mdpTipo VARCHAR(30);

--changeset mosorio:02
--comment: Insersion tabla parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('TIMER_APO_SUB_FALLECIMIENTO','10d',0,'EJECUCION_TIMER','Tiempo limite que tiene el supervisor de subsidio para verificar la liquidación por fallecimiento');
