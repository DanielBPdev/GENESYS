CREATE OR ALTER     PROCEDURE [dbo].[USP_ExecuteCARTERACrearCartera]
AS

SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON



BEGIN TRY
  -- se agrega registro en bitacora de ejecución
IF EXISTS(SELECT 1 FROM BitacoraEjecucionSp WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACrearCartera')
BEGIN
UPDATE BitacoraEjecucionSp
SET besUltimoInicio = dbo.getLocalDate()
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACrearCartera'
END
ELSE
BEGIN
INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
VALUES ('USP_ExecuteCARTERACrearCartera', dbo.getLocalDate())
END

  DECLARE @fechaInicioEjecucion DATETIME = dbo.GetLocalDate()
  DECLARE @cantidadRegistros BIGINT = 0

  DECLARE @diaHabilVencimientoAporte INT

  DECLARE @idPersona BIGINT
  DECLARE @idRolAfiliado BIGINT
  DECLARE @idEmpleador BIGINT
  DECLARE @idEmpresa BIGINT

  DECLARE @fechaActual DATE
  DECLARE @fechaInicioMesActual DATE
  DECLARE @fechaDummy DATE -- Para pruebas

  DECLARE @periodoEvaluacionMesActual VARCHAR(7)
  DECLARE @periodoEvaluacionMesVencido VARCHAR(7)
  DECLARE @periodoEvaluacion VARCHAR(7)

  DECLARE @aportantesCursor AS CURSOR

  DECLARE @activaParametrizacionPersonas BIT
  DECLARE @activaParametrizacionLC2 BIT
  DECLARE @activaParametrizacionLC3 BIT
  DECLARE @ejecutarCartera BIT

  -- Fechas y periodos
  SET @fechaActual = dbo.GetLocalDate()

  -- Consulta @fechaDummy, para pruebas
SELECT @fechaDummy = CONVERT(DATE, cnsValor, 111) FROM Constante WHERE cnsNombre = 'FECHA_DUMMY'
  IF @fechaDummy IS NOT NULL
BEGIN
          SET @fechaActual = @fechaDummy
END

  SET @fechaInicioMesActual = DATEADD(dd, 1, EOMonth(@fechaActual, -1))
  SET @periodoEvaluacionMesActual = CONVERT(VARCHAR(7), @fechaActual, 20)
  SET @periodoEvaluacionMesVencido = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaActual), 20)
  --SELECT 'IMPRESION 1', @fechaInicioMesActual, '-', @periodoEvaluacionMesActual, '-', @periodoEvaluacionMesVencido
  -- Día hábil de vencimiento de aportes
SELECT @diaHabilVencimientoAporte =
     (DATEDIFF(dd, @fechaInicioMesActual, @fechaActual) + 1)
         - (DATEDIFF(wk, @fechaInicioMesActual, @fechaActual) * 2)
         - (CASE WHEN DATEPART(dw, @fechaInicioMesActual) = 1 THEN 1 ELSE 0 END)
         - (CASE WHEN DATEPART(dw, @fechaActual) = 7 THEN 1 ELSE 0 END)
         - COUNT(0)
         - 1
FROM DiasFestivos pif
WHERE DATEPART(dw, pif.pifFecha) NOT IN (7, 1)
AND pif.pifFecha BETWEEN @fechaInicioMesActual AND @fechaActual

  SET @ejecutarCartera = CASE WHEN DATEPART(dw, @fechaActual) IN (7, 1) THEN 0 ELSE 1 END
  --SELECT 'IMPRESION 2', @diaHabilVencimientoAporte
  IF @ejecutarCartera = 1
BEGIN
SELECT @ejecutarCartera = 0
FROM DiasFestivos pif
WHERE pif.pifFecha = @fechaActual
END



---------=================


-- Personas con una novedad vigente para el periodo del tipo:
-- 		Incapacidad (IGE, IRL)
-- 		Licencia de Maternidad (LMA)
-- 		Suspensión Temporal del Contrato (SLN).
DECLARE @tablaPersonasConNovedad AS TablaPersonaIdType

              INSERT INTO @tablaPersonasConNovedad (perId)
