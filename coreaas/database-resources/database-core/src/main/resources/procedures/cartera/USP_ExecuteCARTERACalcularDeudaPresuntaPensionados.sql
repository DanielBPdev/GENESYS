-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2017/12/07
-- Description:	Procedimiento almacenado encargado de calcular y guardar la deuda presunta para un aportante pensionado
-- HU169
-- =============================================
CREATE OR ALTER   PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaPensionados] 
@idPersona BIGINT, -- Identificador único de persona
@idRolAfiliado BIGINT , -- Identificador único de rol afiliado
@periodoEvaluacion VARCHAR(7), -- Periodo en evaluación YYYY-MM
@crearNuevoRegistro BIT , -- 1: Si se va a crear un registro en Cartera. 0: Si se va a actualizar el registro
@idCartera BIGINT -- Identificador único de cartera. NULL cuando @crearNuevoRegistro = 1
AS
BEGIN TRY
    -- se agrega registro en bitacora de ejecución
IF EXISTS(SELECT 1
              FROM BitacoraEjecucionSp
              WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaPensionados')
BEGIN
UPDATE BitacoraEjecucionSp
SET besUltimoInicio = dbo.getLocalDate()
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaPensionados'
END
ELSE
BEGIN
INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
VALUES ('USP_ExecuteCARTERACalcularDeudaPresuntaPensionados', dbo.getLocalDate())
END

    DECLARE @fechaInicioEjecucion DATETIME = dbo.GetLocalDate()

    DECLARE @aportePeriodoEvaluacion NUMERIC(19, 5)
    DECLARE @deudaPresunta NUMERIC(19, 5)

    DECLARE @fechaPeriodoEvaluacion DATE

    DECLARE @estadoCartera VARCHAR(6)

    DECLARE @iRevision BIGINT
    DECLARE @iNewId BIGINT

    DECLARE @fechaActual DATETIME
    DECLARE @fechaDummy DATETIME

    DECLARE @estadoRolAfiliado VARCHAR(8)

    SET @fechaActual = dbo.GetLocalDate()
    -- Consulta @fechaDummy, para pruebas
--	select '@idRolAfiliado',@idRolAfiliado

SELECT @fechaDummy = CONVERT(DATETIME, cnsValor, 120) FROM Constante WHERE cnsNombre = 'FECHA_DUMMY'
    IF @fechaDummy IS NOT NULL
BEGIN
            SET @fechaActual = @fechaDummy
END

    -- Fechas y periodos
    SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01', 121)

    DECLARE @carteraAgrupadoraCreada AS TABLE
                                        (
                                            cagNumeroOperacion bigint,
                                            cagCartera         bigint,
                                            cagId              bigint
                                        )
	DECLARE @tablaPersonasConNovedad AS TABLE (	perid bigInt )

	-- Consulta las persona con algun radicado de novedad (RET, SLN, LMA, IRL, IGE)
	-- en el periodo regular actual o en el periodo regular anterior 
	-- ( Novedad interna de la CCF pendiente aun no esta definada desde el aplicativo )
	insert into @tablaPersonasConNovedad
	select distinct snp.snpPersona
	from Persona P
	inner join SolicitudNovedadPersona snp on p.perId = snp.snpPersona
	inner join SolicitudNovedad sn on sn.snoId = snp.snpSolicitudNovedad
	inner join Solicitud s on s.solId = sn.snoSolicitudGlobal
	where p.perId = @idPersona and s.solCanalRecepcion = 'CARTERA' AND 
	s.solTipoTransaccion in (	'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
	'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL','SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',	'RETIRO_TRABAJADOR_INDEPENDIENTE',
	'RETIRO_TRABAJADOR_DEPENDIENTE',	'RETIRO_TRABAJADOR_PENSIONADO')
	AND cast(s.solFechaCreacion as varchar(7)) BETWEEN  CONVERT(VARCHAR(7), DATEADD(mm, -1, getdate()), 20)  AND  CONVERT(VARCHAR(7), DATEADD(mm, 0, getdate()), 20)


	-- Mostramos las persona con novedades
	-- select * from @tablaPersonasConNovedad

    -- Consulta el valor del aporte a pagar, según el tipo de pensionado
SELECT @deudaPresunta = ISNULL(CASE WHEN aporte.apdSalarioBasico = 0
                       THEN roa.roaValorSalarioMesadaIngresos
                   ELSE aporte.apdSalarioBasico
                  END,  roa.roaValorSalarioMesadaIngresos)/30*isnull(CASE WHEN aporte.apdDiasCotizados = 0 THEN 30 ELSE aporte.apdDiasCotizados END,30) *
       CASE sol.solClasificacion
           WHEN 'MENOS_1_5_SM_0_6_POR_CIENTO' THEN 0.006
           WHEN 'MENOS_1_5_SM_2_POR_CIENTO' THEN 0.02
           WHEN 'MAS_1_5_SM_0_6_POR_CIENTO' THEN 0.006
           WHEN 'MAS_1_5_SM_2_POR_CIENTO' THEN 0.02
           ELSE 0
           END
FROM SolicitudAfiliacionPersona sap
         JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
         JOIN RolAfiliado roa ON roa.roaId = sap.sapRolAfiliado
    -------CALCULO DEL ULTIMO APORTE
         LEFT JOIN (SELECT *
                    FROM (
                             SELECT CASE
                                        WHEN apgx.apgPeriodoAporte = MAX(apgx.apgPeriodoAporte)
                                            over (partition by apdx.apdpersona )
                                                THEN CONVERT(NUMERIC, (MAX(apdx.apdSalarioBasico)))
                                        ELSE NULL END AS apdSalarioBasico,
                                    CASE
                                        WHEN apgx.apgPeriodoAporte =
                                             MAX(apgx.apgPeriodoAporte) over (partition by apdx.apdpersona )
                                                THEN apgPeriodoAporte
                                        ELSE NULL END AS apgPeriodoAporte,
                                    apdx.apdPersona,
                                    roaid,apdx.apdDiasCotizados
                             FROM afiliado as afix WITH (NOLOCK)
                                          INNER JOIN Persona as px WITH (NOLOCK)
                             ON afix.afiPersona = px.perId
                                 INNER JOIN AporteDetallado as apdx WITH (NOLOCK)
                             ON apdx.apdPersona = px.perId
                                 INNER JOIN AporteGeneral as apgx WITH (NOLOCK)
                             ON apgx.apgId = apdx.apdAporteGeneral
                                 INNER JOIN RolAfiliado on roaAfiliado = afiid
                                 AND roaTipoAfiliado = apdx.apdTipoCotizante
                             WHERE roaTipoAfiliado = 'PENSIONADO'
                             AND roaid = @idRolAfiliado
                             GROUP BY apdx.apdPersona, roaid, apgPeriodoAporte,apdx.apdDiasCotizados
                         ) AS aporte
                    WHERE aporte.apdSalarioBasico IS NOT NULL) aporte
                   ON aporte.roaid = roa.roaId

     -------FIN CALCULO DEL ULTIMO APORTE
WHERE (sol.solResultadoProceso = 'APROBADA' OR sol.solAnulada = 0 OR sol.solAnulada IS NULL)
  AND sap.sapRolAfiliado = @idRolAfiliado
ORDER BY sol.solFechaRadicacion

SELECT @estadoRolAfiliado = roaEstadoAfiliado FROM RolAfiliado WHERE roaId = @idRolAfiliado


---prueba
select 'deudaPresunta', @deudaPresunta as deudaPresunta, @idRolAfiliado AS idRolAfiliado,(select * from @tablaPersonasConNovedad) as [Personas_con_Novedades],@estadoRolAfiliado [estadoRolAfiliado]


IF @estadoRolAfiliado = 'INACTIVO'
BEGIN
--select 'entra por estado INACTIVO'
-- Consulta el total de aportes del pensionado INACTIVO para el periodo en evaluación
SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0)
FROM AporteDetallado apd WITH (NOLOCK)
                     JOIN AporteGeneral apg WITH (NOLOCK) ON apd.apdAporteGeneral = apg.apgId
