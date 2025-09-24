/****** Object:  StoredProcedure [dbo].[USP_ExecuteCARTERAAsignarAccionCobro]    Script Date: 16/11/2023 3:44:49 p. m. ******/
--SET ANSI_NULLS ON
--GO
--SET QUOTED_IDENTIFIER ON
--GO
---- =============================================
---- Author:		Ferney Alonso Vásquez Benavides - Juan Diego Ocampo Q.
---- Create date: 2018/02/08
---- Update date: 2019/07/10
---- Update date: 2023/01/02 NICOLAS JARAMILLO
---- Description:	Procedimiento almacenado encargado de asignar las acciones de cobro a entidades en cartera
---- HU164
------ =============================================
ALTER PROCEDURE [dbo].[USP_ExecuteCARTERAAsignarAccionCobro] @accionCobroFutura VARCHAR(4),
                                                              @lineaCobro VARCHAR(3),
                                                              @metodo VARCHAR(8) 
AS
	SET NOCOUNT ON

BEGIN TRY

	--declare 
	--	@accionCobroFutura VARCHAR(4) = 'A2',
	--	@lineaCobro VARCHAR(3) = 'LC1',
	--	@metodo VARCHAR(8) = 'METODO_2'

	-- se agrega registro en bitacora de ejecución
	IF EXISTS(SELECT 1 FROM BitacoraEjecucionSp WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAAsignarAccionCobro')
		BEGIN
			UPDATE BitacoraEjecucionSp
			SET besUltimoInicio = dbo.getLocalDate()
			WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAAsignarAccionCobro'
		END
	ELSE
		BEGIN
			INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
			VALUES ('USP_ExecuteCARTERAAsignarAccionCobro', dbo.getLocalDate())
		END
	
	DECLARE
		@fechaInicioEjecucion DATETIME = dbo.GetLocalDate()
	DECLARE
		@cantidadRegistros BIGINT = 0
	
	--
	DECLARE
		@fechaActual DATE = dbo.GetLocalDate()
	DECLARE
		@fechaDummy DATE
	-- Para pruebas
	
	-- Consulta @fechaDummy, para pruebas
	SELECT @fechaDummy = CONVERT(DATE, cnsValor, 111)
	FROM Constante
	WHERE cnsNombre = 'FECHA_DUMMY'
	SET @fechaActual = ISNULL(@fechaDummy, @fechaActual)
	
	print @fechaActual

-- varialbles para crear la auditoría
	DECLARE
		@iRevision BIGINT
	DECLARE
		@sql NVARCHAR(MAX)
	
	DECLARE
		@camposTablaCartera VARCHAR(1000)
	SELECT @camposTablaCartera = COALESCE(@camposTablaCartera + ', ', '') + COLUMN_NAME
	FROM INFORMATION_SCHEMA.COLUMNS
	WHERE TABLE_NAME = 'Cartera'
	  AND TABLE_SCHEMA = 'dbo'
	
	DECLARE
		@CarteraAud AS TablaBigintIdType
	
	DECLARE
		@inicioDiasConteoD VARCHAR(13)
	DECLARE
		@inicioDiasConteoE VARCHAR(13)
	DECLARE
		@inicioDiasConteoEPersuasivo VARCHAR(13)
	DECLARE
		@inicioDiasConteoF VARCHAR(13)
	DECLARE
		@inicioDiasConteoG VARCHAR(13)
	DECLARE
		@siguienteAccionF VARCHAR(29)
	DECLARE
		@siguienteAccion VARCHAR(20)
	
	DECLARE
		@diasLimitePagoA INT
	DECLARE
		@diasParametrizadosA INT
	DECLARE
		@diasLimiteEnvioComunicadoA INT
	DECLARE
		@diasGeneracionAvisoB INT
	DECLARE
		@limiteEnvioComunicadoB INT
	DECLARE
		@diasLiquidacionC INT
	DECLARE
		@limiteEnvioDocumentoC INT
	DECLARE
		@diasEjecucionC INT
	DECLARE
		@diasTranscurridosD INT
	DECLARE
		@diasLimiteEnvioD INT
	DECLARE
		@diasTranscurridosE INT
	DECLARE
		@diasTranscurridosEPersuasivo INT
	DECLARE
		@diasTranscurridosF INT
	DECLARE
		@diasLimiteEnvioE INT
	DECLARE
		@diasLimiteEnvioEPersuasivo INT
	DECLARE
		@limiteEnvioF INT
	DECLARE
		@diasParametrizadosF INT
	DECLARE
		@diasNotificacionF INT
	DECLARE
		@diasEdictoF INT
	DECLARE
		@diasTranscurridosG INT
	DECLARE
		@limiteEnvioG INT
	DECLARE
		@diasRegistroH INT
	DECLARE
		@diasParametrizadosH INT
	
	-- Lectura de parametrización de la línea y de la acción de cobro
	IF @accionCobroFutura IN ('LC2A', 'LC3A') -- Parametrización Acción LC2A | LC3A
		BEGIN
			SELECT @diasLimitePagoA = lco.lcoDiasLimitePago,
			       @diasParametrizadosA = lco.lcoDiasParametrizados
			FROM LineaCobro lco
			WHERE lco.lcoTipoLineaCobro = @lineaCobro
		END
	
	IF
		@accionCobroFutura IN ('LC4A', 'LC5A', 'L4AC', 'L5AC') -- Parametrización Acción LC4A | LC5A
		BEGIN
			SELECT @diasLimitePagoA = lcp.lcpDiasLimitePago,
			       @diasParametrizadosA = lcp.lcpDiasParametrizados
			FROM LineaCobroPersona lcp
			WHERE lcp.lcpTipoLineaCobro = @lineaCobro
		END
	
	IF
		@accionCobroFutura IN ('A1', 'AB1', 'A2', 'AB2') -- Parametrización Acción 1A | 2A
		BEGIN
			SELECT @diasLimitePagoA = aca.acaDiasLimitePago,
			       @diasLimiteEnvioComunicadoA = aca.acaDiasLimiteEnvioComunicado
			FROM AccionCobroA aca
			WHERE aca.acaMetodo = @metodo
		END
	
	IF
		@accionCobroFutura IN ('AB1', 'B1', 'BC1', 'AB2', 'B2', 'BC2') -- Parametrización Acción 1B | 2B
		BEGIN
			SELECT @diasGeneracionAvisoB = acb.acbDiasGeneracionAviso,
			       @limiteEnvioComunicadoB = acb.acbLimiteEnvioComunicado
			FROM AccionCobroB acb
			WHERE acb.acbMetodo = @metodo
		END
	
	IF
		@accionCobroFutura IN ('BC1', 'C1', 'CD1', 'E1') -- Parametrización Acción 1C
		BEGIN
			SELECT @diasLiquidacionC = acc.accDiasLiquidacion,
			       @limiteEnvioDocumentoC = acc.accLimiteEnvioDocumento
			FROM AccionCobro1C acc
		END
	
	IF
		@accionCobroFutura IN ('C2') -- Parametrización Acción 2C
		BEGIN
			SELECT @diasEjecucionC = aoc.aocDiasEjecucion
			FROM AccionCobro2C aoc
		END
	
	IF
		@accionCobroFutura IN ('CD2', 'D2', 'DE2') -- Parametrización Acción 2D
		BEGIN
			SELECT @inicioDiasConteoD = aod.aodInicioDiasConteo,
			       @diasTranscurridosD = aod.aodDiasTranscurridos
			FROM AccionCobro2D aod
		END
	
	IF
		@accionCobroFutura IN ('D1', 'DE1', 'E1') -- Parametrización Acción 1D
		BEGIN
			SELECT @inicioDiasConteoD = acd.acdInicioDiasConteo,
			       @diasTranscurridosD = acd.acdDiasTranscurridos,
			       @diasLimiteEnvioD = acd.acdLimiteEnvio
			FROM AccionCobro1D acd
		END
	
	IF
		@accionCobroFutura IN ('E1', 'EF1') -- Parametrización Acción 1E
		BEGIN
			SELECT @inicioDiasConteoE = aoe.aoeInicioDiasConteo,
			       @diasTranscurridosE = aoe.aoeDiasTranscurridos,
			       @diasLimiteEnvioE = aoe.aoeLimiteEnvio,
			       @inicioDiasConteoEPersuasivo = aoe.aoeInicioDiasConteoPersuasivo,
			       @diasTranscurridosEPersuasivo = aoe.aoeDiasTranscurridosPersuasivo,
			       @diasLimiteEnvioEPersuasivo = aoe.aoeLimiteEnvioPersuasivo
			FROM AccionCobro1E aoe
		END
	
	IF
		@accionCobroFutura IN ('E2', 'EF2') -- Parametrización Acción 2E
		BEGIN
			SELECT @inicioDiasConteoE = ace.aceInicioDiasConteo,
			       @diasTranscurridosE = ace.aceDiasTranscurridos
			FROM AccionCobro2E ace
		END
	
	IF
		@accionCobroFutura = 'F1' -- Parametrización Acción 1F
		BEGIN
			SELECT @diasParametrizadosF = abf.abfDiasParametrizados,
			       @siguienteAccionF = abf.abfSiguienteAccion
			FROM AccionCobro1F abf
		END
	
	IF
		@accionCobroFutura IN ('F2', 'FG2') -- Parametrización Acción 2F
		BEGIN
			SELECT @inicioDiasConteoF = aof.aofInicioDiasConteo,
			       @diasTranscurridosF = aof.aofDiasTranscurridos,
			       @limiteEnvioF = aof.aofLimiteEnvio,
			       @diasNotificacionF = aof.aofDiasRegistro,
			       @diasEdictoF = aof.aofDiasParametrizados
			FROM AccionCobro2F aof
		END
	
	IF
		@accionCobroFutura IN ('G2', 'GH2') -- Parametrización Acción 2G
		BEGIN
			SELECT @inicioDiasConteoG = aog.aogInicioDiasConteo,
			       @diasTranscurridosG = aog.aogDiasTranscurridos,
			       @limiteEnvioG = aog.aogLimiteEnvio
			FROM AccionCobro2G aog
		END
	
	IF
		@accionCobroFutura IN ('H2') -- Parametrización Acción 2H
		BEGIN
			SELECT @diasRegistroH = ach.achDiasRegistro,
			       @diasParametrizadosH = ach.achDiasParametrizados
			FROM AccionCobro2H ach
		END
	-- Fin lectura parametrización
	
	DECLARE
		@accionCobroReferencia VARCHAR(4)
	-- Acción de cobro de referencia
	SELECT @accionCobroReferencia = CASE
		-- LC1 - Método 1
		                                WHEN @accionCobroFutura = 'A1' THEN 'A01'
		                                WHEN @accionCobroFutura = 'AB1' THEN 'A1'
		                                WHEN @accionCobroFutura = 'B1' THEN 'AB1'
		                                WHEN @accionCobroFutura = 'BC1' THEN 'B1'
		                                WHEN @accionCobroFutura = 'C1' THEN 'BC1'
		                                WHEN @accionCobroFutura = 'CD1' THEN 'C1'
		                                WHEN @accionCobroFutura = 'D1' THEN 'CD1'
		                                WHEN @accionCobroFutura = 'DE1' THEN 'D1'
		                                WHEN @accionCobroFutura = 'E1' THEN 'DE1'
		                                WHEN @accionCobroFutura = 'EF1' THEN 'E1'
		                                WHEN @accionCobroFutura = 'F1' THEN 'EF1'
		-- LC1 - Método 2
		                                WHEN @accionCobroFutura = 'A2' THEN 'A02'
		                                WHEN @accionCobroFutura = 'AB2' THEN 'A2'
		                                WHEN @accionCobroFutura = 'B2' THEN 'AB2'
		                                WHEN @accionCobroFutura = 'BC2' THEN 'B2'
		                                WHEN @accionCobroFutura = 'C2' THEN 'BC2'
		                                WHEN @accionCobroFutura = 'CD2' THEN 'C2'
		                                WHEN @accionCobroFutura = 'D2' THEN 'CD2'
		                                WHEN @accionCobroFutura = 'DE2' THEN 'D2'
		                                WHEN @accionCobroFutura = 'E2' THEN 'DE2'
		                                WHEN @accionCobroFutura = 'EF2' THEN 'E2'
		                                WHEN @accionCobroFutura = 'F2' THEN 'EF2'
		                                WHEN @accionCobroFutura = 'FG2' THEN 'F2'
		                                WHEN @accionCobroFutura = 'G2' THEN 'FG2'
		                                WHEN @accionCobroFutura = 'GH2' THEN 'G2'
		                                WHEN @accionCobroFutura = 'H2' THEN 'GH2'
		-- LC2
		                                WHEN @accionCobroFutura = 'LC2A' THEN 'LC20'
		-- LC3
		                                WHEN @accionCobroFutura = 'LC3A' THEN 'LC30'
		-- LC4
		                                WHEN @accionCobroFutura = 'LC4A' THEN 'LC40'
		                                WHEN @accionCobroFutura = 'L4AC' THEN 'LC4A'
		                                WHEN @accionCobroFutura = 'LC4C' THEN 'L4AC'
		-- LC5
		                                WHEN @accionCobroFutura = 'LC5A' THEN 'LC50'
		                                WHEN @accionCobroFutura = 'L5AC' THEN 'LC5A'
		                                WHEN @accionCobroFutura = 'LC5C' THEN 'L5AC'
		END
	
	DECLARE
		@carteraActualizada AS TABLE
		                       (
			                       carId                    BIGINT,
			                       carTipoAccionCobro       VARCHAR(4),
			                       carFechaAsignacionAccion DATETIME
		                       )
	
	
	-- Asigna estado inicial
	INSERT INTO @carteraActualizada (carId, carTipoAccionCobro)
	SELECT carId,
	       CASE
		       WHEN @accionCobroFutura = 'A1' AND carTipoLineaCobro = 'LC1' AND carMetodo = 'METODO_1' THEN 'A01'
		       WHEN @accionCobroFutura = 'A2' AND carTipoLineaCobro = 'LC1' AND carMetodo = 'METODO_2' THEN 'A02'
		       WHEN @accionCobroFutura = 'LC2A' AND carTipoLineaCobro = 'LC2' THEN 'LC20'
		       WHEN @accionCobroFutura = 'LC3A' AND carTipoLineaCobro = 'LC3' THEN 'LC30'
		       WHEN @accionCobroFutura = 'LC4A' AND carTipoLineaCobro = 'LC4' THEN 'LC40'
		       WHEN @accionCobroFutura = 'LC5A' AND carTipoLineaCobro = 'LC5' THEN 'LC50'
		     END
	FROM Cartera
	WHERE carTipoAccionCobro IS NULL
	  AND carTipoLineaCobro = @lineaCobro
	
	------------------------------------------------------------------------------------------------------
	-- Inicio actualización de datos de cartera con su auditoría
	IF (SELECT COUNT(1) FROM @carteraActualizada) > 0
		BEGIN
			UPDATE car
			SET car.carTipoAccionCobro = cac.carTipoAccionCobro
			FROM Cartera car
				     INNER JOIN @carteraActualizada cac
				                ON car.carId = cac.carId
			EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', 1, '', 'USUARIO_SISTEMA',
			     @iRevision OUTPUT
			
			INSERT
			INTO @CarteraAud(id)
			SELECT carId
			FROM @carteraActualizada
			SET @sql = 'INSERT INTO aud.Cartera_aud (' + @camposTablaCartera + ', REV, REVTYPE)' +
			           ' SELECT ' + @camposTablaCartera + ', ' + CAST(@iRevision AS VARCHAR) + ', 1' +
			           ' FROM Cartera INNER JOIN @CarteraAud ON carId = id'
			EXEC sp_executesql @sql,
			     N'@CarteraAud TablaBigintIdType READONLY',
			     @CarteraAud
			
			DELETE
				@carteraActualizada
			DELETE
				@CarteraAud
		END
	-- Fin actualización de datos de cartera con su auditoría
	------------------------------------------------------------------------------------------------------
	
	DECLARE
		@tCarteraVigenteSinExclusionConvenio AS TABLE
		                                        (
			                                        carId                    BIGINT,
			                                        carPersona               BIGINT,
			                                        carTipoLineaCobro        VARCHAR(3),
			                                        carTipoAccionCobro       VARCHAR(4),
			                                        carTipoSolicitante       VARCHAR(13),
			                                        carPeriodoDeuda          DATE,
			                                        carFechaCreacion         DATE,
			                                        carFechaAsignacionAccion DATE
		                                        )

	DECLARE
		@tMoraPerido AS TABLE
		                                        (
			                                        carId                    BIGINT,
													excId                    BIGINT,
			                                        carPersona               BIGINT,
													carTipoAccionCobro		 VARCHAR(4),
													carFechaAsignacionAccion DATE
		                                        )
	
	DECLARE
		@exclusionesConveniosInactivos AS TABLE
		                                  (
			                                  eciPersona         BIGINT,
			                                  eciTipoSolicitante VARCHAR(13),
			                                  eciFecha           DATE
		                                  )
	
	IF @lineaCobro IN ('LC1', 'LC2', 'LC3')
		BEGIN
			INSERT INTO 
		@tCarteraVigenteSinExclusionConvenio (carId, carPersona, carTipoLineaCobro, carTipoAccionCobro,
	carTipoSolicitante, carPeriodoDeuda, carFechaCreacion,
	carFechaAsignacionAccion)

	SELECT carId,
			       carPersona,
			       carTipoLineaCobro,
			       carTipoAccionCobro,
			       carTipoSolicitante,
			       carPeriodoDeuda,
			       CAST(carFechaCreacion AS DATE),
			       CAST(carFechaAsignacionAccion AS DATE)
			FROM Empleador emd
				     INNER JOIN Empresa emp ON emd.empEmpresa = emp.empId
				     INNER JOIN Cartera car ON car.carPersona = emp.empPersona
				     INNER JOIN CarteraAgrupadora cag ON cag.cagCartera = car.carId
				     INNER JOIN (
				SELECT MIN(cagCartera) AS cagCartera
				FROM CarteraAgrupadora
					     JOIN Cartera ON carId = cagCartera
				WHERE carEstadoOperacion = 'VIGENTE'
				  AND carTipoSolicitante = 'EMPLEADOR'
				  AND carTipoLineaCobro = @lineaCobro
				  AND (carDeudaPresunta IS NOT NULL AND carDeudaPresunta > 0) -- ???
				  AND (
					-- LC1 LC2 LC3
							carTipoAccionCobro = @accionCobroReferencia
						-- LC1 casos especiales método 1
						OR (
									(@accionCobroFutura = 'E1' AND carTipoAccionCobro IN ('CD1', 'DE1'))
									OR (@accionCobroFutura = 'F1' AND carTipoAccionCobro IN ('CD1', 'DE1', 'EF1'))
								)
						-- LC1 casos especiales método 2
						OR (@accionCobroFutura IN ('F2', 'H2') AND
						    carTipoAccionCobro IN ('AB2', 'BC2', 'CD2', 'DE2', 'EF2', 'FG2', 'GH2')
								)
						OR (@accionCobroFutura = 'F2' AND
						    carTipoAccionCobro IN ('AB2', 'BC2', 'CD2', 'DE2', 'EF2', 'FG2', 'GH2', 'D2', 'E2', 'C2')
								)
					)
				GROUP BY cagNumeroOperacion
			) cav ON car.carId = cav.cagCartera
				     left JOIN ExclusionCartera exc ON (
						exc.excEstadoExclusionCartera = 'ACTIVA'
					AND exc.excTipoSolicitante = car.carTipoSolicitante
					AND exc.excPersona = car.carPersona
					AND exc.excPeriodoDeuda IS NOT NULL AND CAST(dbo.GetLocalDate() AS date) <= CAST(exc.excFechaFin AS date)  
					AND exc.excTipoExclusionCartera IN ('EXCLUSION_NEGOCIO', 'IMPOSICION_RECURSO', 'ACLARACION_MORA','EXCLUSION_MORA')
					
				)
				     LEFT JOIN (
				SELECT cop.copPersona, cop.copTipoSolicitante, ppc.ppcPeriodo
				FROM ConvenioPago cop
					     JOIN PagoPeriodoConvenio ppc ON ppc.ppcConvenioPago = cop.copId
				WHERE cop.copEstadoConvenioPago = 'ACTIVO'
			) AS con ON (
						con.copPersona = car.carPersona
					AND con.copTipoSolicitante = car.carTipoSolicitante
					AND con.ppcPeriodo = car.carPeriodoDeuda
				)
			WHERE 
			excId IS NULL
			  AND
			  con.copPersona IS NULL
			  AND car.carPeriodoDeuda = (SELECT MIN(carD.carPeriodoDeuda)
			                             FROM Cartera carD
			                             WHERE car.carPersona = carD.carPersona
				                           AND carD.carEstadoOperacion = 'VIGENTE'
				                           AND carD.carTipoLineaCobro = @lineaCobro
				                           AND (carDeudaPresunta IS NOT NULL AND carDeudaPresunta > 0))

			--select * from @tCarteraVigenteSinExclusionConvenio where carPersona = 125419

			INSERT
			INTO @exclusionesConveniosInactivos(eciPersona, eciTipoSolicitante, eciFecha)
			SELECT cp.copPersona, cp.copTipoSolicitante, cp.copFechaAnulacion
			FROM ConvenioPago cp,
			     @tCarteraVigenteSinExclusionConvenio cv
			WHERE cp.copPersona = cv.carPersona
			  AND cp.copTipoSolicitante = cv.carTipoSolicitante
			  AND cp.copEstadoConvenioPago = 'ANULADO'
			  AND cp.copMotivoAnulacion = 'INCUMPLIMIENTO'
			  AND CONVERT(DATE, dbo.getLocalDate(), 111) = CONVERT(DATE, cp.copFechaAnulacion, 111)
			UNION
			SELECT ec.excPersona, ec.excTipoSolicitante, ec.excFechaRegistro
			FROM ExclusionCartera ec,
			     @tCarteraVigenteSinExclusionConvenio cv
			WHERE ec.excPersona = cv.carPersona
			  AND ec.excTipoSolicitante = cv.carTipoSolicitante
			  AND ec.excEstadoExclusionCartera = 'NO_ACTIVA'
			  --AND ec.excPeriodoDeuda IS NULL
			  AND CONVERT(DATE, dbo.getLocalDate(), 111) = CONVERT(DATE, ec.excFechaRegistro, 111)

			   --select * from @exclusionesConveniosInactivos

	END
		BEGIN

		--select excFechaFin from ExclusionCartera where excid = 281
		INSERT INTO
		@tMoraPerido (carId, excId, carPersona, carTipoAccionCobro, carFechaAsignacionAccion)

	SELECT carId, ec.excId, carPersona, @accionCobroFutura, @fechaActual
				   from Cartera c inner Join ExclusionCartera ec
				   on c.carPersona = ec.excPersona
				   and carPeriodoDeuda = excPeriodoDeuda 
				   where ec.excPeriodoDeuda IS NOT NULL
				   AND carTipoLineaCobro = @lineaCobro --
				   AND 
				   CAST(dbo.GetLocalDate() AS date) <= CAST(ec.excFechaFin AS date)
				    
	--print dbo.GetLocalDate()

	--select * from @tMoraPerido
    END 
	
	IF
		@lineaCobro IN ('LC4', 'LC5')
		BEGIN
			INSERT INTO @tCarteraVigenteSinExclusionConvenio (carId, carPersona, carTipoLineaCobro, carTipoAccionCobro,
			                                                  carTipoSolicitante, carPeriodoDeuda, carFechaCreacion,
			                                                  carFechaAsignacionAccion)
			SELECT carId,
			       carPersona,
			       carTipoLineaCobro,
			       carTipoAccionCobro,
			       carTipoSolicitante,
			       carPeriodoDeuda,
			       CAST(carFechaCreacion AS DATE),
			       CAST(carFechaAsignacionAccion AS DATE)
			FROM RolAfiliado roa
				     INNER JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
				     INNER JOIN Cartera car ON car.carPersona = afi.afiPersona
				     INNER JOIN CarteraAgrupadora cag ON cag.cagCartera = car.carId
				     INNER JOIN (
				SELECT MIN(cagCartera) AS cagCartera
				FROM CarteraAgrupadora
					     JOIN Cartera ON carId = cagCartera
				WHERE carEstadoOperacion = 'VIGENTE'
				  AND carTipoSolicitante IN ('INDEPENDIENTE', 'PENSIONADO')
				  AND carTipoLineaCobro = @lineaCobro
				  AND (carDeudaPresunta IS NOT NULL AND carDeudaPresunta > 0) -- ???
				  AND carTipoAccionCobro = @accionCobroReferencia
				GROUP BY cagNumeroOperacion
			) cav ON car.carId = cav.cagCartera
				     LEFT JOIN ExclusionCartera exc ON (
						exc.excEstadoExclusionCartera = 'ACTIVA'
					AND exc.excTipoSolicitante = car.carTipoSolicitante
					AND exc.excPersona = car.carPersona
					AND exc.excTipoExclusionCartera IN ('EXCLUSION_NEGOCIO', 'IMPOSICION_RECURSO', 'ACLARACION_MORA')
				)
				     LEFT JOIN (
				SELECT cop.copPersona, cop.copTipoSolicitante, ppc.ppcPeriodo
				FROM ConvenioPago cop
					     JOIN PagoPeriodoConvenio ppc ON ppc.ppcConvenioPago = cop.copId
				WHERE cop.copEstadoConvenioPago = 'ACTIVO'
			) AS con ON (
						con.copPersona = car.carPersona
					AND con.copTipoSolicitante = car.carTipoSolicitante
					AND con.ppcPeriodo = car.carPeriodoDeuda
				)
			WHERE excId IS NULL
			  AND con.copPersona IS NULL
			--AND roa.roaEstadoAfiliado = 'ACTIVO'
			
			INSERT
			INTO @exclusionesConveniosInactivos(eciPersona, eciTipoSolicitante, eciFecha)
			SELECT cp.copPersona, cp.copTipoSolicitante, cp.copFechaAnulacion
			FROM ConvenioPago cp,
			     @tCarteraVigenteSinExclusionConvenio cv
			WHERE cp.copPersona = cv.carPersona
			  AND cp.copTipoSolicitante = cv.carTipoSolicitante
			  AND cp.copEstadoConvenioPago = 'ANULADO'
			  AND cp.copMotivoAnulacion = 'INCUMPLIMIENTO'
			  AND CONVERT(DATE, dbo.getLocalDate(), 111) = CONVERT(DATE, cp.copFechaAnulacion, 111)
			UNION
			SELECT ec.excPersona, ec.excTipoSolicitante, ec.excFechaRegistro
			FROM ExclusionCartera ec,
			     @tCarteraVigenteSinExclusionConvenio cv
			WHERE ec.excPersona = cv.carPersona
			  AND ec.excTipoSolicitante = cv.carTipoSolicitante
			  AND ec.excEstadoExclusionCartera = 'NO_ACTIVA'
			  AND CONVERT(DATE, dbo.getLocalDate(), 111) = CONVERT(DATE, ec.excFechaRegistro, 111)
		END
	-- MotivoNoGestionCobro ???

--	------------------------------------------------------------------------
--	-- Exclusion general sobre la parcial
--	------------------------------------------------------------------------

	IF EXISTS(SELECT 1 FROM ExclusionCartera ec
	INNER JOIN @tMoraPerido mp ON mp.carPersona = ec.excPersona
	WHERE ec.excPeriodoDeuda IS NULL
	)
	OR EXISTS (
        SELECT 1
        FROM ConvenioPago cp
		INNER JOIN @tMoraPerido mp ON mp.carPersona = cp.copPersona 
    )
	BEGIN
	print 'entra aca if'
	--select excId from @tMoraPerido

	--BEGIN TRAN
	--select e.excPersona from ExclusionCartera e inner join
	--@tMoraPerido t on t.carPersona = e.excPersona where e.excPeriodoDeuda IS NULL
	--AND e.excEstadoExclusionCartera = 'ACTIVA'

	--select t.excId from @tMoraPerido t join ExclusionCartera as e on e.excPersona = t.carPersona
	--where e.excPeriodoDeuda IS NOT NULL
	--AND e.excPersona IN
	--(select e.excPersona from ExclusionCartera e inner join
	--@tMoraPerido t on t.carPersona = e.excPersona where e.excPeriodoDeuda IS NULL
	--)


	delete e from ExclusionCartera e where e.excId IN(
	select t.excId from @tMoraPerido t join ExclusionCartera as e on e.excPersona = t.carPersona
	where e.excPeriodoDeuda IS NOT NULL
	AND e.excPersona IN
	(select e.excPersona from ExclusionCartera e inner join
	@tMoraPerido t on t.carPersona = e.excPersona where e.excPeriodoDeuda IS NULL
	AND e.excEstadoExclusionCartera = 'ACTIVA') 
	OR e.excPersona IN (
    SELECT mp.carPersona 
    FROM ConvenioPago cp
    INNER JOIN @tMoraPerido mp ON mp.carPersona = cp.copPersona
	)
	)

	delete e from @tMoraPerido e where e.excId IN(
	select t.excId from @tMoraPerido t join ExclusionCartera as e on e.excPersona = t.carPersona
	where e.excPeriodoDeuda IS NOT NULL
	AND e.excPersona IN
	(select e.excPersona from ExclusionCartera e inner join
	@tMoraPerido t on t.carPersona = e.excPersona where e.excPeriodoDeuda IS NULL
	AND e.excEstadoExclusionCartera = 'ACTIVA') 
	OR e.excPersona IN (
    SELECT mp.carPersona 
    FROM ConvenioPago cp
    INNER JOIN @tMoraPerido mp ON mp.carPersona = cp.copPersona
	)
	)

	-- select t.excId from @tMoraPerido t join ExclusionCartera as e on e.excPersona = t.carPersona
	--where e.excPeriodoDeuda IS NOT NULL
	--AND e.excPersona IN
	--(select e.excPersona from ExclusionCartera e inner join
	--@tMoraPerido t on t.carPersona = e.excPersona where e.excPeriodoDeuda IS NULL)

		--ROLLBACK
	END

--	------------------------------------------------------------------------
--	-- Escenarios
--	------------------------------------------------------------------------
	--select * from @tCarteraVigenteSinExclusionConvenio where carPersona = 125419
	
	IF
		@lineaCobro = 'LC1'
		BEGIN
			IF
				@metodo = 'METODO_1'
				BEGIN
					-- METODO_1
					IF
						@accionCobroFutura = 'A1'
						BEGIN
							IF @diasLimitePagoA = 1
								BEGIN
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT tc.carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio tc
									WHERE tc.carTipoAccionCobro = 'A01'
									  AND @accionCobroFutura = 'A1'
									  AND (@fechaActual =
									       dbo.UFN_SumarDiasFecha(tc.carFechaCreacion, (@diasLimitePagoA), 1)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = tc.carPersona
											      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))
									  
								END
							ELSE
								BEGIN
									
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT tc.carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio tc
									WHERE tc.carTipoAccionCobro = 'A01'
									  AND @accionCobroFutura = 'A1'
									  AND (@fechaActual =
									       dbo.UFN_SumarDiasFecha(tc.carFechaCreacion, (@diasLimitePagoA - 1), 1)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = tc.carPersona
											      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))
								END
						END
					
					IF
						@accionCobroFutura = 'AB1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT tc.carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio tc
							WHERE tc.carTipoAccionCobro = 'A1'
							  AND @accionCobroFutura = 'AB1'
							  AND @fechaActual =
							      dbo.UFN_SumarDiasFecha(dbo.UFN_SumarDiasFecha(EOMonth(tc.carFechaCreacion),
							                                                    @diasGeneracionAvisoB, 1),
							                             - @diasLimiteEnvioComunicadoA, 1)