SELECT per.perId
FROM SolicitudNovedadPersona snp
       INNER JOIN SolicitudNovedad sno ON snoId = snpSolicitudNovedad
       INNER JOIN NovedadDetalle nop ON nopSolicitudNovedad = snoId
       INNER JOIN Solicitud sol ON solId = snoSolicitudGlobal
       INNER JOIN Persona per ON per.perId = snpPersona

WHERE solTipoTransaccion IN (
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL',
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB',
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB',
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB',
                           'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB',
                           'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL',
                           'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB',
                           'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB',
                           'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',
                           'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB',
                           'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL',
                           'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB'
  )
AND (
          CONVERT(VARCHAR(7), nopFechaInicio, 120) = @periodoEvaluacionMesVencido
      OR CONVERT(VARCHAR(7), nopFechaFin, 120) = @periodoEvaluacionMesVencido
  )
    ---*******INICIA GLPI 62206
UNION
     SELECT snppersona
          FROM solicitud
    INNER JOIN solicitudnovedad on snoSolicitudGlobal= solid
    INNER JOIN solicitudnovedadpersona on snpsolicitudnovedad= snoid
    INNER JOIN rolafiliado on roaid = snpRolAfiliado
         WHERE solTipoTransaccion = 'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL'
           AND roaFechaInicioCondicionVeterano IS NOT NULL
           AND roaclasetrabajador = 'VETERANO_FUERZA_PUBLICA'
           AND CONVERT(VARCHAR(7), roaFechaInicioCondicionVeterano, 120) <= @periodoEvaluacionMesVencido
           AND CONVERT(VARCHAR(7), roaFechaFinCondicionVeterano, 120) >= @periodoEvaluacionMesVencido


UNION

select perid from persona
    join afiliado on afipersona = perId
    join rolafiliado on roaafiliado = afiId
    join solicitudAfiliacionpersona on sapRolAfiliado = roaid
    join solicitud on sapSolicitudGlobal = solid
where solTipoTransaccion like '%nueva%'
    and roaClaseTrabajador = 'VETERANO_FUERZA_PUBLICA'
    and sapEstadoSolicitud = 'CERRADA'
    and solResultadoProceso = 'APROBADA'
    AND CONVERT(VARCHAR(7), roaFechaInicioCondicionVeterano, 120) <= @periodoEvaluacionMesVencido
    AND CONVERT(VARCHAR(7), roaFechaFinCondicionVeterano, 120) >= @periodoEvaluacionMesVencido

    ---*******FIN GLPI 62206

UNION

SELECT /*DATEDIFF(DAY, rdnfechainicionovedad,'2022-04-30') AS DiasIncap,*/ IdPersonaNov
FROM (
       SELECT CASE WHEN  rdnfechainicionovedad  = MAX(rdnfechainicionovedad )
                       OVER (PARTITION BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante )
                         THEN rdnfechainicionovedad
                   ELSE rdnFechaFinNovedad END AS rdnfechainicionovedad,
              regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
              redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
              rdnTipoNovedad,	rdnAccionNovedad, rdnFechaFinNovedad ,perId AS IdPersonaNov
       FROM [pila].[RegistroDetallado]
           INNER JOIN [pila].[RegistroGeneral]
       ON redRegistroGeneral = regid
           INNER JOIN [pila].[RegistroDetalladoNovedad]
           ON rdnRegistroDetallado = redid
           INNER JOIN Persona pn
           ON pn.perTipoIdentificacion = redTipoIdentificacionCotizante
           AND pn.perNumeroIdentificacion = redNumeroIdentificacionCotizante
       WHERE --redDiasIRL IS NOT NULL --OR redFechaInicioIRL
           ( (redNovIGE = 'X') OR (redNovLMA = 'X') OR (redDiasIRL > 0) OR (redNovSLN = 'X') OR (redNovRetiro = 'X')/* cambio glpi 93793 */) -- Cambio GLPI 76696
   ) AS NovPila
WHERE CONVERT(VARCHAR(7),rdnfechainicionovedad, 120) =  @periodoEvaluacionMesVencido 

---------=================

  --SELECT 'impresion 3', @ejecutarCartera
  IF @ejecutarCartera = 1
BEGIN
BEGIN TRY
              -----------------------------------------------------------------------------------------------------------------
              ---------------------------------------------- INICIA PENSIONADOS -----------------------------------------------
              -----------------------------------------------------------------------------------------------------------------

