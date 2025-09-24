IF NOT EXISTS (select * from INFORMATION_SCHEMA.TABLES T WHERE TABLE_NAME = 'ModuloPlantillaComunicado')
BEGIN
    CREATE TABLE dbo.ModuloPlantillaComunicado
    (
        mpcId                      int identity
            primary key,
        mpcModulo                  varchar(100)
            constraint CK_ModuloPlantillaComunicado_mpcModulo
                check ([mpcModulo] = 'CARTERA'),
        mpcPlantillaComunicado     bigint                 not null
            constraint FK_ModuloPlantillaComunicado_mpcPlantillaComunicado
                references PlantillaComunicado,
        mpcCertificacionComunicado bit          default 0 not null,
        mpcBloqueoEnvioComunicado  bit          default 1 not null,
        mpcMetodo                     varchar(200) default NULL
    )

    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 322, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 343, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 328, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 330, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 221, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 324, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 323, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 354, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 313, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 315, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 317, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 326, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 331, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 325, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 136, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 137, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 327, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 314, 0, 1, null);
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', 316, 0, 1, null);
    
    INSERT INTO dbo.Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla) VALUES (N'ENVIO_COMUNICADOS_CARTERA', N'FALSE', 0, N'VALOR_GLOBAL_TECNICO', N'PERMITE INACTIVAR LOS COMUNICADOS UNICAMENTE DEL MODULO DE CARTERA, CON LA FINALIDAD QUE GENESYS ENVÍE TODOS SUS COMUNICADOS EXCEPTO CARTERA, AL SELECCIONAR LA OPCIÓN (INACTIVO) en cartera', N'BOOLEAN', 1);


    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'PENSIONADO', 1, N'NOVEDAD_EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'TRABAJADOR_DEPENDIENTE', 1, N'NOVEDAD_EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'TRABAJADOR_INDEPENDIENTE', 1, N'NOVEDAD_EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'CONYUGE', 0, N'NOVEDAD_INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'CONYUGE', 1, N'NOVEDAD_EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'PENSIONADO', 0, N'NOVEDAD_INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'TRABAJADOR_DEPENDIENTE', 0, N'NOVEDAD_INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
    INSERT INTO dbo.ValidacionProceso (vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa, vapBloque) VALUES (N'VALIDACION_PERSONA_SUMATORIA_SALARIO_PADRE', N'NOVEDADES_PERSONAS_PRESENCIAL', N'ACTIVO', 1, N'TRABAJADOR_INDEPENDIENTE', 0, N'NOVEDAD_INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL');
END