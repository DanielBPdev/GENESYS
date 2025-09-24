CREATE or ALTER PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores] 
                        @idPersonaEmpleador BIGINT, -- Identificador único de persona
                        @idEmpleador BIGINT, -- Identificador único de empleador
                        @idEmpresa BIGINT, -- Identificador único de empresa
                        @periodoEvaluacion VARCHAR(7), -- Periodo en evaluación YYYY-MM
                        @crearNuevoRegistro BIT, -- 1: Si se va a crear un registro en Cartera. 0: Si se va a actualizar el registro
                        @idCartera BIGINT, -- Identificador de cartera. NULL cuando @crearNuevoRegistro = 1
                        @tablaPersonasConNovedad TablaPersonaIdType READONLY,
                        @activaParametrizacionLC2 BIT, -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 2
                        @activaParametrizacionLC3 BIT -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 3

    AS
    BEGIN TRY
        -- se agrega registro en bitacora de ejecución
        IF EXISTS(SELECT 1
                FROM BitacoraEjecucionSp
                WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores')
            BEGIN
                UPDATE BitacoraEjecucionSp
                SET besUltimoInicio = dbo.getLocalDate()
                WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadorFes'
            END
        ELSE
            BEGIN
                INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
                VALUES ('USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores', dbo.getLocalDate())
            END

        DECLARE @fechaInicioEjecucion DATETIME = dbo.GetLocalDate()

        DECLARE @iCantidadRegistrosCreadosModificados BIGINT = 0

        DECLARE @iCantidadRegistrosCartera BIGINT
        DECLARE @iCantidadRegistrosCarteraAgrupadora BIGINT
        DECLARE @iCantidadRegistrosCarteraDependiente BIGINT

        DECLARE @contadorPeriodos INT
        DECLARE @sumatoriaAportes NUMERIC(19, 5)

        DECLARE @valorAporte NUMERIC(19, 5)

        DECLARE @fechaRetiro DATE
        DECLARE @fechaPeriodoEvaluacion DATE
        DECLARE @fechaActual DATETIME
        DECLARE @fechaDummy DATETIME -- Para pruebas

        DECLARE @lineaCobro VARCHAR(3)
        DECLARE @periodo1MesAtras VARCHAR(7)
        DECLARE @periodo1AnioAtras VARCHAR(7)
        DECLARE @periodo2AniosAtras VARCHAR(7)
        DECLARE @periodoAporte VARCHAR(7)
        DECLARE @periodoCambioEstadoAfiliacion VARCHAR(7)

        DECLARE @periodosCursor AS CURSOR

        DECLARE @deudaPresunta NUMERIC(19, 5)
        DECLARE @deudaPresuntaLC NUMERIC(19, 5)
        DECLARE @deudaPresuntaUnitaria NUMERIC(19, 5)
        DECLARE @aporte NUMERIC(19, 5)

        DECLARE @metodo VARCHAR(8)
        DECLARE @fechaAsignacionAccion DATETIME
        DECLARE @TipoAccionCobro VARCHAR(4)
        DECLARE @numeroOperacionLC1 BIGINT = 0

        DECLARE @iNewId BIGINT
        DECLARE @iRevision BIGINT

        DECLARE @tipoLineaCobro VARCHAR(3)

        DECLARE @dFechaBeneficio DATE
        DECLARE @bDepartamentoEspecial BIT
        DECLARE @porcentajeEmpleador NUMERIC(6, 5)

        -- Fechas y periodos
        SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01', 121)
        SET @periodo1MesAtras = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaPeriodoEvaluacion), 20)
        SET @periodo1AnioAtras = CONVERT(VARCHAR(7), DATEADD(yy, -1, @fechaPeriodoEvaluacion), 20)
        SET @periodo2AniosAtras = CONVERT(VARCHAR(7), DATEADD(yy, -2, @fechaPeriodoEvaluacion), 20)
        SET @fechaActual = dbo.GetLocalDate()

        -- Consulta @fechaDummy, para pruebas
        SELECT @fechaDummy = CONVERT(DATETIME, cnsValor, 120) FROM Constante WHERE cnsNombre = 'FECHA_DUMMY'
        IF @fechaDummy IS NOT NULL
            BEGIN
                SET @fechaActual = @fechaDummy
            END

        --Verifica el porcentaje del empleador
        SELECT @bDepartamentoEspecial = bemPerteneceDepartamento, @dFechaBeneficio = bemFechaVinculacion
        FROM BeneficioEmpleador,
            Beneficio
        WHERE bemBeneficioActivo = 1
        AND bemBeneficio = befId
        AND befTipoBeneficio = 'LEY_1429'
        AND bemEmpleador = @idEmpleador


        SET @porcentajeEmpleador = dbo.UFN_GET_Porcentaje1429(@fechaActual, @dFechaBeneficio, @bDepartamentoEspecial)

    -- Almacena el identificador de las personas que son trabajadores o cotizantes
        DECLARE @tablaTrabajadoresCotizantesSinNov AS TABLE
                                                    (
                                                        perId                        BIGINT,
                                                        trabajadorActivo             BIT,
                                                        trabajadorActivoAlMenosUnDia BIT,
                                                        sinArportePeriodoEvaluacion  BIT,
                                                        cadDeudaPresunta             NUMERIC
                                                    )

        -- ALMACENA EL IDENTIFICADOR DE LAS PERSONAS QUE SON TRBAJADORES O COTIZANTES TEMPORALMENTE
        DROP TABLE IF EXISTS #tablaPosiblesCotizantesSinNovTmp;
        CREATE TABLE #tablaPosiblesCotizantesSinNovTmp
        (
            perId BIGINT
        )

    -- almacena la deuda presunta por cotizante
        DROP TABLE IF EXISTS #deudaPresuntaPorCotizante;
        CREATE TABLE #deudaPresuntaPorCotizante
        (
            perId   BIGINT,
            calculo BIGINT
        )

    -- Almacena los aportes del periodos por empresa/aportante
        DECLARE @tablaAportesVigentes TABLE
                                    (
                                        perId           BIGINT,
                                        aporte          NUMERIC(19, 5),
                                        deudaIndividual NUMERIC(19, 5),
                                        tipoAporte      VARCHAR(37),
                                        DiasCotizados   NUMERIC(19, 5),
                                        SalarioBasico   NUMERIC(19, 5),
                                        tarifa          NUMERIC(5, 5)
                                    )

        -- temporal para las carteras creadas
        DECLARE @carteraCreada AS TABLE
                                (
                                    carDeudaPresunta         NUMERIC(19, 5),
                                    carDeudaPresuntaUnitaria NUMERIC(19, 5),
                                    carEstadoCartera         varchar(6),
                                    carEstadoOperacion       varchar(10),
                                    carFechaCreacion         datetime,
                                    carPersona               bigint,
                                    carMetodo                varchar(8),
                                    carPeriodoDeuda          date,
                                    carRiesgoIncobrabilidad  varchar(48),
                                    carTipoAccionCobro       varchar(4),
                                    carTipoDeuda             varchar(11),
                                    carTipoLineaCobro        varchar(3),
                                    carTipoSolicitante       varchar(13),
                                    carFechaAsignacionAccion datetime,
                                    carUsuarioTraspaso       varchar(255),
                                    carId                    bigint
                                )

        -- temporal para las carteras dependientes creadas
        DECLARE @carteraDependienteCreada AS TABLE
                                            (
                                                cadDeudaPresunta   numeric(19, 5),
                                                cadEstadoOperacion varchar(10),
                                                cadCartera         bigint,
                                                cadPersona         bigint,
                                                cadDeudaReal       numeric(19, 5),
                                                cadAgregadoManual  bigint,
                                                cadId              bigint,
                                                cdcAccion          VARCHAR(1)
                                            )

        DECLARE @carteraAgrupadoraCreada AS TABLE
                                            (
                                                cagNumeroOperacion bigint,
                                                cagCartera         bigint,
                                                cagId              bigint
                                            )

        -- Almacena los aportes de la empresa
        DECLARE @tablaAportesTemp AS TABLE
                                    (
                                        apdPersona                     BIGINT,
                                        apdAporteObligatorio           NUMERIC(19, 5),
                                        apdSalarioBasico               NUMERIC(19, 5),
                                        apdEstadoRegistroAporteArchivo VARCHAR(60),
                                        apdEstadoAporteAjuste          VARCHAR(50),
                                        apgPeriodoAporte               VARCHAR(7),
                                        apgEstadoAporteAportante       VARCHAR(40),
                                        apdDiasCotizados               NUMERIC(19, 5),
                                        apdtarifa                      NUMERIC(19, 5)
                                    )

        -- En esta tabla se agregan las novedades que no se identificaron en el primer punto por no tener novedad detalle
        -- y la fecha se restringe en este caso con roaFechaRetiro
        DECLARE @novedadRetiro AS TABLE
                                (
                                    norPersona BIGINT
                                )

        BEGIN TRY


            --SELECT PRUEBA ,@idEmpresa,@periodoEvaluacion, @periodo1MesAtras,'@idEmpresa,@periodoEvaluacion, @periodo1MesAtras'
            -- Se consultan los aportes de la empresa de los periodos indicados
            INSERT INTO @tablaAportesTemp(apdPersona, apdAporteObligatorio, apdSalarioBasico,
                                        apdEstadoRegistroAporteArchivo, apdEstadoAporteAjuste,
                                        apgPeriodoAporte, apgEstadoAporteAportante, apdDiasCotizados, apdtarifa)
            SELECT apdPersona,
                apdAporteObligatorio,
                apdSalarioBasico,
                apdEstadoRegistroAporteArchivo,
                apdEstadoAporteAjuste,
                apgPeriodoAporte,
                apgEstadoAporteAportante,
                apdDiasCotizados,
                apdTarifa
            FROM AporteDetallado apd WITH (NOLOCK)
                    JOIN AporteGeneral apg WITH (NOLOCK) ON apd.apdAporteGeneral = apg.apgId
            WHERE apg.apgEmpresa = @idEmpresa
            AND apd.apdEstadoCotizante = 'ACTIVO'-----CAMBIO OLGA VEGA 20221215
            AND apg.apgPeriodoAporte IN (@periodoEvaluacion, @periodo1MesAtras)
            AND apd.apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'


        END TRY
        BEGIN CATCH
            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
            VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores Sgft:Consulta de aportes',
                    ERROR_MESSAGE());
            THROW;
        END CATCH


        BEGIN TRY
            INSERT INTO @tablaAportesVigentes (perId, aporte, deudaIndividual, tipoAporte)
            SELECT apdPersona,
                apdAporteObligatorio,
                0,
                ISNULL(apdEstadoRegistroAporteArchivo, '')

            FROM @tablaAportesTemp
            WHERE apgPeriodoAporte = @periodoEvaluacion
            AND apgEstadoAporteAportante = 'VIGENTE'
            AND apdEstadoAporteAjuste = 'VIGENTE'


        END TRY
        BEGIN CATCH
            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
            VALUES (dbo.getLocalDate(),
                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Selección de aportes vigentes para trabajadores/cotizantes',
                    ERROR_MESSAGE());
            THROW;
        END CATCH

        BEGIN TRY
            IF @crearNuevoRegistro = 1
                BEGIN
                    INSERT INTO @novedadRetiro (norPersona)
                    SELECT DISTINCT snpPersona
                    FROM SolicitudNovedadPersona
                            INNER JOIN SolicitudNovedad ON snoId = snpSolicitudNovedad
                            INNER JOIN Solicitud ON solId = snoSolicitudGlobal
                            INNER JOIN Persona ON perId = snpPersona
                            INNER JOIN Afiliado ON afiPersona = perId
                            INNER JOIN RolAfiliado ON roaAfiliado = afiId
                    WHERE solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
                    AND roaEmpleador = @idEmpleador
                    --AND CONVERT(VARCHAR(7), roaFechaRetiro, 120) = @periodoEvaluacion
                    AND ((CONVERT(VARCHAR(7), solFechaRadicacion, 120) between @periodo1MesAtras and @periodoEvaluacion))
			UNION 
                    select snpPersona
                    from Solicitud s
                    inner join SolicitudNovedad sn on (sn.snoSolicitudGlobal = s.solId)
                    inner join SolicitudNovedadPila snp on (snp.spiSolicitudNovedad = sn.snoId)
                    inner join SolicitudNovedadPersona snpe on (snpe.snpSolicitudNovedad = sn.snoId)
                    inner join RolAfiliado ra on ra.roaId = snpe.snpRolAfiliado
                    inner join Afiliado a on a.afiId = ra.roaAfiliado
                    where 
                    ra.roaEmpleador =  @idEmpleador
                    and (solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE') )
                    and (s.solCanalRecepcion in ('PILA'))
                    AND ((CONVERT(VARCHAR(7), solFechaRadicacion, 120) between @periodo1MesAtras and @periodoEvaluacion))                       
                END
            ELSE --@crearNuevoRegistro = 0
                BEGIN
                    INSERT INTO @novedadRetiro (norPersona)
                    SELECT DISTINCT snpPersona
                    FROM SolicitudNovedadPersona
                            INNER JOIN SolicitudNovedad ON snoId = snpSolicitudNovedad
                            INNER JOIN Solicitud ON solId = snoSolicitudGlobal
                            INNER JOIN Persona ON perId = snpPersona
                            INNER JOIN Afiliado ON afiPersona = perId
                            INNER JOIN RolAfiliado ON roaAfiliado = afiId
                    WHERE solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
                    AND roaEmpleador = @idEmpleador
                    AND ((CONVERT(VARCHAR(7), solFechaRadicacion, 120) between @periodo1MesAtras and @periodoEvaluacion))
			UNION 
                    select snpPersona
                    from Solicitud s
                    inner join SolicitudNovedad sn on (sn.snoSolicitudGlobal = s.solId)
                    inner join SolicitudNovedadPila snp on (snp.spiSolicitudNovedad = sn.snoId)
                    inner join SolicitudNovedadPersona snpe on (snpe.snpSolicitudNovedad = sn.snoId)
                    inner join RolAfiliado ra on ra.roaId = snpe.snpRolAfiliado
                    inner join Afiliado a on a.afiId = ra.roaAfiliado
                    where 
                    ra.roaEmpleador =  @idEmpleador
                    and (solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE') )
                    and (s.solCanalRecepcion in ('PILA'))
                    AND ((CONVERT(VARCHAR(7), solFechaRadicacion, 120) between @periodo1MesAtras and @periodoEvaluacion))              
                END
        END TRY
        BEGIN CATCH
            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
            VALUES (dbo.getLocalDate(),
                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Selección de novedades de retiro para trabajadores/cotizantes',
                    ERROR_MESSAGE());
            THROW;
        END CATCH


        IF @crearNuevoRegistro = 1 AND @porcentajeEmpleador > 0 -- Ejecutado desde USP_ExecuteCARTERACrearCartera
            BEGIN
                BEGIN TRY
                    -- Consulta si el empleador tuvo impago el mes anterior al periodo en evaluación
                    SELECT @deudaPresunta = ISNULL(car.carDeudaPresunta, 0)
                    FROM Cartera car
                    WHERE car.carPersona = @idPersonaEmpleador
                    AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodo1MesAtras
                    AND car.carTipoSolicitante = 'EMPLEADOR'
                    AND car.carEstadoOperacion = 'VIGENTE'

                    IF NOT EXISTS(SELECT car.carDeudaPresunta
                                FROM Cartera car
                                WHERE car.carPersona = @idPersonaEmpleador
                                    AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodo1MesAtras
                                    AND car.carTipoSolicitante = 'EMPLEADOR'
                                    AND car.carEstadoOperacion = 'VIGENTE')
                        BEGIN
                            SET @deudaPresunta = 0
                        END
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Cálculo monto de deuda presunta',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH

                BEGIN TRY

                    -- HU-223-169
                    -- El valor de la deuda presunta se distribuirán en trabajadores que:
                    -- a) Se tenga activos en al menos un día en el periodo en estudio.
                    -- Considerar aquellos que No tengan para el periodo una novedad vigente del tipo:
                    -- 		Incapacidad (IGE, IRL)
                    -- 		Licencia de Maternidad (LMA)
                    -- 		Suspensión Temporal del Contrato (SLN).
                    -- SE DEBE AJUSTAR POR QUE ESTAN INGRESANDO TRABAJADORES ACTIVOS
                    INSERT INTO @tablaTrabajadoresCotizantesSinNov (perId, trabajadorActivoAlMenosUnDia,
                                                                    trabajadorActivo, sinArportePeriodoEvaluacion)
                    SELECT afi.afiPersona,
                        1,
                        CASE WHEN roa.roaEstadoAfiliado = 'ACTIVO' THEN 1 ELSE 0 END,
                        0
                    FROM RolAfiliado roa
                            JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
                            LEFT JOIN @tablaPersonasConNovedad AS exclusiones ON afi.afiPersona = exclusiones.perId
                    WHERE exclusiones.perId IS NULL
                    AND roa.roaEmpleador = @idEmpleador
                    AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
                    AND roa.roaEstadoAfiliado IN ('ACTIVO', 'INACTIVO')

                    -- Activos al menos un día en el periodo
                    AND (
                        -- se activó en el periodo de estudio
                                CONVERT(DATE, @periodoEvaluacion + '-01', 121) =
                                DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaAfiliacion), 0)
                            -- aún activo
                            OR (roa.roaFechaRetiro IS NULL AND
                                EOMONTH(CONVERT(DATE, @periodoEvaluacion + '-01', 121)) >=
                                DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaAfiliacion), 0))
                            -- se retiró en el mes de estudio
                            OR CONVERT(DATE, @periodoEvaluacion + '-01', 121) =
                            DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaRetiro), 0)
                        )


    -- Almacena el identificador de las personas que son trabajadores o cotizantes
                    DECLARE @tablaPosiblesCotizantesSinNov AS TABLE
                                                            (
                                                                perId BIGINT
                                                            )


                    -- Personas con aporte del mes anterior al evaluado, sin aporte en el mes evaluado y sin novedad para la empresa evaluada
                    INSERT INTO @tablaPosiblesCotizantesSinNov (perId)
                    SELECT DISTINCT(cotizantesMesAnterio.apdPersona)
                    FROM (
                            SELECT apdPersona
                            FROM @tablaAportesTemp
                            WHERE apgPeriodoAporte = @periodo1MesAtras
                        ) AS cotizantesMesAnterio
                            LEFT JOIN (
                        SELECT apdPersona
                        FROM @tablaAportesTemp
                        WHERE apgPeriodoAporte = @periodoEvaluacion
                    ) AS cotizantesPeriodoEvaluado
                                    ON cotizantesMesAnterio.apdPersona = cotizantesPeriodoEvaluado.apdPersona
                            LEFT JOIN @tablaPersonasConNovedad AS exclusiones
                                    ON cotizantesMesAnterio.apdPersona = exclusiones.perId
                    WHERE cotizantesPeriodoEvaluado.apdPersona IS NULL -- Personas con aporte en el mes anterior pero sin aporte en el mes evaluado
                    AND exclusiones.perId IS NULL
                    -- Personas con aporte en el mes anterior pero sin noveades en el mes evaluado

                    -- HU-223-169
                    -- El valor de la deuda presunta se distribuirán trabajadores que:
                    -- b) Se hayan recibidos pagos de aportes del periodo anterior y no tengan
                    -- aportes para el periodo en estudio y tampoco tengan una novedad registrada o guardada.

                    INSERT INTO @tablaTrabajadoresCotizantesSinNov (perId, trabajadorActivoAlMenosUnDia,
                                                                    trabajadorActivo, sinArportePeriodoEvaluacion)
                    SELECT tcot.perId, 0, 0, 1
                    FROM @tablaPosiblesCotizantesSinNov tcot
                            LEFT JOIN @tablaTrabajadoresCotizantesSinNov ttcs ON ttcs.perId = tcot.perId
                    WHERE ttcs.perId IS NULL

                    -- Se marcan las personas que ya se habian consultado como activas al menos un día
    -- indicando que no tienen aporte en el periodo evaluado
                    UPDATE ttcs
                    SET sinArportePeriodoEvaluacion = 1
                    FROM @tablaPosiblesCotizantesSinNov tcot
                            INNER JOIN @tablaTrabajadoresCotizantesSinNov ttcs ON ttcs.perId = tcot.perId

                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Selección de trabajadores/cotizantes',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH

                ----inicio reestruturacion
                ----tabla que trae los trabajadores de medio tiempo o pordias que no deben entrar en cartera 20230310
                IF OBJECT_ID('tempdb.dbo.#Trabpordias', 'U') IS NOT NULL
                DROP TABLE #Trabpordias;
    
                CREATE TABLE #Trabpordias (	
                                            idempleador BIGINT,
                                            TipoIdEmp VARCHAR(20),
                                            IdEmp VARCHAR(20),					 
                                            perIdtrab BIGINT,	
                                            TipoIdtrab VARCHAR(20),
                                            Idtrab VARCHAR(20),										
                                            shard VARCHAR(500)
                                        )

                DECLARE @sql NVARCHAR(max)

                SET @sql ='SELECT 0,regTipoIdentificacionAportante,
                                regNumeroIdentificacionAportante, 
                                0,redTipoIdentificacionCotizante,
                                redNumeroIdentificacionCotizante
                            FROM staging.registrogeneral rg WITH(NOLOCK)
                        INNER JOIN staging.registrodetallado rd WITH(NOLOCK)
                                ON rg.regid = rd.redregistrogeneral
                            WHERE rg.regPeriodoAporte = @periodoEvaluacion
                            AND rd.redTipoCotizante = 51
                        GROUP BY regTipoIdentificacionAportante,
                                regNumeroIdentificacionAportante,regPeriodoAporte,
                                redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante '
            
                INSERT INTO #Trabpordias
                    EXEC sp_execute_remote N'PilaReferenceData',
                            @sql,
                            N'@periodoEvaluacion VARCHAR(7)',
                            @periodoEvaluacion = @periodoEvaluacion 

                        UPDATE X SET idempleador = em.empid ,perIdtrab = pt.perid
                        FROM #Trabpordias X 
                    INNER JOIN persona pe 
                            ON x.TipoIdEmp = pe.perTipoIdentificacion 
                        AND x.IdEmp = pe.perNumeroIdentificacion
                    INNER JOIN empresa e 
                            ON e.empPersona = pe.perid 
                    INNER JOIN empleador em 
                            ON e.empid = em.empEmpresa
                    INNER JOIN Persona pt 
                            ON x.TipoIdtrab = pt.perTipoIdentificacion 
                        AND x.Idtrab = pt.perNumeroIdentificacion
                        WHERE em.empid = @idEmpleador

            --		SELECT * FROM #Trabpordias

                --select '@tablaTrabajadoresCotizantesSinNov', * from @tablaTrabajadoresCotizantesSinNov
            END
        DECLARE @calculodeudapresunta TABLE
                                    (
                                        primary_key      INT IDENTITY (1,1) NOT NULL,
                                        apdSalarioBasico NUMERIC(19, 5),
                                        apgPeriodoAporte VARCHAR(7),
                                        apdPersona       BIGINT,
                                        roaEmpleador     BIGINT,
                                        apdDiasCotizados INT,
                                        apdTarifa        NUMERIC(19, 5),
                                        roaId            BIGINT
                                    )


        INSERT INTO @calculodeudapresunta
        SELECT *
        FROM (
                SELECT CASE
                            WHEN apgx.apgPeriodoAporte = MAX(apgx.apgPeriodoAporte)
                                                            over (partition by apdx.apdpersona, roaEmpleador )
                                THEN CONVERT(NUMERIC, (MAX(apdx.apdSalarioBasico)))
                            ELSE NULL END          AS apdSalarioBasico,
                        apgPeriodoAporte,
                        apdx.apdPersona,
                        roaEmpleador,
                        sum(apdx.apdDiasCotizados) as apdDiasCotizados,
                        max(apdx.apdTarifa)        as apdTarifa,
                        roaId
                FROM AporteGeneral as apgx WITH (NOLOCK)
                        INNER JOIN AporteDetallado as apdx WITH (NOLOCK)
                                    ON apdx.apdaportegeneral = apgx.apgid
                        INNER JOIN Persona as px WITH (NOLOCK) ON px.perid = apdx.apdPersona
                        INNER JOIN afiliado as afix WITH (NOLOCK) ON afix.afiPersona = px.perId
                        INNER JOIN RolAfiliado WITH (NOLOCK) ON roaAfiliado = afix.afiId
                        INNER JOIN Empleador empl WITH (NOLOCK) ON empl.empid = roaEmpleador
                        INNER JOIN Empresa empr WITH (NOLOCK) ON empr.empId = empl.empempresa
                    AND apgx.apgEmpresa = empr.empid
                WHERE roaEmpleador = @idEmpleador
                AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
                -- AND apgx.apgPeriodoAporte = @periodoEvaluacion ----cambio olga vega 20230220
                GROUP BY apdx.apdPersona, roaEmpleador, apgPeriodoAporte,
                        roaId
            ) x
        WHERE x.apdSalarioBasico IS NOT NULL

        UNION
        ----LOS trabajadores QUE NUNCA HAN TENIDO APORTES
        SELECT roaValorSalarioMesadaIngresos AS apdSalarioBasico,
            @periodoEvaluacion            AS Periodo,
            px.perId,
            roaEmpleador,
            30                            as apdDiasCotizados,
            0.04                          as apdTarifa,
            roaId
        FROM Persona as px WITH (NOLOCK)
                INNER JOIN afiliado as afix WITH (NOLOCK) ON afix.afiPersona = px.perId
                INNER JOIN RolAfiliado WITH (NOLOCK)
                            ON roaAfiliado = afix.afiId
                                AND roaEstadoAfiliado = 'ACTIVO'
                INNER JOIN Empleador empl WITH (NOLOCK) ON empl.empid = roaEmpleador
                INNER JOIN Empresa empr WITH (NOLOCK) ON empr.empId = empl.empempresa
                LEFT JOIN AporteGeneral as apgx WITH (NOLOCK) ON empr.empid = apgx.apgEmpresa
                LEFT JOIN AporteDetallado as apdx WITH (NOLOCK) ON apdx.apdaportegeneral = apgx.apgid
        WHERE roaEmpleador = @idEmpleador
        AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
        AND apdx.apdId IS NULL


        --         SELECT '@calculodeudapresunta',* FROM @calculodeudapresunta
        --         SELECT '@tablaPersonasConNovedad', * FROM @tablaPersonasConNovedad
        --         SELECT '@tablaAportesVigentes' ,* FROM @tablaAportesVigentes
                --SELECT '@tablaAportesTemp',* FROM @tablaAportesTemp
        --         SELECT '@tablaTrabajadoresCotizantesSinNov', * FROM @tablaTrabajadoresCotizantesSinNov
                --select '@tablaAportesTemp', * from @tablaAportesTemp

    ---CALCULO DEUDA PRESUNTA LC1

        DECLARE @loop_counter4 INT, @item_category_counter4 INT ,
            @Perid bigint, @calculo NUMERIC,
            @calculoEmpleador NUMERIC

        DECLARE @Salario numeric(19, 5) ,
            @periodo varchar(7) ,
            @diascotizados INT ,
            @tarifa NUMERIC(19, 5),
            @roaid BIGINT

        SET @calculoEmpleador = 0

        SET @loop_counter4 = (SELECT COUNT(*) FROM @calculodeudapresunta);
        SET @item_category_counter4 = 1;

        WHILE @loop_counter4 > 0 AND @item_category_counter4 <= @loop_counter4
            BEGIN
                SELECT @Salario = apdSalarioBasico,
                    @periodo = apgPeriodoAporte,
                    @perid = apdPersona,
                    @diascotizados = apdDiasCotizados,
                    @tarifa = apdTarifa,
                    @roaid = roaId
                FROM @calculodeudapresunta
                WHERE primary_key = @item_category_counter4

                SET @calculo = 0
                SELECT @calculo =
                    ROUND(CONVERT(decimal, (ISNULL(@Salario, r.roaValorSalarioMesadaIngresos) / 30) *
                                            ISNULL(@diascotizados, 30) * isnull(@tarifa, 0.04)), 3)
                FROM persona p WITH (NOLOCK)
                        INNER JOIN Afiliado WITH (NOLOCK) on afiPersona = p.perId
                        INNER JOIN RolAfiliado r WITH (NOLOCK) on r.roaAfiliado = afiId
                        INNER JOIN @tablaTrabajadoresCotizantesSinNov TSN
                                    ON tsn.perId = p.perId ----CAMBIO 20230222
                        LEFT JOIN @tablaAportesVigentes av ON av.perId = p.perId ----CAMBIO 20230222
                WHERE r.roaId = @roaid
                AND roaEmpleador = @idEmpleador

                UPDATE @tablaTrabajadoresCotizantesSinNov
                SET cadDeudaPresunta = ISNULL(@calculo, 0)
                WHERE perId = @PerId


                -- se llena la variable que calcula la deuda presunta por empleador

                ---	SET @calculoEmpleador = @calculoEmpleador + @calculo CALCULAR EN CADA LINEA

                SET @item_category_counter4 = @item_category_counter4 + 1


                SET @calculo = 0
                SET @Salario = 0
                SET @diascotizados = 0
                SET @tarifa = 0
                SET @roaid = 0

            END--FIN DEL CALCULO DE LA DEUDAPRESUNTA DE CADA TRABAJADOR

    --  SELECT 'DESPUES@tablaTrabajadoresCotizantesSinNov', * FROM @tablaTrabajadoresCotizantesSinNov

        ----- CALCULO PARA LINEA DE COBRO LC1


        DECLARE @LC1 TABLE
                    (
                        primary_key      INT IDENTITY (1,1) NOT NULL,
                        apdPersona       BIGINT,
                        cadDeudaPresunta NUMERIC(19, 5)
                    )


                INSERT INTO @LC1
                    SELECT presunta.apdPersona, sinnov.cadDeudaPresunta
                    FROM @calculodeudapresunta AS presunta
                INNER JOIN @tablaTrabajadoresCotizantesSinNov AS sinnov
                        ON presunta.apdPersona = sinnov.perId
                LEFT JOIN @tablaAportesVigentes AS Aportes
                        ON sinnov.perId = aportes.perId
                    WHERE presunta.roaEmpleador = @idEmpleador
                        AND Aportes.perId IS NULL
                GROUP BY presunta.apdPersona, sinnov.cadDeudaPresunta


    --   SELECT '@LC1',* FROM @LC1

        DECLARE @loop_counter INT, @item_category_counter INT

        SET @calculoEmpleador = 0

        SET @loop_counter = (SELECT COUNT(*) FROM @LC1);
        SET @item_category_counter = 1;

        WHILE @loop_counter > 0 AND @item_category_counter <= @loop_counter
            BEGIN
                SELECT @perid = apdPersona,
                    @calculo = cadDeudaPresunta
                FROM @LC1
                WHERE primary_key = @item_category_counter


                -- se llena la variable que calcula la deuda presunta por empleador
                SET @calculoEmpleador = @calculoEmpleador + @calculo


                SET @item_category_counter = @item_category_counter + 1

            END


        ----INSERTAR CARTERA SOLO PARA LC1
        IF (SELECT COUNT(*) FROM @LC1) > 0
            BEGIN
                -- Se consulta si ya existe una cartera con el tipo de LC1 vigente se hereda el valor de los campos
                -- carTipoAccionCobro, carFechaAsignacionAccion, carMetodo
                IF EXISTS(SELECT 1
                        FROM Cartera
                        WHERE carTipoLineaCobro = 'LC1'
                            AND carTipoSolicitante = 'EMPLEADOR'
                            AND carPersona = @idPersonaEmpleador
                            AND carEstadoOperacion = 'VIGENTE')
                    BEGIN
                        -- En este caso, el aportante ya se encuentra en una acción de cobro sobre la misma línea de cobro
                        SELECT TOP 1 @TipoAccionCobro = carTipoAccionCobro,
                                    @fechaAsignacionAccion = carFechaAsignacionAccion,
                                    @metodo = carMetodo,
                                    @numeroOperacionLC1 = cagNumeroOperacion
                        FROM Cartera
                                INNER JOIN CarteraAgrupadora ON carId = cagCartera
                        WHERE carTipoLineaCobro = 'LC1'
                        AND carTipoSolicitante = 'EMPLEADOR'
                        AND carPersona = @idPersonaEmpleador
                        AND carEstadoOperacion = 'VIGENTE'
                    END
                ELSE
                    BEGIN
                        SELECT @TipoAccionCobro = NULL,
                            @fechaAsignacionAccion = NULL,
                            @metodo = pcr.pcrMetodo,
                            @numeroOperacionLC1 = 0
                        FROM ParametrizacionCriterioGestionCobro pcr
                        WHERE pcr.pcrLineaCobro = 'LC1'
                        AND pcr.pcrAccion = 'AUTOMATICA'
                        AND pcr.pcrActiva = 1
                    END

                -- se crea la cartera LC1 temporal
                BEGIN TRY
                    -- se toma el siguiente ID de secuencia
                    EXEC USP_GET_GestorValorSecuencia 1, 'Sec_cartera', @iNewId OUTPUT

                    INSERT INTO @carteraCreada(carDeudaPresunta, carEstadoCartera,
                                            carEstadoOperacion, carFechaCreacion, carPersona,
                                            carPeriodoDeuda, carTipoDeuda,
                                            carTipoLineaCobro, carTipoSolicitante,
                                            carTipoAccionCobro, carFechaAsignacionAccion,
                                            carMetodo, carId)
                    SELECT @calculoEmpleador,
                        'MOROSO',
                        'VIGENTE',
                        @fechaActual,
                        @idPersonaEmpleador,
                        @fechaPeriodoEvaluacion,
                        'POR_MORA',
                        'LC1',
                        'EMPLEADOR',
                        @TipoAccionCobro,
                        @fechaAsignacionAccion,
                        @metodo,
                        @iNewId
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear Cartera LC1',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH


                -- se crea cartera dependiente LC1 temporal para todos los trabajadores / aportantes sin novedades
                BEGIN TRY
                    SET @idCartera = @iNewId
                    BEGIN
                        INSERT INTO @carteraDependienteCreada (cadDeudaPresunta,
                                                            cadEstadoOperacion,
                                                            cadCartera, cadPersona,
                                                            cadDeudaReal,
                                                            cadId)
                        SELECT x.cadDeudaPresunta,
                            'VIGENTE',
                            @idCartera,
                            perId,
                            0,
                            NEXT VALUE FOR Sec_CarteraDependiente cadId
                        FROM @tablaTrabajadoresCotizantesSinNov x
                INNER JOIN @LC1 lc1 ON  lc1.apdPersona = x.perId
                        WHERE perId IN (SELECT apdPersona FROM @calculodeudapresunta)
                        ---prueba 20220707
                        AND x.cadDeudaPresunta > 0

                        -- Se guarda valor original de la deuda presunta unitara caso LC1 y LC2
                        UPDATE @carteraCreada
                        SET carDeudaPresuntaUnitaria = @calculoEmpleador
                        WHERE carId = @idCartera

                    END
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC1',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH
            END

        --  INSERTAR LC2

        BEGIN
            IF @activaParametrizacionLC2 = 1
                BEGIN
                    DECLARE @LC2 TABLE
                                (
                                    primary_key   INT IDENTITY (1,1) NOT NULL,
                                    apdPersona    BIGINT,
                                    SalarioBasico NUMERIC(19, 5),
                                    aporte        NUMERIC(19, 5)
                                )


                    INSERT INTO @LC2
                    SELECT presunta.apdPersona,
                        presunta.apdSalarioBasico,
                        Aportes.aporte
                        -- '0.04' AS tarifa
                    FROM @calculodeudapresunta AS presunta
            INNER JOIN @tablaTrabajadoresCotizantesSinNov AS sinnov
                    ON presunta.apdPersona = sinnov.perId
            INNER JOIN @tablaAportesVigentes AS Aportes
                    ON sinnov.perId = aportes.perId
                    AND Aportes.tipoAporte = 'OK'
                WHERE presunta.roaEmpleador = @idEmpleador
                    AND trabajadorActivoAlMenosUnDia = 1
                    AND apdPersona NOT IN (SELECT perIdtrab FROM #Trabpordias WHERE idempleador =  @idEmpleador )---ES ES PARA LOS TRAB DE MEDIO TIEMPO 
                GROUP BY presunta.apdPersona,
                            Aportes.aporte,
                            presunta.apdSalarioBasico, cadDeudaPresunta
                    HAVING (sinnov.cadDeudaPresunta - Aportes.aporte) > 0

                    UNION

                    SELECT Aportes.perId,
                        Aportes.aporte,
                        Aportes.SalarioBasico
                    FROM @tablaAportesVigentes Aportes
            INNER JOIN @tablaPersonasConNovedad pn ON Aportes.perId = pn.perId
            INNER JOIN SolicitudNovedadPersona snp ON snp.snpPersona = pn.perId
            INNER JOIN SolicitudNovedad sno ON snoId = snpSolicitudNovedad
            INNER JOIN NovedadDetalle nop ON nopSolicitudNovedad = snoId
            INNER JOIN Solicitud S on S.solId = sno.snoSolicitudGlobal
                    WHERE s.solTipoTransaccion IN
                        ('VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL',
                        'VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL')

    -- SELECT '@LC2',* FROM @LC2


                    DECLARE @loop_counter2 INT, @item_category_counter2 INT

                
                    ---CALCULO DEUDA PRESUNTA LC2

                    DECLARE
                        @peridlc2 BIGINT,
                        @item_category_counterlc2 INT ,
                        @deudaIndividuallc2 NUMERIC(19, 5) = 0,
                        @DeudaEmpleadorlc2 NUMERIC(19, 5) = 0,
                        @Salariolc2 NUMERIC(19, 5)


                    SET @loop_counter2 = (SELECT COUNT(*) FROM @LC2);
                    SET @item_category_counter2 = 1;

                    WHILE @loop_counter2 > 0 AND @item_category_counter2 <= @loop_counter2
                        BEGIN
                            SELECT @peridlc2 = apdPersona,
                                @Salariolc2 = SalarioBasico,
                                @aporte = SUM(aporte)
                            FROM @LC2
                            WHERE primary_key = @item_category_counter2
                            GROUP BY apdPersona, SalarioBasico


                            SET @deudaIndividuallc2 = (@Salariolc2 * 0.04000) - @aporte

                            SET @deudaIndividuallc2 = CASE
                                                        WHEN @deudaIndividuallc2 < 0 THEN 0
                                                        ELSE @deudaIndividuallc2 END

                            UPDATE @tablaAportesVigentes
                            SET deudaIndividual = @deudaIndividuallc2
                            WHERE perId = @Peridlc2

                            -- se llena la variable que calcula la deuda presunta por empleador
                            SET @DeudaEmpleadorlc2 = @DeudaEmpleadorlc2 + @deudaIndividuallc2
                            
                        ---	SELECT @DeudaEmpleadorlc2,'@DeudaEmpleadorlc2'

                            SET @item_category_counter2 = @item_category_counter2 + 1

                        END
                END
        END
        IF (SELECT COUNT(*) FROM @LC2) > 0
            BEGIN
                -- se crea la cartera LC2 temporal
                BEGIN TRY
                    -- se toma el siguiente ID de secuencia
                    EXEC USP_GET_GestorValorSecuencia 1, 'Sec_cartera', @iNewId OUTPUT

                    --Mantis 259601 Se consulta si ya existe una cartera con el tipo de LC1 vigente se hereda el valor de los campos
                    -- carTipoAccionCobro, carFechaAsignacionAccion, carMetodo
                    IF EXISTS(SELECT 1
                            FROM Cartera
                            WHERE carTipoLineaCobro = 'LC2'
                                AND carTipoSolicitante = 'EMPLEADOR'
                                AND carPersona = @idPersonaEmpleador
                                AND carEstadoOperacion = 'VIGENTE'
                                AND carPeriodoDeuda <> @fechaPeriodoEvaluacion)
                        BEGIN

                            -- En este caso, el aportante ya se encuentra en una acción de cobro sobre la misma línea de cobro
                            SELECT TOP 1 @TipoAccionCobro = carTipoAccionCobro,
                                        @fechaAsignacionAccion = carFechaAsignacionAccion,
                                        @metodo = carMetodo,
                                        @numeroOperacionLC1 = cagNumeroOperacion
                            FROM Cartera
                    INNER JOIN CarteraAgrupadora ON carId = cagCartera
                            WHERE carTipoLineaCobro = 'LC2'
                            AND carTipoSolicitante = 'EMPLEADOR'
                            AND carPersona = @idPersonaEmpleador
                            AND carEstadoOperacion = 'VIGENTE'
                            AND carPeriodoDeuda <> @fechaPeriodoEvaluacion----prueba20230216

                            INSERT INTO @carteraCreada(carDeudaPresunta,
                                                    carEstadoCartera,
                                                    carEstadoOperacion,
                                                    carFechaCreacion,
                                                    carPersona,
                                                    carPeriodoDeuda,
                                                    carTipoDeuda,
                                                    carTipoLineaCobro,
                                                    carTipoSolicitante,
                                                    carTipoAccionCobro,
                                                    carFechaAsignacionAccion,
                                                    carMetodo, carId)
                            SELECT ROUND(@DeudaEmpleadorlc2,0),
                                'AL_DIA',
                                'VIGENTE',
                                @fechaActual,
                                @idPersonaEmpleador,
                                @fechaPeriodoEvaluacion,
                                'INEXACTITUD',
                                'LC2',
                                'EMPLEADOR',
                                @TipoAccionCobro,
                                @fechaAsignacionAccion,
                                @metodo,
                                @iNewId
                        END
                    ELSE
                        BEGIN

                            INSERT INTO @carteraCreada(carDeudaPresunta,
                                                    carDeudaPresuntaUnitaria,
                                                    carEstadoCartera,
                                                    carEstadoOperacion,
                                                    carFechaCreacion,
                                                    carPersona,
                                                    carPeriodoDeuda,
                                                    carTipoDeuda,
                                                    carTipoLineaCobro,
                                                    carTipoSolicitante,
                                                    carTipoAccionCobro,
                                                    carFechaAsignacionAccion,
                                                    carMetodo,
                                                    carId)
                            SELECT ROUND(@deudaIndividuallc2,0),
                                ROUND(@deudaIndividuallc2,0),
                                'AL_DIA',
                                'VIGENTE',
                                @fechaActual,
                                @idPersonaEmpleador,
                                @fechaPeriodoEvaluacion,
                                'INEXACTITUD',
                                'LC2',
                                'EMPLEADOR',
                                NULL,
                                NULL,
                                NULL,
                                @iNewId

                        END
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear Cartera LC2',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH
                -- se crea cartera dependiente LC2 temporal para todos los trabajadores / aportantes sin novedades
                BEGIN TRY
                    SET @idCartera = @iNewId


                    INSERT INTO @carteraDependienteCreada(cadDeudaPresunta,
                                                        cadEstadoOperacion,
                                                        cadCartera,
                                                        cadPersona,
                                                        cadDeudaReal,
                                                        cadId)
                    SELECT ROUND(deudaIndividual,0),
                        'VIGENTE',
                        @idCartera,
                        perId,
                        0,
                        NEXT VALUE FOR Sec_CarteraDependiente cadId
                    FROM @tablaAportesVigentes tap
            INNER JOIN @LC2 lc2 ON lc2.apdPersona = tap.perId
            GROUP BY deudaIndividual,perId

                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC2',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH
            END
    --**--*****
        IF @activaParametrizacionLC3 = 1
            BEGIN
                DECLARE @LC3 TABLE
                            (
                                primary_key      INT IDENTITY (1,1) NOT NULL,
                                apdSalarioBasico NUMERIC,
                                apgPeriodoAporte VARCHAR(7),
                                apdPersona       BIGINT,
                                roaEmpleador     BIGINT,
                                apdDiasCotizados INT,
                                tarifa           NUMERIC(5, 5),
                                DiasIncap        INT,
                                aporte           NUMERIC(19, 5)
                            )

    
                INSERT INTO @LC3
                SELECT dp.apdSalarioBasico,
                    @periodoEvaluacion,
                    a.perId,
                    @idEmpleador,
                    SUM(dp.apdDiasCotizados)                                                as apdDiasCotizados,
                    CASE WHEN ISNULL(dp.apdTarifa, 0) = 0 THEN 0.04000 ELSE dp.apdTarifa END as tarifa,
                    0                                                                      as DiasIncap,
                    SUM(a.aporte)                                                          as aporte
                FROM @tablaAportesVigentes a
        INNER JOIN @tablaAportesTemp B ON A.perId = B.apdPersona
        INNER JOIN @calculodeudapresunta dp ON dp.apdPersona = a.perId
                WHERE B.apgPeriodoAporte = @periodoEvaluacion
                AND A.tipoAporte != 'OK'
                AND A.perId NOT IN (SELECT perIdtrab FROM #Trabpordias WHERE idempleador =  @idEmpleador )---ES ES PARA LOS TRAB DE MEDIO TIEMPO 
            GROUP BY dp.apdSalarioBasico,
                    a.perId,dp.apdTarifa


    --SELECT '@LC3',* FROM @LC3

                DECLARE @loop_counter3 INT, @item_category_counter3 INT ,
                    @peridlc3 BIGINT,
                    @DeudaEmpleadorlc3 NUMERIC(19,5)= 0,
                    @Salariolc3 NUMERIC(19,5) ,
                    @diascotizadoslc3 INT ,
                    @tarifalc3 NUMERIC(5, 5) ,
                    @DiasIncaplc3 INT,
                    @Salariodialc3 NUMERIC(19,5),
                    @diasincapacitadolc3 NUMERIC(19,5),
                    @deudaIndividuallc3 NUMERIC(19,5)

                SET @DeudaEmpleadorlc3 = 0
                ---CALCULO DEUDA PRESUNTA LC3


                SET @loop_counter3 = (SELECT COUNT(*) FROM @LC3);
                SET @item_category_counter3 = 1;

                WHILE @loop_counter3 > 0 AND @item_category_counter3 <= @loop_counter3
                    BEGIN

                        SELECT @Salariolc3 = apdSalarioBasico,
                            @peridlc3 = apdPersona,
                            @diascotizadoslc3 = apdDiasCotizados,
                            @tarifalc3 = Tarifa,
                            @DiasIncaplc3 = DiasIncap,
                            @aporte = aporte
                        FROM @LC3
                        WHERE primary_key = @item_category_counter3


                        SELECT @salariodialc3 = (apdSalarioBasico / 30),
                            @diasincapacitadolc3 = ISNULL(DiasIncap, 0),
                            @tarifalc3 = tarifa,
                            @diascotizadoslc3 = apdDiasCotizados
                        FROM @LC3 cd
                        WHERE apdpersona = @peridlc3

                        --calculo
                        SET @deudaIndividuallc3 = (@Salariolc3 * 0.04000) - @aporte

                        SET @deudaIndividuallc3 = CASE
                                                    WHEN @deudaIndividuallc3 < 0 THEN 0
                                                    ELSE @deudaIndividuallc3 END

                        UPDATE @tablaAportesVigentes
                        SET deudaIndividual = @deudaIndividuallc3
                        WHERE perId = @Peridlc3

                        -- se llena la variable que calcula la deuda presunta por empleador
                        SET @DeudaEmpleadorlc3 = @DeudaEmpleadorlc3 + @deudaIndividuallc3


                        SET @item_category_counter3 = @item_category_counter3 + 1


                        SET @Salariolc3 = 0
                        SET @diascotizadoslc3 = 0
                        SET @tarifalc3 = 0
                        SET @DiasIncaplc3 = 0
                        SET @Salariodialc3 = 0
                        SET @diasincapacitadolc3 = 0
                        SET @deudaIndividuallc3 = 0


                        SET @item_category_counter2 = @item_category_counter2 + 1

                    END
            END
        
        IF (SELECT COUNT(*) FROM @LC3) > 0
            BEGIN
                -- se crea la cartera LC3 temporal sin deuda presunta aun falta por calcular
                BEGIN TRY
                    IF @DeudaEmpleadorlc3>0
                    BEGIN
                    ---SELECT 'ENTRO POR ',@deudaPresuntaLC
                    -- se toma el siguiente ID de secuencia
                    EXEC USP_GET_GestorValorSecuencia 1, 'Sec_cartera', @iNewId OUTPUT

                    BEGIN
                        INSERT INTO @carteraCreada(carDeudaPresunta,
                                                carDeudaPresuntaUnitaria,
                                                carEstadoCartera,
                                                carEstadoOperacion,
                                                carFechaCreacion,
                                                carPersona,
                                                carPeriodoDeuda,
                                                carTipoDeuda,
                                                carTipoLineaCobro,
                                                carTipoSolicitante,
                                                carTipoAccionCobro,
                                                carFechaAsignacionAccion,
                                                carMetodo, carId)
                        SELECT @deudaIndividuallc3,
                            @deudaIndividuallc3,
                            'AL_DIA',
                            'VIGENTE',
                            @fechaActuaL,
                            @idPersonaEmpleador,
                            @fechaPeriodoEvaluacion,
                            'INEXACTITUD',
                            'LC3',
                            'EMPLEADOR',
                            NULL,
                            NULL,
                            NULL,
                            @iNewId
                        END
                    END
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear Cartera LC3',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH

                -- se crea cartera dependiente LC3 temporal para todos los trabajadores / aportantes sin novedades
                BEGIN TRY

                    SET @idCartera = @iNewId

            IF @DeudaEmpleadorlc3>0
            BEGIN
                    -- La deuda presunta unitaria es lo esperado en aporte menos los aportes realizados
                    INSERT INTO @carteraDependienteCreada(cadDeudaPresunta,
                                                        cadEstadoOperacion,
                                                        cadCartera,
                                                        cadPersona,
                                                        cadDeudaReal,
                                                        cadId)
                        SELECT deudaIndividual,
                            'VIGENTE',
                            @idCartera,
                            tcs.perId,
                            0,
                            NEXT VALUE FOR Sec_CarteraDependiente cadId
                        FROM @tablaAportesVigentes tcs
                INNER JOIN @LC3 lc3 ON lc3.apdPersona = tcs.perId
                        WHERE deudaIndividual>0
                GROUP BY tcs.perId, deudaIndividual
            END 
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC3',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH

                -- se crea cartera dependiente LC3 temporal para todos los trabajadores / aportantes sin novedades
                BEGIN TRY
                    UPDATE @carteraCreada
                    SET carDeudaPresunta = (SELECT CASE
                                                    WHEN SUM(cadDeudaPresunta) < 0 THEN 0
                                                    ELSE SUM(cadDeudaPresunta) END
                                            FROM @carteraDependienteCreada
                                            WHERE cadCartera = @idCartera)
                    FROM @carteraCreada cac
                    WHERE carId = @idCartera
                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualiza la deuda presunta sengu suma de deuda por inexactitud LC3',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH
            END

        -- fin hay aportes es decir   LC3


        ----FIN DE EL CALCULO DE LAS CARTERAS


        ------inserción en las tablas ooficiales cartera y cartera dependiente

        -- Se persiste en el modelo core los datos resultado
        BEGIN TRY

            -- Si no hay dependientes se elimina la cartera
            DELETE car
            FROM @carteraCreada car
                    LEFT JOIN @carteraDependienteCreada ON carid = cadCartera
            WHERE cadId IS NULL

            SELECT @iCantidadRegistrosCartera = COUNT(*) FROM @carteraCreada
            SELECT @iCantidadRegistrosCarteraDependiente = COUNT(*) FROM @carteraDependienteCreada

            SET @iCantidadRegistrosCreadosModificados =
                        @iCantidadRegistrosCartera + @iCantidadRegistrosCarteraDependiente

            -- se escribe la traza de auditoría
            IF @iCantidadRegistrosCartera > 0 AND @iCantidadRegistrosCarteraDependiente > 0
                BEGIN
                    -- Se crea en Cartera
                    INSERT INTO Cartera(carDeudaPresunta, carEstadoCartera, carEstadoOperacion,
                                        carFechaCreacion,
                                        carPersona, carPeriodoDeuda, carTipoDeuda,
                                        carDeudaPresuntaUnitaria, carTipoLineaCobro,
                                        carTipoSolicitante,
                                        carTipoAccionCobro, carFechaAsignacionAccion, carMetodo,
                                        carId)
                    SELECT carDeudaPresunta,
                        carEstadoCartera,
                        carEstadoOperacion,
                        carFechaCreacion,
                        carPersona,
                        carPeriodoDeuda,
                        carTipoDeuda,
                        carDeudaPresuntaUnitaria,
                        carTipoLineaCobro,
                        carTipoSolicitante,
                        carTipoAccionCobro,
                        carFechaAsignacionAccion,
                        carMetodo,
                        carId
                    FROM @carteraCreada

                    -- Se crea el histórico de Cartea
                    EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera',
                        @iCantidadRegistrosCartera, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

                    INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera,
                                                carEstadoOperacion,
                                                carFechaCreacion, carPersona, carMetodo,
                                                carPeriodoDeuda,
                                                carDeudaPresuntaUnitaria, carTipoAccionCobro,
                                                carTipoDeuda,
                                                carTipoLineaCobro, carTipoSolicitante, carId, REV,
                                                REVTYPE)
                    SELECT carDeudaPresunta,
                        carEstadoCartera,
                        carEstadoOperacion,
                        carFechaCreacion,
                        carPersona,
                        carMetodo,
                        carPeriodoDeuda,
                        carDeudaPresuntaUnitaria,
                        carTipoAccionCobro,
                        carTipoDeuda,
                        carTipoLineaCobro,
                        carTipoSolicitante,
                        carId,
                        @iRevision,
                        0
                    FROM @carteraCreada

                    -- se relaciona un numero de operación, (Se crea el registro de cartera agrupadora)
                    -- para los casos de LC1 que tienen una LC1 vigente anterior se hereda el número de operación
                    IF @numeroOperacionLC1 > 0
                        BEGIN
                            -- LC1 agrupada
                            INSERT INTO @carteraAgrupadoraCreada(cagCartera, cagId, cagNumeroOperacion)
                            SELECT carId,
                                NEXT VALUE FOR Sec_CarteraAgrupadora cagCartera,
                                @numeroOperacionLC1
                            FROM @carteraCreada
                            WHERE carTipoLineaCobro = 'LC1'

                            -- LC2 Y LC3
                            INSERT INTO @carteraAgrupadoraCreada(cagCartera, cagId, cagNumeroOperacion)
                            SELECT carId,
                                NEXT VALUE FOR Sec_CarteraAgrupadora cagCartera,
                                NEXT VALUE FOR Sec_CarteraAgrupadora_NumeroOperacion
                            FROM @carteraCreada
                            WHERE carTipoLineaCobro != 'LC1'
                        END
                    ELSE
                        BEGIN
                            -- No se debe agrupar la LC1
                            INSERT INTO @carteraAgrupadoraCreada(cagCartera, cagId, cagNumeroOperacion)
                            SELECT carId,
                                NEXT VALUE FOR Sec_CarteraAgrupadora cagCartera,
                                NEXT VALUE FOR Sec_CarteraAgrupadora_NumeroOperacion
                            FROM @carteraCreada
                        END

                    SELECT @iCantidadRegistrosCarteraAgrupadora = COUNT(*)
                    FROM @carteraAgrupadoraCreada
                    SET @iCantidadRegistrosCreadosModificados =
                                @iCantidadRegistrosCreadosModificados +
                                @iCantidadRegistrosCarteraAgrupadora

                    -- Se crea cartera agrupadora
                    INSERT INTO CarteraAgrupadora(cagId, cagCartera, cagNumeroOperacion)
                    SELECT cagId,
                        cagCartera,
                        cagNumeroOperacion
                    FROM @carteraAgrupadoraCreada

                    -- Se crea el histórico de CarteaAgrupadora
                    EXEC USP_UTIL_GET_CrearRevision
                        'com.asopagos.entidades.ccf.cartera.CarteraAgrupadora',
                        @iCantidadRegistrosCartera, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

                    INSERT INTO aud.CarteraAgrupadora_aud (cagId, cagCartera, cagNumeroOperacion, REV, REVTYPE)
                    SELECT cagId,
                        cagCartera,
                        cagNumeroOperacion,
                        @iRevision,
                        0
                    FROM @carteraAgrupadoraCreada

                END

            IF @iCantidadRegistrosCarteraDependiente > 0
                BEGIN
                    -- Se crea en CarteraDependiente
                    INSERT INTO CarteraDependiente(cadDeudaPresunta, cadEstadoOperacion, cadCartera,
                                                cadPersona,
                                                cadDeudaReal, cadId)
                    SELECT cadDeudaPresunta,
                        cadEstadoOperacion,
                        cadCartera,
                        cadPersona,
                        cadDeudaReal,
                        cadId
                    FROM @carteraDependienteCreada

                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores CD1:CarteraDependiente cantidad de cotizantes '
                                + CAST(@iCantidadRegistrosCreadosModificados as varchar(max)) +
                            ' Crear registro: '
                                + CAST(@crearNuevoRegistro as varchar(max)) +
                            ' Identificador persona empleador '
                                + CAST(@idPersonaEmpleador as varchar(max)) + ' Periodo: '
                                + CAST(@periodoEvaluacion as varchar(max)), ERROR_MESSAGE());

    -- Se crea el histórico de CarteraDependiente
                    EXEC USP_UTIL_GET_CrearRevision
                        'com.asopagos.entidades.ccf.cartera.CarteraDependiente',
                        @iCantidadRegistrosCarteraDependiente, '', 'USUARIO_SISTEMA',
                        @iRevision OUTPUT

                    INSERT INTO aud.CarteraDependiente_aud (cadDeudaPresunta, cadEstadoOperacion,
                                                            cadCartera,
                                                            cadPersona, cadDeudaReal, cadId, REV,
                                                            REVTYPE)
                    SELECT cadDeudaPresunta,
                        cadEstadoOperacion,
                        cadCartera,
                        cadPersona,
                        cadDeudaReal,
                        cadId,
                        @iRevision,
                        0
                    FROM @carteraDependienteCreada
                END
        END TRY
        BEGIN CATCH
            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
            VALUES (dbo.getLocalDate(),
                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Persistir registros temporales',
                    ERROR_MESSAGE());
            THROW;
        END CATCH -- Fin de cálculo de deuda presunta
        IF @crearNuevoRegistro = 0 -- Ejecutado desde USP_ExecuteCARTERAActualizarCartera
            BEGIN
                -- acualización de la deuda presunta
                BEGIN TRY
                    IF @idCartera IS NULL
                        BEGIN

                            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                            VALUES (dbo.getLocalDate(),
                                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:No se especifica el identificador de cartera a actualizar',
                                    'No se especifica el identificador de cartera a actualizar');

                            RAISERROR ('USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:No se especifica el identificador de cartera a actualizar', -- Message text.
                                -1, -- Severity.
                                -1 -- State.
                                );
                        END

                    IF NOT EXISTS(SELECT * FROM Cartera WHERE carId = @idCartera)
                        BEGIN

                            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                            VALUES (dbo.getLocalDate(),
                                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:No existe la cartera indicada',
                                    'No existe la cartera indicada');

                            RAISERROR ('USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:No existe la cartera indicada', -- Message text.
                                -1, -- Severity.
                                -1 -- State.
                                );
                        END

                    -- 2020/06/09
                    DECLARE @CarteraDependienteNoVigente AS TABLE
                                                            (
                                                                idCartera bigint,
                                                                idPersona bigint
                                                            )

                    DECLARE @CarteraDependienteVigente AS TABLE
                                                        (
                                                            idCartera bigint,
                                                            idPersona bigint,
                                                            deuda     numeric(19, 5)
                                                        )

                    INSERT INTO @CarteraDependienteNoVigente (idCartera, idPersona)
                    SELECT cadCartera, cadPersona
                    FROM CarteraDependiente
                    WHERE cadCartera = @idCartera
                    AND cadEstadoOperacion = 'NO_VIGENTE'

                    INSERT INTO @CarteraDependienteVigente (idCartera, idPersona, deuda)
                    SELECT cadCartera, cadPersona, ISNULL(cadDeudaPresunta, 0)
                    FROM CarteraDependiente
                    WHERE cadCartera = @idCartera
                    AND cadEstadoOperacion = 'VIGENTE'
                    -- 2020/06/09

    -- Se busca el tipo de linea de cobro de la cartera
                    SELECT @tipoLineaCobro = carTipoLineaCobro
                    FROM Cartera
                    WHERE carId = @idCartera

    -- Decrece deuda
                    BEGIN TRY
                        -- Decrece deuda por aportes
                        IF (@tipoLineaCobro = 'LC1' OR @tipoLineaCobro = 'LC2' OR @tipoLineaCobro = 'C6')
                            BEGIN
                                -- se seleccionan las personas que tuvieron un aporte y que se encontraban en la CarteraDependiente
                                BEGIN TRY
                                    INSERT INTO @carteraDependienteCreada(cdcAccion, cadCartera, cadPersona, cadId, cadDeudaPresunta)
                                    SELECT DISTINCT 'D', @idCartera, cadPersona, cadId, cadDeudaPresunta
                                    FROM CarteraDependiente cad
                                            INNER JOIN @tablaAportesVigentes tap ON cadPersona = tap.perId
                                    WHERE cadCartera = @idCartera
                                    AND tap.perId NOT IN (SELECT norPersona FROM @novedadRetiro)

                                    --  Se seleccionan los cotizantes que se retiraron en ese periodo
                                    INSERT INTO @carteraDependienteCreada(cdcAccion, cadCartera, cadPersona, cadId, cadDeudaPresunta)
                                    SELECT DISTINCT 'D', @idCartera, cadPersona, cadId, cadDeudaPresunta
                                    FROM CarteraDependiente cad
                                            INNER JOIN @novedadRetiro ON cadPersona = norPersona----cambio olga vega 20221222
                                    WHERE cadCartera = @idCartera

                                END TRY
                                BEGIN CATCH
                                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                    VALUES (dbo.getLocalDate(),
                                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto por aportes recibido',
                                            ERROR_MESSAGE());
                                    THROW;
                                END CATCH

                            END
                        --ELSE
                        IF @tipoLineaCobro = 'LC3'
                            BEGIN
                                -- se seleccionan las personas que tuvieron un aporte no ok y ya tiene aporte ok que se encontraban en la CarteraDependiente
                                BEGIN TRY
                                    INSERT INTO @carteraDependienteCreada(cdcAccion, cadCartera, cadPersona, cadId)
                                    SELECT 'D', @idCartera, cadPersona, cadId
                                    FROM CarteraDependiente cad
                                            LEFT JOIN (
                                        SELECT perId
                                        FROM @tablaAportesVigentes
                                        WHERE tipoAporte != 'OK'
                                    ) tap ON cadPersona = tap.perId
                                    WHERE cadCartera = @idCartera
                                    AND tap.perId IS NULL -- todos lo que no tienen aportes no ok
                                END TRY
                                BEGIN CATCH
                                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                    VALUES (dbo.getLocalDate(),
                                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto por aportes ok',
                                            ERROR_MESSAGE());
                                    THROW;
                                END CATCH

                                -- se actualizan los detalles de detalles con aportes no ok que se encuentran en la CarteraDependiente
                                BEGIN TRY
                                    INSERT INTO @carteraDependienteCreada(cdcAccion, cadDeudaPresunta,
                                                                        cadCartera,
                                                                        cadPersona, cadId)
                                    SELECT DISTINCT 'U',
                                                    montoAportes.pendiente,
                                                    @idCartera,
                                                    cadPersona,
                                                    cadId
                                    FROM CarteraDependiente
                                            INNER JOIN (
                                        SELECT DISTINCT personasAporteNoOk.perId,
                                                        (aportePorPagar.porPagar - sumaAportes.sumaPagado) AS pendiente
                                        FROM @tablaAportesVigentes AS personasAporteNoOk
                                                INNER JOIN (
                                            SELECT perId, MAX(deudaIndividual) AS porPagar
                                            FROM @tablaAportesVigentes
                                            GROUP BY perId
                                        ) AS aportePorPagar
                                                            ON personasAporteNoOk.perId = aportePorPagar.perId
                                                INNER JOIN (
                                            SELECT perId, SUM(aporte) AS sumaPagado
                                            FROM @tablaAportesVigentes
                                            GROUP BY perId
                                        ) AS sumaAportes ON personasAporteNoOk.perId = sumaAportes.perId
                                        WHERE tipoAporte != 'OK'
                                    ) AS montoAportes ON montoAportes.perId = cadPersona
                                    WHERE cadCartera = @idCartera

                                END TRY
                                BEGIN CATCH
                                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                    VALUES (dbo.getLocalDate(),
                                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC1',
                                            ERROR_MESSAGE());
                                    THROW;
                                END CATCH
                            END

                        -- Decrece deuda por novedades tiene una novedad
                        BEGIN TRY
                            INSERT INTO @carteraDependienteCreada (cdcAccion, cadCartera, cadPersona, cadId, cadDeudaPresunta)
                            SELECT DISTINCT 'D', @idCartera, cadPersona, cadId, cadDeudaPresunta----20221222
                            FROM CarteraDependiente cad
                                    INNER JOIN @tablaPersonasConNovedad AS exclusiones
                                                ON cad.cadPersona = exclusiones.perId
                            WHERE cadCartera = @idCartera

                        END TRY
                        BEGIN CATCH
                            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                            VALUES (dbo.getLocalDate(),
                                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto por novedades',
                                    ERROR_MESSAGE());
                            THROW;
                        END CATCH

                    END TRY
                    BEGIN CATCH
                        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                        VALUES (dbo.getLocalDate(),
                                'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (Decrece)',
                                ERROR_MESSAGE());
                        THROW;
                    END CATCH

                    -- Incrementa deuda
                    BEGIN TRY
                        IF (@tipoLineaCobro = 'LC1' OR @tipoLineaCobro = 'LC2' OR @tipoLineaCobro = 'C6')
                            BEGIN
                                -- se busca una persona creada con anterioridad para obtener la deuda presunta individual
                                SELECT @deudaPresuntaUnitaria = carDeudaPresuntaUnitaria
                                FROM Cartera
                                WHERE carId = @idCartera
                            END
                        -- si es LC3 se calcula según salario

                        -- Incrementa deuda por:
                        -- 		* registro novedad de reingreso (sin novades y sin aporte) caso LC1 y LC2
                        -- 		* registro novedad de reingreso (sin novades y sin aporte ok) caso LC3
                        --		* que no esten previamente registrados.
                        BEGIN TRY
                            -- personas con reingreso y sin novedad complementaria
                            DECLARE @tablaPersonasConNovedadReingreso AS TablaPersonaIdType
                            INSERT INTO @tablaPersonasConNovedadReingreso (perId)
                            SELECT afi.afiPersona
                            FROM RolAfiliado roa
                                    JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
                                    LEFT JOIN @tablaPersonasConNovedad AS exclusiones
                                            ON afi.afiPersona = exclusiones.perId
                            WHERE exclusiones.perId IS NULL
                            AND roa.roaEmpleador = @idEmpleador
                            AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
                            -- Activos al menos un día en el periodo
                            AND (
                                -- se activó en el periodo de estudio
                                        CONVERT(DATE, @periodoEvaluacion + '-01', 121) =
                                        DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaAfiliacion), 0)
                                    -- aún activo
                                    OR roa.roaFechaRetiro IS NULL
                                    -- se retiró en el mes de estudio
                                    OR CONVERT(DATE, @periodoEvaluacion + '-01', 121) =
                                    DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaRetiro), 0)
                                )

                            -- Si hubo reingreso o cancelación de novedad
                            IF 0 < (SELECT COUNT(*) FROM @tablaPersonasConNovedadReingreso)
                                BEGIN
                                    IF (@tipoLineaCobro = 'LC1' OR @tipoLineaCobro = 'LC2' OR @tipoLineaCobro = 'C6')
                                        AND 0 = (SELECT COUNT(*)
                                                FROM @tablaPersonasConNovedadReingreso pre
                                                        INNER JOIN @tablaAportesVigentes tap ON pre.perId = tap.perId)
                                        BEGIN
                                            --Sin aportes vigentes para el periodo caso LC1 y LC2
                                            -- se crean los nuevos detalles
                                            BEGIN TRY
                                                INSERT INTO @carteraDependienteCreada(cdcAccion,
                                                                                    cadDeudaPresunta,
                                                                                    cadEstadoOperacion,
                                                                                    cadCartera,
                                                                                    cadPersona, cadId)
                                                SELECT 'I',
                                                    @deudaPresuntaUnitaria,
                                                    'VIGENTE',
                                                    @idCartera,
                                                    pre.perId,
                                                    NEXT VALUE FOR Sec_CarteraDependiente cadId
                                                FROM @tablaPersonasConNovedadReingreso pre
                                                        LEFT JOIN @tablaAportesVigentes tap ON pre.perId = tap.perId
                                                        LEFT JOIN CarteraDependiente ON cadPersona = pre.perId AND cadCartera = @idCartera
                                                WHERE tap.perId IS NULL -- personas con novedad de reingreso y sin aportes vigentes
                                                AND cadId IS NULL -- personas que no se encuentren en la cartera actualmente
                                            END TRY
                                            BEGIN CATCH
                                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                                VALUES (dbo.getLocalDate(),
                                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC1',
                                                        ERROR_MESSAGE());
                                                THROW;
                                            END CATCH
                                        END
                                    ELSE -- con aportes para el periodo
                                        BEGIN
                                            IF @tipoLineaCobro = 'LC3'
                                                AND 0 < (SELECT COUNT(*)
                                                        FROM @tablaPersonasConNovedadReingreso pre
                                                                INNER JOIN @tablaAportesVigentes tap ON pre.perId = tap.perId
                                                        WHERE tipoAporte != 'OK')
                                                BEGIN
                                                    --Con aportes vigentes NO OK y caso LC3
                                                    -- se crean los nuevos detalles
                                                    BEGIN TRY
                                                        INSERT INTO @carteraDependienteCreada(cdcAccion,
                                                                                            cadDeudaPresunta,
                                                                                            cadEstadoOperacion,
                                                                                            cadCartera,
                                                                                            cadPersona,
                                                                                            cadDeudaReal,
                                                                                            cadId)
                                                        SELECT 'I',
                                                            montoAportes.pendiente,
                                                            'VIGENTE',
                                                            @idCartera,
                                                            pre.perId,
                                                            0,
                                                            NEXT VALUE FOR Sec_CarteraDependiente cadId
                                                        FROM @tablaPersonasConNovedadReingreso pre
                                                                INNER JOIN (
                                                            SELECT DISTINCT personasAporteNoOk.perId,
                                                                            (aportePorPagar.porPagar - sumaAportes.sumaPagado) AS pendiente
                                                            FROM @tablaAportesVigentes AS personasAporteNoOk
                                                                    INNER JOIN (
                                                                SELECT perId, MAX(deudaIndividual) AS porPagar
                                                                FROM @tablaAportesVigentes
                                                                GROUP BY perId
                                                            ) AS aportePorPagar
                                                                                ON personasAporteNoOk.perId = aportePorPagar.perId
                                                                    INNER JOIN (
                                                                SELECT perId, SUM(aporte) AS sumaPagado
                                                                FROM @tablaAportesVigentes
                                                                GROUP BY perId
                                                            ) AS sumaAportes
                                                                                ON personasAporteNoOk.perId = sumaAportes.perId
                                                            WHERE tipoAporte != 'OK'
                                                        ) AS montoAportes
                                                                            ON montoAportes.perId = pre.perId
                                                                LEFT JOIN CarteraDependiente ON cadPersona = pre.perId AND cadCartera = @idCartera
                                                        WHERE montoAportes.pendiente > 0
                                                        AND cadId IS NULL -- personas que no se encuentren en la cartera actualmente
                                                    END TRY
                                                    BEGIN CATCH
                                                        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                                        VALUES (dbo.getLocalDate(),
                                                                'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Crear CarteraDependiente LC1',
                                                                ERROR_MESSAGE());
                                                        THROW;
                                                    END CATCH
                                                END
                                        END
                                END -- fin reingreso
                        END TRY
                        BEGIN CATCH
                            INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                            VALUES (dbo.getLocalDate(),
                                    'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (Incrementa por nov de reingreso)',
                                    ERROR_MESSAGE());
                            THROW;
                        END CATCH
                    END TRY
                    BEGIN CATCH
                        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                        VALUES (dbo.getLocalDate(),
                                'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (Incrementa)',
                                ERROR_MESSAGE());
                        THROW;
                    END CATCH

                    IF (SELECT COUNT(*) FROM @carteraDependienteCreada) > 0
                        BEGIN
                            -- actualizar la deuda presunta con la sumatoria
                            SELECT @iCantidadRegistrosCreadosModificados = (1 + COUNT(*))
                            FROM @carteraDependienteCreada

    -- nuevos dependientes
                            BEGIN TRY

                                -- Conteo datos a modificar
                                SELECT @iCantidadRegistrosCarteraDependiente = COUNT(*)
                                FROM @carteraDependienteCreada
                                WHERE cdcAccion = 'I'

                                -- Se crea en CarteraDependiente
                                INSERT INTO CarteraDependiente(cadDeudaPresunta, cadEstadoOperacion,
                                                            cadCartera,
                                                            cadPersona, cadDeudaReal, cadId)
                                SELECT cadDeudaPresunta,
                                    cadEstadoOperacion,
                                    cadCartera,
                                    cadPersona,
                                    ISNULL(cadDeudaReal, 0),
                                    cadId
                                FROM @carteraDependienteCreada
                                WHERE cdcAccion = 'I'

                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores CD2:CarteraDependiente cantidad de cotizantes '
                                            +
                                        CAST(@iCantidadRegistrosCreadosModificados as varchar(max)) +
                                        ' Crear registro: '
                                            + CAST(@crearNuevoRegistro as varchar(max)) +
                                        ' Identificador persona empleador '
                                            + CAST(@idPersonaEmpleador as varchar(max)) + ' Periodo: '
                                            + CAST(@periodoEvaluacion as varchar(max)),
                                        ERROR_MESSAGE());

    -- Se crea el histórico de CarteraDependiente
                                EXEC USP_UTIL_GET_CrearRevision
                                    'com.asopagos.entidades.ccf.cartera.CarteraDependiente',
                                    @iCantidadRegistrosCarteraDependiente, '', 'USUARIO_SISTEMA',
                                    @iRevision OUTPUT

                                INSERT INTO aud.CarteraDependiente_aud (cadDeudaPresunta,
                                                                        cadEstadoOperacion,
                                                                        cadCartera, cadPersona,
                                                                        cadDeudaReal, cadId,
                                                                        REV, REVTYPE)
                                SELECT cadDeudaPresunta,
                                    cadEstadoOperacion,
                                    cadCartera,
                                    cadPersona,
                                    ISNULL(cadDeudaReal, 0),
                                    cadId,
                                    @iRevision,
                                    0
                                FROM @carteraDependienteCreada
                                WHERE cdcAccion = 'I'

                            END TRY
                            BEGIN CATCH
                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (I)',
                                        ERROR_MESSAGE());
                                THROW;
                            END CATCH

                            -- cambio de valor deuda por aportanes
                            BEGIN TRY

                                -- Conteo datos a modificar
                                SELECT @iCantidadRegistrosCarteraDependiente = COUNT(*)
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                WHERE cdcAccion = 'U'
                                AND cadc.cadDeudaPresunta > 0

    -- Se actualiza valor de deuda presunta en CarteraDependiente si es mayor a 0
                                UPDATE cad
                                SET cad.cadDeudaPresunta = cadc.cadDeudaPresunta
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                WHERE cdcAccion = 'U'
                                AND cadc.cadDeudaPresunta > 0

                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores CD3:CarteraDependiente cantidad de cotizantes '
                                            +
                                        CAST(@iCantidadRegistrosCreadosModificados as varchar(max)) +
                                        ' Crear registro: '
                                            + CAST(@crearNuevoRegistro as varchar(max)) +
                                        ' Identificador persona empleador '
                                            + CAST(@idPersonaEmpleador as varchar(max)) + ' Periodo: '
                                            + CAST(@periodoEvaluacion as varchar(max)),
                                        ERROR_MESSAGE());

    -- Se crea el histórico de CarteraDependiente
                                EXEC USP_UTIL_GET_CrearRevision
                                    'com.asopagos.entidades.ccf.cartera.CarteraDependiente',
                                    @iCantidadRegistrosCarteraDependiente, '', 'USUARIO_SISTEMA',
                                    @iRevision OUTPUT

                                INSERT INTO aud.CarteraDependiente_aud (cadDeudaPresunta,
                                                                        cadEstadoOperacion,
                                                                        cadCartera, cadPersona,
                                                                        cadDeudaReal,
                                                                        cadAgregadoManual, cadId, REV,
                                                                        REVTYPE)
                                SELECT cad.cadDeudaPresunta,
                                    cad.cadEstadoOperacion,
                                    cad.cadCartera,
                                    cad.cadPersona,
                                    ISNULL(cad.cadDeudaReal, 0),
                                    cad.cadAgregadoManual,
                                    cad.cadId,
                                    @iRevision,
                                    1
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                WHERE cdcAccion = 'U'
                                AND cadc.cadDeudaPresunta > 0

                            END TRY
                            BEGIN CATCH
                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (U)',
                                        ERROR_MESSAGE());
                                THROW;
                            END CATCH


                            -- retiro de cartera
                            BEGIN TRY

                                -- Conteo datos a modificar
                                SELECT @iCantidadRegistrosCarteraDependiente = COUNT(*)
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                WHERE cdcAccion = 'D'
                                OR (cdcAccion = 'U' AND cadc.cadDeudaPresunta <= 0)

                                -- Se actualiza valor de deuda presunta en CarteraDependiente
    -- TODO: Verificar escenario de aportes para personas agregadas manualmente con anterioridad

                                ------cambio 20221227


                                SELECT nov.norPersona      as persona,
                                    cc.caddeudapresunta as deudapresunta,
                                    cc.caddeudapresunta as aporte
                                INTO #CarteraActualizar
                                FROM @novedadRetiro nov ---CAMBIO OLGA VEGA 20221222
                                        INNER JOIN @carteraDependienteCreada cc
                                                    ON nov.norpersona = cc.cadpersona
                                WHERE cdcAccion = 'D'
                                OR (cdcAccion = 'U' AND cc.cadDeudaPresunta <= 0)
                                GROUP BY nov.norPersona, cc.caddeudapresunta

                                UNION
                                SELECT novedades.perid, cc.caddeudapresunta, cc.caddeudapresunta
                                FROM @tablaPersonasConNovedad novedades
                                        INNER JOIN @carteraDependienteCreada cc
                                                    ON cc.cadpersona = novedades.perid
                                WHERE cdcAccion = 'D'
                                OR (cdcAccion = 'U' AND cc.cadDeudaPresunta <= 0)
                                GROUP BY novedades.perid, cc.caddeudapresunta


                                INSERT INTO #CarteraActualizar
                                SELECT DISTINCT cad.cadpersona,
                                                ISNULL(cadc.cadDeudaPresunta, 0)      as deudapresunta,
                                                ISNULL(atemp.apdAporteObligatorio, 0) as aporte
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                        INNER JOIN @tablaAportesTemp atemp
                                                    ON atemp.apdPersona = cadc.cadPersona
                                                        AND apgPeriodoAporte = @periodoevaluacion---cambio olga vega 20221227
                                        LEFT JOIN #CarteraActualizar CA
                                                ON ca.persona = cad.cadpersona
                                WHERE ca.persona is null
                                    and cdcAccion = 'D'
                                OR (cdcAccion = 'U' AND cadc.cadDeudaPresunta <= 0)


                                UPDATE cad
                                SET cad.cadDeudaPresunta   = CASE
                                                                WHEN (ISNULL(cadc.cadDeudaPresunta, 0) - ISNULL(nov.Aporte, 0)) < 0
                                                                    THEN 0
                                                                ELSE ISNULL(cadc.cadDeudaPresunta, 0) - ISNULL(nov.Aporte, 0) END,
                                    --  cad.cadDeudaReal       =  ISNULL(cadc.cadDeudaPresunta,0) -ISNULL(nov.aporte,0),
                                    cad.cadEstadoOperacion = CASE
                                                                WHEN (ISNULL(cadc.cadDeudaPresunta, 0) - ISNULL(nov.Aporte, 0)) = 0
                                                                    THEN 'NO_VIGENTE'
                                                                ELSE 'VIGENTE' END ----CAMBIO OLGA VEGA 20221221
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                        INNER JOIN #CarteraActualizar nov ON nov.persona = cadc.cadPersona
                                    ---CAMBIO OLGA VEGA 20221227
                                WHERE cdcAccion = 'D'
                                    AND cad.cadEstadoOperacion = 'VIGENTE'
                                OR (cdcAccion = 'U' AND cadc.cadDeudaPresunta <= 0)


                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores CD4:CarteraDependiente cantidad de cotizantes '
                                            +
                                        CAST(@iCantidadRegistrosCreadosModificados as varchar(max)) +
                                        ' Crear registro: '
                                            + CAST(@crearNuevoRegistro as varchar(max)) +
                                        ' Identificador persona empleador '
                                            + CAST(@idPersonaEmpleador as varchar(max)) + ' Periodo: '
                                            + CAST(@periodoEvaluacion as varchar(max)),
                                        ERROR_MESSAGE());

    -- Se crea el histórico de CarteraDependiente
                                EXEC USP_UTIL_GET_CrearRevision
                                    'com.asopagos.entidades.ccf.cartera.CarteraDependiente',
                                    @iCantidadRegistrosCarteraDependiente, '', 'USUARIO_SISTEMA',
                                    @iRevision OUTPUT

                                INSERT INTO aud.CarteraDependiente_aud (cadDeudaPresunta,
                                                                        cadEstadoOperacion,
                                                                        cadCartera, cadPersona,
                                                                        cadDeudaReal,
                                                                        cadAgregadoManual, cadId, REV,
                                                                        REVTYPE)
                                SELECT cad.cadDeudaPresunta,
                                    cad.cadEstadoOperacion,
                                    cad.cadCartera,
                                    cad.cadPersona,
                                    ISNULL(cad.cadDeudaReal, 0),
                                    cad.cadAgregadoManual,
                                    cad.cadId,
                                    @iRevision,
                                    1
                                FROM CarteraDependiente cad
                                        INNER JOIN @carteraDependienteCreada cadc ON cadc.cadId = cad.cadId
                                WHERE cdcAccion = 'D'
                                OR (cdcAccion = 'U' AND cadc.cadDeudaPresunta <= 0)

                            END TRY
                            BEGIN CATCH
                                INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                                VALUES (dbo.getLocalDate(),
                                        'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (D)',
                                        ERROR_MESSAGE());
                                THROW;
                            END CATCH
                        END

                    -- Actualización de la cartera
                    BEGIN TRY

                        -- Se actualiza valor de deuda presunta sumando los CarteraDependiente
                        -- TODO: Verificar escenario de sumatoria de deuda real y presunta para personas en la LC base
                        SET @deudaPresunta = 0

                        -- 2020/06/09
                        --SELECT @deudaPresunta = SUM(ISNULL(cadDeudaPresunta, 0)) -- + SUM(ISNULL(cadDeudaReal,0))
                        --FROM Cartera car
                        --INNER JOIN CarteraDependiente cad ON cad.cadCartera = car.carId
                        --WHERE carId = @idCartera
                        --AND cadEstadoOperacion = 'VIGENTE'

                        -- Inicio ajuste deuda
                        --2020/06/09 Se modifica la forma que se suma la deuda presunta por datos que se encuentran a la fecha en producción
                        -- que no deberían estar, al hacer una actualización sobre estos trabajadores dañaría el valor calculado en la deuda presunta
                        -- para la tabla cartera
                        DECLARE @deudaPresuntaUnitariaTemp NUMERIC(19, 5) = 0
                        DECLARE @deudaPresuntaUnitariaTempD INT
                        --DECLARE @contadorCarteraDependienteUD INT
                        DECLARE @contadorCarteraDependienteU INT
                        DECLARE @incrementoDeuda NUMERIC(19, 5) = 0
                        DECLARE @incrementoDeudaActual NUMERIC(19, 5) = 0
                        DECLARE @incrementoDeudaAnterior NUMERIC(19, 5) = 0
                        DECLARE @decreceDeuda NUMERIC(19, 5) = 0
                        DECLARE @decreceDeudaActual NUMERIC(19, 5) = 0
                        DECLARE @decreceDeudaAnterior NUMERIC(19, 5) = 0


                        SELECT @deudaPresunta = ISNULL(carDeudaPresunta, 0),
                            @deudaPresuntaUnitariaTemp = ISNULL(SUM(cadDeudaPresunta), 0)
                        FROM Cartera
                                INNER JOIN CarteraDependiente ON cadCartera = carId
                        WHERE carId = @idCartera
                        GROUP BY carDeudaPresunta


    --Se suman las deudas presuntas de cada cartera dependiente cuando el tipo de linea de cobro sea LC3 - Mantis 266543
                        SELECT @deudaPresunta = ISNULL(SUM(cadDeudaPresunta), @deudaPresunta)
                        FROM CarteraDependiente
                                JOIN Cartera ON cadCartera = carId
                        WHERE cadCartera = @idCartera
                        AND cadDeudaPresunta >= 0
                        AND carTipoLineaCobro = 'LC3'

                        -- Cuando la accion es D o (U y que su deuda se igual o menor a cero), decrece el monto de la deuda

                        ----CAMBIO REALIZADO PARA EL CALCULO DE DEUDAPRESUNTA CARTERA 20221222 OLGA VEGA


                        --			SELECT '#CarteraActualizar',* FROM #CarteraActualizar

                        --                 SELECT @deudaPresuntaUnitariaTempD = SUM(x.caddeudapresunta)- SUM(x.apdaporteobligatorio)
                        --                  FROM ( SELECT cc.cadPersona, cc.caddeudapresunta,
                        --		   ISNULL(x.aporte,cc.caddeudapresunta) AS apdaporteobligatorio
                        --                          FROM  @carteraDependienteCreada cc
                        --LEFT JOIN  #CarteraActualizar   x
                        --		ON x.persona = cc.cadPersona
                        --	  ----CAMBIO OLGA VEGA 20221222
                        --                          WHERE cdcAccion = 'D'
                        --                            AND cc.cadCartera = @idCartera

                        --                          UNION ALL

                        --                          SELECT cd.cadpersona ,   cd.caddeudapresunta  ,0
                        --                          FROM CarteraDependiente cd
                        --                          WHERE cd.cadCartera = @idCartera
                        --                            AND cd.cadEstadoOperacion ='VIGENTE'
                        --                            AND cd.cadPersona NOT IN (SELECT cadPersona FROM @carteraDependienteCreada)

                        --                      ) X


                        --                 SELECT @deudaPresuntaUnitariaTemp =  SUM(ISNULL(x.caddeudapresunta ,0))-SUM(ISNULL(x.apdaporteobligatorio,0))
                        --                             FROM
                        --		( SELECT cc.cadPersona, cc.caddeudapresunta,
                        --		   ISNULL(x.aporte,cc.caddeudapresunta) AS apdaporteobligatorio
                        --                          FROM  @carteraDependienteCreada cc
                        --LEFT JOIN  #CarteraActualizar   x
                        --		ON x.persona = cc.cadPersona
                        --	  ----CAMBIO OLGA VEGA 20221222
                        --                          WHERE cdcAccion = 'U'
                        --                            AND cc.cadCartera = @idCartera

                        --                          UNION ALL

                        --                          SELECT cd.cadpersona ,   cd.caddeudapresunta  ,0
                        --                          FROM CarteraDependiente cd
                        --                          WHERE cd.cadCartera = @idCartera
                        --                            AND cd.cadEstadoOperacion ='VIGENTE'
                        --                            AND cd.cadPersona NOT IN (SELECT cadPersona FROM @carteraDependienteCreada)

                        --                      ) X

                        --SELECT @deudaPresunta = isnull(@deudaPresuntaUnitariaTempD,0) - isnull(@deudaPresuntaUnitariaTemp,0)

                        ------CAMBIO OLGA VEGA 20221228 MANDAMOS TODO A LA VERGA

                        SELECT @deudaPresunta = CASE
                                                    WHEN SUM(cadDeudaPresunta) < 0 THEN 0
                                                    ELSE SUM(cadDeudaPresunta) END
                        FROM CarteraDependiente cc
                        WHERE cc.cadCartera = @idCartera
                        AND cc.cadEstadoOperacion = 'VIGENTE'

                        ---isnull(@deudaPresuntaUnitariaTempD,0) - isnull(@deudaPresuntaUnitariaTemp,0)

                        ---CAMBIO OLGA VEGA  20221221
    --SELECT @deudaPresunta,'SELECT @deudaPresunta'
    --SELECT '@carteraDependienteCreada',* FROM @carteraDependienteCreada

    -- Cuando la accion es I, incrementa el monto de la deuda por nuevos registros
                        SELECT @incrementoDeuda = ISNULL(SUM(cadDeudaPresunta), 0)
                        FROM @carteraDependienteCreada
                        WHERE cdcAccion = 'I'
                        AND cadCartera = @idCartera
                        AND cadPersona NOT IN
                            (SELECT idPersona
                            FROM @CarteraDependienteVigente
                            WHERE cadCartera = idCartera)
                        GROUP BY cadPersona

                        SELECT @incrementoDeudaAnterior = ISNULL(SUM(deuda), 0)
                        FROM @CarteraDependienteVigente
                        WHERE idCartera = @idCartera
                        AND idPersona IN
                            (SELECT cadPersona
                            FROM @carteraDependienteCreada
                            WHERE cdcAccion = 'I'
                                AND cadCartera = idCartera
                                AND deuda <> cadDeudaPresunta)
                        GROUP BY idPersona

                        SELECT @incrementoDeudaActual = ISNULL(SUM(cadDeudaPresunta), 0)
                        FROM @carteraDependienteCreada
                        WHERE cdcAccion = 'I'
                        AND cadCartera = @idCartera
                        AND cadPersona IN
                            (SELECT idPersona
                            FROM @CarteraDependienteVigente
                            WHERE cadCartera = idCartera
                                AND deuda <> cadDeudaPresunta)
                        GROUP BY cadPersona

                        IF @deudaPresunta IS NULL
                            BEGIN
                                SET @deudaPresunta = @deudaPresunta + @incrementoDeuda -
                                                    @incrementoDeudaAnterior +
                                                    @incrementoDeudaActual
                            END
    -- Cuando la accion es U y la deuda sigue siendo mayor a 0
                        SELECT @contadorCarteraDependienteU = COUNT(DISTINCT cadPersona)
                        FROM @carteraDependienteCreada
                        WHERE cdcAccion = 'U'
                        AND cadDeudaPresunta > 0
                        AND cadCartera = @idCartera
                        AND cadPersona NOT IN
                            (SELECT idPersona
                            FROM @CarteraDependienteVigente
                            WHERE cadCartera = idCartera)
                        AND cadPersona NOT IN
                            (SELECT perId FROM @tablaPersonasConNovedad)---CAMBIO OLGA VEGA 20221222

                        SELECT @decreceDeuda = ISNULL(SUM(cadDeudaPresunta), 0)
                        FROM @carteraDependienteCreada
                        WHERE cdcAccion = 'U'
                        AND cadDeudaPresunta > 0
                        AND cadCartera = @idCartera
                        AND cadPersona NOT IN
                            (SELECT idPersona
                            FROM @CarteraDependienteVigente
                            WHERE cadCartera = idCartera)

                        GROUP BY cadPersona

                        SELECT @decreceDeudaAnterior = ISNULL(SUM(deuda), 0)
                        FROM @CarteraDependienteVigente
                        WHERE idCartera = @idCartera
                        AND idPersona IN
                            (SELECT cadPersona
                            FROM @carteraDependienteCreada
                            WHERE cdcAccion = 'U'
                                AND cadDeudaPresunta > 0
                                AND cadCartera = idCartera
                                AND deuda <> cadDeudaPresunta)
                        GROUP BY idPersona

                        SELECT @decreceDeudaActual = ISNULL(SUM(cadDeudaPresunta), 0)
                        FROM @carteraDependienteCreada
                        WHERE cdcAccion = 'U'
                        AND cadDeudaPresunta > 0
                        AND cadCartera = @idCartera
                        AND cadPersona IN
                            (SELECT idPersona
                            FROM @CarteraDependienteVigente
                            WHERE cadCartera = idCartera
                                AND deuda <> cadDeudaPresunta)

                        GROUP BY cadPersona


                        IF @deudaPresunta IS NULL
                            BEGIN
                                SET @deudaPresunta = @deudaPresunta -
                                                    ((@deudaPresuntaUnitariaTemp * @contadorCarteraDependienteU) -
                                                    @decreceDeuda) + @decreceDeudaAnterior -
                                                    @decreceDeudaActual
                            END

    -- Fin ajuste deuda	2020/06/09


                        UPDATE car
                        SET carDeudaPresunta = CASE
                                                WHEN ISNULL(@deudaPresunta, 0) < 0 THEN 0
                                                ELSE ISNULL(@deudaPresunta, 0) END
                        FROM Cartera car
                        WHERE carId = @idCartera

    --Indica si pago todos los periodos para una persona que se encuentra en la linea de cobro 1
                        DECLARE @numeroOperacion BIGINT

                        SELECT @numeroOperacion = cagNumeroOperacion
                        FROM CarteraAgrupadora
                        WHERE cagCartera = @idCartera
    ---      INNER JOIN #CarteraActualizar nov ON nov.persona = cadc.cadPersona


                        IF NOT EXISTS(SELECT TOP 1 carId
                                    FROM Cartera,
                                        CarteraAgrupadora
                                    WHERE cagCartera = carId
                                        and cagNumeroOperacion = @numeroOperacion
                                        AND carDeudaPresunta > 0)
                            BEGIN


                                UPDATE car
                                SET carDeudaPresunta   = 0,
                                    carEstadoCartera   ='AL_DIA',
                                    carEstadoOperacion = 'NO_VIGENTE'
                                FROM Cartera car
                                WHERE carId IN (SELECT c.carId
                                                FROM Cartera c,
                                                    CarteraAgrupadora
                                                WHERE cagCartera = c.carId
                                                and cagNumeroOperacion = @numeroOperacion
                                                AND c.carDeudaPresunta <= 0)
                            END

                        -- Se crea el histórico de Cartea
                        EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', 1,
                            '',
                            'USUARIO_SISTEMA', @iRevision OUTPUT

                        INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera,
                                                    carEstadoOperacion,
                                                    carFechaCreacion, carPersona, carMetodo,
                                                    carPeriodoDeuda,
                                                    carDeudaPresuntaUnitaria, carTipoAccionCobro,
                                                    carTipoDeuda,
                                                    carTipoLineaCobro, carTipoSolicitante, carId, REV,
                                                    REVTYPE)
                        SELECT carDeudaPresunta,
                            carEstadoCartera,
                            carEstadoOperacion,
                            carFechaCreacion,
                            carPersona,
                            carMetodo,
                            carPeriodoDeuda,
                            carDeudaPresuntaUnitaria,
                            carTipoAccionCobro,
                            carTipoDeuda,
                            carTipoLineaCobro,
                            carTipoSolicitante,
                            carId,
                            @iRevision,
                            1
                        FROM Cartera
                        WHERE carId = @idCartera

    ----validar que no este ya insertado en la tabla de cartera para no insertar duplicados futuro cambio 20220810
                    END TRY
                    BEGIN CATCH
                        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                        VALUES (dbo.getLocalDate(),
                                'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta (D)',
                                ERROR_MESSAGE());
                        THROW;
                    END CATCH

                END TRY
                BEGIN CATCH
                    INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
                    VALUES (dbo.getLocalDate(),
                            'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores S:Actualizar monto de deuda presunta',
                            ERROR_MESSAGE());
                    THROW;
                END CATCH
            END

        INSERT INTO TiempoProcesoCartera
        (tpcNombreProceso, tpcFechaInicio, tpcFechaFin, tpcRegistros, tpcMensaje)
        VALUES ('USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores', @fechaInicioEjecucion,
                dbo.getLocalDate(), NULL,
                NULL);

    -- se marca en bitácora el fin de la ejecución exitosa y los registros afectados
        UPDATE BitacoraEjecucionSp
        SET besUltimoExito        = dbo.getLocalDate(),
            besRegistrosAfectados = @iCantidadRegistrosCreadosModificados
        WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores'

    END TRY
    BEGIN CATCH
        -- se marca en bitácora el fin de la ejecución fallida
        UPDATE BitacoraEjecucionSp
        SET besUltimoFallo        = dbo.getLocalDate(),
            besRegistrosAfectados = 0
        WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores'

        DECLARE @ErrorMessage NVARCHAR(4000),
            @ErrorSeverity INT,
            @ErrorState INT

        INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
        VALUES (dbo.getLocalDate(),
                (SELECT '[dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores] | ' +
                        '@idPersonaEmpleador' + CAST(@idPersonaEmpleador AS VARCHAR) +
                        '@idEmpleador' + CAST(@idEmpleador AS VARCHAR) +
                        '@idEmpresa' + CAST(@idEmpresa AS VARCHAR) +
                        '@periodoEvaluacion' + CAST(@periodoEvaluacion AS VARCHAR) +
                        '@crearNuevoRegistro' + CAST(@crearNuevoRegistro AS VARCHAR) +
                        '@idCartera' + CAST(@idCartera AS VARCHAR)),
                @ErrorMessage)

        SELECT @ErrorMessage =
            '[dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores] | ' + ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();
        RAISERROR (@ErrorMessage, -- Message text.
            @ErrorSeverity, -- Severity.
            @ErrorState -- State.
            );
        THROW;
    END CATCH