SELECT @activaParametrizacionPersonas = p.pcrActiva
FROM ParametrizacionCriterioGestionCobro p
WHERE p.pcrLineaCobro = 'LC5'
AND p.pcrAccion = 'AUTOMATICA'

  IF @activaParametrizacionPersonas = 1
BEGIN
                      -- Consulta la lista de pensionados, posibles candidatos a entrar en Cartera
SET @aportantesCursor = CURSOR FAST_FORWARD FOR

SELECT afiPersona,  roaId
 FROM
            (
            SELECT afi.afiPersona, roa.roaId
              FROM RolAfiliado roa
              JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
             WHERE roa.roaEstadoAfiliado = 'ACTIVO'
               AND roa.roaTipoAfiliado = 'PENSIONADO'
               AND roa.roaDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
               AND CONVERT(VARCHAR(7), roa.roaFechaAfiliacion, 20) <>  CONVERT(VARCHAR(7), @fechaActual, 20)
               AND 0 = (SELECT COUNT(0)
                        FROM Cartera car
                        WHERE car.carPersona = afi.afiPersona
                          AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacionMesVencido
                          AND car.carTipoSolicitante = 'PENSIONADO')

             EXCEPT


              SELECT pt.perId, roaId
                FROM aportedetallado
          INNER JOIN persona pt ON pt.perid = apdPersona
          INNER JOIN afiliado ON afipersona = pt.perid
          INNER JOIN RolAfiliado ON roaAfiliado= afiid
          INNER JOIN AporteGeneral ON apgid = apdAporteGeneral
               WHERE apdTipoCotizante = 'PENSIONADO'
                 AND apgPeriodoAporte = @periodoEvaluacionMesVencido
                 AND apdAporteObligatorio >0
                 ---PARA EVITAR QUE RECORRA A TODOS CAMBIO OLGA VEGA 20230719 SACANDO A LOS QUE TIENEN PAGO YA REALIZADO Y NO LOS EVALUE
            ) PENSIONADOS


  OPEN @aportantesCursor
                      FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idRolAfiliado

  -- Recorre la lista de pensionados
  WHILE @@FETCH_STATUS = 0
BEGIN
                              --select 'entra pensionados:::',
                                     --@idPersona                   as idPersona,
                                     --@idRolAfiliado               as idRolAfiliado,
                                     --@periodoEvaluacionMesVencido as periodoEvaluacionMesVencido
                              SET @cantidadRegistros = @cantidadRegistros + 1
                              EXEC dbo.USP_ExecuteCARTERACalcularDeudaPresuntaPensionados @idPersona, @idRolAfiliado,
                                   @periodoEvaluacionMesVencido, 1, NULL,@tablaPersonasConNovedad
                              FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idRolAfiliado
END

CLOSE @aportantesCursor
  DEALLOCATE @aportantesCursor
END
              -----------------------------------------------------------------------------------------------------------------
              --------------------------------------------- FINALIZA PENSIONADOS ----------------------------------------------
              -----------------------------------------------------------------------------------------------------------------
END TRY
BEGIN CATCH
INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
              VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERACrearCartera S:PENSIONADOS', ERROR_MESSAGE());
              THROW;
END CATCH

BEGIN TRY
              -----------------------------------------------------------------------------------------------------------------
              ---------------------------------------------- INICIA INDEPENDIENTES --------------------------------------------
              -----------------------------------------------------------------------------------------------------------------