WHERE apd.apdPersona = @idPersona
  AND apd.apdTipoCotizante = 'PENSIONADO'
  AND apg.apgPeriodoAporte = @periodoEvaluacion
  AND apd.apdEstadoRegistroAporteArchivo = 'NO_VALIDADO_BD_APROBADO'
END
ELSE
BEGIN
--select 'entra por estado INACTIVO ELSE'
-- Consulta el total de aportes del pensionado para el periodo en evaluación
SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0)
FROM AporteDetallado apd WITH (NOLOCK)
                     JOIN AporteGeneral apg WITH (NOLOCK) ON apd.apdAporteGeneral = apg.apgId
WHERE apd.apdPersona = @idPersona
  AND apd.apdTipoCotizante = 'PENSIONADO'
  AND apg.apgPeriodoAporte = @periodoEvaluacion
  AND apd.apdEstadoRegistroAporteArchivo = 'OK'
END

    -- Estado de Cartera para el aportante
    SET @estadoCartera = 'AL_DIA'

--select '@aportePeriodoEvaluacion', @aportePeriodoEvaluacion as aportePeriodoEvaluacion

    IF (@aportePeriodoEvaluacion = 0 OR (@deudaPresunta > @aportePeriodoEvaluacion))
		BEGIN
		--SELECT 'ENTRA A MOROSO'
			SET @estadoCartera = 'MOROSO'
		END

