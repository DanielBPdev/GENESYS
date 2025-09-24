-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoNovedadesEstadoAfiliacion
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(4000);

	DECLARE @tCartera_aud AS TABLE (persona BIGINT, shard VARCHAR(500))

	SET @sql = '
			SELECT car.carPersona
			FROM Cartera_aud car
			INNER JOIN Revision rev ON car.REV = rev.revId
			WHERE car.carEstadoOperacion = ''NO_VIGENTE''
			  AND car.carEstadoCartera = ''AL_DIA''
			  AND cast(dateadd(second, rev.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' as date) between @fechaInicio AND @fechaFin
			  AND (SELECT TOP 1 carEstadoOperacion
					FROM Cartera_aud
					WHERE REV<rev.revId
					  AND carId = car.carId
				   ORDER BY REV DESC) = ''VIGENTE'''

	INSERT INTO @tCartera_aud (persona, shard)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@fechaInicio DATE, @fechaFin DATE',
	@fechaInicio = @fechaInicio, @fechaFin = @fechaFin

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoNovedadesEstadoAfiliacion(
			hneFechaHistorico,
			hneTipoRegistro,
			hneCodigoCCF,
			hnePerTipoIdentificacion,
			hnePerNumeroIdentificacion,
			hnePerPrimerApellido,
			hnePerSegundoApellido,
			hnePerPrimerNombre,
			hnePerSegundoNombre,
			hneCodigoNovedad,
			hneEstadoAfiliacion,
			hneTipoIdApor,
			hneNumIdApor,
			hneDigitoVerificacion,
			hneRazonSocial,
			hneEstadoMora,
			hneFechaInicialReporte,
			hneFechaFinalReporte)
		SELECT @fechaFin, * ,@fechaInicio,@fechaFin
		FROM (
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE IND Y PENSIONADOS ------------------------------------------------
			SELECT
				2 tipoRegistro,
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF,
				CASE per.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				END perTipoIdentificacion,
				per.perNumeroIdentificacion,
				substring(per.perPrimerApellido,1,60) perPrimerApellido,
				substring(per.perSegundoApellido,1,60) perSegundoApellido,
				substring(per.perPrimerNombre,1,60) perPrimerNombre,
				substring(per.perSegundoNombre,1,60) perSegundoNombre,
				'C05' codigoNovedad,
				CASE Mora.estadoCartera
					WHEN 2 THEN 3
					ELSE 1
				END estadoAfiliacion,
				CASE per.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				    WHEN 'NIT' THEN 'NI'
				END tipoIdApor,
				per.perNumeroIdentificacion numIdApor,
				NULL digitoVerificacion,
				LEFT(CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
				ELSE (
					per.perPrimerNombre +
					CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
					per.perPrimerApellido +
					CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
				) END,150) razonSocial,
				NULL estadoMora
			FROM dbo.Persona per
			INNER JOIN (
				SELECT DISTINCT A.persona, A.tipoAfiliacion
				FROM (
					-- activaciÃ³n de independiente o pensionado
					SELECT afi.afiPersona persona, roa.roaTipoAfiliado tipoAfiliacion
					FROM dbo.RolAfiliado roa
					INNER JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado
					WHERE roa.roaFechaAfiliacion BETWEEN @fechaInicio AND @fechaFin
					AND roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
					AND roa.roaEstadoAfiliado IS NOT NULL
				) AS A
			) AS T ON T.persona = per.perId
			LEFT JOIN (
				SELECT DISTINCT Cartera.persona, Cartera.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @fechaInicio AND @fechaFin
					 AND car.carEstadoOperacion = 'VIGENTE'
					 AND car.carDeudaPresunta >0	
				) AS Cartera
			) AS Mora ON Mora.persona = per.perId
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado = T.tipoAfiliacion
			WHERE roa.roaEstadoAfiliado = 'ACTIVO'
		UNION ALL
		----------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE DEPENDIENTES ------------------------------------------------
			SELECT
				2 tipoRegistro,
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF,
				CASE per.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				END perTipoIdentificacion,
				per.perNumeroIdentificacion,
				substring(per.perPrimerApellido,1,60) perPrimerApellido,
				substring(per.perSegundoApellido,1,60) perSegundoApellido,
				substring(per.perPrimerNombre,1,60) perPrimerNombre,
				substring(per.perSegundoNombre,1,60) perSegundoNombre,
				'C05' codigoNovedad,
				CASE Mora.estadoCartera
					WHEN 2 THEN 3
					ELSE 1
				END estadoAfiliacion,
				CASE pem.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				    WHEN 'NIT' THEN 'NI'
				END tipoIdApor,
				pem.perNumeroIdentificacion numIdApor,
				pem.perDigitoVerificacion digitoVerificacion,
				LEFT(CASE WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre +
					CASE WHEN pem.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pem.perSegundoNombre+' ' END +
					pem.perPrimerApellido +
					CASE WHEN pem.perSegundoApellido IS NULL THEN '' ELSE ' '+pem.perSegundoApellido END
				) END,150) razonSocial,
				NULL estadoMora
			FROM dbo.Persona per
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId
			INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId
			INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
			INNER JOIN dbo.Persona pem ON emp.empPersona = pem.perId
			INNER JOIN (
				SELECT DISTINCT A.persona, A.tipoAfiliacion
				FROM (
					-- activaciÃ³n de empleador
					SELECT emp.empPersona persona, 'EMPLEADOR' tipoAfiliacion
					FROM dbo.Empleador eml
					INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
					WHERE eml.empEstadoEmpleador = 'ACTIVO'
					AND eml.empFechaCambioEstadoAfiliacion BETWEEN @fechaInicio AND @fechaFin
				) AS A
			) AS T ON T.persona = pem.perId
			LEFT JOIN (
				SELECT DISTINCT Cartera.persona, Cartera.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @fechaInicio AND @fechaFin
					 AND car.carEstadoOperacion = 'VIGENTE'
					 AND car.carDeudaPresunta >0	
				) AS Cartera
			) AS Mora ON Mora.persona = pem.perId 
			WHERE roa.roaEstadoAfiliado = 'ACTIVO'
		UNION ALL
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE CARTERA DE IND Y PENSIONADOS ------------------------------------------------
			SELECT 
				2 tipoRegistro, 
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF, 
				CASE per.perTipoIdentificacion 
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
				END perTipoIdentificacion, 
				per.perNumeroIdentificacion, 
				substring(per.perPrimerApellido,1,60) perPrimerApellido,  
				substring(per.perSegundoApellido,1,60) perSegundoApellido,  
				substring(per.perPrimerNombre,1,60) perPrimerNombre,  
				substring(per.perSegundoNombre,1,60) perSegundoNombre,  
				'C06' codigoNovedad, 
				NULL estadoAfiliacion, 
				CASE per.perTipoIdentificacion 
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				    WHEN 'NIT' THEN 'NI'
				END tipoIdApor,
				per.perNumeroIdentificacion numIdApor,
				NULL digitoVerificacion, 
				LEFT(CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
				ELSE (
					per.perPrimerNombre + 
					CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
					per.perPrimerApellido + 
					CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
				) END,150) razonSocial, 
				T.estadoCartera estadoMora
			FROM dbo.Persona per 
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			INNER JOIN (
				SELECT DISTINCT C.persona, C.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @fechaInicio AND @fechaFin
					  AND car.carEstadoOperacion = 'VIGENTE'
					  AND car.carDeudaPresunta >0					  
					UNION
					SELECT persona, 1 estadoCartera 
					FROM @tCartera_aud 
				) AS C
			) AS T ON T.persona = per.perId
			WHERE roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
		UNION ALL
		------------------------------------------------ REGISTROS DE CAMBIO EN ESTADO DE CARTERA DE DEPENDIENTES ------------------------------------------------
			SELECT
				2 tipoRegistro,
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF,
				CASE per.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				END perTipoIdentificacion,
				per.perNumeroIdentificacion,
				substring(per.perPrimerApellido,1,60) perPrimerApellido,
				substring(per.perSegundoApellido,1,60) perSegundoApellido,
				substring(per.perPrimerNombre,1,60) perPrimerNombre,
				substring(per.perSegundoNombre,1,60) perSegundoNombre,
				'C06' codigoNovedad,
				NULL estadoAfiliacion,
				CASE per.perTipoIdentificacion
				    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				    WHEN 'PASAPORTE' THEN 'PA'
				    WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				    WHEN 'NIT' THEN 'NI'
				END tipoIdApor,
				pem.perNumeroIdentificacion numIdApor,
				pem.perDigitoVerificacion digitoVerificacion, 
				LEFT(CASE WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre + 
					CASE WHEN pem.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pem.perSegundoNombre+' ' END +
					pem.perPrimerApellido + 
					CASE WHEN pem.perSegundoApellido IS NULL THEN '' ELSE ' '+pem.perSegundoApellido END
				) END,150) razonSocial, 
				T.estadoCartera estadoMora
			FROM dbo.Persona per 
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId 
			INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId 
			INNER JOIN dbo.Persona pem ON emp.empPersona = pem.perId 
			INNER JOIN (
				SELECT DISTINCT C.persona, C.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @fechaInicio AND @fechaFin
					 AND car.carEstadoOperacion = 'VIGENTE'
					 AND car.carDeudaPresunta >0	
					UNION
					SELECT persona, 1 estadoCartera 
					FROM @tCartera_aud 
				) AS C
			) AS T ON T.persona = pem.perId
		) AS Detalle
		WHERE Detalle.perTipoIdentificacion IN ('CC','TI','CE','PA','CD')
		  AND Detalle.tipoIdApor IN ('CC','TI','CE','PA','CD','NI')
		ORDER BY Detalle.perTipoIdentificacion, Detalle.perNumeroIdentificacion, Detalle.codigoNovedad
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;