SELECT @activaParametrizacionPersonas = p.pcrActiva
FROM ParametrizacionCriterioGestionCobro p
WHERE p.pcrLineaCobro = 'LC4'
AND p.pcrAccion = 'AUTOMATICA'

  IF @activaParametrizacionPersonas = 1
    BEGIN
                            -- Consulta la lista de independientes, posibles candidatos a entrar en Cartera
    SET @aportantesCursor = CURSOR FAST_FORWARD FOR

    SELECT afiPersona,  roaId,  periodoEvaluacionMesVencido
      FROM (
                SELECT afiPersona,  roaId,  periodoEvaluacionMesVencido
                  FROM (
                SELECT afi.afiPersona, roa.roaId, @periodoEvaluacionMesVencido AS periodoEvaluacionMesVencido
                 FROM RolAfiliado roa
                 JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
                WHERE roa.roaEstadoAfiliado = 'ACTIVO'
                  AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
                  AND roa.roaDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
                  AND 0 = (SELECT COUNT(0)
                            FROM Cartera car
                            WHERE car.carPersona = afi.afiPersona
                              AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacionMesVencido
                              AND car.carTipoSolicitante = 'INDEPENDIENTE'
                            )
                  AND (roa.roaOportunidadPago = 'MES_ACTUAL' OR roa.roaOportunidadPago IS NULL)

                UNION

                SELECT afi.afiPersona, roa.roaId, @periodoEvaluacionMesVencido AS periodoEvaluacionMesVencido
                  FROM RolAfiliado roa
                  JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
                 WHERE roa.roaEstadoAfiliado = 'ACTIVO'
                   AND roa.roaDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
                   AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
                   AND 0 = (SELECT COUNT(0)
                             FROM Cartera car
                            WHERE car.carPersona = afi.afiPersona
                              AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacionMesVencido
                              AND car.carTipoSolicitante = 'INDEPENDIENTE'
                            )
                  AND roa.roaOportunidadPago = 'MES_VENCIDO'
                  ) PENS

                  EXCEPT

                SELECT pt.perId, roaId    , @periodoEvaluacionMesVencido
                  FROM aportedetallado
            INNER JOIN persona pt ON pt.perid = apdPersona
            INNER JOIN afiliado ON afipersona = pt.perid
            INNER JOIN RolAfiliado ON roaAfiliado= afiid
            INNER JOIN AporteGeneral ON apgid = apdAporteGeneral
                  WHERE apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
                    AND apgPeriodoAporte = @periodoEvaluacionMesVencido
                    AND apdAporteObligatorio >0

        ---PARA EVITAR QUE RECORRA A TODOS CAMBIO OLGA VEGA 20230719 SACANDO A LOS QUE TIENEN PAGO YA REALIZADO Y NO LOS EVALUE
        ) PENSIONADOS

        OPEN @aportantesCursor
FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idRolAfiliado, @periodoEvaluacion

        -- Recorre la lista de independientes
    WHILE @@FETCH_STATUS = 0

    BEGIN
                                    --select 'entra independientes:::'
                                    SET @cantidadRegistros = @cantidadRegistros + 1
                                    EXEC dbo.USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes @idPersona,
                                         @idRolAfiliado, @periodoEvaluacion, 1, NULL,@tablaPersonasConNovedad
                                    FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idRolAfiliado, @periodoEvaluacion
    END

CLOSE @aportantesCursor
  DEALLOCATE @aportantesCursor
END
              -----------------------------------------------------------------------------------------------------------------
              --------------------------------------------- FINALIZA INDEPENDIENTES -------------------------------------------
              -----------------------------------------------------------------------------------------------------------------
END TRY
BEGIN CATCH
INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
              VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERACrearCartera S:INDEPENDIENTES', ERROR_MESSAGE());
              THROW;
END CATCH

BEGIN TRY
              -----------------------------------------------------------------------------------------------------------------
              ---------------------------------------------- INICIA EMPLEADORES -----------------------------------------------
              -----------------------------------------------------------------------------------------------------------------

SELECT @activaParametrizacionLC2 = p.pcrActiva
FROM ParametrizacionCriterioGestionCobro p
WHERE p.pcrLineaCobro = 'LC2'
AND p.pcrAccion = 'AUTOMATICA'

SELECT @activaParametrizacionLC3 = p.pcrActiva
FROM ParametrizacionCriterioGestionCobro p
WHERE p.pcrLineaCobro = 'LC3'
AND p.pcrAccion = 'AUTOMATICA'


    -- Consulta la lista de empleadores, posibles candidatos a entrar en Cartera
  SET @aportantesCursor = CURSOR FAST_FORWARD FOR
