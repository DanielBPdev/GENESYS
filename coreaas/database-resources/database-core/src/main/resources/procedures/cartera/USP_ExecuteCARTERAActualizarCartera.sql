/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 31/05/2024 5:02:08 p. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 2024-05-31 1:27:43 PM ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 18/04/2024 12:26:39 p. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 2024-02-02 10:17:58 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 2024-01-17 9:58:54 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 2024-01-16 12:10:01 PM ******/
/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAActualizarCartera]    Script Date: 2023-11-17 10:11:06 AM **/
----EXEC USP_ExecuteCARTERAActualizarCartera 'CEDULA_CIUDADANIA', '1100395284','2024-04','INDEPENDIENTE'
----EXEC USP_ExecuteCARTERAActualizarCartera 'NIT', '1073508460','2023-09','EMPLEADOR'
CREATE OR ALTER   PROCEDURE [dbo].[USP_ExecuteCARTERAActualizarCartera]
	@tipoIdentificacion VARCHAR(20),
	@numeroIdentificacion VARCHAR(16),
	@periodoEvaluacion VARCHAR(7),
	@tipoAportante VARCHAR(13)
AS

BEGIN TRY
	-- se agrega registro en bitacora de ejecución
IF EXISTS(SELECT 1 FROM BitacoraEjecucionSp WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCartera')
BEGIN
UPDATE BitacoraEjecucionSp
SET besUltimoInicio = dbo.getLocalDate()
WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCartera'
END
ELSE
BEGIN
INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
VALUES ('USP_ExecuteCARTERAActualizarCartera', dbo.getLocalDate())
END

-- Se Crea un Log con los parametros que recibe el objeto para dejar una traza de que recibe al momento de la ejecución  -- GLPI 90466
IF EXISTS (select 1 from INFORMATION_SCHEMA.TABLES t where t.TABLE_NAME = 'BitacoraActualizarCarteraLog')
BEGIN 
	INSERT INTO BitacoraActualizarCarteraLog (tipoDocumento,numeroDocumento,periodo,tipoSolicitante,fecha) 
	VALUES(@tipoIdentificacion,@numeroIdentificacion,@periodoEvaluacion,@tipoAportante,dbo.GetLocalDate())
END

	DECLARE @idPersona BIGINT
	DECLARE @idRolAfiliado BIGINT
	DECLARE @idEmpleador BIGINT
	DECLARE @idEmpresa BIGINT
	DECLARE @idCartera BIGINT
	DECLARE @periodo VARCHAR(7)

	 

	IF @tipoAportante IN ('PENSIONADO', 'INDEPENDIENTE') -- Actualiza deuda para independientes y pensionados
BEGIN
		SELECT
				@idPersona = per.perId,
				@idCartera = car.carId,
				@idRolAfiliado = roa.roaId
			FROM Persona per
			JOIN Afiliado afi ON afi.afiPersona = per.perId
			JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			 
			JOIN Cartera car ON car.carPersona = per.perId
		WHERE per.perTipoIdentificacion = @tipoIdentificacion
		  AND per.perNumeroIdentificacion = @numeroIdentificacion
		  AND car.carTipoSolicitante = @tipoAportante
		  AND  CASE WHEN roaTipoAfiliado = 'PENSIONADO' THEN 'PENSIONADO'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE'------20240531 GLPI 80344
					ELSE roaTipoAfiliado END = @tipoAportante
		  AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacion
		  AND car.carEstadoOperacion = 'VIGENTE'

    IF @idPersona IS NOT NULL AND @idRolAfiliado IS NOT NULL AND @idCartera IS NOT NULL
	BEGIN
			IF @tipoAportante = 'PENSIONADO'
	BEGIN
	EXEC dbo.USP_ExecuteCARTERACalcularDeudaPresuntaPensionados @idPersona, @idRolAfiliado, @periodoEvaluacion, 0, @idCartera
END
	ELSE
BEGIN
	EXEC dbo.USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes @idPersona, @idRolAfiliado, @periodoEvaluacion, 0, @idCartera
END
	END
END
ELSE -- Actualiza deuda para empleadores
BEGIN

		-- Personas con una novedad vigente para el periodo del tipo:
		-- 		Incapacidad (IGE, IRL)
		-- 		Licencia de Maternidad (LMA)
		-- 		Suspensión Temporal del Contrato (SLN).
		DECLARE @tablaPersonasConNovedad AS TablaPersonaIdType

		DECLARE @carterasCursor AS CURSOR
		SET @carterasCursor = CURSOR FAST_FORWARD FOR
		SELECT
			per.perId,
			emd.empId,
			emp.empId,
			car.carId,
			CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20)
		FROM Empleador emd
				 JOIN Empresa emp ON emd.empEmpresa = emp.empId
				 JOIN Persona per ON emp.empPersona = per.perId
				 JOIN Cartera car ON car.carPersona = per.perId
		WHERE per.perTipoIdentificacion = @tipoIdentificacion
		  AND per.perNumeroIdentificacion = @numeroIdentificacion
		  AND car.carTipoSolicitante = @tipoAportante
		  AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) >= @periodoEvaluacion
		  AND car.carEstadoOperacion = 'VIGENTE'

        OPEN @carterasCursor
		FETCH NEXT FROM @carterasCursor INTO @idPersona, @idEmpleador, @idEmpresa, @idCartera, @periodo

    -- Recorre la lista de empleadores
    WHILE @@FETCH_STATUS = 0