IF @crearNuevoRegistro = 1 -- Crea un nuevo registro en Cartera
BEGIN
--SELECT 'ENTRA A CREAR REGISTRO CARTERA'
DECLARE @carteraCreada AS TABLE
                                      (
                                          carDeudaPresunta         numeric(19, 5),
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

            IF @deudaPresunta > @aportePeriodoEvaluacion -- Indica que el pensionado entra en Cartera
BEGIN
--SELECT 'LINEA 145 deudaPresunta', @deudaPresunta AS deudaPresunta
    IF EXISTS(SELECT 1
                              FROM Cartera
                              WHERE carTipoLineaCobro = 'LC5'
                                AND carTipoSolicitante = 'PENSIONADO'
                                AND carPersona = @idPersona
                                AND carEstadoOperacion = 'VIGENTE')
BEGIN
                            -- En este caso, el aportante ya se encuentra en una acción de cobro sobre la misma línea de cobro
                            -- se toma el siguiente ID de secuencia
EXEC USP_GET_GestorValorSecuencia 1, 'Sec_cartera', @iNewId OUTPUT

                            INSERT INTO Cartera
                                --INSERT INTO cartera.CarteraTemp
                            (carDeudaPresunta,
                             carEstadoCartera,
                             carEstadoOperacion,
                             carFechaCreacion,
                             carPersona,
                             carMetodo,
                             carPeriodoDeuda,
                             carTipoAccionCobro,
                             carTipoDeuda,
                             carTipoLineaCobro,
                             carTipoSolicitante,
                             carFechaAsignacionAccion,
                             carId)
                            OUTPUT INSERTED.carDeudaPresunta,
                                   INSERTED.carEstadoCartera,
                                   INSERTED.carEstadoOperacion,
                                   INSERTED.carFechaCreacion,
                                   INSERTED.carPersona,
                                   INSERTED.carMetodo,
                                   INSERTED.carPeriodoDeuda,
                                   INSERTED.carTipoAccionCobro,
                                   INSERTED.carTipoDeuda,
                                   INSERTED.carTipoLineaCobro,
                                   INSERTED.carTipoSolicitante,
                                   INSERTED.carFechaAsignacionAccion,
                                   INSERTED.carId
                                INTO @carteraCreada (carDeudaPresunta,
                                                     carEstadoCartera,
                                                     carEstadoOperacion,
                                                     carFechaCreacion,
                                                     carPersona,
                                                     carMetodo,
                                                     carPeriodoDeuda,
                                                     carTipoAccionCobro,
                                                     carTipoDeuda,
                                                     carTipoLineaCobro,
                                                     carTipoSolicitante,
                                                     carFechaAsignacionAccion,
                                                     carId)
SELECT TOP 1 @deudaPresunta,
        carEstadoCartera,
       'VIGENTE',
       @fechaActual,
       carPersona,
       carMetodo,
       @fechaPeriodoEvaluacion,
       carTipoAccionCobro,
       carTipoDeuda,
       carTipoLineaCobro,
       'PENSIONADO',
       carFechaAsignacionAccion,
       @iNewId
FROM Cartera
WHERE carTipoLineaCobro = 'LC5'
  AND carTipoSolicitante = 'PENSIONADO'
  AND carPersona = @idPersona
  AND carEstadoOperacion = 'VIGENTE'
END
ELSE
BEGIN
                            -- se toma el siguiente ID de secuencia
EXEC USP_GET_GestorValorSecuencia 1, 'Sec_cartera', @iNewId OUTPUT

                            INSERT INTO Cartera
                                --INSERT INTO cartera.CarteraTemp
                            (carDeudaPresunta,
                             carEstadoCartera,
                             carEstadoOperacion,
                             carFechaCreacion,
                             carPersona,
                             carMetodo,
                             carPeriodoDeuda,
                             carTipoAccionCobro,
                             carTipoDeuda,
                             carTipoLineaCobro,
                             carTipoSolicitante,
                             carid)
                            OUTPUT INSERTED.carDeudaPresunta,
                                   INSERTED.carEstadoCartera,
                                   INSERTED.carEstadoOperacion,
                                   INSERTED.carFechaCreacion,
                                   INSERTED.carPersona,
                                   INSERTED.carMetodo,
                                   INSERTED.carPeriodoDeuda,
                                   INSERTED.carTipoAccionCobro,
                                   INSERTED.carTipoDeuda,
                                   INSERTED.carTipoLineaCobro,
                                   INSERTED.carTipoSolicitante,
                                   INSERTED.carid
                                INTO @carteraCreada (carDeudaPresunta,
                                                     carEstadoCartera,
                                                     carEstadoOperacion,
                                                     carFechaCreacion,
                                                     carPersona,
                                                     carMetodo,
                                                     carPeriodoDeuda,
                                                     carTipoAccionCobro,
                                                     carTipoDeuda,
                                                     carTipoLineaCobro,
                                                     carTipoSolicitante,
                                                     carid)
                            VALUES (@deudaPresunta - @aportePeriodoEvaluacion,
                                    @estadoCartera,
                                    'VIGENTE',
                                    @fechaActual,
                                    @idPersona,
                                    NULL,
                                    @fechaPeriodoEvaluacion,
                                    NULL,
                                    'INEXACTITUD',
                                    'LC5',
                                    'PENSIONADO',
                                    @iNewId)
END
END

            DECLARE @iCantidadRegistros BIGINT
SELECT @iCantidadRegistros = COUNT(carId) FROM @carteraCreada
                                                   --SELECT 'lINEA 277 deudaPresunta', @deudaPresunta AS deudaPresunta
                                                   -- se escribe la traza de auditoría
                                                   IF @iCantidadRegistros > 0
BEGIN
EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', @iCantidadRegistros,
                         '', 'USUARIO_SISTEMA', @iRevision OUTPUT

                    INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera, carEstadoOperacion,
                                                 carFechaCreacion, carPersona, carMetodo, carPeriodoDeuda,
                                                 carTipoAccionCobro, carTipoDeuda, carTipoLineaCobro,
                                                 carTipoSolicitante, carId, REV, REVTYPE)