SELECT per.perId, emd.empId, emp.empId
FROM Empleador emd
       JOIN Empresa emp ON emd.empEmpresa = emp.empId
       JOIN Persona per ON emp.empPersona = per.perId
       LEFT JOIN (
  SELECT car.carPersona
  FROM Cartera car
  WHERE CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacionMesVencido
    AND car.carTipoSolicitante = 'EMPLEADOR'
) AS carerasPer ON carerasPer.carPersona = per.perId
WHERE emd.empEstadoEmpleador = 'ACTIVO'
--- AND PER.perNumeroIdentificacion= '860007738'
AND emd.empDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
AND CONVERT(VARCHAR(7), emd.empFechaCambioEstadoAfiliacion, 20) <>
    CONVERT(VARCHAR(7), @fechaActual, 20)
AND carerasPer.carPersona IS NULL
group by per.perId, emd.empId, emp.empId



  OPEN @aportantesCursor
              FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idEmpleador, @idEmpresa

  -- Recorre la lista de empleadores
  WHILE @@FETCH_STATUS = 0
BEGIN

                      SET @cantidadRegistros = @cantidadRegistros + 1
                    ---print @cantidadRegistros
                      --select 'EMPLEADOR ',
                             --@idEmpleador                 as idEmpleador,
                             --@idEmpresa                   as idEmpresa,
                             --@idPersona                   as idPersona,
                             --@periodoEvaluacionMesVencido as periodoEvaluacionMesVencido,
                             --@activaParametrizacionLC2
                             --                             as activaParametrizacionLC2,
                             --@activaParametrizacionLC3    as activaParametrizacionLC3
                      -- LOGS DE VALIDAION
                      --SELECT ' lista de empleadores ',
                      --       @idPersona,
                      --       @idEmpleador,
                      --       @idEmpresa,
                      --       @periodoEvaluacionMesVencido,
                      --       1,
                      --       NULL,
                      --       @activaParametrizacionLC2,
                      --       @activaParametrizacionLC3
                      -- FIN LOGS DE VALIDACION
                      --SELECT @activaParametrizacionLC2, @activaParametrizacionLC3

                      EXEC dbo.USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores @idPersona, @idEmpleador,
                           @idEmpresa, @periodoEvaluacionMesVencido, 1, NULL,
                           @tablaPersonasConNovedad, @activaParametrizacionLC2, @activaParametrizacionLC3

                      FETCH NEXT FROM @aportantesCursor INTO @idPersona, @idEmpleador, @idEmpresa
END

CLOSE @aportantesCursor
  DEALLOCATE @aportantesCursor
-----------------------------------------------------------------------------------------------------------------
--------------------------------------------- FINALIZA EMPLEADORES ----------------------------------------------
-----------------------------------------------------------------------------------------------------------------
END TRY
BEGIN CATCH
INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
              VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERACrearCartera S:EMPLEADORES', ERROR_MESSAGE());
              THROW;
END CATCH

begin try
              -- se marca en bitácora el fin de la ejecución exitosa ylos registros afectados
UPDATE BitacoraEjecucionSp
SET besUltimoExito = dbo.getLocalDate()
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACrearCartera'

END TRY
BEGIN CATCH
INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
              VALUES (dbo.getLocalDate(), 'ssss S:4', ERROR_MESSAGE());
              THROW;
END CATCH
END

INSERT INTO TiempoProcesoCartera
(tpcNombreProceso, tpcFechaInicio, tpcFechaFin, tpcRegistros, tpcMensaje)
VALUES ('USP_ExecuteCARTERACrearCartera', @fechaInicioEjecucion, dbo.getLocalDate(), @cantidadRegistros, NULL);

END TRY
BEGIN CATCH
  -- se marca en bitácora el fin de la ejecución fallida
UPDATE BitacoraEjecucionSp
SET besUltimoFallo = dbo.getLocalDate()
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERACrearCartera'

DECLARE @ErrorMessage NVARCHAR(4000),
      @ErrorSeverity INT,
      @ErrorState INT

SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERACrearCartera] | ' + ERROR_MESSAGE(),
     @ErrorSeverity = ERROR_SEVERITY(),
     @ErrorState = ERROR_STATE();



INSERT INTO CarteraLogError (cleFecha, cleNombreSP, cleaMensaje)
VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERACrearCartera', ERROR_MESSAGE())

  RAISERROR (@ErrorMessage, -- Message text.
      @ErrorSeverity, -- Severity.
      @ErrorState -- State.
      );

END CATCH