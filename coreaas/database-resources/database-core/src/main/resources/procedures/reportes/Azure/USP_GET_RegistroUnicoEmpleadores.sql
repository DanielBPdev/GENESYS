/****** Object:  StoredProcedure [dbo].[USP_GET_RegistroUnicoEmpleadores]    Script Date: 6/06/2024 9:00:18 a. m. ******/
-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2019/01/12
-- Description:	SP que lista los datos del reporte Registro �nico de Empleadores
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_GET_RegistroUnicoEmpleadores]
	@dFechaInicial DATE ,
	@dFechaFin DATE,
	@bCount BIT
WITH EXECUTE AS OWNER
AS 

BEGIN
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);

	DECLARE @tEstadoAfiliacionEmpleadorCaja AS TABLE (
		eecId BIGINT, eecPersona BIGINT, eecEstadoAfiliacion VARCHAR (50), eecFechaCambioEstado	DATETIME, shard VARCHAR(500))

	SET @sql = '
		SELECT eec.eecId, eec.eecPersona, eec.eecEstadoAfiliacion, eec.eecFechaCambioEstado 
		FROM dbo.EstadoAfiliacionEmpleadorCaja eec
		INNER JOIN (
			SELECT MAX(eec2.eecId) eecId, eec2.eecPersona 
			FROM dbo.EstadoAfiliacionEmpleadorCaja eec2
			WHERE eec2.eecEstadoAfiliacion = ''ACTIVO''
			AND eec2.eecFechaCambioEstado <= @dFechaFin
			GROUP BY eec2.eecPersona
		) AS T ON eec.eecId = T.eecId'

	INSERT INTO @tEstadoAfiliacionEmpleadorCaja (eecId, eecPersona, eecEstadoAfiliacion, eecFechaCambioEstado, shard)
	EXEC sp_execute_remote ReportesReferenceData,
	@sql,
	N'@dFechaFin DATE',
	@dFechaFin = @dFechaFin

	IF @bCount = 1 
	BEGIN
		SELECT COUNT(*) FROM @tEstadoAfiliacionEmpleadorCaja
	END
	ELSE
	BEGIN
		DECLARE @tCategoriaAfiliado AS TABLE (
			ctaAfiliado VARCHAR(50), ctaEstadoAfiliacion VARCHAR(8), ctaFechaCambioCategoria DATETIME, ctaCategoria VARCHAR(50), shard VARCHAR(500))

		SET @sql = '
			SELECT cta.ctaAfiliado, cta.ctaEstadoAfiliacion, cta.ctaFechaCambioCategoria, cta.ctaCategoria
			FROM dbo.CategoriaAfiliado cta
			INNER JOIN (
				SELECT MAX(cta2.ctaId) ctaId, cta2.ctaAfiliado
				FROM dbo.CategoriaAfiliado cta2
				WHERE cta2.ctaEstadoAfiliacion = ''ACTIVO''
				AND cta2.ctaFechaCambioCategoria <= @dFechaFin
				GROUP BY cta2.ctaAfiliado
			) AS T ON cta.ctaId = T.ctaId'

		INSERT INTO @tCategoriaAfiliado (ctaAfiliado, ctaEstadoAfiliacion, ctaFechaCambioCategoria, ctaCategoria, shard)
		EXEC sp_execute_remote ReportesReferenceData,
		@sql,
		N'@dFechaFin DATE',
		@dFechaFin = @dFechaFin

		SELECT CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END aportante,
			CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 1 ELSE 2 END tipoDocumento,
			per.perNumeroIdentificacion,
			CASE WHEN ISNULL(prl.perPrimerNombre, '') = '' THEN prl.perRazonSocial
			ELSE (
				prl.perPrimerNombre + 
				CASE WHEN prl.perSegundoNombre IS NULL THEN ' ' ELSE ' '+prl.perSegundoNombre+' ' END +
				prl.perPrimerApellido + 
				CASE WHEN prl.perSegundoApellido IS NULL THEN '' ELSE ' '+prl.perSegundoApellido END
			) END representanteLegal,
			CASE WHEN TRIM(ubi.ubiDireccionFisica) like 'SIN DIRECCI%' THEN 'S.R' ELSE TRIM(ubi.ubiDireccionFisica) END  direccion, -- cambio GLPI 81246
			mun.munNombre municipio, dep.depNombre departamento, mun.munCodigo divipola,
			ubi.ubiTelefonoFijo telefono, ubi.ubiEmail correoElectronico, emp.empPaginaWeb paginaWeb,
			ISNULL(categorias.cantidadA, 0) trabajadoresA,
			ISNULL(categorias.cantidadB, 0) trabajadoresB,
			ISNULL(categorias.cantidadC, 0) trabajadoresC,
			ISNULL(categorias.cantidadTotal, 0) totalTrabajadores, 
			cii.ciiCodigo,
			CASE WHEN ISNULL(pce.perPrimerNombre, '') = '' THEN pce.perRazonSocial
			ELSE (
				pce.perPrimerNombre + 
				CASE WHEN pce.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pce.perSegundoNombre+' ' END +
				pce.perPrimerApellido + 
				CASE WHEN pce.perSegundoApellido IS NULL THEN '' ELSE ' '+pce.perSegundoApellido END
			) END responsableAfiliaciones, 
			uce.ubiTelefonoFijo telefonoFijoResponsable, 
			uce.ubiTelefonoCelular telefonoCelularResponsable, 
			uce.ubiEmail eMailResponsable
		FROM @tEstadoAfiliacionEmpleadorCaja eec
		INNER JOIN dbo.Persona per ON per.perId = eec.eecPersona
		INNER JOIN dbo.Empresa emp ON emp.empPersona = eec.eecPersona
		INNER JOIN dbo.Empleador eml ON eml.empEmpresa = emp.empId
		LEFT JOIN (
			SELECT rce.rceEmpleador, rce.rcePersona, rce.rceUbicacion 
			FROM dbo.RolContactoEmpleador rce
			INNER JOIN (
				SELECT MAX(rce2.rceId) rceId, rce2.rceEmpleador
				FROM dbo.RolContactoEmpleador rce2
				WHERE rce2.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_AFILIACIONES'
				GROUP BY rce2.rceEmpleador
			) AS T ON rce.rceId = T.rceId
		) rce ON rce.rceEmpleador = eml.empId 
		LEFT JOIN dbo.Persona pce ON rce.rcePersona = pce.perId
		LEFT JOIN dbo.Ubicacion uce ON rce.rceUbicacion = uce.ubiId
		LEFT JOIN dbo.CodigoCIIU cii ON emp.empCodigoCIIU = cii.ciiId
		LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		LEFT JOIN dbo.Persona prl ON emp.empRepresentanteLegal = prl.perId
		LEFT JOIN (
			SELECT cat.empId,
				SUM(CASE WHEN cat.ctaCategoria = 'A' THEN 1 ELSE 0 END) cantidadA, 
				SUM(CASE WHEN cat.ctaCategoria = 'B' THEN 1 ELSE 0 END) cantidadB, 
				SUM(CASE WHEN cat.ctaCategoria = 'C' THEN 1 ELSE 0 END) cantidadC,
				COUNT(*) cantidadTotal
				FROM (
					SELECT eml.empId, MAX(cta.ctaCategoria) ctaCategoria, MAX(ctaFechaCambioCategoria) ctaFechaCambioCategoria
					FROM @tCategoriaAfiliado cta
					INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = cta.ctaAfiliado
					INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId
					WHERE cta.ctaEstadoAfiliacion = 'ACTIVO'
					AND cta.ctaFechaCambioCategoria <= @dFechaFin
					GROUP BY eml.empId
				) AS cat
				GROUP BY cat.empId
		) AS categorias ON eml.empId = categorias.empId
	END
END