SELECT carDeudaPresunta,
       carEstadoCartera,
       carEstadoOperacion,
       carFechaCreacion,
       carPersona,
       carMetodo,
       carPeriodoDeuda,
       carTipoAccionCobro,
       carTipoDeuda,
       carTipoLineaCobro,
       carTipoSolicitante,
       carId,
       @iRevision,
       0
FROM @carteraCreada

    -- Se crea cartera agrupadora
    INSERT INTO @carteraAgrupadoraCreada(cagCartera, cagId, cagNumeroOperacion)
SELECT carId,
    NEXT VALUE FOR Sec_CarteraAgrupadora,
    NEXT VALUE FOR Sec_CarteraAgrupadora_NumeroOperacion
FROM @carteraCreada

    -- Se crea cartera agrupadora
INSERT INTO CarteraAgrupadora(cagId, cagCartera, cagNumeroOperacion)
SELECT cagId,
       cagCartera,
       cagNumeroOperacion
FROM @carteraAgrupadoraCreada

    -- Se crea el histórico de CarteaAgrupadora
    EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.CarteraAgrupadora',
                         @iCantidadRegistros, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

INSERT INTO aud.CarteraAgrupadora_aud (cagId, cagCartera, cagNumeroOperacion, REV, REVTYPE)
SELECT cagId,
       cagCartera,
       cagNumeroOperacion,
       @iRevision,
       0
FROM @carteraAgrupadoraCreada

END
END
ELSE -- Actualiza el registro en Cartera
BEGIN
            IF @idCartera IS NULL
BEGIN
                    PRINT 'No se recibió el identificador de cartera'
                    RETURN
END

            IF @aportePeriodoEvaluacion >= @deudaPresunta -- Indica que el periodo ya no está en mora
BEGIN
UPDATE Cartera
SET carEstadoOperacion = 'NO_VIGENTE',
    carDeudaPresunta   = 0,
    carEstadoCartera   = 'AL_DIA'
WHERE carId = @idCartera