-- fin a              |-- inicio b --------------------------------------------------------------|
						END
					
					IF
						@accionCobroFutura = 'B1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT tc.carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio tc
							WHERE tc.carTipoAccionCobro = 'AB1'
							  AND @accionCobroFutura = 'B1'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(EOMonth(tc.carFechaCreacion),
							                                             @diasGeneracionAvisoB, 1)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = tc.carPersona
									      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))
						END
					
					IF
						@accionCobroFutura = 'BC1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT tc.carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio tc
							WHERE tc.carTipoAccionCobro = 'B1'
							  AND @accionCobroFutura = 'BC1'
							  AND @fechaActual = dbo.UFN_SumarDiasFecha(dbo.UFN_SumarDiasFecha(tc.carFechaAsignacionAccion,
							                                                                   @limiteEnvioComunicadoB,
							                                                                   1),
							                                            - @diasGeneracionAvisoB, 1)
						END
					
					IF
						@accionCobroFutura = 'C1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'BC1'
							  AND @accionCobroFutura = 'C1'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(EOMonth(carFechaAsignacionAccion),
							                                             @diasLiquidacionC, 1)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = carPersona
									      AND ec.eciTipoSolicitante = carTipoSolicitante))
							  
						END
					
					IF
						@accionCobroFutura = 'CD1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'C1'
							  AND @accionCobroFutura = 'CD1'
							  AND (
										@fechaActual = dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion,
										                                      @limiteEnvioDocumentoC, 0)
									OR dbo.UFN_ObtenerFechaComunicadoCartera('C1', 'FECHA_ENVIO', carId) IS NOT NULL
								)
						END
					
					IF
						@accionCobroFutura = 'D1'
						BEGIN
							IF @diasTranscurridosD = 1
								BEGIN
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio
									WHERE carTipoAccionCobro = 'CD1'
									  AND @accionCobroFutura = 'D1'
									  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
											ISNULL(dbo.UFN_ObtenerFechaComunicadoCartera('CC1', @inicioDiasConteoD,
											                                             carId),
											       carFechaAsignacionAccion), @diasTranscurridosD - 1, 0)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = carPersona
											      AND ec.eciTipoSolicitante = carTipoSolicitante))
								END
							ELSE
								BEGIN
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio
									WHERE carTipoAccionCobro = 'CD1'
									  AND @accionCobroFutura = 'D1'
									  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
											ISNULL(dbo.UFN_ObtenerFechaComunicadoCartera('CC1', @inicioDiasConteoD,
											                                             carId),
											       carFechaAsignacionAccion), @diasTranscurridosD - 1, 0)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = carPersona
											      AND ec.eciTipoSolicitante = carTipoSolicitante))
								END
						END
					BEGIN
						INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
						SELECT carId, @accionCobroFutura, @fechaActual
						FROM @tCarteraVigenteSinExclusionConvenio
						WHERE carTipoAccionCobro = 'CD1'
						  AND @accionCobroFutura = 'D1'
						  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
								ISNULL(dbo.UFN_ObtenerFechaComunicadoCartera('CC1', @inicioDiasConteoD, carId),
								       carFechaAsignacionAccion), @diasTranscurridosD, 0)
							OR 0 < (SELECT COUNT(*)
							        FROM @exclusionesConveniosInactivos ec
							        WHERE ec.eciPersona = carPersona
								      AND ec.eciTipoSolicitante = carTipoSolicitante))
					END
					
					
					IF
						@accionCobroFutura = 'DE1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'D1'
							  AND @accionCobroFutura = 'DE1'
							  AND (
										@fechaActual = dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion,
										                                      @diasLimiteEnvioD - @diasTranscurridosD,
										                                      0)
									OR dbo.UFN_ObtenerFechaComunicadoCartera('D1', 'FECHA_ENVIO', carId) IS NOT NULL
								)
						END
					
					IF
						@accionCobroFutura = 'E1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE @accionCobroFutura = 'E1'
							  AND ((
								       --@fechaActual = dbo.UFN_SumarDiasFecha(dbo.UFN_ObtenerFechaComunicadoCartera('C1', @inicioDiasConteoE, carId), @diasTranscurridosE, 0)
								       --OR (
										       carTipoAccionCobro = 'DE1'
									       AND @fechaActual = dbo.UFN_SumarDiasFecha(
										       dbo.UFN_ObtenerFechaComunicadoCartera('D1', @inicioDiasConteoEPersuasivo,
										                                             carId),
										       @diasTranscurridosE, 0)
								       )
								OR (
									       0 < (SELECT COUNT(*)
									            FROM @exclusionesConveniosInactivos ec
									            WHERE ec.eciPersona = carPersona
										          AND ec.eciTipoSolicitante = carTipoSolicitante)
								       )
								)
						END
					
					IF
						@accionCobroFutura = 'EF1'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'E1'
							  AND @accionCobroFutura = 'EF1'
							  AND (
										@fechaActual = dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion,
										                                      @diasLimiteEnvioE - @diasTranscurridosE,
										                                      0)
									OR @fechaActual = dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion,
									                                         @diasLimiteEnvioEPersuasivo -
									                                         @diasTranscurridosEPersuasivo, 0)
									OR dbo.UFN_ObtenerFechaComunicadoCartera('C1', @inicioDiasConteoE,
									                                         carId) IS NOT NULL
									OR dbo.UFN_ObtenerFechaComunicadoCartera('D1', @inicioDiasConteoEPersuasivo,
									                                         carId) IS NOT NULL
								)
						END
					
					IF
						@accionCobroFutura = 'F1'
						BEGIN
							SET
								@siguienteAccion = 'FECHA_ENVIO'
							IF @siguienteAccionF = 'REGISTRO_RECEPCION_COMUNICADO'
								BEGIN
									SET
										@siguienteAccion = 'FECHA_ENTREGA'
								END
							
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'EF1'
							  AND @accionCobroFutura = 'F1'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
									dbo.UFN_ObtenerFechaComunicadoCartera('E1', @siguienteAccion, carId),
									@diasParametrizadosF, 0)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = carPersona
									      AND ec.eciTipoSolicitante = carTipoSolicitante))
						END
					--END --AJUSTE REALIZADO END AGREGADO 19/04/2022 JUANGUZMAN
				END
			ELSE
				BEGIN
					-- METODO_2
					
					DECLARE
						@tBitacoras AS TABLE
						               (
							               idCartera     BIGINT,
							               actividad     VARCHAR(50),
							               fechaBitacora DATE
						               )
					
					IF @accionCobroFutura = 'F2' OR @accionCobroFutura = 'H2'
						BEGIN
							INSERT INTO @tBitacoras (idCartera, actividad, fechaBitacora)
							
							SELECT cagCartera, bc.bcaActividad, MIN(bcaFecha) fecha
							FROM BitacoraCartera bc
								     INNER JOIN CarteraAgrupadora cag ON cagNumeroOperacion = bcaNumeroOperacion
							WHERE bcaActividad IN
							      ('ENVIAR_LIQUIDACION', 'REGISTRO_NOTIFICACION_PERSONAL', 'GENERAR_LIQUIDACION')
							  --bcaPersona NOT IN (SELECT b.bcaPersona FROM bitacoracartera b WHERE  bcaActividad = 'REGISTRO_NOTIFICACION_PERSONAL' and b.bcaPersona = bc.bcapersona ) ----CAMBIO OLGA VEGA 20210715
							  AND bcaNumeroOperacion IS NOT NULL
							  AND ISNULL(bcaResultado, 'NO_EXITOSO') != 'NO_EXITOSO'
							GROUP BY cagCartera, bc.bcaActividad
						END
					
					IF
						@accionCobroFutura = 'A2'
						BEGIN

						
							IF
								@diasLimitePagoA = 1
								BEGIN
								print 'caso 1 A2'
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT tc.carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio tc
									WHERE  
								   tc.carTipoAccionCobro = 'A02'
									  AND @accionCobroFutura = 'A2'
									  AND (@fechaActual = dbo.UFN_SumarDiasFecha(tc.carFechaCreacion,
									                                             @diasLimitePagoA, 1)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = tc.carPersona
											      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))
												 

								END
							
							ELSE
								BEGIN
								print 'caso 2 A2'
									INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
									SELECT tc.carId, @accionCobroFutura, @fechaActual
									FROM @tCarteraVigenteSinExclusionConvenio tc
									WHERE 
									
									tc.carTipoAccionCobro = 'A02'
									  AND @accionCobroFutura = 'A2'
									  AND (@fechaActual =
									       dbo.UFN_SumarDiasFecha(tc.carFechaCreacion, (@diasLimitePagoA - 1), 1)
										OR 0 < (SELECT COUNT(*)
										        FROM @exclusionesConveniosInactivos ec
										        WHERE ec.eciPersona = tc.carPersona
											      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))

	--select carId from @carteraActualizada


								END
						END
					
					IF
						@accionCobroFutura = 'AB2'
						print 'caso 2 AB2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT tc.carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio tc
							WHERE tc.carTipoAccionCobro = 'A2'
							  AND @accionCobroFutura = 'AB2'
							  AND @fechaActual =
							      dbo.UFN_SumarDiasFecha(dbo.UFN_SumarDiasFecha(EOMonth(tc.carFechaCreacion),
							                                                    @diasGeneracionAvisoB, 1),
							                             - @diasLimiteEnvioComunicadoA, 1)
																		  	--select * from @carteraActualizada

						END
					
					IF
						@accionCobroFutura = 'B2'
						print 'caso 2 B2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT tc.carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio tc
							WHERE tc.carTipoAccionCobro = 'AB2'
							  AND @accionCobroFutura = 'B2'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(EOMonth(tc.carFechaCreacion),
							                                             @diasGeneracionAvisoB, 1)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = tc.carPersona
									      AND ec.eciTipoSolicitante = tc.carTipoSolicitante))
						END
					
					IF
						@accionCobroFutura = 'BC2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'B2'
							  AND @accionCobroFutura = 'BC2'
							  AND @fechaActual = dbo.UFN_SumarDiasFecha(dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion,
							                                                                   @limiteEnvioComunicadoB,
							                                                                   1),
							                                            - @diasGeneracionAvisoB, 1)
						END
					
					IF
						@accionCobroFutura = 'C2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'BC2'
							  AND @accionCobroFutura = 'C2'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(EOMonth(carFechaAsignacionAccion),
							                                             @diasEjecucionC, 1)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = carPersona
									      AND ec.eciTipoSolicitante = carTipoSolicitante))
						END
					
					IF
						@accionCobroFutura = 'CD2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'C2'
							  AND @accionCobroFutura = 'CD2'
							  AND dbo.UFN_ObtenerFechaComunicadoCartera('C2', 'FECHA_ENVIO', carId) IS NOT NULL --CAMBIO DE TIPO FECHA A "FECHA_ENVIO" PARA QUE CONTINUE CON LA SIGUIENTE ACCIÓN CAMBIO JEAN MURCIA 2021-08-12
						END
					
					IF
						@accionCobroFutura = 'D2'
						BEGIN
							
							SELECT 'INGRESA A LA D2'
							SELECT @inicioDiasConteoD
							SELECT @fechaActual
							SELECT @diasTranscurridosD
							SELECT * FROM @tCarteraVigenteSinExclusionConvenio
							
							
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
								     LEFT JOIN NotificacionPersonal ON ntpCartera = carId
								     LEFT JOIN BitacoraCartera bca ON bca.bcaPersona = carPersona --CAMBIO 2021-08-12 JEAN MURCIA
							WHERE carTipoAccionCobro = 'CD2' 
							AND bca.bcaActividad <>'REGISTRO_NOTIFICACION_PERSONAL'
							  AND @accionCobroFutura = 'D2'
							  --AND ntpId IS NULL -NICOLAS JARAMILLO 18/01/2023
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
									dbo.UFN_ObtenerFechaComunicadoCartera('C2', @inicioDiasConteoD, carId),
									@diasTranscurridosD, 1)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = carPersona
									      AND ec.eciTipoSolicitante = carTipoSolicitante))
							--AND bca.bcaResultado = 'NO_EXITOSO' --CAMBIO 2021-08-21 JEAN MURCIA
