CREATE OR ALTER   PROCEDURE [dbo].[USP_Get_Consultapanelcartera]  @accioncobro VARCHAR(10), @lineaCobro VARCHAR(3), 
			  @usuarioAnalista VARCHAR(255) , @identificacion VARCHAR(20), @offset	INT, @rows INT,@filtros VARCHAR(50)
 
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
	---opc 1 = 'CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL_ANALISTA'
	---opc 2 = 'CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL_ANALISTA'
	---opc 3 = 'CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL_ANALISTA'

    SET NOCOUNT ON

	SET @accioncobro = NULLIF(@accioncobro, 'null')
    SET @lineaCobro = NULLIF(@lineaCobro, 'null')
    SET @usuarioAnalista = NULLIF(@usuarioAnalista, 'null')
    SET @identificacion = NULLIF(@identificacion, 'null')
	SET @filtros = NULLIF(@filtros, 'null')
	
	DECLARE @desc bit
	set @desc = 0
	
	if @filtros is not null and '-' = SUBSTRING (@filtros, 1, 1) begin set @desc = 1 end


	DECLARE @Acciones TABLE (Valor nvarchar(255))

	-- Insertar valores en la tabla de variables basados en @lineaCobro
	IF @lineaCobro = 'C6'
	BEGIN
		INSERT INTO @Acciones (Valor)
		VALUES ('A1'), ('B1'), ('C1'), ('D1'), ('E1'), ('F1'),
				('A2'), ('B2'), ('C2'), ('D2'), ('E2'), ('F2'), ('G2'), ('H2'),
				('A01'), ('AB1'), ('BC1'), ('CD1'), ('DE1'), ('EF1'),
				('A02'), ('AB2'), ('BC2'), ('CD2'), ('DE2'), ('EF2'), ('FG2'), ('GH2')
	END
	ELSE IF @lineaCobro = 'C7'
	BEGIN
		INSERT INTO @Acciones (Valor)
		VALUES ('LC4A'), ('LC4B'), ('LC4C'), ('LC40')
	END
	ELSE IF @lineaCobro = 'C8'
	BEGIN
		INSERT INTO @Acciones (Valor)
		VALUES ('LC5A'), ('LC5B'), ('LC5C'), ('LC50')
	END

	DECLARE @accionesIntermedias TABLE (AccionOriginal VARCHAR(10), AccionIntermedia VARCHAR(10))
    
    -- Poblamos la tabla con las acciones intermedias
    INSERT INTO @accionesIntermedias (AccionOriginal, AccionIntermedia) VALUES
    ('A1', 'AB1'), ('B1', 'BC1'), ('C1', 'CD1'),
    ('D1', 'DE1'), ('E1', 'EF1'), ('A2', 'AB2'),
    ('B2', 'BC2'), ('C2', 'CD2'), ('D2', 'DE2'),
    ('E2', 'EF2'), ('F2', 'FG2'), ('G2', 'GH2'),
    ('LC4A', 'L4AC'), ('LC5A', 'L5AC')
	DECLARE @AccionIntermedia VARCHAR(10)
    SELECT @AccionIntermedia = CONCAT(AccionOriginal,',',AccionIntermedia) FROM @accionesIntermedias WHERE AccionOriginal = @accioncobro

	print @AccionIntermedia

    -- Insert statements for procedure here
    IF @accioncobro IS NOT NULL
	BEGIN 
	      IF @accioncobro IN ('G1','I2','LC2B','LC3B','LC4B','LC5B')
		  BEGIN

				IF @usuarioAnalista IS NOT NULL  
				BEGIN
						WITH DeudaRealAgg AS (
							SELECT 
								cad.cadCartera AS carId,
								CASE 
									WHEN SUM(ISNULL(cad.cadDeudaReal, 0)) > 0 THEN 'DEUDA_REAL'
									ELSE 'DEUDA_PRESUNTA'
								END AS TipoDeuda
							FROM 
								CarteraDependiente cad
							GROUP BY 
								cad.cadCartera
						)
						SELECT 
							MAX(car.carId) AS carId,
							MAX(car.carTipoAccionCobro) AS carTipoAccionCobro,
							car.carTipoLineaCobro,
							COALESCE(d.TipoDeuda, 'DEUDA_PRESUNTA') AS carTipoDeuda, -- Usamos COALESCE por si no hay coincidencia en DeudaRealAgg
							SUM(sumatoria.carDeudaPresunta) AS carDeudaPresunta,
							car.carEstadoCartera,
							car.carTipoSolicitante,
							per.perNumeroIdentificacion,
							per.perTipoIdentificacion,
							per.perRazonSocial,
							sol.solNumeroRadicacion,
							'null' as carFechaAsignacionAccion
						FROM 
							Solicitud sol
						INNER JOIN 
							SolicitudGestionCobroManual scm ON sol.solId = scm.scmSolicitudGlobal
						JOIN 
							CicloAportante cap ON scm.scmCicloAportante = cap.capId
						JOIN 
							CicloCartera ccr ON ccr.ccrId = cap.capCicloCartera
						JOIN 
							Cartera car ON car.carPersona = cap.capPersona
						JOIN 
							Persona per ON per.perId = car.carPersona
						INNER JOIN (
							SELECT 
								carPersona, 
								SUM(carDeudaPresunta) AS carDeudaPresunta
							FROM 
								Cartera
							WHERE 
								carEstadoOperacion = 'VIGENTE'
								AND carTipoLineaCobro = @lineaCobro
							GROUP BY 
								carPersona
						) AS sumatoria ON sumatoria.carPersona = per.perId
						LEFT JOIN DeudaRealAgg d ON car.carId = d.carId
						INNER JOIN (
							SELECT 
								MIN(cagCartera) AS carteraId
							FROM 
								CarteraAgrupadora
							JOIN 
								Cartera ON carId = cagCartera
							WHERE 
								carEstadoOperacion = 'VIGENTE'
							GROUP BY 
								cagNumeroOperacion
						) AS caretaMasAntigua ON caretaMasAntigua.carteraId = car.carId
						WHERE 
							scm.scmLineaCobro = @lineaCobro
							AND ccr.ccrEstadoCiclo = 'ACTIVO' 
							AND scm.scmEstadoSolicitud IN ('ASIGNADO', 'EN_PROCESO')
							AND car.carTipoLineaCobro = @lineaCobro
							AND car.carEstadoOperacion = 'VIGENTE'
							AND car.carPersona = per.perId
							AND sol.solDestinatario = @usuarioAnalista
						GROUP BY 
							car.carPersona, car.carTipoLineaCobro, car.carEstadoCartera, car.carTipoSolicitante, per.perNumeroIdentificacion, per.perTipoIdentificacion, per.perRazonSocial, d.TipoDeuda,sol.solNumeroRadicacion
					ORDER BY 
						(CASE
							WHEN @filtros = 'tipoIdentificacion' AND @desc = 0 then per.perTipoIdentificacion
							WHEN @filtros = 'numeroIdentificacion' AND @desc = 0 THEN per.perNumeroIdentificacion
							WHEN @filtros = 'nombreCompleto' AND @desc = 0 THEN per.perRazonSocial
							WHEN @filtros = 'tipoSolicitante' AND @desc = 0 THEN car.carTipoSolicitante
							WHEN @filtros = 'estadoCartera' AND @desc = 0 THEN car.carEstadoCartera
							WHEN @filtros = 'lineaCobro' AND @desc = 0 THEN car.carTipoLineaCobro
							end) asc,
						(CASE 
							WHEN @filtros = '-tipoIdentificacion' AND @desc = 1 THEN per.perTipoIdentificacion
							WHEN @filtros = '-numeroIdentificacion' AND @desc = 1 THEN per.perNumeroIdentificacion
							WHEN @filtros = '-nombreCompleto' AND @desc = 1 THEN per.perRazonSocial
							WHEN @filtros = '-tipoSolicitante' AND @desc = 1 THEN car.carTipoSolicitante
							WHEN @filtros = '-estadoCartera' AND @desc = 1 THEN car.carEstadoCartera
							WHEN @filtros = '-lineaCobro' AND @desc = 1 THEN car.carTipoLineaCobro
							end) desc,
						(CASE
							WHEN @filtros = 'montoDeuda' AND @desc = 0 THEN SUM(sumatoria.carDeudaPresunta)
							END) ASC,
						(CASE
							WHEN @filtros = '-montoDeuda' AND @desc = 1 THEN SUM(sumatoria.carDeudaPresunta)
							END) Desc
							OFFSET @offset ROWS FETCH NEXT @rows ROWS only

						RETURN;
					END


					----CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL

				IF @usuarioAnalista IS NULL  
				BEGIN
					;WITH DeudaRealAgg AS (
						SELECT 
							cad.cadCartera AS carId,
							CASE 
								WHEN SUM(ISNULL(cad.cadDeudaReal, 0)) > 0 THEN 'DEUDA_REAL'
								ELSE 'DEUDA_PRESUNTA'
							END AS TipoDeuda
						FROM 
							CarteraDependiente cad
						GROUP BY 
							cad.cadCartera
					)
					SELECT 
						MAX(car.carId) AS carId,
						MAX(car.carTipoAccionCobro) AS carTipoAccionCobro,
						car.carTipoLineaCobro,
						COALESCE(d.TipoDeuda, 'DEUDA_PRESUNTA') AS carTipoDeuda,
						SUM(sumatoria.carDeudaPresunta) AS carDeudaPresunta,
						car.carEstadoCartera,
						car.carTipoSolicitante,
						per.perNumeroIdentificacion,
						per.perTipoIdentificacion,
						per.perRazonSocial,
						sol.solNumeroRadicacion,
						'null' as carFechaAsignacionAccion
					FROM 
						Solicitud sol
					JOIN 
						SolicitudGestionCobroManual scm ON sol.solId = scm.scmSolicitudGlobal
					JOIN 
						CicloAportante cap ON scm.scmCicloAportante = cap.capId
					JOIN 
						CicloCartera ccr ON ccr.ccrId = cap.capCicloCartera
					JOIN 
						Cartera car ON car.carPersona = cap.capPersona
					JOIN 
						Persona per ON per.perId = car.carPersona
					INNER JOIN (
						SELECT 
							carPersona, 
							SUM(carDeudaPresunta) AS carDeudaPresunta
						FROM 
							Cartera
						WHERE 
							carEstadoOperacion = 'VIGENTE'
							AND carTipoLineaCobro = @lineaCobro
						GROUP BY 
							carPersona
					) AS sumatoria ON sumatoria.carPersona = per.perId
					LEFT JOIN 
						DeudaRealAgg d ON car.carId = d.carId
					INNER JOIN (
						SELECT 
							MIN(cagCartera) AS carteraId
						FROM 
							CarteraAgrupadora
						JOIN 
							Cartera ON carId = cagCartera
						WHERE 
							carEstadoOperacion = 'VIGENTE'
						GROUP BY 
							cagNumeroOperacion
					) AS caretaMasAntigua ON caretaMasAntigua.carteraId = car.carId
					WHERE 
						scm.scmLineaCobro = @lineaCobro
						AND ccr.ccrEstadoCiclo = 'ACTIVO'
						AND scm.scmEstadoSolicitud IN ('ASIGNADO', 'EN_PROCESO')
						AND car.carTipoLineaCobro = @lineaCobro
						AND car.carEstadoOperacion = 'VIGENTE'
						AND car.carPersona = per.perId
						AND (@identificacion IS NULL OR per.perNumeroIdentificacion LIKE @identificacion)
					GROUP BY 
						car.carPersona, car.carTipoLineaCobro, car.carEstadoCartera, car.carTipoSolicitante, per.perNumeroIdentificacion, per.perTipoIdentificacion, per.perRazonSocial, d.TipoDeuda,sol.solNumeroRadicacion
						ORDER BY 
						(CASE
							WHEN @filtros = 'tipoIdentificacion' AND @desc = 0 then per.perTipoIdentificacion
							WHEN @filtros = 'numeroIdentificacion' AND @desc = 0 THEN per.perNumeroIdentificacion
							WHEN @filtros = 'nombreCompleto' AND @desc = 0 THEN per.perRazonSocial
							WHEN @filtros = 'tipoSolicitante' AND @desc = 0 THEN car.carTipoSolicitante
							WHEN @filtros = 'estadoCartera' AND @desc = 0 THEN car.carEstadoCartera
							WHEN @filtros = 'lineaCobro' AND @desc = 0 THEN car.carTipoLineaCobro
							end) asc,
						(CASE 
							WHEN @filtros = '-tipoIdentificacion' AND @desc = 1 THEN per.perTipoIdentificacion
							WHEN @filtros = '-numeroIdentificacion' AND @desc = 1 THEN per.perNumeroIdentificacion
							WHEN @filtros = '-nombreCompleto' AND @desc = 1 THEN per.perRazonSocial
							WHEN @filtros = '-tipoSolicitante' AND @desc = 1 THEN car.carTipoSolicitante
							WHEN @filtros = '-estadoCartera' AND @desc = 1 THEN car.carEstadoCartera
							WHEN @filtros = '-lineaCobro' AND @desc = 1 THEN car.carTipoLineaCobro
							end) desc,
						(CASE
							WHEN @filtros = 'montoDeuda' AND @desc = 0 THEN SUM(sumatoria.carDeudaPresunta)
							END) ASC,
						(CASE
							WHEN @filtros = '-montoDeuda' AND @desc = 1 THEN SUM(sumatoria.carDeudaPresunta)
							END) Desc
						OFFSET @offset ROWS FETCH NEXT @rows ROWS only
						RETURN;
				END
			END
			ELSE
			if  @lineaCobro = 'LC2' 
			BEGIN
			        SELECT car.carId,
							carTipoAccionCobro,
							carTipoLineaCobro,carTipoDeuda,
							sumaDeuda.sumCarDeudaPresunta as carDeudaPresunta,
							carEstadoCartera,
							carTipoSolicitante,
							perNumeroIdentificacion,
							perTipoIdentificacion,
							perRazonSocial,
							-- se comenta por inconsistencia en el objeto	
							(CASE
							WHEN (SELECT count(*)
							FROM BitacoraCartera bc
							WHERE bc.bcaPersona = per.perid
							AND bc.bcaActividad = 'FIRMEZA_TITULO_EJECUTIVO') = 0 THEN 'DEUDA_PRESUNTA'
							ELSE 'DEUDA_REAL' END) AS cadDeudaReal,
							caretaMasAntigua.cagNumeroOperacion as solNumeroRadicacion,
							'null' as carFechaAsignacionAccion
					FROM Cartera car
						INNER JOIN Persona per	ON car.carPersona = per.perId
						INNER JOIN (
								SELECT
								MAX(c.carfechaCreacion) AS maxFechaCreacion,
								p.perId
								FROM Cartera c  join persona p on p.perid=carPersona
								WHERE c.cartipoLineaCobro =@lineaCobro
								AND carestadoOperacion = 'VIGENTE'
								AND (
									(@AccionIntermedia IS NOT NULL AND c.carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
									OR
									(@AccionIntermedia IS NULL AND c.carTipoAccionCobro = @AccionCobro)
								)
								GROUP BY p.perId
								) AS maxFecha ON car.carfechaCreacion = maxFecha.maxFechaCreacion AND per.perId = maxFecha.perId
						INNER JOIN (
							SELECT MIN(carId) AS carteraId, cagNumeroOperacion
								FROM CarteraAgrupadora
								JOIN Cartera cart ON carId = cagCartera
							WHERE carEstadoOperacion = 'VIGENTE'
								GROUP BY cagNumeroOperacion
						) AS caretaMasAntigua ON caretaMasAntigua.carteraId = car.carId
						LEFT JOIN (SELECT sum(s) as suma, C.carId
								FROM (
									SELECT (case
										when ca.cadDeudaReal > 0 then ca.cadDeudaReal
										else ca.cadDeudaPresunta end) as s,
										ca.cadCartera as cartera
									FROM CarteraDependiente ca
										INNER JOIN Cartera C ON C.carId = ca.cadCartera
									WHERE c.carPersona = C.carPersona
										and c.carestadoOperacion = 'VIGENTE'
										AND c.cartipoLineaCobro = @lineaCobro
										AND (
                      (@AccionIntermedia IS NOT NULL AND c.carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
                      OR
                      (@AccionIntermedia IS NULL AND c.carTipoAccionCobro = @AccionCobro)
                    )
										and cardeudapresunta > 0
								) as valor
							INNER JOIN Cartera C on C.carId = valor.cartera
								group by C.carId
						) as vs on vs.carId = car.carId
					    INNER JOIN (
							SELECT car.carPersona, sum(carDeudaPresunta) AS sumCarDeudaPresunta
								FROM Cartera car
							WHERE car.carestadoOperacion = 'VIGENTE'
								AND car.cartipoLineaCobro = @lineaCobro
								AND (
									(@AccionIntermedia IS NOT NULL AND car.carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
									OR
									(@AccionIntermedia IS NULL AND car.carTipoAccionCobro = @AccionCobro)
								)
							GROUP BY car.carPersona
						) AS sumaDeuda ON sumaDeuda.carPersona = per.perId
							WHERE cartipoLineaCobro = @lineaCobro
							AND carestadoOperacion = 'VIGENTE'
							AND (
                (@AccionIntermedia IS NOT NULL AND carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
                OR
                (@AccionIntermedia IS NULL AND carTipoAccionCobro = @AccionCobro)
              )
							AND ((@identificacion IS NULL ) OR @identificacion like per.perNumeroIdentificacion)
						ORDER BY 
						(CASE
							WHEN @filtros = 'tipoIdentificacion' AND @desc = 0 then per.perTipoIdentificacion
							WHEN @filtros = 'numeroIdentificacion' AND @desc = 0 THEN per.perNumeroIdentificacion
							WHEN @filtros = 'nombreCompleto' AND @desc = 0 THEN per.perRazonSocial
							WHEN @filtros = 'tipoSolicitante' AND @desc = 0 THEN car.carTipoSolicitante
							WHEN @filtros = 'estadoCartera' AND @desc = 0 THEN car.carEstadoCartera
							WHEN @filtros = 'lineaCobro' AND @desc = 0 THEN car.carTipoLineaCobro
							end) asc,
						(CASE 
							WHEN @filtros = '-tipoIdentificacion' AND @desc = 1 THEN per.perTipoIdentificacion
							WHEN @filtros = '-numeroIdentificacion' AND @desc = 1 THEN per.perNumeroIdentificacion
							WHEN @filtros = '-nombreCompleto' AND @desc = 1 THEN per.perRazonSocial
							WHEN @filtros = '-tipoSolicitante' AND @desc = 1 THEN car.carTipoSolicitante
							WHEN @filtros = '-estadoCartera' AND @desc = 1 THEN car.carEstadoCartera
							WHEN @filtros = '-lineaCobro' AND @desc = 1 THEN car.carTipoLineaCobro
							end) desc
							,
						(CASE
							WHEN @filtros = 'montoDeuda' AND @desc = 0 THEN sumaDeuda.sumCarDeudaPresunta
							END) ASC,
						(CASE
							WHEN @filtros = '-montoDeuda' AND @desc = 1 THEN sumaDeuda.sumCarDeudaPresunta
							END) Desc
							OFFSET @offset ROWS FETCH NEXT @rows ROWS only
							RETURN;
			END
			else 
			BEGIN
			         SELECT car.carId,
							carTipoAccionCobro,
							carTipoLineaCobro,
							car.carTipoDeuda,
							sumaDeuda.sumCarDeudaPresunta as carDeudaPresunta,
							carEstadoCartera,
							carTipoSolicitante,
							perNumeroIdentificacion,
							perTipoIdentificacion,
							perRazonSocial,
							-- se comenta por inconsistencia en el objeto	
							(CASE
							WHEN (SELECT count(*)
							FROM BitacoraCartera bc
							WHERE bc.bcaPersona = per.perid
							AND bc.bcaActividad = 'FIRMEZA_TITULO_EJECUTIVO') = 0 THEN 'DEUDA_PRESUNTA'
							ELSE 'DEUDA_REAL' END) AS cadDeudaReal,
							caretaMasAntigua.cagNumeroOperacion as solNumeroRadicacion,
							'null' as carFechaAsignacionAccion
					    FROM Cartera car
				  INNER JOIN Persona per
						  ON car.carPersona = per.perId
					 INNER JOIN (
							SELECT MIN(carId) AS carteraId, cagNumeroOperacion
								FROM CarteraAgrupadora
								JOIN Cartera cart ON carId = cagCartera
							WHERE carEstadoOperacion = 'VIGENTE'
								GROUP BY cagNumeroOperacion
							) AS caretaMasAntigua ON caretaMasAntigua.carteraId = car.carId
					 LEFT JOIN (SELECT sum(s) as suma, C.carId
								FROM (
								SELECT (case
								when ca.cadDeudaReal > 0 then ca.cadDeudaReal
								else ca.cadDeudaPresunta end) as s,
								ca.cadCartera as cartera
								FROM CarteraDependiente ca
						  INNER JOIN Cartera C ON C.carId = ca.cadCartera
								WHERE c.carPersona = C.carPersona
								and c.carestadoOperacion = 'VIGENTE'
								AND c.cartipoLineaCobro = @lineaCobro
								AND (
									(@AccionIntermedia IS NOT NULL AND c.carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
									OR
									(@AccionIntermedia IS NULL AND c.carTipoAccionCobro = @AccionCobro)
								)
								and cardeudapresunta > 0
								) as valor
						 INNER JOIN Cartera C on C.carId = valor.cartera
							group by C.carId
							) as vs on vs.carId = car.carId
					    INNER JOIN (
							SELECT car.carPersona, sum(carDeudaPresunta) AS sumCarDeudaPresunta
							FROM Cartera car
							WHERE car.carestadoOperacion = 'VIGENTE'
							AND car.cartipoLineaCobro = @lineaCobro
					  AND (
              (@AccionIntermedia IS NOT NULL AND car.carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
              OR
              (@AccionIntermedia IS NULL AND car.carTipoAccionCobro = @AccionCobro)
            )
							GROUP BY car.carPersona
							) AS sumaDeuda ON sumaDeuda.carPersona = per.perId
							WHERE cartipoLineaCobro = @lineaCobro
							AND carestadoOperacion = 'VIGENTE'
							AND (
                (@AccionIntermedia IS NOT NULL AND carTipoAccionCobro IN (SELECT value FROM string_split(@AccionIntermedia, ',')))
                OR
                (@AccionIntermedia IS NULL AND carTipoAccionCobro = @AccionCobro)
              )
							AND ((@identificacion IS NULL ) OR @identificacion like per.perNumeroIdentificacion)
							ORDER BY 
						(CASE
							WHEN @filtros = 'tipoIdentificacion' AND @desc = 0 then per.perTipoIdentificacion
							WHEN @filtros = 'numeroIdentificacion' AND @desc = 0 THEN per.perNumeroIdentificacion
							WHEN @filtros = 'nombreCompleto' AND @desc = 0 THEN per.perRazonSocial
							WHEN @filtros = 'tipoSolicitante' AND @desc = 0 THEN car.carTipoSolicitante
							WHEN @filtros = 'estadoCartera' AND @desc = 0 THEN car.carEstadoCartera
							WHEN @filtros = 'lineaCobro' AND @desc = 0 THEN car.carTipoLineaCobro
							end) asc,
						(CASE 
							WHEN @filtros = '-tipoIdentificacion' AND @desc = 1 THEN per.perTipoIdentificacion
							WHEN @filtros = '-numeroIdentificacion' AND @desc = 1 THEN per.perNumeroIdentificacion
							WHEN @filtros = '-nombreCompleto' AND @desc = 1 THEN per.perRazonSocial
							WHEN @filtros = '-tipoSolicitante' AND @desc = 1 THEN car.carTipoSolicitante
							WHEN @filtros = '-estadoCartera' AND @desc = 1 THEN car.carEstadoCartera
							WHEN @filtros = '-lineaCobro' AND @desc = 1 THEN car.carTipoLineaCobro
							end) desc,
						(CASE
							WHEN @filtros = 'montoDeuda' AND @desc = 0 THEN sumaDeuda.sumCarDeudaPresunta
							END) ASC,
						(CASE
							WHEN @filtros = '-montoDeuda' AND @desc = 1 THEN sumaDeuda.sumCarDeudaPresunta
							END) Desc
							OFFSET @offset ROWS FETCH NEXT @rows ROWS only
							RETURN;
			END
		END
		----ACCION DE COBRO NULA

		    SELECT carTipoAccionCobro,carTipoLineaCobro,carTipoDeuda,carTipoDeuda as cadDeudaReal,
				sumaDeuda.carDeudaPresunta AS carDeudaPresunta,
				carEstadoCartera,carTipoSolicitante,perNumeroIdentificacion,perTipoIdentificacion,perRazonSocial,carFechaAsignacionAccion,carId,caretaMasAntigua.cagNumeroOperacion as solNumeroRadicacion
			FROM Cartera car
                
				INNER JOIN Persona Per ON carPersona = perId
				INNER JOIN (
					SELECT MIN(carId) AS carteraId, cagNumeroOperacion
						FROM CarteraAgrupadora
						JOIN Cartera ON carId=cagCartera
					WHERE carEstadoOperacion='VIGENTE'
						GROUP BY cagNumeroOperacion
				) AS caretaMasAntigua ON caretaMasAntigua.carteraId = car.carId
				INNER JOIN (
					SELECT car.carPersona, sum(carDeudaPresunta) AS carDeudaPresunta
						FROM Cartera car
					WHERE car.carestadoOperacion = 'VIGENTE'
						AND car.cartipoLineaCobro = @lineaCobro
						GROUP BY car.carPersona
				) AS sumaDeuda ON sumaDeuda.carPersona = perId
				WHERE cartipoLineaCobro = @lineaCobro
				AND carestadoOperacion = 'VIGENTE'
				AND ((@identificacion IS NULL) OR (@identificacion like per.perNumeroIdentificacion))
				AND carTipoAccionCobro IN (SELECT Valor FROM @Acciones )
					
					ORDER BY 
						(CASE
							WHEN @filtros = 'tipoIdentificacion' AND @desc = 0 then per.perTipoIdentificacion
							WHEN @filtros = 'numeroIdentificacion' AND @desc = 0 THEN per.perNumeroIdentificacion
							WHEN @filtros = 'nombreCompleto' AND @desc = 0 THEN per.perRazonSocial
							WHEN @filtros = 'tipoSolicitante' AND @desc = 0 THEN car.carTipoSolicitante
							WHEN @filtros = 'estadoCartera' AND @desc = 0 THEN car.carEstadoCartera
							WHEN @filtros = 'lineaCobro' AND @desc = 0 THEN car.carTipoLineaCobro
							end) asc,
						(CASE 
							WHEN @filtros = '-tipoIdentificacion' AND @desc = 1 THEN per.perTipoIdentificacion
							WHEN @filtros = '-numeroIdentificacion' AND @desc = 1 THEN per.perNumeroIdentificacion
							WHEN @filtros = '-nombreCompleto' AND @desc = 1 THEN per.perRazonSocial
							WHEN @filtros = '-tipoSolicitante' AND @desc = 1 THEN car.carTipoSolicitante
							WHEN @filtros = '-estadoCartera' AND @desc = 1 THEN car.carEstadoCartera
							WHEN @filtros = '-lineaCobro' AND @desc = 1 THEN car.carTipoLineaCobro
							end) desc,
						(CASE
							WHEN @filtros = 'montoDeuda' AND @desc = 0 THEN sumaDeuda.carDeudaPresunta
							END) ASC,
						(CASE
							WHEN @filtros = '-montoDeuda' AND @desc = 1 THEN sumaDeuda.carDeudaPresunta
							END) Desc,
						(CASE
							WHEN @filtros = 'fechaIngreso' AND @desc = 0 THEN car.carFechaAsignacionAccion
							END) ASC,
						(CASE
							WHEN @filtros = '-fechaIngreso' AND @desc = 1 THEN car.carFechaAsignacionAccion
							END) Desc
						OFFSET @offset ROWS FETCH NEXT @rows ROWS only

					RETURN;
END