BEGIN
	 IF @idPersona IS NOT NULL AND @idEmpresa IS NOT NULL AND @idEmpleador IS NOT NULL AND @idCartera IS NOT NULL
BEGIN
INSERT INTO @tablaPersonasConNovedad (perId)
			
			SELECT per.perId
			  FROM SolicitudNovedadPersona snp WITH(NOLOCK)
		INNER JOIN SolicitudNovedad sno WITH(NOLOCK) ON snoId = snpSolicitudNovedad
		INNER JOIN NovedadDetalle nop WITH(NOLOCK) ON nopSolicitudNovedad = snoId
		INNER JOIN Solicitud sol WITH(NOLOCK) ON solId = snoSolicitudGlobal
		INNER JOIN Persona per WITH(NOLOCK) ON per.perId = snpPersona
		INNER JOIN RolAfiliado r WITH(NOLOCK) ON snpRolAfiliado = r.roaid 
		INNER JOIN Empleador em WITH(NOLOCK) ON em.empid = r.roaEmpleador
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
			  AND CONVERT(VARCHAR(7), nopFechaInicio, 120) = @periodo---glpi 72111 -66105
			  --AND CONVERT(VARCHAR(7), nopFechaFin, 120) <= @periodo
			  AND em.empId = @idEmpleador
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
			   AND CONVERT(VARCHAR(7), roaFechaInicioCondicionVeterano, 120) <= @periodo
			   AND CONVERT(VARCHAR(7), roaFechaFinCondicionVeterano, 120) >= @periodo

UNION 
	select perid 
	   from persona 
	INNER JOIN afiliado on afipersona = perId
	INNER JOIN rolafiliado on roaafiliado = afiId
	INNER JOIN solicitudAfiliacionpersona on sapRolAfiliado = roaid
	INNER JOIN solicitud on sapSolicitudGlobal = solid
	     WHERE solTipoTransaccion like '%nueva%'
		   AND roaClaseTrabajador = 'VETERANO_FUERZA_PUBLICA'
		   AND sapEstadoSolicitud = 'CERRADA'
		   AND solResultadoProceso = 'APROBADA'
		   AND CONVERT(VARCHAR(7), roaFechaInicioCondicionVeterano, 120) <= @periodo
		   AND CONVERT(VARCHAR(7), roaFechaFinCondicionVeterano, 120) >= @periodo

	---*******FIN GLPI 62206
UNION

SELECT IdPersonaNov
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
         WHERE ( (redNovIGE = 'X') OR (redNovLMA = 'X') OR (redDiasIRL > 0) OR (redNovSLN= 'X') )
		   AND regTipoIdentificacionAportante = @tipoIdentificacion  
		   AND regNumeroIdentificacionAportante =  @numeroIdentificacion
     ) AS NovPila
	WHERE CONVERT(VARCHAR(7),rdnfechainicionovedad, 120) = @periodo---glpi 72111 -66105

		UNION

			SELECT canPersona  
				  FROM carteranovedad 
			INNER JOIN Carteradependiente cd 
			        ON cd.cadpersona = canpersona 
				   AND cadDeudaPresunta>0
		    INNER JOIN Cartera c ON c.carid = cadcartera 
			       AND LEFT(c.carPeriodoDeuda,7) BETWEEN LEFT(canFechaInicio,7) AND LEFT(canFechaFin,7)---GLPI 76941
				 WHERE canTipoNovedad IN (
											'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL',
											'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',
											'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL',
											'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
											'PROCESO_INTERNO_DE_LA_CCF'
										  )
					AND CONVERT(VARCHAR(7),canFechaInicio, 120) = @periodoEvaluacion ---glpi 72111 -66105
	----ANEXAR LAS NOVEDADES DE PILA PARA EXCLUIRLAS DE CARTERA 20230620
    --SELECT * FROM Seguimiento 
	--INSERT INTO Seguimiento
	--SELECT @idPersona , @idEmpleador , @idEmpresa  , @periodo  , @idCartera  
	
    EXEC dbo.USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores @idPersona, @idEmpleador, @idEmpresa, @periodo, 0, @idCartera, @tablaPersonasConNovedad,0,0
	DELETE FROM @tablaPersonasConNovedad

    FETCH NEXT FROM @carterasCursor INTO @idPersona, @idEmpleador, @idEmpresa, @idCartera, @periodo
END
END
CLOSE @carterasCursor
    DEALLOCATE @carterasCursor
END
			-- se marca en bitácora el fin de la ejecución exitosa ylos registros afectados
		UPDATE BitacoraEjecucionSp
		SET besUltimoExito = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCartera'
		END TRY
		BEGIN CATCH
			-- se marca en bitácora el fin de la ejecución fallida
		UPDATE BitacoraEjecucionSp
		SET besUltimoFallo = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCartera'

DECLARE @ErrorMessage NVARCHAR(4000),
		@ErrorSeverity INT,
		@ErrorState INT

SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERAActualizarCartera] | ' + ERROR_MESSAGE(),
       @ErrorSeverity = ERROR_SEVERITY(),
       @ErrorState = ERROR_STATE();

RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );
END CATCH