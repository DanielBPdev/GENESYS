if not exists (select * from ParametrizacionNovedad where novTipoTransaccion like '%CAMBIAR_DATOS_CARACTERIZACION_POBLACION%')
begin
INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES('CAMBIAR_DATOS_CARACTERIZACION_POBLACION_DEPWEB','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona','GENERAL','NOVEDADES_DEPENDIENTE_WEB',NULL);
INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES('CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona','GENERAL','NOVEDADES_PERSONAS_WEB',NULL);
INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES('CAMBIAR_DATOS_CARACTERIZACION_POBLACION','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona','GENERAL','NOVEDADES_PERSONAS_PRESENCIAL',NULL);
end

if not exists (select * from ParametrizacionNovedad where novTipoTransaccion like '%NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION%')
begin
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'PENSIONADO',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_DEPWEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_DEPWEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_DEPWEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);


INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT INTO ValidacionProceso (vapBloque,vapValidacion, vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES('NOVEDAD_CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB','VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'PENSIONADO',0);
end