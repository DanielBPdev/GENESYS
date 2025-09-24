--liquibase formatted sql

--changeset alquintero:01
--comment:Eliminacion de tabla VariableComunicado para corregir la parametrización de los encabezados de novedades fovis
DELETE FROM VariableComunicado WHERE vcoPlantillaComunicado = (SELECT pco.pcoId FROM PlantillaComunicado pco WHERE pco.pcoEtiqueta LIKE 'HU_PROCESO_325_077') AND vcoOrden IN ('12','13','9','10','11');

--changeset jocorrea:02
--comment:Eliminacion de registros en la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_POSTULADO_FOVIS' AND vapBloque LIKE 'NOVEDAD_%';

--changeset dsuesca:03
--comment:Se adicionan campos en las tablas CuentaAdministradorSubsidio, DetalleSubsidioAsignado y DescuentosSubsidioAsignado 
ALTER TABLE CuentaAdministradorSubsidio ADD casCondicionPersonaAdmin BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado ADD dsaCondicionPersonaBeneficiario BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado ADD dsaCondicionPersonaAfiliado BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado ADD dsaCondicionPersonaEmpleador BIGINT NULL;
ALTER TABLE DescuentosSubsidioAsignado ADD desNombreEntidadDescuento VARCHAR(250) NULL;

--changeset mamonroy:04
--comment:Eliminacion de registros en la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_REEMPLAZANTE_JEFE' AND vapValidacion IN ('VALIDACION_MAYOR_18','VALIDACION_PERSONA_ACTIVA_HOGAR') AND vapProceso = 'NOVEDADES_FOVIS_ESPECIAL' AND vapObjetoValidacion = 'HOGAR';

--changeset jocorrea:05
--comment:Actualizacion de registros en la tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIO_OTROS_DATOS_ADOPTIVO' WHERE novTipoTransaccion = 'CAMBIO_OTROS_DATOS_HIJO_ADOPTIVO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIO_OTROS_DATOS_BIOLOGICO' WHERE novTipoTransaccion = 'CAMBIO_OTROS_DATOS_HIJO_BIOLOGICO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIOS_CONDICION_ESPECIAL_ADOPTIVO' WHERE novTipoTransaccion = 'CAMBIOS_CONDICION_ESPECIAL_HIJO_ADOPTIVO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIOS_CONDICION_ESPECIAL_BIOLOGICO' WHERE novTipoTransaccion = 'CAMBIOS_CONDICION_ESPECIAL_HIJO_BIOLOGICO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIOS_INGRESOS_ADOPTIVO' WHERE novTipoTransaccion = 'CAMBIOS_INGRESOS_HIJO_ADOPTIVO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'CAMBIOS_INGRESOS_BIOLOGICO' WHERE novTipoTransaccion = 'CAMBIOS_INGRESOS_HIJO_BIOLOGICO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'RETIRAR_MIEMBRO_HOGAR_ADOPTIVO' WHERE novTipoTransaccion = 'RETIRAR_MIEMBRO_HOGAR_HIJO_ADOPTIVO';
UPDATE ParametrizacionNovedad SET novTipoTransaccion = 'RETIRAR_MIEMBRO_HOGAR_BIOLOGICO' WHERE novTipoTransaccion = 'RETIRAR_MIEMBRO_HOGAR_HIJO_BIOLOGICO';

--changeset jocorrea:06
--comment:Actualizacion de registros en la tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.fovis.convertidores.RechazarPostulacionNovedadFovis' WHERE novTipoTransaccion = 'RECHAZO_DE_POSTULACION';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.fovis.convertidores.LevantarInhabilidadSancionNovedadFovis' WHERE novTipoTransaccion IN ('LEVANTAR_INHABILIDAD_SANCION_JEFE_HOGAR','LEVANTAR_INHABILIDAD_SANCION_CONYUGE','LEVANTAR_INHABILIDAD_SANCION_BIOLOGICO','LEVANTAR_INHABILIDAD_SANCION_HIJASTRO','LEVANTAR_INHABILIDAD_SANCION_ADOPTIVO','LEVANTAR_INHABILIDAD_SANCION_HERMANO','LEVANTAR_INHABILIDAD_SANCION_PADRE','LEVANTAR_INHABILIDAD_SANCION_MADRE','LEVANTAR_INHABILIDAD_SANCION_ABUELO','LEVANTAR_INHABILIDAD_SANCION_NIETO','LEVANTAR_INHABILIDAD_SANCION_TIO','LEVANTAR_INHABILIDAD_SANCION_SOBRINO','LEVANTAR_INHABILIDAD_SANCION_BISABUELO','LEVANTAR_INHABILIDAD_SANCION_BISNIETO','LEVANTAR_INHABILIDAD_SANCION_PADRE_ADOPTANTE','LEVANTAR_INHABILIDAD_SANCION_YERNO_NUERA','LEVANTAR_INHABILIDAD_SANCION_SUEGRO','LEVANTAR_INHABILIDAD_SANCION_CUNIADO');
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.fovis.convertidores.ProrrogaPostulacionNovedadFovis' WHERE novTipoTransaccion = 'PRORROGA_FOVIS';

--changeset fvasquez:07
--comment:Se modifica campo en la tabla Cartera
ALTER TABLE Cartera ALTER COLUMN carMetodo VARCHAR(8) NULL;
