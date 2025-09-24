--liquibase formatted sql

--changeset jocorrea:01
--comment:
INSERT INTO ParametrizacionNovedad(novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso) VALUES 
('AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018','BACK','com.asopagos.novedades.fovis.convertidores.AjusteActualizacionValorSFVNovedadFovis','GENERAL','NOVEDADES_FOVIS_REGULAR');	

/*
Validaciones habilitación novedad 31 FOVIS
*/
INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES
('NOVEDAD_AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018', 'VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_NUEVA_URBANA_64_FOVIS', 'NOVEDADES_FOVIS_REGULAR', 'ACTIVO', '1', 'HOGAR', 0),
('NOVEDAD_AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018', 'VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_134', 'NOVEDADES_FOVIS_REGULAR', 'ACTIVO', '1', 'HOGAR', 0);

/*
Validaciones de negocio novedad 31 FOVIS
*/
INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES
('AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018', 'VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_134', 'NOVEDADES_FOVIS_REGULAR', 'ACTIVO', '1', 'HOGAR', 0),
('AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018', 'VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_NUEVA_URBANA_64_FOVIS', 'NOVEDADES_FOVIS_REGULAR', 'ACTIVO', '1', 'HOGAR', 0),
('AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018', 'VALIDACION_REGISTRO_NOVEDAD_ACTUALIZA_SFV', 'NOVEDADES_FOVIS_REGULAR', 'ACTIVO', '1', 'HOGAR', 0);
