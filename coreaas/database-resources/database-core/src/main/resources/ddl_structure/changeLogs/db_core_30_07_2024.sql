--- Creacion de tablas --
-- CORE --
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PersonaExclusionSumatoriaSalario')
begin
    create table PersonaExclusionSumatoriaSalario
        (
        pessId                   bigint IDENTITY NOT NULL,
        pessPersona              bigint not null
            constraint FK_PersonaExclusionSumatoriaSalario_pessPersona
                references dbo.Persona,
        pessEstadoExclusion      bit,
        pessFechaInicioExclusion date,
        pessFechaFinExclusion    date
    )
end

----------------------------------------------------------------------------------
--- Creacion de novedades --- 
-- CORE --
-- ParametrizacionNovedad

IF not exists
(
    SELECT *
    FROM ParametrizacionNovedad
    WHERE novTipoTransaccion = 'EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL'
)
BEGIN

    INSERT INTO ParametrizacionNovedad (novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles) VALUES (N'EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL', N'FRONT', N'com.asopagos.novedades.convertidores.persona.ActualizarPersonaExclusionSumatoriaSalarioPersona', N'GENERAL', N'NOVEDADES_PERSONAS_PRESENCIAL', null);
    INSERT INTO ParametrizacionNovedad (novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles) VALUES (N'INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL', N'FRONT', N'com.asopagos.novedades.convertidores.persona.ActualizarPersonaExclusionSumatoriaSalarioPersona', N'GENERAL', N'NOVEDADES_PERSONAS_PRESENCIAL', null);

    -- ValidacionProceso --
    INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES (N'NOVEDAD_EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL', N'VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'CONYUGE', 1);
    INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES (N'NOVEDAD_INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL', N'VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'CONYUGE', 0);
    ----------------------------------------------------------------------------------
    --- Añadir nuevos Requisitos listas de chequeo --- 
    -- CORE --
    -- CONYUGE
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 67, N'CONYUGE', N'EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El nombre y apellido de los PADREs debe corresponder a los PADREs del afiliado principal y del hermano huérfano y además deben coincidir con los demás documentos soporte<br />-Que la fecha del registro civil de defunción sea válida (no futura)<br />-Emitido por la Registraduría Nacional del Estado Civil', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 67, N'CONYUGE', N'INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El nombre y apellido de los PADREs debe corresponder a los PADREs del afiliado principal y del hermano huérfano y además deben coincidir con los demás documentos soporte<br />-Que la fecha del registro civil de defunción sea válida (no futura)<br />-Emitido por la Registraduría Nacional del Estado Civil', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 84, N'CONYUGE', N'EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en el documento.<br />-Tipo y número de documento de identidad<br />-Nombres y apellidos del trabajador y cónyuge<br />-Fecha de disolución y liquidación de la sociedad conyugal o divorcio.', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 84, N'CONYUGE', N'INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en el documento.<br />-Tipo y número de documento de identidad<br />-Nombres y apellidos del trabajador y cónyuge<br />-Fecha de disolución y liquidación de la sociedad conyugal o divorcio.', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'CONYUGE', N'EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'CONYUGE', N'INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');

END

IF not exists
(
    SELECT *
    FROM ParametrizacionNovedad
    WHERE novTipoTransaccion = 'NOVEDAD_EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL'
)
BEGIN

    INSERT INTO ParametrizacionNovedad (novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles) VALUES (N'EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', N'FRONT', N'com.asopagos.novedades.convertidores.persona.ActualizarPersonaExclusionSumatoriaSalarioPersona', N'GENERAL', N'NOVEDADES_PERSONAS_PRESENCIAL', null);
    INSERT INTO ParametrizacionNovedad (novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles) VALUES (N'INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', N'FRONT', N'com.asopagos.novedades.convertidores.persona.ActualizarPersonaExclusionSumatoriaSalarioPersona', N'GENERAL', N'NOVEDADES_PERSONAS_PRESENCIAL', null);

    -- ValidacionProceso --
    INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES (N'NOVEDAD_EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'PADRES', 1);
    INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES (N'NOVEDAD_INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'PADRES', 0);

    ----------------------------------------------------------------------------------
    --- Añadir nuevos Requisitos listas de chequeo --- 
    -- CORE --
    -- PADRES
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'PADRE', N'EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'PADRE', N'INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'MADRE', N'EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');
    INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES (N'OPCIONAL', 78, N'MADRE', N'INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL', 1, N'Debe coincidir la información de los documentos de identidad (trabajador y cónyuge), con la información registrada en la declaración. <br> -Tipo y número de documento de identidad <br> -Nombres y apellidos del trabajador y su cónyuge <br> -Fecha declarada de fin de convivencia', N'ESTANDAR');

END