--INSERT INTO cartera.CarteraTemp (carId, carEstadoOperacion, carDeudaPresunta, carEstadoCartera)
--VALUES (@idCartera, 'NO_VIGENTE', 0, 'AL_DIA')
END
ELSE -- Indica que el aportante continúa en cartera y se actualiza la deuda presunta
BEGIN
UPDATE Cartera
SET carDeudaPresunta = isnull(@deudaPresunta,0) - isnull(@aportePeriodoEvaluacion,0),  ---20240531 GLPI 80344
    carEstadoCartera = @estadoCartera
WHERE carId = @idCartera  

--INSERT INTO cartera.CarteraTemp (carId, carDeudaPresunta, carEstadoCartera)
--VALUES (@idCartera, @deudaPresunta - @aportePeriodoEvaluacion, @estadoCartera)
END

EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', 1, '', 'USUARIO_SISTEMA',
                 @iRevision OUTPUT

            INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera, carEstadoOperacion, carFechaCreacion,
                                         carPersona, carMetodo, carPeriodoDeuda,
                                         carTipoAccionCobro, carTipoDeuda, carTipoLineaCobro, carTipoSolicitante, carId,
                                         REV, REVTYPE)
SELECT carDeudaPresunta,
       carEstadoCartera,
       carEstadoOperacion,
       carFechaCreacion,
       carPersona,
       carMetodo,
       carPeriodoDeuda,
       carTipoAccionCobro,
       carTipoDeuda,
       carTipoLineaCobro,
       carTipoSolicitante,
       carId,
       @iRevision,
       1
FROM Cartera
WHERE carId = @idCartera
END



	-- actualiza la cartera para las personas que presentan una novedad de cartera
	IF (@deudaPresunta > 0 AND (select 1 from @tablaPersonasConNovedad ) = 1 )
	BEGIN
		UPDATE Cartera
			SET carEstadoOperacion = 'NO_VIGENTE',carDeudaPresunta = 0,carEstadoCartera   = 'AL_DIA'
		WHERE carId = @idCartera AND carPersona in (select * from @tablaPersonasConNovedad)



		-- se crea la auditoria de la cartera
            EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', 1, '', 'USUARIO_SISTEMA',
                 @iRevision OUTPUT

            INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera, carEstadoOperacion, carFechaCreacion,
                                         carPersona, carMetodo, carPeriodoDeuda,
                                         carTipoAccionCobro, carTipoDeuda, carTipoLineaCobro, carTipoSolicitante, carId,
                                         REV, REVTYPE)
            SELECT carDeudaPresunta,
                   carEstadoCartera,
                   carEstadoOperacion,
                   carFechaCreacion,
                   carPersona,
                   carMetodo,
                   carPeriodoDeuda,
                   carTipoAccionCobro,
                   carTipoDeuda,
                   carTipoLineaCobro,
                   carTipoSolicitante,
                   carId,
                   @iRevision,
                   1
            FROM Cartera
            WHERE carId = @idCartera

	END

EXEC USP_ExecuteCARTERACalculoDeudaParcial @idCartera

INSERT INTO TiempoProcesoCartera
(tpcNombreProceso, tpcFechaInicio, tpcFechaFin, tpcRegistros, tpcMensaje)
VALUES ('USP_ExecuteCARTERACalcularDeudaPresuntaPensionados', @fechaInicioEjecucion, dbo.getLocalDate(), NULL,
        NULL);

-- se marca en bitácora el fin de la ejecución exitosa ylos registros afectados
UPDATE BitacoraEjecucionSp
SET besUltimoExito        = dbo.getLocalDate(),
    besRegistrosAfectados = 1
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaPensionados'
END TRY
BEGIN CATCH
    -- se marca en bitácora el fin de la ejecución fallida
UPDATE BitacoraEjecucionSp
SET besUltimoFallo        = dbo.getLocalDate(),
    besRegistrosAfectados = 0
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACalcularDeudaPresuntaPensionados'

DECLARE @ErrorMessage NVARCHAR(4000),
        @ErrorSeverity INT,
        @ErrorState INT

SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaPensionados] | ' + ERROR_MESSAGE(),
       @ErrorSeverity = ERROR_SEVERITY(),
       @ErrorState = ERROR_STATE();

RAISERROR (@ErrorMessage, -- Message text.
        @ErrorSeverity, -- Severity.
        @ErrorState -- State.
        );
END CATCH