--AND bca.bcaResultado = 'NO_EXITOSO' --CAMBIO 2021-08-21 JEAN MURCIA
--AND bca.bcaActividad = 'C2'
--CAMBIO 2021-08-21 JEAN MURCIA
						
						END
					
					IF
						@accionCobroFutura = 'DE2'
						BEGIN
							
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'D2'
							  AND @accionCobroFutura = 'DE2'
							  AND dbo.UFN_ObtenerFechaComunicadoCartera('D2', 'FECHA_ENVIO', carId) IS NOT NULL
-----CAMBIO OLGA VEGA 20210719
						END
					
					IF
						@accionCobroFutura = 'E2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
								     LEFT JOIN NotificacionPersonal ON ntpCartera = carId
							WHERE carTipoAccionCobro = 'DE2'
							  AND @accionCobroFutura = 'E2'
							  AND ntpId IS NULL
							  AND (
									@fechaActual = dbo.UFN_SumarDiasFecha((SELECT bcaFecha
									                                       FROM BitacoraCartera,
									                                            CarteraAgrupadora
									                                       WHERE bcaNumeroOperacion = cagNumeroOperacion
										                                     AND cagCartera = carId
										                                     AND bcaActividad = 'D2'
										                                     AND bcaResultado = (CASE
											                                                         WHEN @inicioDiasConteoE = 'FECHA_ENTREGA'
												                                                         THEN 'NO_EXITOSO'
											                                                         ELSE 'NO_ENVIADO' END)),
									                                      @diasTranscurridosE, 1)
								--OR dbo.UFN_ObtenerFechaComunicadoCartera('D2', @inicioDiasConteoE, carId) IS NOT NULL
								)
						END
					IF
						@accionCobroFutura = 'EF2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM Solicitud sol
								     INNER JOIN SolicitudGestionCobroFisico sgf ON sol.solId = sgf.sgfSolicitud
								     INNER JOIN DetalleSolicitudGestionCobro dsg ON sgf.sgfId IN
								                                                    (dsg.dsgSolicitudPrimeraRemision,
								                                                     dsg.dsgSolicitudSegundaRemision)
								     INNER JOIN @tCarteraVigenteSinExclusionConvenio ON dsg.dsgCartera = carId
							WHERE carTipoAccionCobro = 'E2'
							  AND @accionCobroFutura = 'EF2'
							  AND carFechaAsignacionAccion IS NOT NULL
							  AND sgf.sgfTipoAccionCobro = carTipoAccionCobro
							  AND sgf.sgfEstado = 'CERRADA'
						END
					
					IF
						@accionCobroFutura = 'F2'
						BEGIN
							
							SELECT 'ingreso a F2', @accionCobroFutura
							SELECT 'fECHA_DUMMY', @fechaActual
							SELECT 'BITACORA'
							SELECT * FROM @tBitacoras
							SELECT '@tCarteraVigenteSinExclusionConvenio'
							SELECT * FROM @tCarteraVigenteSinExclusionConvenio
							
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
								     LEFT JOIN @tBitacoras ON idCartera = carId
							WHERE @accionCobroFutura = 'F2'
							
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
								     LEFT JOIN @tBitacoras ON idCartera = carId
							WHERE @accionCobroFutura = 'F2'
							  AND (
									(
												actividad = 'REGISTRO_NOTIFICACION_PERSONAL' AND
												@fechaActual =
												dbo.UFN_SumarDiasFecha(fechaBitacora, @diasNotificacionF, 0)
										)
									OR (
												@fechaActual = dbo.UFN_SumarDiasFecha(
													dbo.UFN_ObtenerFechaComunicadoCartera('D2', @inicioDiasConteoF,
													                                      carId), @diasTranscurridosF,
													0)---CAMBIO OLGA VEGA 20210719 C2 POR D2
											AND carId NOT IN (SELECT carid
											                  from cartera
											                  where carpersona IN (SELECT b.bcaPersona
											                                       FROM bitacoracartera b
											                                       WHERE bcaActividad = 'REGISTRO_NOTIFICACION_PERSONAL')) ---CAMBIO OLGA VEGA 20210715
										)
									OR (
												carTipoAccionCobro = 'EF2'
											AND @fechaActual = dbo.UFN_SumarDiasFecha(
												dbo.UFN_ObtenerFechaComunicadoCartera('E2', 'FECHA_ENVIO', carId),
												@diasEdictoF, 0)
										)
									OR (
												actividad = 'GENERAR_LIQUIDACION' AND
												@fechaActual =
												dbo.UFN_SumarDiasFecha(
														dbo.UFN_ObtenerFechaComunicadoCartera('DD2', @inicioDiasConteoF,
														                                      carId),
														@diasTranscurridosF, 0)
										)
									OR (
												actividad = 'FIRMEZA_TITULO_EJECUTIVO' AND
												@fechaActual =
												dbo.UFN_SumarDiasFecha(
														dbo.UFN_ObtenerFechaComunicadoCartera('DD2', @inicioDiasConteoF,
														                                      carId),
														@diasTranscurridosF, 0)
										)
									OR (
											0 < (SELECT COUNT(*)
											     FROM @exclusionesConveniosInactivos ec
											     WHERE ec.eciPersona = carPersona
												   AND ec.eciTipoSolicitante = carTipoSolicitante)
										)
								)
							
							SELECT 'CARTERA ACTUALIZADA'
							--SELECT * from @carteraActualizada
						
						END
					
					IF
						@accionCobroFutura = 'FG2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'F2'
							  AND @accionCobroFutura = 'FG2'
							  AND (
										@fechaActual =
										dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion, @limiteEnvioF, 0)
									OR dbo.UFN_ObtenerFechaComunicadoCartera('C2', 'FECHA_ENVIO', carId) IS NOT NULL
								)
						END
					
					IF
						@accionCobroFutura = 'G2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'FG2'
							  AND @accionCobroFutura = 'G2'
							  AND (@fechaActual = dbo.UFN_SumarDiasFecha(
									dbo.UFN_ObtenerFechaComunicadoCartera('F2', @inicioDiasConteoG, carId),
									@diasTranscurridosG - 1, 0)
								OR 0 < (SELECT COUNT(*)
								        FROM @exclusionesConveniosInactivos ec
								        WHERE ec.eciPersona = carPersona
									      AND ec.eciTipoSolicitante = carTipoSolicitante))
						
						END
					
					IF @accionCobroFutura = 'GH2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
							WHERE carTipoAccionCobro = 'G2'
							  AND @accionCobroFutura = 'GH2'
							  AND (
										@fechaActual =
										dbo.UFN_SumarDiasFecha(carFechaAsignacionAccion, @limiteEnvioG, 0)
									OR
										dbo.UFN_ObtenerFechaComunicadoCartera('C2', 'FECHA_ENVIO', carId) IS NOT NULL
								)
						END
					
					IF
						@accionCobroFutura = 'H2'
						BEGIN
							INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
							SELECT carId, @accionCobroFutura, @fechaActual
							FROM @tCarteraVigenteSinExclusionConvenio
								     LEFT JOIN @tBitacoras bit
								               ON bit.idCartera = carId
							WHERE @accionCobroFutura = 'H2'
							  AND (
									(
												actividad = 'REGISTRO_NOTIFICACION_PERSONAL'
											AND @fechaActual =
											    dbo.UFN_SumarDiasFecha(fechaBitacora
												    , @diasRegistroH
												    , 0)
										)
									OR (
											@fechaActual = dbo.UFN_SumarDiasFecha(
												dbo.UFN_ObtenerFechaComunicadoCartera('E2'
													, 'FECHA_ENVIO'
													, carId)
											, @diasParametrizadosH
											, 0)
										)
									OR (
											0
											< (SELECT COUNT(*)
											   FROM @exclusionesConveniosInactivos ec
											   WHERE ec.eciPersona = carPersona
												 AND ec.eciTipoSolicitante = carTipoSolicitante)
										)
								)
						END
				END
		END
	IF
		@lineaCobro = 'LC2'
		BEGIN
			INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
			SELECT carId, @accionCobroFutura, @fechaActual
			FROM @tCarteraVigenteSinExclusionConvenio
			WHERE carTipoAccionCobro = 'LC20'
			  AND @accionCobroFutura = 'LC2A'
			  AND (@fechaActual = dbo.UFN_SumarDiasFecha(carFechaCreacion, (@diasLimitePagoA - 1), 1)
				OR 0 < (SELECT COUNT(*)
				        FROM @exclusionesConveniosInactivos ec
				        WHERE ec.eciPersona = carPersona
					      AND ec.eciTipoSolicitante = carTipoSolicitante))
		END
	IF
		@lineaCobro = 'LC3'
		BEGIN
			INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
			SELECT carId, @accionCobroFutura, @fechaActual
			FROM @tCarteraVigenteSinExclusionConvenio
			WHERE carTipoAccionCobro = 'LC30'
			  AND @accionCobroFutura = 'LC3A'
			  AND (@fechaActual = dbo.UFN_SumarDiasFecha(carFechaCreacion, (@diasLimitePagoA - 1), 1)
				OR 0 < (SELECT COUNT(*)
				        FROM @exclusionesConveniosInactivos ec
				        WHERE ec.eciPersona = carPersona
					      AND ec.eciTipoSolicitante = carTipoSolicitante))
		END
	IF
		@lineaCobro = 'LC4'
		BEGIN
			
			IF
				@accionCobroFutura = 'LC4A'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio
					WHERE carTipoAccionCobro = 'LC40'
					  AND @accionCobroFutura = 'LC4A'
					  AND (@fechaActual = dbo.UFN_SumarDiasFecha(carFechaCreacion, (@diasLimitePagoA - 1), 1)
						OR 0 < (SELECT COUNT(*)
						        FROM @exclusionesConveniosInactivos ec
						        WHERE ec.eciPersona = carPersona
							      AND ec.eciTipoSolicitante = carTipoSolicitante))
				END
			
			IF
				@accionCobroFutura = 'L4AC'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT tc.carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio tc
					WHERE tc.carTipoAccionCobro = 'LC4A'
					  AND @accionCobroFutura = 'L4AC'
					  AND (@fechaActual =
					       CASE
						       WHEN
								       (SELECT roa.roaOportunidadPago
								        FROM RolAfiliado roa
									             JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
									             JOIN Persona per ON afi.afiPersona = per.perId
									             JOIN Cartera car ON car.carPersona = per.perId
								        WHERE roa.roaEmpleador IS NULL
									      AND car.carId = tc.carId) = 'MES_VENCIDO'
							       THEN
							       DATEADD(DAY, -@diasParametrizadosA, DATEADD(MONTH, 2, tc.carPeriodoDeuda))
						       ELSE
							       DATEADD(DAY, -@diasParametrizadosA, DATEADD(MONTH, 1, tc.carPeriodoDeuda))
						       END
						)
				END
			
			IF
				@accionCobroFutura = 'LC4C'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio
					WHERE carTipoAccionCobro = 'L4AC'
					  AND @accionCobroFutura = 'LC4C'
					  AND @fechaActual = DATEADD(MONTH, 1, carPeriodoDeuda)
				END
		END
	IF
		@lineaCobro = 'LC5'
		BEGIN
			IF
				@accionCobroFutura = 'LC5A'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio
					WHERE carTipoAccionCobro = 'LC50'
					  AND @accionCobroFutura = 'LC5A'
					  AND (@fechaActual = dbo.UFN_SumarDiasFecha(carFechaCreacion, (@diasLimitePagoA - 1), 1)
						OR 0 < (SELECT COUNT(*)
						        FROM @exclusionesConveniosInactivos ec
						        WHERE ec.eciPersona = carPersona
							      AND ec.eciTipoSolicitante = carTipoSolicitante))
				END
			
			IF
				@accionCobroFutura = 'L5AC'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio
					WHERE carTipoAccionCobro = 'LC5A'
					  AND @accionCobroFutura = 'L5AC'
					  AND @fechaActual = DATEADD(DAY, -@diasParametrizadosA, DATEADD(MONTH, 2, carPeriodoDeuda))
				END
			
			IF
				@accionCobroFutura = 'LC5C'
				BEGIN
					INSERT INTO @carteraActualizada (carId, carTipoAccionCobro, carFechaAsignacionAccion)
					SELECT carId, @accionCobroFutura, @fechaActual
					FROM @tCarteraVigenteSinExclusionConvenio
					WHERE carTipoAccionCobro = 'L5AC'
					  AND @accionCobroFutura = 'LC5C'
					  AND @fechaActual = DATEADD(MONTH, 2, carPeriodoDeuda)
				END
		END

	
	------------------------------------------------------------------------------------------------------
	-- Inicio actualización de datos de cartera con su auditoría

	--select carId from @carteraActualizada

	IF
			(
				SELECT COUNT(1)
				FROM @carteraActualizada) > 0
		BEGIN

		print '@carteraActualizada'
			UPDATE car
			SET car.carTipoAccionCobro       = cac.carTipoAccionCobro,
			    car.carFechaAsignacionAccion = cac.carFechaAsignacionAccion
			FROM Cartera car
				     INNER JOIN @carteraActualizada cac
				                ON car.carId = cac.carId

			UPDATE car
			SET car.carTipoAccionCobro       = @accionCobroFutura,
			    car.carFechaAsignacionAccion = @fechaActual
			FROM Cartera car
			WHERE car.carPersona IN (SELECT cac.carPersona
			                         FROM Cartera cac
				                              INNER JOIN @carteraActualizada ca ON cac.carId = ca.carId)
			  AND car.carEstadoOperacion = 'VIGENTE'
			  AND car.carTipoLineaCobro = @lineaCobro
			  AND car.carTipoAccionCobro = @accionCobroReferencia

	--		--select * from cartera join persona on carpersona = perid where  perNumeroIdentificacion= '1223100'

			EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera'
				, 1
				, ''
				, 'USUARIO_SISTEMA'
				, @iRevision OUTPUT
			
			INSERT
			INTO HistoricoAsignacionCartera (hacCartera, hacTipoAccionCobro, hacFechaAsignacionAccion)
			SELECT carId, carTipoAccionCobro, carFechaAsignacionAccion
			FROM @carteraActualizada
			INSERT
			INTO HistoricoAsignacionCartera (hacCartera, hacTipoAccionCobro, hacFechaAsignacionAccion)
			SELECT car.carId, car.carTipoAccionCobro, car.carFechaAsignacionAccion
			FROM Cartera car
			WHERE car.carPersona IN (SELECT cac.carPersona
			                         FROM Cartera cac
				                              INNER JOIN @carteraActualizada ca ON cac.carId = ca.carId)
			  AND car.carEstadoOperacion = 'VIGENTE'
			  AND car.carTipoLineaCobro = @lineaCobro
			INSERT
			INTO @CarteraAud(id)
			SELECT carId
			FROM @carteraActualizada
			INSERT
			INTO @CarteraAud(id)
			SELECT car.carId
			FROM Cartera car
			WHERE car.carPersona IN (SELECT cac.carPersona
			                         FROM Cartera cac
				                              INNER JOIN @carteraActualizada ca ON cac.carId = ca.carId)
			  AND car.carEstadoOperacion = 'VIGENTE'
			  AND car.carTipoLineaCobro = @lineaCobro
			SET @sql = 'INSERT INTO aud.Cartera_aud (' + @camposTablaCartera + ', REV, REVTYPE)' +
			           ' SELECT ' + @camposTablaCartera + ', ' + CAST(@iRevision AS VARCHAR) + ', 1' +
			           ' FROM Cartera INNER JOIN @CarteraAud ON carId = id'
			EXEC sp_executesql @sql,
			     N'@CarteraAud TablaBigintIdType READONLY',
			     @CarteraAud
			
			DELETE
				@CarteraAud
		END
	----				select * from cartera join persona on carpersona = perid where  perNumeroIdentificacion= '1223100'

	--				-- Fin actualización de datos de cartera con su auditoría
	--------------------------------------------------------------------------------------------------------
	
	---- se marca en bitácora el fin de la ejecución exitosa y los registros afectados
	UPDATE BitacoraEjecucionSp
	SET besUltimoExito        = dbo.getLocalDate(),
	    besRegistrosAfectados = (SELECT COUNT(1) FROM @carteraActualizada car)
	WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAAsignarAccionCobro'
	
	-- Resultados del procedimiento. Se retornan únicamente aquellos registros para los cuales se enviará comunicado o se escalará solicitud
	IF @lineaCobro IN ('LC1', 'LC2', 'LC3')
		BEGIN
			SELECT ISNULL(ubi.ubiAutorizacionEnvioEmail, 0) autoriza,
			       car.carTipoSolicitante,
			       cac.carId,
			       cac.carTipoAccionCobro,
			       per.perTipoIdentificacion                tipoIdentificacion,
			       per.perNumeroIdentificacion              numeroIdentificacion
			FROM Cartera car
				     INNER JOIN Persona per ON per.perId = car.carPersona
				     LEFT JOIN Empresa emp ON emp.empPersona = per.perId
				     LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId
				     LEFT JOIN Ubicacion ubi ON ubeUbicacion = ubi.ubiId
				     INNER JOIN @carteraActualizada cac ON car.carId = cac.carId
			WHERE car.carTipoAccionCobro IN
			      ('A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2', 'LC2A', 'LC3A',
			       'LC4A', 'LC4C', 'LC5A', 'LC5C')
			  AND ube.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
		END
	IF
		@lineaCobro IN ('LC4', 'LC5')
		BEGIN
			SELECT ISNULL(ubi.ubiAutorizacionEnvioEmail, 0) autoriza,
			       car.carTipoSolicitante,
			       cac.carId,
			       cac.carTipoAccionCobro,
			       per.perTipoIdentificacion                tipoIdentificacion,
			       per.perNumeroIdentificacion              numeroIdentificacion
			FROM Cartera car
				     INNER JOIN Persona per ON per.perId = car.carPersona
				     LEFT JOIN Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
				     INNER JOIN @carteraActualizada cac ON car.carId = cac.carId
			WHERE car.carTipoAccionCobro IN
			      ('A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2', 'LC2A', 'LC3A',
			       'LC4A', 'LC4C', 'LC5A', 'LC5C')
		END
	
	SET
		@cantidadRegistros = (SELECT COUNT(*)
		                      FROM @carteraActualizada)
	
	INSERT INTO TiempoProcesoCartera
	(tpcNombreProceso, tpcFechaInicio, tpcFechaFin, tpcRegistros, tpcMensaje)
	VALUES ('USP_ExecuteCARTERAAsignarAccionCobro', @fechaInicioEjecucion, dbo.getLocalDate(), @cantidadRegistros,
	        @accionCobroFutura);
	---------------------------------------------------------------
		IF
			(
				SELECT COUNT(1)
				FROM @tMoraPerido) > 0
				BEGIN
				print 'Control 1'
		--		--select *  FROM Cartera car
		--		--     INNER JOIN @carteraActualizada cac ON car.carId = cac.carId

		
			
		----print 'entra a actualizar existe 2'
		--	--SELECT tmp.carId FROM @tMoraPerido tmp

		--	--SELECT c.carPersona
		--	--				FROM Cartera c
		--	--				INNER JOIN @tMoraPerido tmp ON c.carId = tmp.carId
		--	--BEGIN TRAN


		--			--select * FROM Cartera car
		--			--	WHERE 
		--			--	car.carPersona IN (
		--			--		SELECT c.carPersona
		--			--		FROM Cartera c
		--			--		INNER JOIN @tMoraPerido tmp ON c.carId = tmp.carId
		--			--	)
		--			--	AND car.carId NOT IN (
		--			--		SELECT carId
		--			--		FROM @tMoraPerido
		--			--	)
		--			--	AND car.carEstadoOperacion = 'VIGENTE'
		--			--	AND car.carTipoLineaCobro = @lineaCobro
		--			--	--AND	car.carTipoAccionCobro = @accionCobroReferencia;


						UPDATE car
						SET car.carTipoAccionCobro       = @accionCobroFutura,
							car.carFechaAsignacionAccion = @fechaActual
						FROM Cartera car
						WHERE 
						car.carPersona IN (
							SELECT c.carPersona
							FROM Cartera c
							INNER JOIN @tMoraPerido tmp ON c.carId = tmp.carId
						)
						AND car.carId NOT IN (
							SELECT carId
							FROM @tMoraPerido
						)
						AND car.carEstadoOperacion = 'VIGENTE'
						AND car.carTipoLineaCobro = @lineaCobro
						AND	car.carTipoAccionCobro = @accionCobroReferencia;
		--			--END
		--	--		select * FROM Cartera car
		--	--			WHERE 
		--	--			car.carPersona IN (
		--	--				SELECT distinct c.carPersona
		--	--				FROM @tMoraPerido c
		--	--				--INNER JOIN @tMoraPerido tmp ON car.carId = tmp.carId
		--	--			)
		--	--			AND car.carId NOT IN (
		--	--				SELECT carId
		--	--				FROM @tMoraPerido
		--	--			)
		--	--			AND car.carEstadoOperacion = 'VIGENTE'
		--	--			AND car.carTipoLineaCobro = @lineaCobro
		--	--			--AND	car.carTipoAccionCobro = @accionCobroReferencia;
		--	--ROLLBACK
			

			EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera'
				, 1
				, ''
				, 'USUARIO_SISTEMA'
				, @iRevision OUTPUT
			
			INSERT
			INTO HistoricoAsignacionCartera (hacCartera, hacTipoAccionCobro, hacFechaAsignacionAccion)
			SELECT carId, carTipoAccionCobro, carFechaAsignacionAccion
			FROM @tMoraPerido
			INSERT
			INTO HistoricoAsignacionCartera (hacCartera, hacTipoAccionCobro, hacFechaAsignacionAccion)
			SELECT car.carId, car.carTipoAccionCobro, car.carFechaAsignacionAccion
			FROM Cartera car
			WHERE car.carPersona IN (SELECT cac.carPersona
			                         FROM Cartera cac
				                              INNER JOIN @tMoraPerido ca ON cac.carId = ca.carId)
			  AND car.carEstadoOperacion = 'VIGENTE'
			  AND car.carTipoLineaCobro = @lineaCobro
			INSERT
			INTO @CarteraAud(id)
			SELECT carId
			FROM @tMoraPerido
			INSERT
			INTO @CarteraAud(id)
			SELECT car.carId
			FROM Cartera car
			WHERE car.carPersona IN (SELECT cac.carPersona
			                         FROM Cartera cac
				                              INNER JOIN @tMoraPerido ca ON cac.carId = ca.carId)
			  AND car.carEstadoOperacion = 'VIGENTE'
			  AND car.carTipoLineaCobro = @lineaCobro
			SET @sql = 'INSERT INTO aud.Cartera_aud (' + @camposTablaCartera + ', REV, REVTYPE)' +
			           ' SELECT ' + @camposTablaCartera + ', ' + CAST(@iRevision AS VARCHAR) + ', 1' +
			           ' FROM Cartera INNER JOIN @CarteraAud ON carId = id'
			EXEC sp_executesql @sql,
			     N'@CarteraAud TablaBigintIdType READONLY',
			     @CarteraAud
			
			DELETE
				@CarteraAud
			

		-- Fin actualización de datos de cartera con su auditoría
	------------------------------------------------------------------------------------------------------
	
	 --se marca en bitácora el fin de la ejecución exitosa y los registros afectados
	UPDATE BitacoraEjecucionSp
	SET besUltimoExito        = dbo.getLocalDate(),
	    besRegistrosAfectados = (SELECT COUNT(1) FROM @tMoraPerido car)
	WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAAsignarAccionCobro'
	
	-- Resultados del procedimiento. Se retornan únicamente aquellos registros para los cuales se enviará comunicado o se escalará solicitud
	IF @lineaCobro IN ('LC1', 'LC2', 'LC3')
		BEGIN
			SELECT ISNULL(ubi.ubiAutorizacionEnvioEmail, 0) autoriza,
			       car.carTipoSolicitante,
			       cac.carId,
			       cac.carTipoAccionCobro,
			       per.perTipoIdentificacion                tipoIdentificacion,
			       per.perNumeroIdentificacion              numeroIdentificacion
			FROM Cartera car
				     INNER JOIN Persona per ON per.perId = car.carPersona
				     LEFT JOIN Empresa emp ON emp.empPersona = per.perId
				     LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId
				     LEFT JOIN Ubicacion ubi ON ubeUbicacion = ubi.ubiId
				     INNER JOIN @tMoraPerido cac ON car.carId = cac.carId
			WHERE car.carTipoAccionCobro IN
			      ('A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2', 'LC2A', 'LC3A',
			       'LC4A', 'LC4C', 'LC5A', 'LC5C')
			  AND ube.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
		END
	IF
		@lineaCobro IN ('LC4', 'LC5')
		BEGIN
			SELECT ISNULL(ubi.ubiAutorizacionEnvioEmail, 0) autoriza,
			       car.carTipoSolicitante,
			       cac.carId,
			       cac.carTipoAccionCobro,
			       per.perTipoIdentificacion                tipoIdentificacion,
			       per.perNumeroIdentificacion              numeroIdentificacion
			FROM Cartera car
				     INNER JOIN Persona per ON per.perId = car.carPersona
				     LEFT JOIN Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
				     INNER JOIN @tMoraPerido cac ON car.carId = cac.carId
			WHERE car.carTipoAccionCobro IN
			      ('A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2', 'LC2A', 'LC3A',
			       'LC4A', 'LC4C', 'LC5A', 'LC5C')
		END
	
	SET
		@cantidadRegistros = (SELECT COUNT(*)
		                      FROM @tMoraPerido)
	
	INSERT INTO TiempoProcesoCartera
	(tpcNombreProceso, tpcFechaInicio, tpcFechaFin, tpcRegistros, tpcMensaje)
	VALUES ('USP_ExecuteCARTERAAsignarAccionCobro', @fechaInicioEjecucion, dbo.getLocalDate(), @cantidadRegistros,
	        @accionCobroFutura);
		END

	--select * from cartera join persona on carpersona = perid where  perNumeroIdentificacion= '1223100'

	

