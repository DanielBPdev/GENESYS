-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2019/01/12
-- Description:	SP que lista los datos del reporte Novedades del estado de la afiliación y 
-- al día del aportante Resolución 1056
-- =============================================
CREATE PROCEDURE USP_GET_NovedadesEstadoAfiliacion
	@dFechaInicial DATE,
	@dFechaFin DATE,
	@bCount BIT
WITH EXECUTE AS OWNER
AS 

BEGIN
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);

	DECLARE @tCartera_aud AS TABLE (persona BIGINT, shard VARCHAR(500))

	SET @sql = '
		SELECT car.carPersona persona 
		FROM dbo.Cartera_aud car 
		INNER JOIN dbo.Revision rev ON rev.revId = car.REV
		WHERE 1 = 1
		AND car.carEstadoCartera = ''AL_DIA'' 
		AND car.carEstadoOperacion = ''NO_VIGENTE''
		AND (DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') BETWEEN @dFechaInicial AND @dFechaFin
		GROUP BY car.carId, car.carPersona, car.REV
		HAVING car.REV = MIN(car.REV)'

	INSERT INTO @tCartera_aud (persona, shard)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@dFechaInicial DATE, @dFechaFin DATE',
	@dFechaInicial = @dFechaInicial, @dFechaFin = @dFechaFin

	IF @bCount = 1 
	BEGIN
		SELECT COUNT(1) 
		FROM (
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE IND Y PENSIONADOS ------------------------------------------------
			SELECT per.perId
			FROM dbo.Persona per 
			INNER JOIN (
				SELECT DISTINCT A.persona, A.tipoAfiliacion
				FROM (
					-- activación de independiente o pensionado
					SELECT afi.afiPersona persona, roa.roaTipoAfiliado tipoAfiliacion 
					FROM dbo.RolAfiliado roa
					INNER JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado
					WHERE roa.roaFechaAfiliacion BETWEEN @dFechaInicial AND @dFechaFin
					AND roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
					AND roa.roaEstadoAfiliado IS NOT NULL
					UNION
					-- retiro de independiente o pensionado
					SELECT afi.afiPersona persona, roa.roaTipoAfiliado tipoAfiliacion 
					FROM dbo.RolAfiliado roa
					INNER JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado
					WHERE roa.roaFechaRetiro BETWEEN @dFechaInicial AND @dFechaFin
					AND roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
					AND roa.roaEstadoAfiliado IS NOT NULL
				) AS A
			) AS T ON T.persona = per.perId
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado = T.tipoAfiliacion
			UNION ALL
		----------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE DEPENDIENTES ------------------------------------------------
			SELECT per.perId
			FROM dbo.Persona per 
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId 
			INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId 
			INNER JOIN dbo.Persona pem ON emp.empPersona = pem.perId 
			INNER JOIN (
				SELECT DISTINCT A.persona, A.tipoAfiliacion
				FROM (
					-- activación de empleador
					SELECT emp.empPersona persona, 'EMPLEADOR' tipoAfiliacion
					FROM dbo.Empleador eml 
					INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
					WHERE eml.empEstadoEmpleador = 'ACTIVO'
					AND eml.empFechaCambioEstadoAfiliacion BETWEEN @dFechaInicial AND @dFechaFin
					UNION
					-- retiro de empleador
					SELECT emp.empPersona persona, 'EMPLEADOR' tipoAfiliacion
					FROM dbo.Empleador eml 
					INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
					WHERE eml.empFechaRetiro BETWEEN @dFechaInicial AND @dFechaFin
				) AS A
			) AS T ON T.persona = pem.perId
			UNION ALL
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE CARTERA DE IND Y PENSIONADOS ------------------------------------------------
			SELECT per.perId
			FROM dbo.Persona per 
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			INNER JOIN (
				SELECT DISTINCT C.persona, C.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @dFechaInicial AND @dFechaFin
					UNION
					SELECT persona, 1 estadoCartera 
					FROM @tCartera_aud 
				) AS C
			) AS T ON T.persona = per.perId
			WHERE roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
			UNION ALL
		------------------------------------------------ REGISTROS DE CAMBIO EN ESTADO DE CARTERA DE DEPENDIENTES ------------------------------------------------
			SELECT per.perId
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
					WHERE car.carFechaCreacion BETWEEN @dFechaInicial AND @dFechaFin
					UNION
					SELECT persona, 1 estadoCartera 
					FROM @tCartera_aud 
				) AS C
			) AS T ON T.persona = pem.perId
		) AS Unificado
	END
	ELSE
	BEGIN
		SELECT * 
		FROM (
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE IND Y PENSIONADOS ------------------------------------------------
			SELECT  
				2 tipoRegistro, 
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF, 
				per.perTipoIdentificacion, 
				per.perNumeroIdentificacion, 
				per.perPrimerApellido, 
				per.perSegundoApellido, 
				per.perPrimerNombre, 
				per.perSegundoNombre, 
				'C05' codigoNovedad, 
				roa.roaEstadoAfiliado estadoAfiliacion, 
				per.perTipoIdentificacion tipoIdApor, 
				per.perNumeroIdentificacion numIdApor,
				NULL digitoVerificacion, 
				CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
				ELSE (
					per.perPrimerNombre + 
					CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
					per.perPrimerApellido + 
					CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
				) END razonSocial, 
				NULL estadoMora
			FROM dbo.Persona per 
			INNER JOIN (
				SELECT DISTINCT A.persona, A.tipoAfiliacion
				FROM (
					-- activación de independiente o pensionado
					SELECT afi.afiPersona persona, roa.roaTipoAfiliado tipoAfiliacion 
					FROM dbo.RolAfiliado roa
					INNER JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado
					WHERE roa.roaFechaAfiliacion BETWEEN @dFechaInicial AND @dFechaFin
					AND roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
					AND roa.roaEstadoAfiliado IS NOT NULL
					UNION
					-- retiro de independiente o pensionado
					SELECT afi.afiPersona persona, roa.roaTipoAfiliado tipoAfiliacion 
					FROM dbo.RolAfiliado roa
					INNER JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado
					WHERE roa.roaFechaRetiro BETWEEN @dFechaInicial AND @dFechaFin
					AND roa.roaTipoAfiliado != 'TRABAJADOR_DEPENDIENTE'
					AND roa.roaEstadoAfiliado IS NOT NULL
				) AS A
			) AS T ON T.persona = per.perId
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado = T.tipoAfiliacion
			UNION ALL
		----------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE AFILIACION DE DEPENDIENTES ------------------------------------------------
			SELECT 
				2 tipoRegistro, 
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF, 
				per.perTipoIdentificacion, 
				per.perNumeroIdentificacion, 
				per.perPrimerApellido, 
				per.perSegundoApellido, 
				per.perPrimerNombre, 
				per.perSegundoNombre, 
				'C05' codigoNovedad, 
				roa.roaEstadoAfiliado estadoAfiliacion, 
				pem.perTipoIdentificacion tipoIdApor, 
				pem.perNumeroIdentificacion numIdApor,
				pem.perDigitoVerificacion digitoVerificacion, 
				CASE WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre + 
					CASE WHEN pem.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pem.perSegundoNombre+' ' END +
					pem.perPrimerApellido + 
					CASE WHEN pem.perSegundoApellido IS NULL THEN '' ELSE ' '+pem.perSegundoApellido END
				) END razonSocial, 
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
					-- activación de empleador
					SELECT emp.empPersona persona, 'EMPLEADOR' tipoAfiliacion
					FROM dbo.Empleador eml 
					INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
					WHERE eml.empEstadoEmpleador = 'ACTIVO'
					AND eml.empFechaCambioEstadoAfiliacion BETWEEN @dFechaInicial AND @dFechaFin
					UNION
					-- retiro de empleador
					SELECT emp.empPersona persona, 'EMPLEADOR' tipoAfiliacion
					FROM dbo.Empleador eml 
					INNER JOIN dbo.Empresa emp ON eml.empEmpresa = emp.empId
					WHERE eml.empFechaRetiro BETWEEN @dFechaInicial AND @dFechaFin
				) AS A
			) AS T ON T.persona = pem.perId
			UNION ALL
		------------------------------------------- REGISTROS DE CAMBIO EN ESTADO DE CARTERA DE IND Y PENSIONADOS ------------------------------------------------
			SELECT 
				2 tipoRegistro, 
				(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoCCF, 
				per.perTipoIdentificacion, 
				per.perNumeroIdentificacion, 
				per.perPrimerApellido, 
				per.perSegundoApellido, 
				per.perPrimerNombre, 
				per.perSegundoNombre, 
				'C06' codigoNovedad, 
				NULL estadoAfiliacion, 
				per.perTipoIdentificacion tipoIdApor, 
				per.perNumeroIdentificacion numIdApor,
				NULL digitoVerificacion, 
				CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
				ELSE (
					per.perPrimerNombre + 
					CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
					per.perPrimerApellido + 
					CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
				) END razonSocial, 
				T.estadoCartera estadoMora
			FROM dbo.Persona per 
			INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId 
			INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			INNER JOIN (
				SELECT DISTINCT C.persona, C.estadoCartera
				FROM (
					SELECT car.carPersona persona, 2 estadoCartera 
					FROM dbo.Cartera car 
					WHERE car.carFechaCreacion BETWEEN @dFechaInicial AND @dFechaFin
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
				per.perTipoIdentificacion, 
				per.perNumeroIdentificacion, 
				per.perPrimerApellido, 
				per.perSegundoApellido, 
				per.perPrimerNombre, 
				per.perSegundoNombre, 
				'C06' codigoNovedad, 
				NULL estadoAfiliacion, 
				pem.perTipoIdentificacion tipoIdApor, 
				pem.perNumeroIdentificacion numIdApor,
				pem.perDigitoVerificacion digitoVerificacion, 
				CASE WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre + 
					CASE WHEN pem.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pem.perSegundoNombre+' ' END +
					pem.perPrimerApellido + 
					CASE WHEN pem.perSegundoApellido IS NULL THEN '' ELSE ' '+pem.perSegundoApellido END
				) END razonSocial, 
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
					WHERE car.carFechaCreacion BETWEEN @dFechaInicial AND @dFechaFin
					UNION
					SELECT persona, 1 estadoCartera 
					FROM @tCartera_aud 
				) AS C
			) AS T ON T.persona = pem.perId
		) AS Detalle
		ORDER BY Detalle.perTipoIdentificacion, Detalle.perNumeroIdentificacion, Detalle.codigoNovedad
	END
END