END TRY
BEGIN CATCH
	-- se marca en bitácora el fin de la ejecución fallida
	UPDATE BitacoraEjecucionSp
	SET besUltimoFallo        = dbo.getLocalDate(),
	    besRegistrosAfectados = 0
	WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAAsignarAccionCobro'
	
	DECLARE
		@ErrorMessage NVARCHAR(4000),
		@ErrorSeverity INT,
		@ErrorState INT
	
	SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERAAsignarAccionCobro] | ' + ERROR_MESSAGE(),
	       @ErrorSeverity = ERROR_SEVERITY(),
	       @ErrorState = ERROR_STATE();
	
	INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
	VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERAAsignarAccionCobro debug:',
	        + '|accionCobroFutura:' + CAST(@accionCobroFutura AS VARCHAR)
		        + '|lineaCobro:' + CAST(@lineaCobro AS VARCHAR)
		        + '|metodo:' + CAST(@metodo AS VARCHAR));
	
	INSERT INTO CarteraLogError(cleFecha, cleNombreSP, cleaMensaje)
	VALUES (dbo.getLocalDate(), 'USP_ExecuteCARTERAAsignarAccionCobro error linea:' + CAST(ERROR_LINE() AS VARCHAR),
	        @ErrorMessage);
	
	RAISERROR
		(@ErrorMessage, -- Message text.
		@ErrorSeverity, -- Severity.
		@ErrorState -- State.
		);
END CATCH